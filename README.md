# LW-API-TESTER

Server to test api call from server to service. You can test rest api communication between servers (not between a browser and a server).

## Prerequisite
To use this project, you need to install `yarn`, `jdk` and necessary modules on frontend directory.
```
cd frontend
yarn install
```

### Compiles and minifies for production
```
cd frontend
yarn build
```

### Run service
You must compile `frontend` before running server 
```
./gradlew bootRun
```