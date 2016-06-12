package chapter5;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

public class ClientIpLoggerValve implements Valve,Contained{
	
	private Container container;
	
	@Override
	public Container getContainer() {
		// TODO Auto-generated method stub
		return this.container;
	}

	@Override
	public void setContainer(Container container) {
		// TODO Auto-generated method stub
		this.container=container;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invoke(Request request, Response response, ValveContext context)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		context.invokeNext(request, response);
		System.out.println("Clinet ip logger");
		ServletRequest sreq=request.getRequest();
		System.out.println(sreq.getRemoteAddr());
	}
	
}
