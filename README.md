# Rest Key Value Store

This is a really simple key value store that you can self-host and query with REST.

It needs a Postgresql database as underlying storage system.

This application is build with java using the Quarkus Framework. This means native builds are available. 

## Security

All calls are protected by basic auth.

You can add more users in the table `t_users`. The passwords are encrypted using BCrypt.

### Default user

The default user is:

username: `admin`

password: `admin`

You can change it using the environment variables:
```
USERS_DEFAULT_USERNAME=admin
USERS_DEFAULT_PASSWORD=admin
```

## Swagger UI

A swagger ui is available at http://localhost:8080/q/swagger-ui/


## Running

### Using docker compose

See file [docker-compose.yml](./docker-compose.yml)

```
docker-compose up
```
