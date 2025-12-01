# Stage 1: Build da aplicação com Maven
FROM maven:3.8-openjdk-11 AS builder

WORKDIR /app

# Copia o pom.xml primeiro (para cache de dependências)
COPY pom.xml ./

# Baixa as dependências (esta camada será cacheada se o pom.xml não mudar)
RUN mvn dependency:go-offline -B

# Copia o código fonte
COPY src/ ./src/

# Compila e gera o WAR
RUN mvn clean package -DskipTests -B

# Stage 2: Imagem final com Jetty 9
FROM jetty:9.4-jre11-alpine

# Remove aplicações padrão do Jetty
RUN rm -rf /var/lib/jetty/webapps/*

# Copia o WAR gerado do stage anterior para o webapps do Jetty
# Renomeia para ROOT.war para servir no contexto raiz (/)
COPY --from=builder /app/target/*.war /var/lib/jetty/webapps/ROOT.war

# Expõe a porta 8080
EXPOSE 8080

# O comando padrão da imagem jetty já inicia o servidor
