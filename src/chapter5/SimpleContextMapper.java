package chapter5;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Container;
import org.apache.catalina.HttpRequest;
import org.apache.catalina.Mapper;
import org.apache.catalina.Request;
import org.apache.catalina.Wrapper;
import org.apache.catalina.Context;

public class SimpleContextMapper implements Mapper{
	private Context context = null;
	@Override
	public Container getContainer() {
		// TODO Auto-generated method stub
		return (Container) context;
	}

	@Override
	public void setContainer(Container container) {
		// TODO Auto-generated method stub
		this.context=(Context)container;
	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProtocol(String protocol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Container map(Request request, boolean update) {
		// TODO Auto-generated method stub
		String contextPath=((HttpServletRequest)request.getRequest()).getContextPath();
		String requestUri=((HttpServletRequest)request.getRequest()).getRequestURI();
		String servletName=requestUri.substring(contextPath.length());
		Wrapper wrapper=null;
		
		String pathInfo=null;
		String name=context.findServletMapping(servletName);
		if(name!=null){
			wrapper=(Wrapper) context.findChild(name);
		}
		return wrapper;
	}

}
