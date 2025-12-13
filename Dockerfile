FROM eclipse-temurin:17
ENV TZ=America/Bahia

ENV TZ America/Sao_Paulo
ENV LANG pt_BR.UTF-8
ENV LANGUAGE pt_BR.UTF-8
ENV LC_ALL pt_BR.UTF-8
RUN mkdir -p /opt/app
RUN mkdir -p /opt/app/logs
RUN mkdir -p /opt/app/htmls

ENV PROJECT_HOME /opt/app

COPY /store-0.0.1-SNAPSHOT.jar $PROJECT_HOME/app.jar

WORKDIR $PROJECT_HOME

CMD ["java", "-Xms128M", "-Xmx4024M", "-jar", "-Dspring.profiles.active=prod", "./app.jar"]
