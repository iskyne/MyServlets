package chapter5;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

public class SimplePipeLine implements Pipeline,Lifecycle{
	
	private ArrayList<Valve> valves=new ArrayList<Valve>();
	
	private Valve basic=null;
	
	private Container container=null;
	
	@Override
	public Valve getBasic() {
		// TODO Auto-generated method stub
		return this.basic;
	}

	@Override
	public void setBasic(Valve valve) {
		// TODO Auto-generated method stub
		this.basic=valve;
	}

	@Override
	public void addValve(Valve valve) {
		// TODO Auto-generated method stub
		valves.add(valve);
	}

	@Override
	public Valve[] getValves() {
		// TODO Auto-generated method stub
		return valves.toArray(new Valve[valves.size()]);
	}

	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		// TODO Auto-generated method stub
		new SimplePipelineValveContext().invokeNext(request, response);
	}

	@Override
	public void removeValve(Valve valve) {
		// TODO Auto-generated method stub
		valves.remove(valve);
	}
	protected class SimplePipelineValveContext implements ValveContext{
		int stage=0;

		@Override
		public String getInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void invokeNext(Request request, Response response)
				throws IOException, ServletException {
			// TODO Auto-generated method stub
			int index=stage;
			stage=stage+1;
			
			if(index<valves.size()){
				valves.get(index).invoke(request, response, this);
			}else if(index==valves.size()&&basic!=null){
				basic.invoke(request, response, this);
			}else{
				throw new ServletException();
			}
		}
		
	}
	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LifecycleListener[] findLifecycleListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() throws LifecycleException {
		// TODO Auto-generated method stub
		
	}
}
