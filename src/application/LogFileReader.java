package application;

import java.io.File;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.apache.commons.io.input.TailerListenerAdapter;

import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;

import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

public class LogFileReader {
	Tailer tailer;
	HTMLEditor textArea;
	public void readFileContent(final HTMLEditor textArea, final File file){
		this.textArea=textArea;
		 TailerListener listener = new Main().new MyListener();
		 tailer = Tailer.create(file, listener, 1000);        
		  
		/*task=new Task <Void>(){

			@Override
			protected Void call() throws Exception {
				FileReader reader=new FileReader(file);
				reader.r
				boolean flag=true;
				while(flag){
					textArea.appendText(new Date()+"\n");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				return null;
				
			}};*/
		
		
	}
	public void startRead(){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread t=new Thread(new Runnable(){

			@Override
			public void run() {
				//tailer.run();
				
			}});
		t.start();
	}
	
}
