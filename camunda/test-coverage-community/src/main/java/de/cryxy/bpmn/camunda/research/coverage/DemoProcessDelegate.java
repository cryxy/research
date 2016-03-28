package de.cryxy.bpmn.camunda.research.coverage;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("demoProcessDelegate")
public class DemoProcessDelegate {
	
	Logger logger = LoggerFactory.getLogger(DemoProcessDelegate.class);
	
	public void onExecuteServiceTask1(DelegateExecution execution) {
		logger.info("Running service task 1");
	}
	
	public void onExecuteServiceTask2(DelegateExecution execution) {
		logger.info("Running service task ");
	}

}
