# state-reporter


Status of Last Deployment:<br>
<img src="https://github.com/mapofzones/state-reporter/workflows/Java%20CI/badge.svg"><br>

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
    * `UNUPDATED_ZONE_SYNC_TIME=<sync_tyme in millis>`
    * `IBC_DATA_SYNC_TYME=<sync_tyme in millis>`
    * `CHAIN_ID_SYNC_TYME=<sync_tyme in millis>`
    * `CHAIN_ID_CHECK_LAST_DAYS=<sync_tyme in millis>`
    * `CHAIN_ID_IGNORE=<chin_ids that do not need to be checked>`
    * `CHAT_ID=<telegram_chat_id for telegram_bot>`
    * `TOKEN=<telegram_bot_token>`
    * `EMULATOR_MODE=<if true-messages in log, if false-message in telegram>`

Running directly:
* `docker build -t state-reporter:v1 .`
* `docker run --env DB_URL=jdbc:postgresql://<ip>:<port>/<db> --env DB_USER=<db_user> --env DB_PASS=<db_user_pass> --env UNUPDATED_ZONE_SYNC_TIME=4h --env IBC_DATA_SYNC_TYME=4h --env CHAIN_ID_SYNC_TYME=4h --env CHAIN_ID_CHECK_LAST_DAYS=7 --env CHAIN_ID_IGNORE=zoneA-1;zoneB-1;zoneC-1 EMULATOR_MODE=true --env SYNC_TYME=<sync_tyme in millis> --env CHAT_ID=<telegram_chat_id for telegram_bot> --env TOKEN=<telegram_bot_token> -it -d --network="host" state-reporter:v1`
