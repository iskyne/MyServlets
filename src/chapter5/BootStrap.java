package chapter5;

import org.apache.catalina.Loader;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;

public class BootStrap {
	public static void main(String args[]){
		
		HttpConnector connector=new HttpConnector();
		Wrapper wrapper=new SimpleWrapper();
		wrapper.setServletClass("PrimitiveServlet");
		Loader loader=new SimpleLoader();
		Valve valve1=new HeaderLoggerValve();
		Valve valve2=new ClientIpLoggerValve();
		wrapper.setLoader(loader);
		((Pipeline) wrapper).addValve(valve1);
		((Pipeline) wrapper).addValve(valve2);
		connector.setContainer(wrapper);
		try{
			connector.initialize();
			connector.start();
			System.in.read();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
