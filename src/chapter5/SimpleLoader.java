package chapter5;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import org.apache.catalina.Container;
import org.apache.catalina.DefaultContext;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;

public class SimpleLoader implements Loader,Lifecycle{
	public static final String WEB_ROOT=System.getProperty("user.dir")+File.separator+"WebRoot";
	
	private ClassLoader loader=null;
	
	private Container container=null;
	
	public SimpleLoader(){
		try{
			URL[] urls=new URL[1];
			URLStreamHandler streamHandler=null;
			File classPath=new File(WEB_ROOT);
			String repository=(new URL("file",null,classPath.getCanonicalPath()+File.separator)).toString();
			urls[0]=new URL(null,repository,streamHandler);
			loader=new URLClassLoader(urls);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public ClassLoader getClassLoader(){
		return loader;
	}

	@Override
	public Container getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContainer(Container container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DefaultContext getDefaultContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDefaultContext(DefaultContext defaultContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getDelegate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDelegate(boolean delegate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getReloadable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setReloadable(boolean reloadable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRepository(String repository) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] findRepositories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean modified() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
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
