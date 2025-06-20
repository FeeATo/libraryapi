CURSO Spring Boot Expert: JPA, REST, JWT, OAuth2 com Docker e AWS


#cria um container com a imagem do POSTGRES
sudo docker run --name librarydb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=library postgres:16.3

#cria container com imagem do pgadmin
sudo docker run --name pgadmin4 -p 15432:80 -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin dpage/pgadmin4

#roda o container que já foi criado
sudo docker container start pgadmin4

#mostra os containers rodando
sudo docker ps
#mostra todos os containers criados
sudo docker ps -a

#para que os containers se comuniquem, tem que criar uma network!
docker network create  *nome_da_network*

docker run -it --network xquote-network --rm mysql mysql -hxquote -uexample-user -p
