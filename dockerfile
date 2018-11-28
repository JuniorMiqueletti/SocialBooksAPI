FROM jboss/wildfly
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
EXPOSE 8080
COPY /target/social-books-api-1.0.0.war /opt/jboss/wildfly/standalone/deployments/social-books.war
