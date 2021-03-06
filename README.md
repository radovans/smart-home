This is my personal IOT project for controlling my smart home. Hardware that I am currently using:

Lighting - Philips Hue
Room information - ESP32, DHT-22 temperature and humidity sensor

I am using 3rd party API - OpenWeatherMap for informations about weather in my home town.

Features:
- Each request has its own request id which is propagated into log
- Each request is logged with whole request and response
- Each request with logged in user have this user informations in logs - e.g. username, roles
- Each endpoint can be secured just be using annotations @LoggedInPermission or @AdminPermission
- Feature flags - you can enable or disable some parts of application e.g. if you dont have esp module you can disable cron with feature flag - feature.toggles.cron.room.info=false
- You can switch between classic and Json output of the log by feature flag - feature.toggles.logging.output.json=true
- Auto Swagger documentation

How to start application:
- Install postgres database with docker:
docker run -d -p 5432:5432 --name smart-home-postgres --restart always -e POSTGRES_USER=smarthome -e POSTGRES_PASSWORD=smarthome -e POSTGRES_DB=smarthome -v smart_home_postgres_data:/var/lib/postgresql/data postgres
- Then you can connect to docker:
docker exec -it smart-home-postgres bash
- Then connect to database and check if there are created tables:
psql -U smarthome
\dt