package de.cryxy.research.camunda.ssh;

import java.io.IOException;

import org.junit.Before;

import com.jcabi.ssh.SSHD;

public class SshCommandExecutorTest {

	@Before
	public void setup() {
	}

	// @Test
	public void testExecution() throws IOException {
		try (SSHD sshd = new SSHD()) {
			SshCommandExecutor sshCommandExecutor = new SshCommandExecutor(sshd.host(), sshd.port(), sshd.login(),
					sshd.key());
			sshCommandExecutor.execute("echo 'Hello, world!' > /tmp/test.txt");
		}

	}

}
