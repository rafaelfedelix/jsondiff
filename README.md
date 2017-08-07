# JsonDiff
JsonDiff find the difference between two base 64 encoded JSON.

## Prerequisites
- Java 1.8 JDK
- Gradle

## How to run
Run the application through gradle
```sh
gradle run
```

## Usage
#### Store the left json
```sh
POST http://localhost:4567/v1/diff/{id}/left
ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=
```
Response: 201 Created

#### Store the right json
```sh
POST http://localhost:4567/v1/diff/{id}/right
ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=
```
Response: 201 Created

#### Requesting the difference between them
```
GET http://localhost:4567/v1/diff/{id}
```
Json Response:
```
{
    "message": "Differences found",
    "differences": [
        {
            "offset": 24,
            "length": 1
        },
        {
            "offset": 30,
            "length": 3
        }
    ]
}
```

## Running tests

All the tests can be executed with gradle.
```sh
gradle test
```

## Suggestions for improvement
- Postgres hybrid mode can store the json with good performance and easily perform queries
- Use UUID instead of Long