package de.cryxy.bpmn.camunda.research.coverage;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.processEngine;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.complete;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.execute;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.job;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.task;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.extension.process_test_coverage.Coverage;
import org.camunda.bpm.extension.process_test_coverage.Coverages;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageTestRunState;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class DemoProcessTest {

	private static final Logger logger = LoggerFactory.getLogger(DemoProcessTest.class);

	private ProcessInstance processInstance;

	@Rule
	public ProcessEngineRule processEngineRule = new ProcessEngineRule();

	private static Map<String, Coverage> theLastCoverage;
	private static Set<String> relevantDeploymentIds = new HashSet<String>();

	@BeforeClass
	public static void setUp() {
		logger.info("Test setup ...");
		TestCoverageTestRunState.INSTANCE().resetCurrentFlowTrace();
	}

	@Before
	public void createMocks() {
		Mocks.register("demoProcessDelegate", new DemoProcessDelegate());
	}

	@Test
	@Deployment(resources = { "demo-process.bpmn" })
	public void testDeployment() {

	}

	@Test
	@Deployment(resources = { "demo-process.bpmn" })
	public void testPfadA() {
		processInstance = processEngine().getRuntimeService().startProcessInstanceByKey(Finals.PROCESS_KEY);

		assertThat(processInstance).isStarted();

		assertThat(processInstance).isWaitingAt(Finals.USER_TASK_1);
		VariableMap variables = Variables.createVariables();
		variables.put(Finals.VAR_ACTION, 1);
		complete(task(), variables);

		assertThat(processInstance).isWaitingAt(Finals.SERVICE_TASK_1);
		execute(job());

		assertThat(processInstance).isEnded();

	}

	@Test
	@Deployment(resources = { "demo-process.bpmn" })
	public void testPfadB() {
		processInstance = processEngine().getRuntimeService()
				.startProcessInstanceByKey(Finals.PROCESS_KEY);

		assertThat(processInstance).isStarted();

		assertThat(processInstance).isWaitingAt(Finals.USER_TASK_1);
		VariableMap variables = Variables.createVariables();
		variables.put(Finals.VAR_ACTION, 2);
		complete(task(), variables);

		assertThat(processInstance).isWaitingAt(Finals.SERVICE_TASK_2);
		execute(job());

		assertThat(processInstance).isEnded();

	}

	@After
	public void calculateCoverage() {
		if (processInstance != null) { 
			theLastCoverage = Coverages.calculate(processEngine(), processInstance);
			ProcessDefinition processDefinition = processEngine().getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(Finals.PROCESS_KEY).singleResult();
			relevantDeploymentIds.add(processDefinition.getDeploymentId());
			 theLastCoverage = Coverages.calculateForDeploymentIds(processEngine(), relevantDeploymentIds);
			logger.info(theLastCoverage.get(Finals.PROCESS_KEY).toString());
		}
		

	}

	@AfterClass
	public static void tearDown() {
		
		//Coverages.calculateForDeploymentIds(processEngine, relevantDeploymentIds)
		assertEquals(1.0, theLastCoverage.get(Finals.PROCESS_KEY).getActualPercentage(), 0);
	}

}
