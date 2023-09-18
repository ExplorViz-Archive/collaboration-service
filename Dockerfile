FROM registry.access.redhat.com/ubi8/ubi-minimal:8.4

WORKDIR /app

COPY /home/axxer/Desktop/Bachelorarbeit/git_stuff/collaboration-service/build/libs/collaboration-service-1.0-SNAPSHOT.jar /app/app.jar

EXPOSE 4444

CMD ["java", "-jar", "/app/app.jar"]

