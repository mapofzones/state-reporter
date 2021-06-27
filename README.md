# zone-height-checker


Status of Last Deployment:<br>
<img src="https://github.com/mapofzones/zone-height-checker/workflows/Java%20CI/badge.svg"><br>

## Requirements

Running directly:
* java 11
* maven

Running in a container:
* Docker

## Usage

Running directly:
* `mvn package -DskipTests` or `mvn package`
* `java -jar /opt/app.jar --spring.profiles.active=prod`
* `envs:`
    * `DB_URL=jdbc:postgresql://<ip>:<port>/<db>`
    * `DB_USER=<db_user>`
    * `DB_PASS=<db_user_pass>`
    * `SYNC_TYME=<sync_tyme in millis>`
    * `CHAT_ID=<telegram_chat_id for telegram_bot>`
    * `TOKEN=<telegram_bot_token>`

Running directly:
* `docker build -t zone-height-checker:v1 .`
* `docker run --env DB_URL=jdbc:postgresql://<ip>:<port>/<db> --env DB_USER=<db_user> --env DB_PASS=<db_user_pass> --env SYNC_TYME=<sync_tyme in millis> --env CHAT_ID=<telegram_chat_id for telegram_bot> --env TOKEN=<telegram_bot_token> -it -d --network="host" zone-height-checker:v1`
