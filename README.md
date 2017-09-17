# quill-free-example

## setup

```
docker-compose up -d
docker exec -it mariadb mysql -h 127.0.0.1 -u root -e "CREATE DATABASE person"
docker exec -it mariadb mysql -h 127.0.0.1 -u root -e "use person; CREATE TABLE person (id BIGINT UNSIGNED AUTO_INCREMENT, state INT UNSIGNED NOT NULL, PRIMARY KEY (id))"
```

## finisup

```
docker-compose down
```
