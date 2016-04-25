#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("${processName}ProcessDelegate")
public class ${processName}ProcessDelegate {
	
	Logger logger = LoggerFactory.getLogger(${processName}ProcessDelegate.class);
	
	public void onExecuteServiceTask1(DelegateExecution execution) {
		logger.info("Running service task 1");
	}
	
	public void onExecuteServiceTask2(DelegateExecution execution) {
		logger.info("Running service task ");
	}

}
