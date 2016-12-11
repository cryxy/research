package de.cryxy.research.camunda.ssh;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions;
import org.camunda.bpm.engine.test.assertions.ProcessEngineTests;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Rule;
import org.junit.Test;

public class SshCommandExecutionProcessTest {

	@Rule
	public ProcessEngineRule engineRule = new ProcessEngineRule();

	@Test
	@Deployment(resources = { "ssh-template-lib.bpmn" })
	public void testHappyPath() {

		VariableMap variables = Variables.putValue("hostname", "int-services");

		ProcessInstance processInstance = engineRule.getRuntimeService()
				.startProcessInstanceByKey("Process_SshTemplateLib", variables);
		ProcessEngineAssertions.assertThat(processInstance).isWaitingAt("ServiceTask_SshKommandoAusfuehren");
		ProcessEngineTests.execute(ProcessEngineTests.job());

	}

}
