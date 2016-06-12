package chapter5;

import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

public class SimpleLifecycleListener implements LifecycleListener{

	@Override
	public void lifecycleEvent(LifecycleEvent event) {
		// TODO Auto-generated method stub
		System.out.println(event.getType()+"is fired");
	}

}
