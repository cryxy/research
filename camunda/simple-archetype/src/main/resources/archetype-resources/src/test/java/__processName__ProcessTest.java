#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.processEngine;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.complete;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.execute;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.job;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.task;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ${processName}ProcessTest {
	
	
	@Rule
	public ProcessEngineRule processEngineRule = new ProcessEngineRule();
	
	@Before
	public void createMocks() {
		Mocks.register("${processName}ProcessDelegate", new ${processName}ProcessDelegate());
	}
	
	
	@Test
	@Deployment(resources={"${processName}-process.bpmn"})
	public void testDeployment() {
		
	}
	
	@After
	public void calculateCoverage() {
//		TestCoverage coverage = TestCoverageCalculator.get(Finals.PROCESS_KEY).calculate();
//		logger.info("Coverage={}",coverage);
	}

}
