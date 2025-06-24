#os comendos do docker sem maiusculos

#########
# build #
#########
FROM maven:3.8.8-amazoncorretto-21-al2023 as build
#WORKDIR cria a pasta
WORKDIR /build

#esse '. .' significa: o primeiro '.' significa 'onde a dockerFile está agora no sistema' e o segundo ponto é um diretório a partir de WORKDIR /build, ou seja, o próprio /build
COPY . .

RUN mvn clean package -DskipTests

#run

FROM amazoncorretto:21.0.5
WORKDIR /app

# copia o jar do build e coloca num arquivo app.jar da pasta /app
# o --from serve para eu "ir para a pasta daquele stage"
COPY --from=build ./build/target/*.jar ./app.jar

#porta da aplicação
EXPOSE 8080
#porta do actuator (definido por nós, não é padrão)
EXPOSE 9090

#variáveis de ambiente
ENV DATASOURCE_URL=''
ENV DATASOURCE_USERNAME=''
ENV DATASOURCE_PASSWORD=''
ENV GOOGLE_CLIENT_ID='client_id'
ENV GOOGLE_CLIENT_SECRET='client_secret'

ENV SPRING_PROFILES_ACTIVE='production'
ENV TZ = 'America/Sao_Paulo'

#o entrypoint é o comando que vai fazer inicializar a aplicação
ENTRYPOINT java -jar app.jar