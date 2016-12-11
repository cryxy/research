package de.cryxy.research.camunda.ssh;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SshJavaDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		String hostName = (String) execution.getVariable("hostname");
		String userName = (String) execution.getVariable("username");
		String password = (String) execution.getVariable("password");
		String command = (String) execution.getVariable("command");

		SshCommandExecutor sshExecutor = new SshCommandExecutor(hostName, 22, userName, password);
		String stdOut = sshExecutor.execute(command);

		execution.setVariable("stdOut", stdOut);

	}

}
