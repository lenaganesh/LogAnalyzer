package application;

import java.io.File;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.io.input.Tailer;

import application.test.SSHCommandExecutor;
import javafx.scene.web.HTMLEditor;

public class RemoteLogFileReader implements Observer{
	Tailer tailer;
	HTMLEditor textArea;
	public void readFileContent(){
		
		
	}
	public void startRead(RemoteLogFileReader remoteLogFileReader){
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				SSHCommandExecutor sshCommandExecutor=new SSHCommandExecutor();
				sshCommandExecutor.addObserver(remoteLogFileReader);
				sshCommandExecutor.connect();
			}
		});
		t.start();
	}
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Receiver:"+arg+":");
		String temp=((String)arg);
		String[] lines=temp.split("\n");
		if(lines != null){
			for (int i = 0; i < lines.length; i++) {
				String string = lines[i];
				Main.updateString(string);
			}
		}
		//System.out.println(lines.length);
		
	}
	
}
