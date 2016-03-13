package de.cryxy.bpmn.camunda.research.coverage.helpers;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.processEngine;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCoverageCalculator {

	private static final Logger logger = LoggerFactory.getLogger(TestCoverageCalculator.class);
	
	/**
	 * Instanzen nach processDefinitionKey.
	 */
	private static Map<String,TestCoverageCalculator> INSTANCES = new HashMap<>();
	
	private String processDefinitionKey;
	/**
	 *  Activity Ids im Prozessmodell.
	 */
	private Set<String> definedActivityIds;
	/**
	 * Activity Ids die durch den Test abgedeckt werden.
	 */
	private Set<String> coveredActivityIds;
	
	public static TestCoverageCalculator get(String processDefinitionKey) {
		
		TestCoverageCalculator calculator = INSTANCES.get(processDefinitionKey);
			
		if(calculator == null) {
			calculator = new TestCoverageCalculator(processDefinitionKey);
			INSTANCES.put(processDefinitionKey, calculator);
		}
		
		return calculator;
	}
	
	private TestCoverageCalculator(String processDefinitionKey) {

		// Initialisierungen
		this.processDefinitionKey = processDefinitionKey;
		coveredActivityIds = new HashSet<>();
		
		// Modell einlesen
		ProcessDefinition processDefinition = processEngine().getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey).singleResult();
		BpmnModelInstance bpmnModelInstance = processEngine().getRepositoryService()
				.getBpmnModelInstance(processDefinition.getId());

		// Alle Flow Nodes des Modells auslesen und speichern
		Collection<FlowNode> flowNodes = bpmnModelInstance.getModelElementsByType(FlowNode.class);
		definedActivityIds = new HashSet<>();
		
		for (FlowNode node : flowNodes) {
			definedActivityIds.add(node.getId());
		}
	}
	
	public TestCoverageCalculator addProccessInstance(String processInstanceId) {
		
		// Sicherstellen, dass die angegebene processInstance eine Instanz von processDefinition ist!
		HistoricProcessInstance processInstance = processEngine().getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if(!processDefinitionKey.equals(processInstance.getProcessDefinitionKey()))
			throw new RuntimeException("Die angegebene Instanz-Id ist keine Instanz der aktuell betrachteten Prozess-Definition");
		
		// Alle beendeten Aktivitäten des Prozesses mit dem angegebenen Key
		// zurückliefern
		List<HistoricActivityInstance> finishedHistoricActivities = processEngine().getHistoryService()
				.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).finished().list();
		
		for (HistoricActivityInstance historicActivityInstance : finishedHistoricActivities) {
			logger.info("# Finished Historic Activity type={}, name={}", historicActivityInstance.getActivityType(),
					historicActivityInstance.getActivityName());
			String activityId = historicActivityInstance.getActivityId();
			coveredActivityIds.add(activityId);
		}
		
		
		return this;
	}

	public TestCoverage calculate() {

		// Auswertung fahren
//		int passedActivities = acitvityPasses.entrySet().stream().filter((i -> i.getValue() > 0))
//				.collect(Collectors.toMap(i -> i.getKey(), i -> i.getValue())).size();
		
		Set<String> notPassedActivityIds = new HashSet<>();
		notPassedActivityIds.addAll(definedActivityIds);
		notPassedActivityIds.removeAll(coveredActivityIds);
		
		double result =  Math.round((double) coveredActivityIds.size() / (double) definedActivityIds.size()*100);
		TestCoverage testCoverage = new TestCoverage(coveredActivityIds,notPassedActivityIds,result);
		logger.debug("TestCoverage={}",testCoverage);
				
		return testCoverage;

	}

}
