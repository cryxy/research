FROM camunda/camunda-bpm-platform:wildfly-7.10.0
RUN /camunda/bin/add-user.sh admin admin --silent
RUN rm  /camunda/standalone/deployments/camunda-example-invoice-7.10.0.war
ADD target/camunda-multi-instance-test.war /camunda/standalone/deployments/camunda-multi-instance-test.war
EXPOSE 8080
EXPOSE 9990