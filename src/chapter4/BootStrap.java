package chapter4;

import org.apache.catalina.connector.http.HttpConnector;

public class BootStrap {
	public static void main(String args[]){
		HttpConnector connector=new HttpConnector();
		SimpleContainer container=new SimpleContainer();
		connector.setContainer(container);
		
		try{
			connector.initialize();
			connector.start();
			System.out.println("bootstrea");
			System.in.read();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
