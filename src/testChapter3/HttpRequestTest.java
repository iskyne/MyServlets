package testChapter3;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import chapter3.*;

public class HttpRequestTest {
	public void textParseFirstLine(){
		InputStream is=new StringBufferInputStream("GET /myApp/ModernServlet?userName=tarzan&password=pwd HTTP/1.1\n\r"
				+ "Accept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-excel, application/vnd.ms-powerpoint, "
				+ "application/msword, application/x-silverlight, application/x-shockwave-flash\n\r"
				+ "\n\r");
		SocketInputStream sis=new SocketInputStream(is);
		System.out.println(sis.getMethod());
		System.out.println(sis.getUri());
		System.out.println(sis.getParameters());
		System.out.println(sis.getProtocol());
	}
	
	public static void main(String args[]){
		HttpRequestTest test=new HttpRequestTest();
		test.textParseFirstLine();
	}
}
