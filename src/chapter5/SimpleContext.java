package chapter5;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;

import javax.naming.directory.DirContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.xml.bind.Marshaller;

import org.apache.catalina.Cluster;
import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Logger;
import org.apache.catalina.Manager;
import org.apache.catalina.Mapper;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Realm;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;
import org.apache.catalina.deploy.ApplicationParameter;
import org.apache.catalina.deploy.ContextEjb;
import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.deploy.ContextLocalEjb;
import org.apache.catalina.deploy.ContextResource;
import org.apache.catalina.deploy.ContextResourceLink;
import org.apache.catalina.deploy.ErrorPage;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.deploy.FilterMap;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.deploy.NamingResources;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.util.CharsetMapper;
import org.apache.catalina.util.LifecycleSupport;


public class SimpleContext implements Context,Pipeline,Lifecycle{
	protected Logger log=null;
	protected LifecycleSupport lifecycle=new LifecycleSupport(this);
	
	protected HashMap children=new HashMap();
	protected Loader loader=null;
	protected SimplePipeLine pipeLine=new SimplePipeLine();
	protected HashMap servletMapping = new HashMap();
	protected Mapper mapper=null;
	protected HashMap mappers=new HashMap();
	private Container parent=null;
	private Valve basicValve=null;
	
	private boolean started=false;
	
	public SimpleContext(){
		basicValve=new SimpleWrapperValve();
		pipeLine.setBasic(basicValve);
	}
	
	public void addServletMapping(String pattern,String name){
		synchronized(servletMapping){
			servletMapping.put(pattern, name);
		}
	}
	
	public Container findChild(String name){
		if(name==null){
			return null;
		}else{
			synchronized(children){
				Container container=(Container)(children.get(name));
				((Contained)basicValve).setContainer(container);
				return container;
			}
		}
	}
	
	public String findServletMapping(String pattern){
		synchronized(servletMapping){
			return (String)servletMapping.get(pattern);
		}
	}
	@Override
	public Valve getBasic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBasic(Valve valve) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addValve(Valve valve) {
		// TODO Auto-generated method stub
		pipeLine.addValve(valve);
	}

	@Override
	public Valve[] getValves() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		// TODO Auto-generated method stub
		pipeLine.invoke(request, response);
	}

	@Override
	public void removeValve(Valve valve) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Loader getLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLoader(Loader loader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return this.log;
	}

	@Override
	public void setLogger(Logger logger) {
		// TODO Auto-generated method stub
		this.log=logger;
	}

	@Override
	public Manager getManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setManager(Manager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cluster getCluster() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCluster(Cluster cluster) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Container getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParent(Container container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClassLoader getParentClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParentClassLoader(ClassLoader parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Realm getRealm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRealm(Realm realm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DirContext getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setResources(DirContext resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addChild(Container child) {
		// TODO Auto-generated method stub
		child.setParent(this);
		((Contained)basicValve).setContainer(child);
		this.children.put(child.getName(), child);
	}

	@Override
	public void addContainerListener(ContainerListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMapper(Mapper mapper) {
		// TODO Auto-generated method stub
		this.mapper=mapper;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Container[] findChildren() {
		// TODO Auto-generated method stub

		Object[] objs=(this.children.values().toArray());
		Container[] children=new Container[objs.length];
		for(int i=0;i<objs.length;i++){
			children[i]=(Container)objs[i];
		}
		return children;
	}

	@Override
	public ContainerListener[] findContainerListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mapper findMapper(String protocol) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mapper[] findMappers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Container map(Request request, boolean update) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeChild(Container child) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeContainerListener(ContainerListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMapper(Mapper mapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] getApplicationListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setApplicationListeners(Object[] listeners) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAvailable(boolean available) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CharsetMapper getCharsetMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCharsetMapper(CharsetMapper mapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getConfigured() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setConfigured(boolean configured) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getCookies() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCookies(boolean cookies) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getCrossContext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCrossContext(boolean crossContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDisplayName(String displayName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getDistributable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDistributable(boolean distributable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDocBase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDocBase(String docBase) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LoginConfig getLoginConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLoginConfig(LoginConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NamingResources getNamingResources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNamingResources(NamingResources namingResources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPath(String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPublicId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPublicId(String publicId) {
		// TODO Auto-generated method stub
		
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
	public boolean getOverride() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setOverride(boolean override) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getPrivileged() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPrivileged(boolean privileged) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSessionTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSessionTimeout(int timeout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getWrapperClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setWrapperClass(String wrapperClass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addApplicationListener(String listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addApplicationParameter(ApplicationParameter parameter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addConstraint(SecurityConstraint constraint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEjb(ContextEjb ejb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEnvironment(ContextEnvironment environment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addErrorPage(ErrorPage errorPage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFilterDef(FilterDef filterDef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFilterMap(FilterMap filterMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addInstanceListener(String listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addLocalEjb(ContextLocalEjb ejb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMimeMapping(String extension, String mimeType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addParameter(String name, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addResource(ContextResource resource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addResourceEnvRef(String name, String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addResourceLink(ContextResourceLink resourceLink) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRoleMapping(String role, String link) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSecurityRole(String role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTaglib(String uri, String location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addWelcomeFile(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addWrapperLifecycle(String listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addWrapperListener(String listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Wrapper createWrapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findApplicationListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationParameter[] findApplicationParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SecurityConstraint[] findConstraints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContextEjb findEjb(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContextEjb[] findEjbs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContextEnvironment findEnvironment(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContextEnvironment[] findEnvironments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ErrorPage findErrorPage(int errorCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ErrorPage findErrorPage(String exceptionType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ErrorPage[] findErrorPages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilterDef findFilterDef(String filterName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilterDef[] findFilterDefs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilterMap[] findFilterMaps() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findInstanceListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContextLocalEjb findLocalEjb(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContextLocalEjb[] findLocalEjbs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findMimeMapping(String extension) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findMimeMappings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findParameter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContextResource findResource(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findResourceEnvRef(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findResourceEnvRefs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContextResourceLink findResourceLink(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContextResourceLink[] findResourceLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContextResource[] findResources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findRoleMapping(String role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean findSecurityRole(String role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] findSecurityRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findServletMappings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findStatusPage(int status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] findStatusPages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findTaglib(String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findTaglibs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean findWelcomeFile(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] findWelcomeFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findWrapperLifecycles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findWrapperListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeApplicationListener(String listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeApplicationParameter(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeConstraint(SecurityConstraint constraint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeEjb(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeEnvironment(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeErrorPage(ErrorPage errorPage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFilterDef(FilterDef filterDef) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFilterMap(FilterMap filterMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeInstanceListener(String listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeLocalEjb(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMimeMapping(String extension) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeParameter(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeResource(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeResourceEnvRef(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeResourceLink(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeRoleMapping(String role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSecurityRole(String role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeServletMapping(String pattern) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTaglib(String uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeWelcomeFile(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeWrapperLifecycle(String listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeWrapperListener(String listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		// TODO Auto-generated method stub
		lifecycle.addLifecycleListener(listener);
	}

	@Override
	public LifecycleListener[] findLifecycleListeners() {
		// TODO Auto-generated method stub
		return lifecycle.findLifecycleListeners();
	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		// TODO Auto-generated method stub
		lifecycle.removeLifecycleListener(listener);
	}

	@Override
	public void start() throws LifecycleException {
		// TODO Auto-generated method stub
		if(started){
			System.out.println("already start");
		}else{
			try{
				this.log.log("has start");
				lifecycle.fireLifecycleEvent(BEFORE_START_EVENT, null);
				started=true;
				if(loader!=null&&(loader instanceof Lifecycle)){
					((Lifecycle)loader).start();
				}
				Container children[]=findChildren();
				for(Container child:children){
					if(child instanceof Container){
						((Lifecycle)child).start();
					}
				}
				
				if(pipeLine instanceof Lifecycle){
					((Lifecycle) pipeLine).start();
				}
				
				lifecycle.fireLifecycleEvent(START_EVENT, null);
			}catch(Exception e){
				e.printStackTrace();
			}
			lifecycle.fireLifecycleEvent(AFTER_START_EVENT, null);
		}
	}

	@Override
	public void stop() throws LifecycleException {
		// TODO Auto-generated method stub
		if(!started){
			System.err.println("not start");
		}else{
			try{
				if(loader instanceof Lifecycle){
					((Lifecycle)loader).start();
				}
				
				started=false;
				lifecycle.fireLifecycleEvent(BEFORE_STOP_EVENT, null);
				
				Container[] children=findChildren();
				for(Container child:children){
					if(child instanceof Lifecycle){
						((Lifecycle) child).start();
					}
				}
				
				if(pipeLine instanceof Lifecycle){
					((Lifecycle) pipeLine).start();
				}
				lifecycle.fireLifecycleEvent(STOP_EVENT, null);
			}catch(Exception e){
				e.printStackTrace();
			}
			lifecycle.fireLifecycleEvent(AFTER_STOP_EVENT, null);
		}
	}

}
