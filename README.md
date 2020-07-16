# Spring Boot Hello World Example – Thymeleaf

Article link : https://www.mkyong.com/spring-boot/spring-boot-hello-world-example-thymeleaf/

## 1. How to start
```
$ git clone [https://github.com/mkyong/spring-boot.git](https://github.com/mkyong/spring-boot.git)
$ cd web-thymeleaf
$ mvn spring-boot:run

```
## projet6-victor.le

### Site communautaire pour les fans de l'escalade

### Il faudra suivre les instructions ci dessous:

## Pré-requis:

- installer un IDE (Eclipse ou Intellij) afin de l'utiliser
- Télécharger le JDK 12.0.2 sur le site (https://www.oracle.com/java/technologies/javase/jdk12-archive-downloads.html) et adapter en fonction du système d'exploitation du PC

- Télécharger et installer MySQL Workbench afin de l'utiliser pour l'application
- Configurer avec ces paramètres ci dessous dans MySQL Workbench
 username : root / hostname: 127.0.0.1 port 3306 / password: n,R7w+Q5c 

## Installation

- Importer le projet via l'IDE (Eclipse ou Intellij) en utilisant le dépôt Git du projet (https://github.com/victorle876/projet6-victor.le.git)

### Base de données:

- Lancer le fichier create-table-escalade.sql pour générer la structure de base de donnée
- Ensuite, lancer la première requete d'insertion de donnée en renseignant l'id, name, username, date de création, date de mise à jour, age, password- Lancer la seconde requete pour les différents profils (admin, user, actuator) 
- Enfin, récupérer l'id lors de l'ajout de l'user et exécuter la 3 requete (id,id_role)

## Deploiement

Sous Intellij, cliquez droit sur le ruban Maven d'abord (Maven -> clique droit 'run build maven') puis install -> clique droit 'run build maven')

localhost:8080



