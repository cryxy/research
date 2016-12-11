package de.cryxy.research.camunda.ssh;

import java.io.IOException;
import java.net.UnknownHostException;

import com.jcabi.ssh.SSHByPassword;
import com.jcabi.ssh.Shell;

public class SshCommandExecutor {
	
	private SSHByPassword sshByPassword;
	
	public SshCommandExecutor(String host, int port, String user, String password) throws UnknownHostException {
		sshByPassword = new SSHByPassword(host,port,user,password);
	}
	
	public String execute(String command) throws IOException {
		String stdout = new Shell.Plain(sshByPassword).exec(command);
		return stdout;
	}

}
