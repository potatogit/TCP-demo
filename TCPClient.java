import java.util.*;
import java.net.*;
import java.io.*;
public class Main {
	public static Scanner scan=new Scanner(System.in);
	static Socket sc=null;
	static OutputStream os=null;
	static InputStream is=null;
	static String severip="127.0.0.1";
	static int port =10000;
	public static void main(String argc[]){
		init();
		while(true){
			System.out.println("Input a number");
			String str=scan.next();
			if(isQuit(str)){
				send(str.getBytes());
				close();
				break;
			}
			if(checkStr(str)){
				send(str.getBytes());
				byte b[]=receive();
				parse(b);
			}
			else{
				System.out.println("Ilegal input");
			}
		}	
	}
	private static void parse(byte b[]){
		if(b!=null) System.out.println(new String(b));
	}
	private static byte[] receive(){
		byte b[]=new byte[10];
		try {
			int n=is.read(b);
			byte bb[]=new byte[n];
			System.arraycopy(b,0,bb,0,n);
			return  bb;
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
	private static boolean checkStr(String s){
		return true;
	}
	private static void close(){
		try{
			is.close();
		    os.close();
		    sc.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	private static void send(byte[] b){
		try {
			os.write(b);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	private static boolean isQuit(String str){
		return str.equalsIgnoreCase("quit");
	}
	public static void init(){
		try{
			sc=new Socket(severip,port);
			os=sc.getOutputStream();
			is=sc.getInputStream();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
