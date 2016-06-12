package chapter5;

import org.apache.catalina.Context;
import org.apache.catalina.Loader;
import org.apache.catalina.Mapper;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;

public class BootStrap2 {
	public static void main(String args[]){
		HttpConnector connector=new HttpConnector();
		Wrapper wrapper=new SimpleWrapper();
		wrapper.setName("Primitive");
		wrapper.setServletClass("PrimitiveServlet");
		Loader loader=new SimpleLoader();
		wrapper.setLoader(loader);
		Context context=new SimpleContext();
		context.addChild(wrapper);
		Valve valve1=new HeaderLoggerValve();
		Valve valve2=new ClientIpLoggerValve();
		((Pipeline)context).addValve(valve1);
		//((Pipeline)context).addValve(valve2);
		Mapper mapper=new SimpleContextMapper();
		mapper.setProtocol("http");
		context.addMapper(mapper);
		
		context.setLoader(loader);
		context.addServletMapping("/Primitive", "Primitive");
		connector.setContainer(context);
		try{
			connector.initialize();
			connector.start();
			System.in.read();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
