# Stage 1: Build da aplicação com Maven
FROM maven:3.8-openjdk-11 AS builder

WORKDIR /app

# Copia os arquivos do Maven Wrapper e pom.xml primeiro (para cache de dependências)
COPY .mvn/ .mvn/
COPY mvnw mvnw.cmd pom.xml ./

# Baixa as dependências (esta camada será cacheada se o pom.xml não mudar)
RUN ./mvnw dependency:go-offline

# Copia o código fonte
COPY src/ ./src/

# Compila e gera o WAR
RUN ./mvnw clean package -DskipTests

# Stage 2: Imagem final com Jetty
FROM jetty:11-jre11-alpine

# Remove aplicações padrão do Jetty
RUN rm -rf /var/lib/jetty/webapps/*

# Copia o WAR gerado do stage anterior para o webapps do Jetty
# Renomeia para ROOT.war para servir no contexto raiz (/)
COPY --from=builder /app/target/*.war /var/lib/jetty/webapps/ROOT.war

# Expõe a porta 8080
EXPOSE 8080

# O comando padrão da imagem jetty já inicia o servidor
