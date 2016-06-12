package chapter3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SocketInputStream{
	private String METHOD=null;
	private String uri=null;
	private String parameters=null;
	private String protocol=null;
	
	private String header=null;
	
	InputStream input=null;
	public SocketInputStream(InputStream input){
		this.input=input;
		parse(input);
		
	}
	
	public void parse(InputStream input){
		String line=null;
		String firstLine=null;
		String header=null;
		int count=0;
		//get the forst line in http request
		BufferedReader reader=new BufferedReader(new InputStreamReader(input));
		try {
			StringBuffer buffer=new StringBuffer();
			while((line=reader.readLine())!=null){
				if((count++)==0){
					firstLine=line;
				}else{
					buffer.append(line);
				}
			}
			header=buffer.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//get the header in http request
		
//		System.out.println(firstLine);
//		System.out.println(header);
		parseFirstLine(firstLine);
		
		this.header=parseHeader(header);
	}
	
	public void parseFirstLine(String firstLine){
		System.out.println("socketInputStream 55 : "+firstLine);
		String firstLineElement[]=null;
		
		firstLineElement=firstLine.split(" ");
		this.METHOD=firstLineElement[0];
		this.uri=firstLineElement[1];
		int question=this.uri.indexOf("?");
		if(question>0){
			this.parameters=this.uri.substring(question+1);
			this.uri=this.uri.substring(0,question);
		}else{
			this.parameters=null;
		}
		this.protocol=firstLineElement[2];
	}
	
	public String parseHeader(String header){
		return header;
	}
	
	public String getMethod(){
		return this.METHOD;
	}
	
	public String getUri(){
		return this.uri;
	}
	
	public String getParameters(){
		return this.parameters;
	}
	
	public String getProtocol(){
		return this.protocol;
	}
	
	public InputStream getInputStream(){
		return this.input;
	}
	
	public String getHeader(){
		return this.header;
	}
}
