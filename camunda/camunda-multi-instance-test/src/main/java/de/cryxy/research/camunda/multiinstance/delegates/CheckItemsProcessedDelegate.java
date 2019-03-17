package de.cryxy.research.camunda.multiinstance.delegates;

import java.util.logging.Logger;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;

@Named
public class CheckItemsProcessedDelegate {

	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

	public boolean areAllItemsProcessed(DelegateExecution execution) {

		long count = execution.getProcessEngineServices().getRuntimeService().createProcessInstanceQuery()
				.processDefinitionKey("Process_ItemProcessing").count();
		
		LOGGER.info(count + "items in process.");

		return count == 0l;

	}

}
