package de.cryxy.research.camunda.multiinstance.delegates;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import de.cryxy.research.camunda.multiinstance.dtos.Item;

@Named
public class GenerateTestDataDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long desiredRuns = (Long) execution.getVariable("desiredRuns");
		
		List<Item> items = new ArrayList<>();
		
		for(int i=0; i< desiredRuns; i++) {
			Item item = new Item();
			item.setData1(UUID.randomUUID().toString());
			item.setData2(UUID.randomUUID().toString());
			item.setData3(UUID.randomUUID().toString());
			item.setData4(UUID.randomUUID().toString());
			items.add(item);
		}
		
		execution.setVariable("items", items);

	}

}
