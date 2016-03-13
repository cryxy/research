package de.cryxy.bpmn.camunda.research.coverage.helpers;

import java.util.Set;

public class TestCoverage {
	
	private Set<String> coveredActivities;
	private Set<String> notCoveredActivities;
	private Double percent;
	
	public TestCoverage(Set<String> coveredActivities, Set<String> notCoveredActivities, Double percent) {
		super();
		this.coveredActivities = coveredActivities;
		this.notCoveredActivities = notCoveredActivities;
		this.percent = percent;
	}
	
	public TestCoverage() {
		
	}

	public Set<String> getCoveredActivities() {
		return coveredActivities;
	}

	public Set<String> getNotCoveredActivities() {
		return notCoveredActivities;
	}

	public Double getPercent() {
		return percent;
	}

	@Override
	public String toString() {
		return "TestCoverage [coveredActivities=" + coveredActivities + ", notCoveredActivities=" + notCoveredActivities
				+ ", percent=" + percent + "]";
	}
	
	
	

}
