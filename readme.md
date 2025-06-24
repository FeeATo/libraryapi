# Criando ambiente de desenvolvimento
No desenvolvimento, utilizei a imagem do postgres e do pgadmin no docker

## Cria um container com a imagem do POSTGRES
``sudo docker run --name librarydb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=library postgres:16.3``

(A primeira porta do 5433:5432 é a porta da máquina host e a porta 5432 é a porta do container)

``sudo docker run --name librarydb-prod -p 5433:5432 -e POSTGRES_PASSWORD=postgresprod -e POSTGRES_USER=postgresprod -e POSTGRES_DB=library postgres:16.3``

## Cria container com imagem do pgadmin
``sudo docker run --name pgadmin4 -p 15432:80 -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin dpage/pgadmin4``

## Roda o container que já foi criado
``sudo docker container start pgadmin4``

## Mostra os containers rodando
``sudo docker ps``
## Mostra todos os containers criados
``sudo docker ps -a``

## Para que os containers se comuniquem, tem que criar uma network!
``docker network create  *nome_da_network*``

# Para conectar os containers na network
``docker network connect library-network librarydb``

``docker network connect library-network pgadmin4``

# Criando ambiente de produção
``sudo docker run --name librarydb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=library postgres:16.3``

## Para conectar os containers na network
``docker network connect library-network librarydb-prod``

## Criei uma cópia do schema do banco librarydb
``docker exec -it librarydb pg_dump -U postgres --schema-only library > schema.sql``

O schema do banco foi gerado na minha pasta /home/miguel e deixei no arquivo sql.txt

## Criando a imagem da aplicação librarydb

``docker build --tag feeato/libraryapi .``

## Criando o container

`` docker run --name libraryapi-production -e DATASOURCE_URL=jdbc:postgressql://librarydb-prod:5432/library -e DATASOURCE_USERNAME=postgresprod -e DATASOURCE_PASSWORD=postgresprod --network library-network -d -p 8080:8080 -p 9090:9090 feeato/libraryapi``
