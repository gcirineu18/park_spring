*** Inicializa o docker
docker compose up

*** Inserções no Banco
- Executar
```
docker exec -it parking_api_oficial-db-1 psql -U postgres -d demo_park
```
- Fazer as inserções no arquivo init-db.sql