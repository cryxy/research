package de.cryxy.research.camunda.multiinstance.delegates;

import java.util.List;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;

import de.cryxy.research.camunda.multiinstance.dtos.Item;

@Named
public class StartItemProcessingDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		@SuppressWarnings("unchecked")
		List<Item> items = (List<Item>) execution.getVariable("items");
		
		for (Item item : items) {
			
			execution.getProcessEngineServices().getRuntimeService()
			.startProcessInstanceByKey("Process_ItemProcessing",execution.getBusinessKey(),Variables.putValue("item",item));
		}
		

	}

}
