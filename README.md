## Running server
```
cd server
./gradlew build
java -jar build/libs/server-0.0.1-SNAPSHOT.jar
```
## Endpoints

API Base URL: `http://localhost:8080`

### Users

| HTTP Verb     | /users | Returns|
| ------------- |:------------|:--------|
| GET           | List all users | `200`|
| POST          | Create a user account | `201` - Location header with /users/{userid}|

| HTTP Verb     | /users/{userid} | Returns|
| ------------- |:------------|:--------|
| GET           | Get information of specific user |

| HTTP Verb     | /users/login | Returns|
| ------------- |:------------|:--------|
| POST          | Login the user - compare the passwords | `200` - if login successful and `406` if isn't. And `404` if user not found|

| HTTP Verb     | /users/{userid}/validatePointsReceived | Returns|
| ------------- |:------------|:--------|
| POST          | Validate the HMAC for a particular tuple | `200` - if it's valid and the actual points of the correspondent userId, `406` if isn't.|


### Trajectories

| HTTP Verb     | /users/{userId}/trajectories | Returns|
| ------------- |:------------|:--------|
| GET           | Get all user trajectories |
| POST          | Create a new trajectory related a user |

| HTTP Verb     | /users/{userId}/trajectories/{trajectoryId} | Returns|
| ------------- |:------------|:--------|
| GET           | Get information of specific station |

### Stations

| HTTP Verb     | /stations | Returns|
| ------------- |:------------|:--------|
| GET           | Get all stations |
| POST          | Create a new station |

| HTTP Verb     | /stations/{stationId} | Returns|
| ------------- |:------------|:--------|
| GET           | Get information of specific station |

| HTTP Verb     | /stations/findByName?name={user_input} | Returns|
| ------------- |:------------|:--------|
| GET           | Search in station by a station name |

| HTTP Verb     | /stations/{stationId}/bookingBike | Returns|
| ------------- |:------------|:--------|
| GET           | Book a bike in a specific station |

| HTTP Verb     | /stations/{stationId}/bikeReturned | Returns|
| ------------- |:------------|:--------|
| GET           | Bike returned in a specific station |
