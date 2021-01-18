
Back end server for an ELO rating api.


## Run Locally

1. start postgres container: `docker-compose up -d`
2. gradle build: `./gradlew build`
3. run application: `./gradlew bootRun`
4. confirm it's working by visiting: http://localhost:8080/

## API Calls

### Get All Players

```
curl --location --request GET '{host}/rating/v1/list/domain/{domain}'
```

### Request a match up of 2 players

```
curl --location --request GET '{host}/rating/v1/match/domain/{domain}'
```

### Report winner of match

```
curl --location --request POST '{host}/rating/v1/match/domain/{domain}/matchId/{matchId}/winningPlayerId/{winningPlayerId}'
```