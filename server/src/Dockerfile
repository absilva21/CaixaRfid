FROM openjdk:17

WORKDIR /app

COPY server.jar /app

COPY dados.db /app

EXPOSE 80

CMD ["java","-jar","/app/server.jar"]
