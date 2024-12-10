# Etapa 1: Build da aplicação
FROM eclipse-temurin:17-jdk as builder

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiar o código da aplicação
COPY target/auth-service-0.0.1-SNAPSHOT.jar app.jar

# Etapa 2: Criação da imagem final
FROM eclipse-temurin:17-jre

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiar o JAR gerado
COPY --from=builder /app/app.jar app.jar

# Definir variáveis de ambiente como opcionais para serem configuradas no runtime
ENV APP_NAME=auth-service \
    CONTEXT_PATH=/auth-service/api \
    CORS_ORIGIN=http://localhost:3000,http://localhost:8080,https://iran.com.br \
    DS_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver \
    DS_URL=jdbc:mysql://mysql:3306/auth_service?useTimezone=true&serverTimezone=UTC \
    DS_USERNAME=root \
    DS_PASSWORD=root \
    EXPIRE_LENGTH=3600000 \
    JPA_DIALECT=org.hibernate.dialect.MySQL57Dialect \
    PROFILES_ACTIVE=dev \
    SECRET_KEY=hc08sjjSnsqVYmtp/gfuQRMeQNXDRo6gkLWT50p8rbQ= \
    SPRINGDOC_PATHS=/auth-service/**,/auth/**,/api/**/v1/**

# Expor a porta 8086
EXPOSE 8086

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
