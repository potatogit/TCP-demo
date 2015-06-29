import java.util.*;
import java.net.*;
import java.io.*;
public class Server {
	public static void main(String argr[]){
		ServerSocket ssc=null;
		try{
			ssc=new ServerSocket(10000);
			while(true){
				Socket sc=ssc.accept();
				new LogicThread(sc);
			}			
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
		    try{
		    	    ssc.close();
		    }catch(Exception e2){}
		}
	}
}
class LogicThread extends Thread{
	Socket sc=null;
	OutputStream os=null;
	InputStream is=null;
	public LogicThread(Socket s){
		sc=s;
		init();
		start();
	}
	private void init(){
		try{
			os=sc.getOutputStream();
			is=sc.getInputStream();
		}catch(Exception e){}
	}
	public void run(){
		while(true){
			byte data[]=receive();
			if(isQuit(data)) break;
			byte back[]=logic(data);
			send(back);
		}
		close();
	}
	private byte[] receive(){
		byte re[]=new byte[1000];
		try{
			int n=is.read(re);
			byte b[]=new byte[n];
			System.arraycopy(re,0,b,0,n);
			return b;
		}catch(Exception e){}
		return null;
	}
	private boolean isQuit(byte[]b){
		String str=new String(b);
		if(str.equalsIgnoreCase("quit")){
			return true;
		}
		return false;
	}
	private byte [] logic(byte []b){
		byte re[]=new byte[1000];
		if (b==null) {
			String str="No input";
			re=str.getBytes();
			return re;
		}
		String str=new String (b);
		int n=Integer.parseInt(str);
		if(n%2==0) str="Even";
		else str="Odd";
		re=str.getBytes();
		return  re;
	}
	private void send(byte b[]){
		try {
			os.write(b);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	private void close(){
		try {
			is.close();
			os.close();
			sc.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
