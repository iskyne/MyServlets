package chapter3;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.servlet.http.Cookie;

public class HttpProcessor {
	
	private SocketInputStream input=null;
	private OutputStream output=null;
	
	private HttpRequest request=null;
	private HttpResponse response=null;
	
	private HttpConnector connector=null;
	
	public HttpProcessor(HttpConnector connector){
		this.connector=connector;
	}
	
	public void process(Socket socket){
		try{
			input=new SocketInputStream(socket.getInputStream());
			output=socket.getOutputStream();
			
			request=new HttpRequest(input);
			response=new HttpResponse(output);
			response.setRequest(request);
			response.setHeader("Servet","Pyrmont Sevlet Container");
			parseRequest(input,output);
			parseHeader(input);
			if(request.getRequestURI().startsWith("/servlet/")){
				ServletProcessor processor=new ServletProcessor();
				processor.process(request,response);
			}else{
				StaticResourceProcessor processor=new StaticResourceProcessor();
				processor.process(request,response);
			}
			socket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void parseRequest(SocketInputStream input,OutputStream output){
		String method=input.getMethod();
		String uri=input.getUri();
		String protocol=input.getProtocol();
		String queryString=input.getParameters();
		
		if(!uri.startsWith("/")){
			int pos=uri.indexOf("://");
			if(pos!=-1){
				pos=uri.indexOf("/", pos+3);
				if(pos==-1){
					uri="";
				}else{
					uri=uri.substring(pos);
				}
			}
		}
		
		
		//parse any requested session Id out of the request Uri
		
		String match=";jssessionid=";
		int semicolon=uri.indexOf(match);
		if(semicolon>=0){
			String rest=uri.substring(semicolon+match.length());
			int semicolon2=rest.indexOf(";");
			if(semicolon2>=0){
				request.setRequestedSessionId(rest.substring(0,semicolon2));
				rest=rest.substring(semicolon2);
			}else{
				request.setRequestedSessionId(rest);
				rest="";
			}
			
			request.setRequestedSessionURL(true);
			uri=uri.substring(0,semicolon)+rest;
		}else{
			request.setRequestedSessionId(null);
			request.setRequestedSessionURL(false);
		}
		
		
		//NormalizeUri((using String operation at the moment)
		
		String normalizedUri=normalize(uri);
		
		this.request.setMethod(method);
		
		this.request.setProtocol(protocol);
		
		this.request.setQueryString(queryString);
		
		if(normalizedUri!=null){
			this.request.setRequestUri(normalizedUri);
		}else{
			this.request.setRequestUri(uri);
		}
		
		
	}
	
	public String normalize(String str){
		return str;
	}
	
	public void parseHeader(SocketInputStream input){
		InputStream is=input.getInputStream();
		String header=input.getHeader();
		String[] keyValues=header.split("\n\r");
		for(String keyValue:keyValues){
			int pos=keyValue.indexOf(":");
			String name=keyValue.substring(0, pos);
			String value=keyValue.substring(pos+1);
			if(name.equals("cookie")){
				this.request.cookies=RequestUtil.parseCookieReader(value);
				for(int i=0;i<this.request.cookies.size();i++){
					Cookie cookie=(Cookie)this.request.cookies.get(i);
					if(cookie.getName().equals("jsessionid")){
						if(!request.isRequestedSessionIdFromCookie()){
							request.setRequestedSessionId(cookie.getValue());
							request.setRequestedSessionURL(false);
							request.setRequestSessionCookie(true);
						}
					}
				}
			}else if(name.equals("content-length")){
				int n=-1;
				try{
					n=Integer.valueOf(value);
				}catch(Exception e){
					e.printStackTrace();
				}
				this.request.setContentLength(n);
			}else if(name.equals("content-type")){
				this.request.setContentType(name);
			}
		}
		
	}
	
	public void parseHeader(InputStream input){
		
	}
}
