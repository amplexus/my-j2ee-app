TODO

	1...[done] Define datasource (in memory)

	2...[done] WSDL2Java

	3...[done] WSDL as separate artefact

	4...[done] Integration test w/ localhost deployment and server startup

	5...[done] Build javadocs

	6...[done] Build metrics - CheckStyle code complexity + style compliance

	7...[done] Properly implement w/s and EJB methods so I can do genuine testing

	8...[done] Instrumentation / code profiling support in load tests

	9...[done] Build metrics - Test success report

	11..[done] Load test w/ localhost deployment and startup

	11..[done] AOP - metrics

	12..[done] Implement unit tests

	13..[done] JavaDocs

	14..[fail!!!!!!] Weblogic support FIXME! FIXME! FIXME! FIXME! FIXME! FIXME! FIXME! FIXME! FIXME! FIXME! FIXME! FIXME! FIXME! FIXME! FIXME!

	15..[done] Build metrics - Test coverage using Cobertura

	16..[done] AOP - alarming

	17..[done] Fix proliferation of log4j properties file problem

	18..[done] Nagios health checks - maybe just a servlet?

	19..[done] test.hostname and test.port details from configuration

	20..[done] WSDL self documenting contracts

	21..[done] WSDL validation during build of order-contract

	22..Package version bump - is this just a built-in maven release plugin capability?

	23..Profile support: release / integrationtest / loadtest

	24..[????] BigPond Live support

	25..[????] Package installation prompts for datasource password

	26..[????] Datasource password encryption - JBoss 4.x, JBoss 7.x and WebLogic 10.3

	27..[????] Example stub

PROFILE SUPPORT (TBD)

	Default profile [done]
	- Instruments for code coverage
	- Runs unit tests
	- Builds EAR
	- Runs integration tests --> starts server, deploys, runs tests, undeploys, stops server
	- Runs load tests --> with hprof enabled - starts server, deploys, runs tests, undeploys, stops server
	- Builds packages

	Release profile (-Prelease)
	- Instruments for code coverage
	- Runs unit tests
	- Builds EAR
	- Runs integration tests --> starts server, deploys, runs tests, undeploys, stops server
	- Runs load tests --> with hprof enabled - starts server, deploys, runs tests, undeploys, stops server
	- Removes code coverage instrumentation
	- Builds packages --> -Dpackaging=(rpm|pkg|deb|...) -- also bumps minor version(?)

	Integration test (-Pintegrationtest)
	- Runs integration tests against a specific environment --> -Dtest.hostname=tdisdi03app -Dtest.port=1234

	Load test (-Ploadtest)
	- Runs load tests against a specific environment --> -Dtest.hostname=tdisdi03app -Dtest.port=1234

