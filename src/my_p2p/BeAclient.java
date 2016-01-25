/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_p2p;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.*;
//import static my_p2p.Download.soc;

/**
 *
 * @author BiNI
 */
public class BeAclient implements Runnable {

    private Socket soc;
    private String host;
    private int port;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private File client_file;
    private Frame frame;
    private FileInputStream fis;
    private FileOutputStream fos;
    String file_path;
    static String original;
    String asm_name;

    public BeAclient(String host, int port, String file_path) {
        this.host = host;
        this.port = port;
        this.file_path = file_path;
        //this.client_file = client_file;
        //frame = new Frame();
        original = file_path.substring(0, file_path.lastIndexOf("."));

    }

    @Override
    public void run() {
        try {
            boolean closed = true;
            System.out.println("Connecting...");
            while (closed) {
                try {
                    soc = new Socket(host, port);
                    if (!soc.isClosed()) {
                        closed = false;
                        System.out.println("Connection established");
                    }
                    oos = new ObjectOutputStream(soc.getOutputStream());
                    oos.writeObject(original);
                    //oos.reset();
                    System.out.println("sent file...");
                    //oos.writeObject(file_path); 
                    ObjectOutputStream j = new ObjectOutputStream(soc.getOutputStream());
                    j.writeObject(file_path);
                    ois = new ObjectInputStream(soc.getInputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(e.getMessage());
                }
            }
            System.out.println("Connection to server at PORT " + soc.getPort() + " established");
            System.err.println("original is : " + original);
            asm_name = original.substring(original.lastIndexOf("\\"));
            System.out.printf("File path is :", file_path);
            beClient();
        } catch (Exception e) {
            System.out.println(e.getMessage() + "ing...");
            e.printStackTrace();
        }
    }

    public void beClient() throws IOException, ClassNotFoundException {

        File f = (File) ois.readObject();
        client_file = f;//new File(file_path);
        System.err.println("f is f is: " + f);
       /* System.out.println("***********Received File Details******************");
        System.out.println("Absoulute path: " + client_file.getAbsolutePath());
        System.out.println("FileName: " + client_file.getName());
        System.out.println("length: " + client_file.length());
        System.out.println("***********End File Details******************");*/
        String targetFileName = "C:\\Users\\BiNI\\Desktop\\Download\\" + f.getName();
        System.out.println("File will be saved to " + targetFileName);
        try {
            copyBytes(f, targetFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyBytes(File f, String targetFileName) throws IOException {
        new Thread() {
            @Override
            public void run() {

                int c;
                int p = 0;
                byte[] b = new byte[1024];

                try {
                    fis = new FileInputStream(f);
                    fos = new FileOutputStream(targetFileName);
                    frame = new Frame();

                    ProgressMonitorInputStream pm = new ProgressMonitorInputStream(frame, frame, fis);
                    pm.getProgressMonitor().setMillisToDecideToPopup(0);
                    pm.getProgressMonitor().setMillisToPopup(0);

                    //Thread t = currentThread();
                    Thread.sleep(1000);
                    while ((c = fis.read(b, 0, b.length)) != -1) {
                        fos.write(b, 0, b.length);
                    }
                    pm.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    fis.close();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }.start();

    }

}
