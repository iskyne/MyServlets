package chapter5;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;
import org.apache.catalina.Wrapper;

public class SimpleWrapperValve implements Valve,Contained{
	
	private Container container=null;

	@Override
	public Container getContainer() {
		// TODO Auto-generated method stub
		return this.container;
	}

	@Override
	public void setContainer(Container container) {
		// TODO Auto-generated method stub
		this.container=container;
		System.out.println("set container");
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
		SimpleWrapper wrapper=(SimpleWrapper)getContainer();
		ServletRequest sreq=request.getRequest();
		ServletResponse sres=response.getResponse();
		Servlet servlet=null;
		HttpServletRequest hsreq=null;
		HttpServletResponse hsres=null;
		if(sreq instanceof HttpServletRequest){
			hsreq=(HttpServletRequest)sreq;
		}
		if(sres instanceof HttpServletResponse){
			hsres=(HttpServletResponse) sres;
		}
		try{
			System.out.println("servelt allocate");
			servlet=wrapper.allocate();
			System.out.println(servlet);
			if(hsreq!=null&&hsres!=null){
				servlet.service(hsreq, hsres);
			}else{
				servlet.service(sreq, sres);
			}
		}catch(ServletException e){
			e.printStackTrace();
		}
	}
}
