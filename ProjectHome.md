Le but de ce projet est de fabriquer un jeux permettenant de jouer à un ou deux joueurs dont le but est de faire bouger des robots qui vont interragir avec leur environement et des robots controlés par l'ordinateur.
Le but pédagogique est de réaliser un programme dont l'architecture logiciel respectent les principes vu en cours et qui intégre l'utilisation d'un moteur de rendu physique.

# Principe du jeux #
Le jeux se compose d'un ensemble de tableaux, lorsqu'un tableau est réussi, on passe au suivant. La réussite du dernier tableau marque la fin du jeu.
Chaque tableau possède deux zones, une zone d'entrée et une zone de sortie représenté par deux cercles, bleu pour l'entrée, vert pour la sortie. Au démarrage d'un tableau, le ou les joueurs seront placés sur les cercles d'entrée, le but pour terminer un tableau est qu'un des joeurs soient sur le cercle de sortie.
Un joueur est éliminé d'un tableau si son robot n'a plus de point de vie, dans ce cas, le robot ne réagi plus aux commandes mais il rest un élements du jeux qui peut être souffler, pousser etc. Le jeux s'arrète si tous les joueurs ont été éliminés.
En plus du cercle d'entrée et de sortie, le tableau de jeu contient un ensemble d'élements comme des barres, des murs en bois, en pierre ou en glace qui vont empécher les joueurs de progresser vers le cercle de sortie.
Par défaut, un joueur ne peut faire que bouger son robot, pour interagir avec l'environement, il faut qu'il récupère des bonus qui vont augmenter ses capacités à interagir avec l'environement.
Voici la liste des bonus:
  * bombe magnétique: cette bombe magnétique si elle est déclenchée projette les élements autour du robot qui l'active comme si les élements étaient soufflés par une bombe dont l'origine serait le centre du robot.
Il existe trois types de bombes magnétiques. Les bombes à bois, les bombes à pierre et les bombes à glace. L'idée est qu'une bombe à pierre souffle avec plus d'intensité les élements qui sont eux-même de type pierre.
  * snap: lorsqu'un snap est activé, le ou les élements les plus proches d'un robot se retrouve collé à lui pour une durée limité. Le robot peut alors bouger les élements avec lui-même. Lorsque la durée de l'effet snap est écoulé, les élements se détache du robot et reste à l'endroit ou ils sont.
Un snap lorqu'il est affiché avant d'être récupérer doit afficher le nombre de secondes correspondant à la durée du snap si le snap est activé.
  * le leurre: un leurre est un bonus qui ressemble visuellement à un robot qui lorsqu'il est activé va après un délais d'une dixaine de seconde, attiré les robots non-joueurs (ceux commandé par l'ordinateur) vers le leurre. Un leurre ne fonctionne que pendant un certain temps, une fois le temps écoulé, le leurre disparait et les robots non joueurs retourne à leur mode de fonctionnement normal.


Les robots non joueurs sont des robots qui fonctionne différemment s'ils ont repéré un robot joueur ou pas. Par défaut, un robot non joueur se déplace dans le tableau et est blockés par les mêmes élements que les robots des joeurs, il doit alors éviter les élements, sont buts est d'essayer de parcourir l'ensemble des zones accessibles pour essayer de trouver un robot joueur.
Un robot non joueur 'trouve' un robot joueur si on peut tracer un segment entre les centres des robots et que celle-ci ne croise pas un ou plusieurs élements. De plus, la distance de ce segment doit être inférieur à un quart de la diagonale du tableau, sinon le joueur est considéré comme étant trop loin pour être visible.
Lorsqu'un robot non joueur voit un robot joueur, il se projète lui-même vers le robot joueur dans le but d'occasioné des dégats au robot joueur si celui-ci n'arrive pas à l'éviter. On considère qu'un robot non-joueur qui occasione des dégats, fait perdre entre un tiers et la moitié des point de vie d'un joueurs avaient à l'origine. Le nombre de point de vie augmente proprotionnelement à la vitesse de l'impact.

Le programme doit être écrit en utilisant deux bibliothèques externes.
  * La bibliothèque Zen, qui est une bibliothèque d'affichage graphique crée uniquement pour le projet. [zen.zip](http://igm.univ-mlv.fr/ens/IR/IR2/2011-2012/Java_Avance/src/zen/zen.zip)
Les sources vous sont données uniquement dans un but pédagogique, votre projet doit utilier le jar zen.jar et non pas les sources.
  * La bibliothèque JBox2D, qui est le moteur de rendu physique, c'est en fait un port d'une librairie C++ en Java. http://jbox2d.org/