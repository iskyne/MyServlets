package first;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class HttpServer1 {
	private boolean isShutDown=false;
	
	private static final String SHUTDOWN_COMMAND="/SHUTDOWN";
	
	public static void main(String args[]){
		new HttpServer1().await();
	}
	
	public void await(){
		ServerSocket ss=null;
		Socket sk=null;
		InputStream is=null;
		OutputStream os=null;
		try {
			ss=new ServerSocket();
			ss.setReuseAddress(true);
			ss.bind(new InetSocketAddress("127.0.0.1",8080));
			while(!isShutDown){
				System.out.println("begin");
				sk=ss.accept();
				System.out.println("end");
				is=sk.getInputStream();
				os=sk.getOutputStream();
				Request request=new Request(is);
				request.parse();
				Response response=new Response(os);
				response.setRequest(request);
				System.out.println("uri: "+request.getUri());
				if(request.getUri().startsWith("/servlet/")){
					ServletProcessor1 processor=new ServletProcessor1();
					processor.process(request,response);
				}else{
					StaticResourceProcessor processor=new StaticResourceProcessor();
					processor.process(request,response);
				}
				
				sk.close();
				isShutDown=request.getUri().equals(SHUTDOWN_COMMAND);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				is.close();
				os.close();
				sk.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
