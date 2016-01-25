
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_p2p;
import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.Scanner;
import java.awt.FileDialog;
import java.awt.Frame;
import javax.swing.JFileChooser;

/**
 *
 * @author BiNI
 */
public class Download {
    int server_port = 8000;
	 ServerSocket s_soc;
	 /*******************************************/
	 int port  = 9000;
	 String host = "localhost";
	 static Socket soc,server_soc; 
	 static DataInputStream in, server_in;
	 static DataOutputStream out, server_out;
	 private int count_client;
	 /********************************************/
	 private ObjectInputStream ois = null;
	 private ObjectOutputStream oos = null;
	 private FileInputStream fis = null;
	 private FileOutputStream fos = null;
	 //private File server_file = new File("C:\\Users\\BiNI\\Desktop\\liyu3.jpg");
	 private File client_file;
	 private Frame frame = new Frame();
	 private JFileChooser jfc = null;
    public Download(){
            /******************file to serve ******************/
		jfc = new JFileChooser();
		jfc.showOpenDialog(null);
		String path = jfc.getSelectedFile().getPath();
		File server_file = new File(path);
            /**********************end file to serve**********************************/    
		/******************CLIENT*************************/
		BeAclient beClient = new BeAclient();
		new Thread(beClient).start();
		 
		 /*********************END CLIENT***************************/
		 
		 /***********************SERVER******************************/ 
		 try{
                    s_soc = new ServerSocket(server_port);
                    System.out.println("Peer statrted at " + new Date());
                    System.out.println("SERVER PORT = " + s_soc.getLocalPort());
                    System.out.println("Waiting for other peers to connect...");
                    while(true){
                            server_soc = s_soc.accept();
                            HandleAclient handle = new HandleAclient(server_file);
                            new Thread(handle).start();
                            count_client++;
                            }
                }catch(Exception e){
                        System.out.println("Download.java server creation error");
                        e.printStackTrace();
                }
		
		 
		/***************************END SERVER******************************/
		
	}
	/************************************************************/
	public static void main(String [] args){
		new Download();
		
    }
	/*****************************************************/
	public void beClient(){
		try{
			ois = new ObjectInputStream(soc.getInputStream());
		}catch(IOException e){
			e.printStackTrace();
		}
			try{client_file = (File)ois.readObject();}catch(Exception e){e.printStackTrace();}
			System.out.println("***********Received File Details******************");
			System.out.println("Absoulute path: " + client_file.getAbsolutePath());
			System.out.println("FileName: " + client_file.getName());
			System.out.println("length: " + client_file.length());
			System.out.println("***********End File Details******************");
			FileDialog f_dialog = new FileDialog(frame,"Choose File Destination",FileDialog.SAVE);
			f_dialog.setDirectory(null);
			f_dialog.setFile("Enter file name here");
			f_dialog.setVisible(true);
			int dotLocation = (client_file.getName()).lastIndexOf(".");
			String targetFileName = f_dialog.getDirectory() + f_dialog.getFile() + (client_file.getName()).substring(dotLocation);
			System.out.println("File will be saved to "+ targetFileName);
			try{copyBytes(client_file,targetFileName);}catch(Exception e){e.printStackTrace();}
	}
	/**********************************************************/
	public void copyBytes(File originalFile, String targetFileName) throws IOException{
		fis = new FileInputStream(originalFile);
		fos = new FileOutputStream(targetFileName);
		int c;
		byte [] b = new byte[1024];
		while((c = fis.read(b,0,b.length)) != -1){
			fos.write(b,0,b.length);
		}
		/*while((c = fis.read()) != -1){
			fos.write(c);
		}*/
		fos.close();
		fis.close();
	}
	/***********************************************************/
	
	class HandleAclient implements Runnable{
		File server_file;
		public HandleAclient(File server_file){
			this.server_file = server_file;
		}
		public void run(){
			if(!server_file.exists()){
				System.out.println("File Doesn't Exist. Exiting...");
				System.exit(-1);
			}
			System.out.println("********************FILE TO BE SENT***************************");
			System.out.println("the File to be sent path: " + server_file.getAbsolutePath());
			System.out.println("the File to be sent length: " + server_file.length());
			System.out.println("File Name: " + server_file.getName());
			System.out.println("***********************************************");
			if(server_file.exists()){
				try{
					oos = new ObjectOutputStream(server_soc.getOutputStream());
					oos.writeObject(server_file);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	class BeAclient implements Runnable{
		//Socket soc;
		public BeAclient(){
		}
		public void run(){
			try{
				boolean closed = true;
				System.out.println("Connecting...");
				while(closed){
					try{
						soc = new Socket(host,port);
						if(!soc.isClosed()){
							closed = false;
						}
					}catch(Exception e){
					}
				}
				System.out.println("Connection to server at PORT "+ soc.getPort() + " established");
				beClient();
		    }catch(Exception e){
				 System.out.println(e.getMessage() + "ing...");
				 e.printStackTrace();
		 }
		}
	}
}
