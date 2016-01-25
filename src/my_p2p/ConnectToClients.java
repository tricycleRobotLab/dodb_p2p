/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_p2p;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author BiNI
 */
public class ConnectToClients implements Runnable {
    private String host;
    private int port;
    private Socket soc;
    private ArrayList list;
    public ConnectToClients(String host,int port,ArrayList list){
        this.host = host;
        this.port = port;
        this.list = list;
    }

    @Override
    public void run() {
        try{
            soc = new Socket(host,port);
            System.out.println("Connection Successfull!");
        }catch(Exception e){
            System.out.println("Error:: " + e.getMessage());
        }
        
    }
    public void doSth(){
        
    }
    
}
