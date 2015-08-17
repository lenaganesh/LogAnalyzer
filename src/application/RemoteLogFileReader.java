package application;

import java.util.Observable;
import java.util.Observer;

import org.apache.commons.io.input.Tailer;

import application.test.SSHCommandExecutor;

public class RemoteLogFileReader implements Observer{
	Tailer tailer;
	
	ConnectInfo connectInfo;
	ILogContentUpdate logFileContainer;
	public RemoteLogFileReader(ConnectInfo connectInfo, ILogContentUpdate logFileContainer) {
		this.connectInfo=connectInfo;
		this.logFileContainer=logFileContainer;
	}
	
	public void startRead(final RemoteLogFileReader remoteLogFileReader){
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Connecting Observer..");
				SSHCommandExecutor sshCommandExecutor=new SSHCommandExecutor(connectInfo);
				sshCommandExecutor.addObserver(remoteLogFileReader);
				sshCommandExecutor.connectRemmote();
			}
		});
		t.start();
	}
	@Override
	public void update(Observable o, Object arg) {
		
		String temp=((String)arg);
		String[] lines=temp.split("\n");
		if(lines != null){
			for (int i = 0; i < lines.length; i++) {
				String string = lines[i];
				//Main.updateString(string);
				logFileContainer.updateString(string);
			}
		}

	}
	
}
