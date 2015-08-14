package application;

import java.io.File;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;

import javafx.application.Platform;

public class LogFileReader {
	Tailer tailer;
	File file;
	LogFileContainer LogFileContainer;
	public LogFileReader(LogFileContainer LogFileContainer){
		this.LogFileContainer=LogFileContainer;
	}
	public void readFileContent( final File file){
		      this.file=file; 
		      
	}
	
	public void startTailer(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					 //Thread.sleep(5000);
					 TailerListener listener = new MyListener(LogFileContainer);
					 tailer = Tailer.create(file, listener, 500);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//tailer.run();
			}
		});
		
	}
}
