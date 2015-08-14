package application.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class FileContentProducer {
	File file = new File("C:/LogFile.txt");

	public static void main(String[] args) {
		new FileContentProducer().startFileContentProduce();
	}

	private void startFileContentProduce() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				boolean flag = true;
				FileWriter writer;
				try {
					writer = new FileWriter(file);
					 BufferedWriter bufferWritter = new BufferedWriter(writer);
					while (flag) {
						Date d=new  Date();
						//System.out.println(d);
						bufferWritter.write( d+ "\n");
						bufferWritter.flush();
						
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// TODO Auto-generated method stub

			}
		});
		t.start();

	}

}
