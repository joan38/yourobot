package fr.umlv.yourobot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Sound player that play sound effects.
 *
 * License: GNU Public license v3.
 *
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class SoundPlayer {

    /**
     * Plays the sound name.
     *
     * @param name Sound to play.
     */
    public static void play(String name) {
        SoundPlayer.getPlayer().playSound(name);
    }
    private final HashMap<String, ByteArrayOutputStream> mediaMap = new HashMap<>();
    private final static SoundPlayer PLAYER = new SoundPlayer();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * Get the music player.
     *
     * @return The music player object.
     */
    public static SoundPlayer getPlayer() {
        return PLAYER;
    }

    private SoundPlayer() {
    }

    /**
     * Registers the music URL with the name name.
     *
     * @param name Name of the music.
     * @param url URL of the music.
     */
    public void registerSound(String name, InputStream input) throws IOException {
        Objects.requireNonNull(name);
        Objects.requireNonNull(input);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = input.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        
        mediaMap.put(name, baos);
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[4096];
        int count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * Play the music name.
     *
     * @param name Name of the music registered.
     *
     * @see #registerMusic(java.lang.String, java.net.URL)
     */
    public void playSound(final String name) {
        final ByteArrayOutputStream mediaFile = mediaMap.get(name);
        if (mediaFile == null) {
            System.err.println("Music not registered: " + name);
            return;
        }

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    AudioInputStream in = AudioSystem.getAudioInputStream(new ByteArrayInputStream(mediaFile.toByteArray()));
                    AudioInputStream din = null;
                    AudioFormat baseFormat = in.getFormat();
                    AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                            baseFormat.getSampleRate(),
                            16,
                            baseFormat.getChannels(),
                            baseFormat.getChannels() * 2,
                            baseFormat.getSampleRate(),
                            false);
                    din = AudioSystem.getAudioInputStream(decodedFormat, in);
                    try {
                        // Play now. 
                        rawplay(decodedFormat, din);
                    } catch (LineUnavailableException ex) {
                        System.err.println("???" + ex);
                    }
                    din.close();
                    in.close();
                } catch (UnsupportedAudioFileException ex) {
                    System.err.println(ex);
                } catch (IOException ex) {
                    System.err.println("Cannot read file: " + name + ". " + ex);
                }
            }
        });
    }

    private void rawplay(AudioFormat targetFormat, AudioInputStream din) throws IOException, LineUnavailableException {
        byte[] data = new byte[4096];
        SourceDataLine line = getLine(targetFormat);
        if (line != null) {
            // Start
            line.start();
            int nBytesRead = 0, nBytesWritten = 0;
            while (nBytesRead != -1) {
                nBytesRead = din.read(data, 0, data.length);
                if (nBytesRead != -1) {
                    nBytesWritten = line.write(data, 0, nBytesRead);
                }
            }
            // Stop
            line.drain();
            line.stop();
            line.close();
        }
    }

    private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException {
        SourceDataLine res = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        res = (SourceDataLine) AudioSystem.getLine(info);
        res.open(audioFormat);
        return res;
    }
}
