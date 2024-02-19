# Projet Jeux de Société en Java

Ce projet consiste en la création des jeux du Domino et de Carcassonne en utilisant la programmation orientée objet en Java. L'objectif principal est l'utilisation de la POO pour maximiser le code réutilisable entre les deux jeux.

### Carcassonne Et Domino
- Génération aléatoire de tuiles Carcassonne et Domino
- Placement de tuiles sur un plateau de jeu.
- Calcul automatique des points selon les règles du jeu.
- Possibilité de jouer des parties à plusieurs joueurs.
- Interface graphique et textuelle
- Possibilité de jouer contre ordinateur grâce à une intelligence artificielle

### Réutilisabilité du Code

Une attention particulière a été portée à la conception du code pour permettre une réutilisation maximale entre les deux jeux. Voici quelques-unes des techniques utilisées pour atteindre cet objectif :

- **Abstraction des concepts communs :** Les concepts communs tels que les joueurs, les pièces/tuiles et les mécanismes de jeu ont été abstraits dans des classes génériques, permettant ainsi leur utilisation dans les deux jeux.
  
- **Interfaces pour la modularité :** Des interfaces ont été utilisées pour définir des comportements communs aux deux jeux, ce qui permet d'écrire du code générique pour manipuler ces comportements sans se soucier de l'implémentation spécifique du jeu.
  
- **Héritage pour la spécialisation :** L'héritage a été utilisé de manière judicieuse pour spécialiser les fonctionnalités spécifiques à chaque jeu tout en réutilisant le code commun. Par exemple, les classes spécifiques à chaque jeu héritent des fonctionnalités génériques telles que la gestion des joueurs et des tours de jeu.

- **Gestion modulaire des règles du jeu :** Les règles spécifiques à chaque jeu ont été encapsulées dans des classes distinctes, ce qui permet de changer ou d'étendre les règles sans modifier le code principal des jeux.
