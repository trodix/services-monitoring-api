# Monitoring Docker containers / HTTP endpoints

## Features

- Docker containers monitoring
- Spring Boot Actuator endpoint (no auth)
- HTTP requests (no auth)
- Disk Usage

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
diskusage:
  volumes:
    - "/"
    - "/boot"
    - "/tmp"
    - "/var"
    - "/opt"
    - "/home"
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

`curl localhost:9015/api/v1/services/disk-usage | jq`

```json
[
  {
    "partitionPath": "/",
    "totalSizeByte": 61840818176,
    "usedSizeByte": 56037609472
  },
  {
    "partitionPath": "/boot",
    "totalSizeByte": 1020702720,
    "usedSizeByte": 377843712
  },
  {
    "partitionPath": "/tmp",
    "totalSizeByte": 8300969984,
    "usedSizeByte": 24895488
  },
  {
    "partitionPath": "/var",
    "totalSizeByte": 61840818176,
    "usedSizeByte": 56037609472
  },
  {
    "partitionPath": "/opt",
    "totalSizeByte": 61840818176,
    "usedSizeByte": 56037609472
  },
  {
    "partitionPath": "/home",
    "totalSizeByte": 61840818176,
    "usedSizeByte": 56037609472
  }
]
```
