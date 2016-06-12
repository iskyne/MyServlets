package chapter8;

import org.apache.catalina.Connector;
import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Mapper;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.logger.FileLogger;

import chapter5.SimpleContext;
import chapter5.SimpleContextMapper;
import chapter5.SimpleLifecycleListener;
import chapter5.SimpleLoader;
import chapter5.SimpleWrapper;

public class BootStrap {
	public static void main(String args[]){
		Connector connector = new HttpConnector();
	    Wrapper wrapper1 = new SimpleWrapper();
	    wrapper1.setName("Primitive");
	    wrapper1.setServletClass("PrimitiveServlet");
	    Wrapper wrapper2 = new SimpleWrapper();
	    wrapper2.setName("Modern");
	    wrapper2.setServletClass("ModernServlet");

	    Context context = new StandardContext();
	    context.setPath("/WebRoot");
	    context.setDocBase("WebRoot");
	    context.addChild(wrapper1);
	    //context.addChild(wrapper2);

	    Mapper mapper = new SimpleContextMapper();
	    mapper.setProtocol("http");
	    LifecycleListener listener = new SimpleLifecycleListener();
	    ((Lifecycle) context).addLifecycleListener(listener);
	    context.addMapper(mapper);
	    Loader loader = new WebappLoader();
	    loader.setContainer(context);
	    wrapper1.setLoader(loader);
	    wrapper2.setLoader(loader);
	    context.setLoader(loader);
	    // context.addServletMapping(pattern, name);
	    context.addServletMapping("/Primitive", "Primitive");
	    //context.addServletMapping("/Modern", "Modern");
	    System.setProperty("catalina.base",System.getProperty("user.dir"));
	    FileLogger log=new FileLogger();
	    log.setPrefix("Filelog_");
	    log.setPrefix(".log");
	    log.setTimestamp(true);
	    log.setDirectory("WEBROOT");
	    context.setLogger(log);
	    connector.setContainer(context);
	    try {
	      connector.initialize();
	      ((Lifecycle) connector).start();
	      ((Lifecycle) context).start();

	      // make the application wait until we press a key.
	      System.in.read();
	      ((Lifecycle) context).stop();
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	
}
