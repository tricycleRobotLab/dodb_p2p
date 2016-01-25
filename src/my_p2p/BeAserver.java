/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_p2p;

import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
//import static my_p2p.Download.server_soc;
import java.net.*;
import java.nio.file.Files;
/**
 *
 * @author BiNI
 */
public class BeAserver implements Runnable {
    private static final int chunkSize = 512 * 1024;
    private Socket server_soc;
    String server_file;
    String file_p;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
		public BeAserver(String server_file,String file_p,Socket server_soc){
			this.server_file = server_file;
                        this.server_soc = server_soc;
                        this.file_p = file_p;
                        try{
                            oos = new ObjectOutputStream(server_soc.getOutputStream());
                            //ois = new ObjectInputStream(server_soc.getInputStream());
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        
		}
                
                @Override
		public void run(){
				try{	
                                        
                                        split(server_file);
                      
                                        System.out.println("file_p*******************" + file_p);
					//oos.writeObject(server_file);
                                        //oos.writeObject(new File(file_p));
                                        File j = new File(file_p);
                                        //byte[] content = Files.readAllBytes(j.toPath());
                                        //oos.writeObject(content);
                                        oos.writeObject(j);
				}catch(Exception e){
					e.printStackTrace();
				}
			//}
                        
		}
                public static void split(String fileName) throws Exception{
                    BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));
                    //get file length
                    File f = new File(fileName);
                    long fileSize = f.length();
                    //loop for each full chunkSize
                    int subfile;
                    for(subfile = 0; subfile < fileSize/chunkSize;subfile++){
                            //open output fileStream
                            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileName + ".chunk" + subfile));

                            for(int currentByte = 0; currentByte < chunkSize; currentByte++){
                                    out.write(in.read());
                            }
                            out.close();
                    }
                    //loop for the last chunk(which may be smaller than the chunk size
                    if(fileSize != chunkSize *(subfile -1)){
                            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileName + ".chunk" + subfile));
                            int b;
                            while((b = in.read()) != -1)
                                    out.write(b);
                            out.close();
                    }
                    in.close();
	}
}
