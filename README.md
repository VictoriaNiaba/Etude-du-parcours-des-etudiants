# Étude du parcours des étudiants

> Attention : ce projet est récupéré à partir de repositories Gitlab privées. Le code source peut être partagé mais les informations sensibles ayant été filtrées, le projet n'est donc pas directement exécutable sans ajustements au préalable (pipeline CI/CD, fichiers de données, etc.).

![Screenshot du graphe des parcours](screenshots/graphe.png?raw=true "Graphe des parcours")

D'autres captures d'écran du prototype sont disponibles dans le dossier `/screenshots`.

##  Sommaire


- [Présentation du projet](#présentation-du-projet)
- [Auteurs](#auteurs)
- [Structure](#structure)
- [License](#license)

## Présentation du projet
Cette repository contient le code source d'un prototype d'une application web pour l'université d'Aix-Marseille. L'objectif du projet est d'analyser et traiter l'ensemble des inscriptions administratives sur 7 ans de l'Université pour en extraire des parcours d'étudiants pertinents. L'application est principalement développée à l'aide des Frameworks [Spring](https://spring.io/) (Java) et [Angular](https://angular.io/) (Typescript).

L'application est composée de deux parties : 

- Un **back-end**, côté serveur et dont les services sont utilisables à travers une API REST.
- Un **front-end**, côté client et développé sous forme d'une Single Page Application (SPA). Cette SPA a principalement deux responsabilités :
  - Présenter publiquement les parcours des étudiants (anonymisés).
  - Permettre à des administrateurs de maintenir les données de l'application (équivalent d'un back-office).

> NB: Les deux responsabilités du front-end auraient pu être séparées en deux applications distinctes mais cela n'a pas été retenu afin de faciliter l'exécution et la livraison du prototype. 

## Auteurs

Ce projet est développé par quatre étudiants en Master d'Ingénierie du Logiciel et des Données à Aix-Marseille Université :

Responsables du Back-end :

- [Victoria Niaba](https://github.com/VictoriaNiaba)
- Alexandre Baroni

Responsables du Front-end :

- Loïc Forestier
- Charlotte Cabouat

> NB : Chaque étudiant assume le rôle de développeur et testeur dans ce projet, en ayant bien-sûr la liberté de contribuer dans les deux parties de l'application (back-end / front-end). 

## Structure

La structure du projet suit dans les grandes lignes l'arbre ci-dessous :

```yaml
Etude-du-parcours-des-etudiants
+---backend
|   |   .gitlab-ci.yml # Pipeline Gitlab CI
|   |   mvnw # Wrapper de Maven pour Linux
|   |   mvnw.cmd # Wrapper de Maven pour Windows
|   |   pom.xml # Informations sur le backend et configuration Maven 
|   |
|   \---src
|       +---main # Sources du back-end
|       |   +---java\fr\univamu\epu
|       |   |   	Starter.java # Starter du back-end
|       |   |
|       |   \---resources # Resources 
|       |           application.properties # Configuration du back-end
|       |
|       \---test # Sources des tests du back-end
|
\---frontend # Single Page Application Angular
    |   angular.json # Configuration de la SPA
    |   package.json # Métadonnées sur la SPA et ses dépendances
    |   
    +---node_modules # Librairies externes
    |
    +---e2e # Tests end-to-end
    \---src
        |   index.html # Page principale
        |   styles.scss # Feuille de style principale
        |
        +---app # Contient les composants (et autres éléments) du projet
        |       app-routing.module.ts
        |       app.component.ts
        |       app.module.ts
        |
        +---assets # Images et autres fichiers copiés tels-quels lors du build
        \---environments # Configurations spécifiques à chaque environnement
            |   environment.prod.ts
            \   environment.ts

```

## Lancer l'application

Etant donné que l'architecture du projet n'est pas monolithique, le back-end et le front-end peuvent être lancés séparément. Vous pouvez retrouver les instructions propre à chaque partie de l'application dans leurs README respectifs :

- [Lancer le Back-end](https://github.com/VictoriaNiaba/Etude-du-parcours-des-etudiants/blob/main/backend/README.md)
- [Lancer le Front-end](https://github.com/VictoriaNiaba/Etude-du-parcours-des-etudiants/blob/main/frontend/README.md)

## License

Voir le fichier LICENCE.md pour plus de détails.
