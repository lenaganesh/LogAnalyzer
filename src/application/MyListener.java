package application;

import org.apache.commons.io.input.TailerListenerAdapter;

public class MyListener extends TailerListenerAdapter {
	private LogFileContainer LogFileContainer;
		public  MyListener(LogFileContainer LogFileContainer) {
		this.LogFileContainer =LogFileContainer;
		System.out.println("Construtctor:"+this.LogFileContainer);
		}
		@Override
		public void handle(String line) {
			try {
				System.out.println("Listener:"+this.LogFileContainer);
				LogFileContainer.updateString(line);
			} catch (Exception e) {
			}

		}
	}