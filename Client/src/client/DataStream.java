package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataStream extends Thread {
	private boolean run;
	private DataInputStream dis;
	private Clientlog client;

	public DataStream(Clientlog client,DataInputStream dis){
		run=true;
		this.client=client;
		this.dis=dis;

		this.start();
	}
	public void run(){
		String msg1,msg2;
		while(run){
			try {
				msg1=dis.readUTF();
				msg2=dis.readUTF();
				client.getMSG(msg1,msg2);
			} catch (IOException ex) {
				Logger.getLogger(Clientlog.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		try {
			dis.close();
		} catch (IOException ex) {
			Logger.getLogger(Clientlog.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	public void stopThread(){
		this.run=false;
	}
}

