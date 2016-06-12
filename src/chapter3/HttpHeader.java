package chapter3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpHeader {
	private HashMap header=new HashMap();
	
	private List cookies=new ArrayList();
	
	private HashMap parameters=new HashMap();
	
	public HashMap getHeader(){
		return this.header;
	}
	
	public List getCookies(){
		return this.cookies;
	}
	
	public HashMap getParameters(){
		return this.parameters;
	}
}
