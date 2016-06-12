package chapter3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpConnector implements Runnable{
	boolean stopped=false;
	private String schema="Http";
	public String getSchema(){
		return this.schema;
	}
	
	@Override
	public void run(){
		ServerSocket ss=null;
		Socket sk=null;
		int port=8080;
		
		try {
			ss=new ServerSocket();
			ss.setReuseAddress(true);
			ss.bind(new InetSocketAddress("127.0.0.1",port));
			while(!stopped){
				sk=ss.accept();
				HttpProcessor processor=new HttpProcessor(this);
				processor.process(sk);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start(){
		Thread thread=new Thread(this);
		thread.start();
	}
}
