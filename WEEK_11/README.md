# week11-jenkins-ci-cd-demo

This is a COMPLETE Maven-based Java project designed to be run in Eclipse IDE.

## Requirements
- Maven project (`pom.xml` included)
- Compatible with Eclipse
- Java version: 11
- Simple structure: `src/main/java` and `src/test/java`

## Project Details
- **groupId:** com.demo
- **artifactId:** jenkins-demo
- **packaging:** jar

## Running the project in Eclipse
1. Open Eclipse IDE.
2. File -> Import -> Maven -> Existing Maven Projects.
3. Browse to this directory and select the `pom.xml`.
4. Click Finish.
5. To run the app: Right-click `App.java` -> Run As -> Java Application
6. To run tests: Right-click project -> Run As -> Maven test
7. To build: Right-click project -> Run As -> Maven build... -> Goals: `clean install` -> Run.

## JENKINS SETUP GUIDE
To run the included Jenkins pipeline:
1. Run Jenkins using: `java -jar jenkins.war`
2. Open `http://localhost:8080` in your browser.
3. Install plugins: Git, Pipeline, Maven (if not already installed).
4. Create a new "Pipeline" job.
5. In the Pipeline section, choose "Pipeline script from SCM", select "Git", and provide your repository URL.
6. Make sure the "Script Path" is set to `Jenkinsfile`.
7. Build the project with Parameters to trigger the pipeline.
