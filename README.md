# my-j2ee-app
A simple Jave EE web service app demonstrating integration and load testing using cargo and jmeter 

TO BUILD / INSTALL

	mvn clean install
	OR
	mvn -Dmy.config=sdi1 clean install
	OR
	mvn -Dmy.config=fn1 clean install
	OR
	mvn -Dmy.config=prod clean install

TO VERIFY RPM

	rpm -qlp /media/Data/workspace-jee/my-j2ee-app/j2ee-app-packaging/target/rpm/j2ee-app-packaging/RPMS/noarch/j2ee-app-packaging-1.0-1.noarch.rpm

TO RUN INTEGRATION TESTS

	mvn -Dtest.host=somehostname verify

TO RUN LOAD TESTS

	mvn verify # defaults to localhost
	mvn -Dtest.host=somehostname verify

TO SHOW DEPENDENCY TREE

	mvn verify # defaults to localhost
	mvn dependency:tree

TO SHOW PLUGIN DEPENDENCIES

	mvn dependency:resolve-plugins

TO GET PLUGIN HELP

	mvn help:describe -Dplugin=org.apache.maven.plugins:maven-dependency-plugin -Dgoal=unpack -Ddetail

TO SHOW CLASSPATH WHEN RUNNING MAVEN

	mvn integration-test -X
