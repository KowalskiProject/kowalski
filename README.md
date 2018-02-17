The Brazilian government has a set of regulations called *Computing laws* that establishes that companies investing in research and development (R&D) activities in Information and Communication Technology (ICT) may claim exemption or reduction of the Tax on Industrialized Products (IPI) for computing and automation goods.

As part of the obligations to enjoy the benefits of this law, the companies must write and send every year to the regulation office a detailed report about the performed activities during the calendar year in projects that were funded with money arising from the law outputs.

The main target of this project is creating a simple project management tool that allows to manage activities and tasks performed during a project execution. As any project management software, it must allow to assign tasks and activities to users, follow the progress of the projects and handle daily routines associated to them. Moreover it aims to create a simple and intuitive way to gather the project information and generate a first draft of the required report to send to Brazilian government.

Although it could (theoretically) be used as the main project management tool, the main KowalskiProject target is working as a hub for project information related to Brazilian computing law (activities, people assigned to activities, motivation, description, finance reports). There already are lots of good options to manage development teams in R&D environments (Jira, Taiga, PivotalTracker, Trello and others), but these alternatives do not have specific rules and procedures oriented to writing reports and managing information according Brazilian regulations. Ideally KowalskiProject should be used together with other management tools.

## How to run

### Requirements
Kowalski project requires a database running in order to run accordingly. There is a script (Ubuntu based) inside _data_ folder that creates a dummy database inside a docker container to accelerate the development of Kowalski features:

```sh
cd data
./dev_database.sh create
```

This script will verify if docker is installed with the required permissions, fetch and run a mysql:latest container and create a dummy database inside it. After that, **you are responsible for running this container before runnning Kowalski application**.

### Running the application
There is no official release of KowalskiProject yet. Currently, the project is under development (back-end written in Java 8 with Spring Boot, Gradle as package manager), so the instructions to run the code are, essentially, the same ones to run a generic [spring boot application](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html):

```
$ gradlew bootRun
```

The REST API documentation is provided through Swagger - available at *http://localhost:8000/swagger-ui.html*