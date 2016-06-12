package chapter5;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Enumeration;

import javax.naming.directory.DirContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Cluster;
import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Logger;
import org.apache.catalina.Manager;
import org.apache.catalina.Mapper;
import org.apache.catalina.Realm;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

public class HeaderLoggerValve implements Valve,Contained{
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
		System.out.println("http header logger");
		ServletRequest sreq=request.getRequest();
		if(sreq instanceof HttpServletRequest){
			HttpServletRequest hsreq=(HttpServletRequest)sreq;
			Enumeration enums=hsreq.getAttributeNames();
			while(enums.hasMoreElements()){
				String name=enums.nextElement().toString();
				String value=hsreq.getHeader(name);
				System.out.println(name+":"+value);
			}
		}else{
			System.out.println("Not a Http request");
		}
		System.out.println("-----------------------------");
	}

}