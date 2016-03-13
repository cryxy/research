package de.cryxy.bpmn.camunda.research.coverage;

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

import de.cryxy.bpmn.camunda.research.coverage.helpers.TestCoverage;
import de.cryxy.bpmn.camunda.research.coverage.helpers.TestCoverageCalculator;

public class DemoProcessTest {
	
	private static final Logger logger = LoggerFactory.getLogger(DemoProcessTest.class);
	
	@Rule
	public ProcessEngineRule processEngineRule = new ProcessEngineRule();
	
	@Before
	public void createMocks() {
		Mocks.register("demoProcessDelegate", new DemoProcessDelegate());
	}
	
	
	@Test
	@Deployment(resources={"demo-process.bpmn"})
	public void testDeployment() {
		
	}
	
	@Test
	@Deployment(resources={"demo-process.bpmn"})
	public void testPfadA() {
		ProcessInstance processInstance = processEngine().getRuntimeService().startProcessInstanceByKey(Finals.PROCESS_KEY);
		
		assertThat(processInstance).isStarted();
		
		assertThat(processInstance).isWaitingAt(Finals.USER_TASK_1);
		VariableMap variables = Variables.createVariables();
		variables.put(Finals.VAR_ACTION, 1);
		complete(task(),variables);
		
		assertThat(processInstance).isWaitingAt(Finals.SERVICE_TASK_1);
		execute(job());
		
		assertThat(processInstance).isEnded();	
		
		TestCoverageCalculator.get(Finals.PROCESS_KEY).addProccessInstance(processInstance.getId());
		
	}
	
//	@Test
	@Deployment(resources={"demo-process.bpmn"})
	public void testPfadB() {
		ProcessInstance processInstance = processEngine().getRuntimeService().startProcessInstanceByKey(Finals.PROCESS_KEY);
		
		assertThat(processInstance).isStarted();
		
		assertThat(processInstance).isWaitingAt(Finals.USER_TASK_1);
		VariableMap variables = Variables.createVariables();
		variables.put(Finals.VAR_ACTION, 2);
		complete(task(),variables);
		
		assertThat(processInstance).isWaitingAt(Finals.SERVICE_TASK_2);
		execute(job());
		
		assertThat(processInstance).isEnded();		
		TestCoverageCalculator.get(Finals.PROCESS_KEY).addProccessInstance(processInstance.getId());
		
	}
	
	@After
	public void calculateCoverage() {
		TestCoverage coverage = TestCoverageCalculator.get(Finals.PROCESS_KEY).calculate();
		logger.info("Coverage={}",coverage);
	}

}
