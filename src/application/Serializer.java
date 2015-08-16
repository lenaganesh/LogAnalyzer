package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class Serializer {

	public static void main(String args[]) {

		Serializer serializer = new Serializer();

	}

	public void storeConnectionList(List<ConnectInfo> connectInfoList) {
		try {
			FileOutputStream fout = new FileOutputStream("Session");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(connectInfoList);
			oos.close();
			System.out.println("Done");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	 public List<ConnectInfo> readConnectionList(){
		   
		 List<ConnectInfo> list;
		   try{
			    
			   FileInputStream fin = new FileInputStream("Session");
			   ObjectInputStream ois = new ObjectInputStream(fin);
			   list=( List<ConnectInfo>) ois.readObject();
			   ois.close();
			  
			   return list;
			   
		   }catch(Exception ex){
			   ex.printStackTrace();
			   return null;
		   } 
	   } 
}