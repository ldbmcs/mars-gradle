FROM ligaard/jdk17-gradle73
WORKDIR /app
COPY /build/libs/mars-gradle-graphql.jar /app/app.jar

EXPOSE 8080
#ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app/mars-dgs.jar"]
ENTRYPOINT ["java","-jar","/app/app.jar"]
