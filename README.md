# Monitoring Docker containers / HTTP endpoints

## Features

- Docker containers monitoring
- Spring Boot Actuator endpoint
- HTTP requests (without auth)

## Usage

### Config

```yml
adapters:
  docker:
    docker-host: "unix:///var/run/docker.sock"
  springactuator:
    actuator-endpoints:
      - "http://localhost:8010/actuator"
  http:
    http-endpoints:
      - "https://google.com"
```

`curl localhost:9015/api/v1/services/health | jq`

```json
[
  {
    "serviceName": "/elasticsearch",
    "serviceStatus": "UP",
    "comment": "Up 2 hours"
  },
  {
    "serviceName": "http://localhost:8010/actuator",
    "serviceStatus": "UP",
    "comment": ""
  },
  {
    "serviceName": "https://google.com",
    "serviceStatus": "UP",
    "comment": "Status code: 200 OK"
  }
]
```