This is my personal IOT project for controlling my smart home. Hardware that I am currently using:

Lighting - Philips Hue
Room information - ESP32, DHT-22 temperature and humidity sensor

I am using 3rd party API - OpenWeatherMap for informations about weather in my home town.

Features:
- Each request has its own request id which is propagated into log
- Each request is logged with whole request and response
- Feature flags - you can enable or disable some parts of application e.g. if you dont have esp module you can disable cron with feature flag - feature.toggles.cron.room.info=false
- You can switch between classic and Json output of the log by feature flag - feature.toggles.logging.output.json=true