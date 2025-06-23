FROM eclipse-temurin:21-jdk
EXPOSE 8081
ADD LeaveManagement/target/leavemanagement-github-actions.jar leavemanagement-github-actions.jar
ENTRYPOINT ["java","-jar","/leavemanagement-github-actions.jar"]