keycloak

```
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:20.0.2 start-dev
```

## Installation

```
git submodule update --init --recursive
```

## Notes

- Support switching timestamps between secMark and odeReceivedAt
