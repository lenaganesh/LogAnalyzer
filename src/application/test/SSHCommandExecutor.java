package application.test;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import application.ConnectInfo;
 
 
public class SSHCommandExecutor extends Observable{
	 Session session;
	 Channel channel;
	 private ConnectInfo connectInfo;
    public SSHCommandExecutor(ConnectInfo connectInfo) {
		this.connectInfo =connectInfo;
	}
	public SSHCommandExecutor() {
		// TODO Auto-generated constructor stub
	}
	/**
     * @param args
     */
    public static void main(String[] args) {
        new SSHCommandExecutor().testConnect();
    }
    public void connectRemmote(){
    	
        try{
             
            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session=jsch.getSession(connectInfo.getUserName(), connectInfo.getHost(), Integer.parseInt(connectInfo.getPort()));
            session.setPassword(connectInfo.getPassword());
            session.setConfig(config);
            session.connect();
            System.out.println("Connected");
             
            channel =session.openChannel("exec");
     
            executeCommand("tail -f "+connectInfo.getFilePath());
            channel.disconnect();
            session.disconnect();
            System.out.println("DONE");
        }catch(Exception e){
            e.printStackTrace();
        }
 
    }
    public void testConnect(){
    	String host="192.168.1.4";
        String user="ganesh";
        String password="123456";
        String command1="cd /tmp";
        try{
             
            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session=jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            System.out.println("Connected");
             
            channel =session.openChannel("exec");
           // executeCommand("ls -l");
            //executeCommand(command1);
            executeCommand("tail -f /tmp/sample.txt");
            channel.disconnect();
            session.disconnect();
            System.out.println("DONE");
        }catch(Exception e){
            e.printStackTrace();
        }
 
    }
    public void executeCommand(String command1) throws Exception{
    	((ChannelExec)channel).setCommand(command1);
        channel.setInputStream(null);
        ((ChannelExec)channel).setErrStream(System.err);
         
        InputStream in=channel.getInputStream();
        channel.connect();
        byte[] tmp=new byte[1024];
        while(true){
          while(in.available()>0){
            int i=in.read(tmp, 0, 1024);
            if(i<0)break;
            //System.out.print(new String(tmp, 0, i));
            setChanged();
            notifyObservers(new String(tmp, 0, i));
          }
          if(channel.isClosed()){
            System.out.println("exit-status: "+channel.getExitStatus());
            break;
          }
          try{Thread.sleep(1000);}catch(Exception ee){}
        }
    }
 
}