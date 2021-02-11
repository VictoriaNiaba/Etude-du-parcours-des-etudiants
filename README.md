# Back-end du projet EPU

Back-end implémenté à l'aide de [Spring Boot](http://projects.spring.io/spring-boot/), exposant une API REST permettant la gestion et l'analyse des parcours des étudiants de l'université d'Aix-Marseille.

## Pré-requis

Pour développer sur ce projet, vous aurez besoin de :

- [JDK 11](https://www.oracle.com/fr/java/technologies/javase-jdk11-downloads.html)
- [Configurer votre IDE](https://projectlombok.org/setup/eclipse) pour qu'il soit compatible avec la librairie [Lombok](https://projectlombok.org/)

## Lancer l'application

Il y a plusieurs manières d'exécuter votre application sur votre machine locale. La première est d'exécuter la méthode `main` dans la classe `fr.univamu.epu.Starter.java`  depuis votre IDE.

Alternativement, vous pouvez utiliser le [plugin Spring Boot Maven](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) comme suit:

```shell
mvn spring-boot:run
```

## Consulter la documentation de l'API REST

Une fois l'application lancée, vous pouvez consulter la documentation de l'API REST à deux URLs différentes :

- http://localhost:8080/swagger-ui.html, sous forme de documentation interactive.
- http://localhost:8080/v3/api-docs/, sous forme de document json.

## Copyright

Publié sous la licence MIT. Voir le fichier [LICENSE](https://etulab.univ-amu.fr/pfe-epu/epu-backend/-/blob/main/LICENSE.md).