# DnD Mapp Server

---

This is the server application for the [DnD Mapp web app](https://github.com/NoNamer777/dma-front) from which the web
app will get all its data. This server will also be responsible for managing Users data and providing access rights to
the Users data from the web app.

This project is initialized with [Spring Boot v2.5.5](https://start.spring.io/), is build in Java JDK 11.0.4 with Gradle,
and is used in a Docker environment on a Raspberry Pi 4 together with a Mariadb SQL database.

## Dev environment

---

We currently use a MySQL database for development purposes, so be sure to set that up before if you'd like to make use
of the schema-, and data- scripts that are in this repository to prepopulate your database everytime the server is started.
Alternatively, you can change the `application-dev` `datasource`, `database-platform`, and `sql.init` to your needs when
you choose to use a different kind of database. Either way, make sure that the database user and password properties in
the `application-dev.yml` match with your setup.

Clone this repository, and let your IDE import the Gradle project. After that is done, make sure to let Spring Boot
know, that it'll need to activate the `dev` profile when running the application.

- InteliJ
In InteliJ you can alter the run configuration by adding an environment VM option: `-Dspring.profiles.active=dev`.

- Visual Studio Code
In Visual Studio Code, when you have to project opened, you need to add a need run configuration. In the root of the project 
add a new directory if it does not exist already `.vscode/` and create a `launch.json` if that does not exist with the
following contents:

```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "DmaServerApplication",
      "request": "launch",
      "cwd": "${workspaceFolder}",
      "console": "integratedTerminal",
      "mainClass": "org.eu.nl.dndmapp.dmaserver.DmaServerApplication",
      "projectName": "dma-server",
      "vmArgs": "-Dspring.profiles.active=dev"
    }
  ]
}
```

This project uses Lombok for generating getters, setter, and simple constructors so be sure to also have installed the
__Lombok Annotations Support VS Code__ extension.

### Making contributions

When making contributions, please make your changes on a separate branch based on the `master` branch with the following
patterns:
- `feature/<feature-name>`
For full features

- `bug/<name>`
For bugs that need to be fixed

- `scout/<name>`
For small fixes/ or changes that are not a full feature 

After you've made your changes you can commit and push them, and create a pull request against the master branch.
This will make sure the tests are triggered and that the server application can launch properly and run as expected.

## Docker configuration

Available Docker containers you can find on the [Github Container Repository](https://ghcr.io/NoNamer777/dma-server)
for this repository. To use a container use the following command:
```shell
docker run -d \
  -p 8080:8080 \
  --name=dma-server \
  ghcr.io/NoNamer777/dma-server \
  -e "SERVER_VERSION=<0.0.0>" \
  -e "DB_URL=<database_location>" \
  -e "DB_USER=<database_username>" \
  -e "DB_PASSWORD=<database_password>"
```

or by using the following structure inside your `docker-compose.yml` file:
```yaml
version: '2.3'

services:
  sever:
    image: 'ghcr.io/NoNamer777/dma-server'
    container_name: 'dma-server'
    ports:
        - '8080:8080'
    environment:
      - DB_USER=database_user
      - DB_PASSWORD=database_password
      - DB_URL=database_location
    restart: 'unless-stopped'
```

## Available routes

---

Currently, the server provides REST end points to these resources:

- `/api/spell`  
Sending a regular `GET` request, will return a page of 20 Spells. A single Spell can be fetched by appending the Spell's
ID to the URL like: `/api/spell/<id>`. This will return a JSON response that looks like this:

```json
{
  "id": "d20de001-170f-4f57-b735-a4afb8857d18",
  "name": "Acid Arrow",
  "level": 2,
  "magicSchool": "Evocation",
  "ritual": false,
  "components": [
    "Vocal",
    "Somatic",
    "Material"
  ],
  "materials": [
    {
      "id": "7f90e209-e819-4588-9a03-8df5d3987ff5",
      "name": "Powdered rhubarb leaf",
      "consumedBySpell": false,
      "cost": 0.00
    },
    {
      "id": "f4afddfe-b1dd-480a-8f75-563ece60301a",
      "name": "an adder's stomach",
      "consumedBySpell": false,
      "cost": 0.00
    }
  ],
  "range": "90 feet",
  "castingTime": "1 action",
  "duration": "Instantaneous",
  "concentration": false,
  "descriptions": [
    {
      "id": "fe352c67-d873-4bb5-9375-ad3c8429dee9",
      "title": "At Higher Levels.",
      "order": 1,
      "text": "When you cast this spell using a spell slot of 3rd level or higher, the damage (both initial and later) increases by 1d4 for each slot level above 2nd."
    },
    {
      "id": "e97a372a-beaf-40ef-9f3a-2e41d92f7ae2",
      "title": null,
      "order": 0,
      "text": "A shimmering green arrow streaks toward a target within range and bursts in a spray of acid. Make a ranged spell attack against the target. On a hit, the target takes 4d4 acid damage immediately and 2d4 acid damage at the end of its next turn. On a miss, the arrow splashes the target with acid for half as much of the initial damage and no damage at the end of its next turn."
    }
  ]
}
```
