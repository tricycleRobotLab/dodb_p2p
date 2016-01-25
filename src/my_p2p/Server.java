/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_p2p;
import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.Map;
import java.util.List;


/**
 *
 * @author BiNI
 */
public class Server {
    private static final int SERVER_PORT = 4700;
    private static final int CHUNK_SIZE = 512; //chunk size is 512kb
    static final String DATABASE_URL = "jdbc:mysql://localhost/p2p_test";
    private static Connection connection = null;
    private Statement stmt = null;
    private ResultSet resultSet = null;
    private Socket soc;
    private ServerSocket s_soc;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private DataInputStream dis;
    private DataOutputStream dos;
    
    private int rows_returned;
   private ArrayList path_map;
    
    public Server(){
        
        try{
            connection = DriverManager.getConnection(DATABASE_URL,"root","");
        }catch(SQLException e){
            //e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
            System.exit(1);
        }
        try{
            s_soc = new ServerSocket(SERVER_PORT);
            System.out.println("Server started at "+ new Date());
            while(true){
                soc = s_soc.accept();
                HandleAclient handle = new HandleAclient();
                new Thread(handle).start();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void Register(String file_name,String ip_address,int port,String hash, int file_size){
        try{ 
            stmt = connection.createStatement();
            String sql = "insert into file_list values('" + file_name + "','" + ip_address + "'," + port + ",'" + hash + "'," + file_size +")";  
            int result = stmt.executeUpdate(sql);
            if(result > 0){
                System.out.println("Registeration Successful");
            }else{
                System.err.println("Error Registering");
            }
            stmt.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
        try{
           stmt = connection.createStatement();
           String chunk = file_name;/*.substring(file_name.lastIndexOf("\\"));*/
           int i;
           for( i = 0; i < file_size / CHUNK_SIZE; i++){
                String chunk_name = chunk + ".chunk" + i;
                String chunk_sql = "insert into chunk_list values('" + chunk_name + "','" + ip_address + "'," + port + ",'" + hash + "')";
                stmt.executeUpdate(chunk_sql);
                System.out.println("Chunk " + i + "is registered");
            } 
           if(file_size % CHUNK_SIZE != 0){
               String chunk_name = chunk + ".chunk" + i;
                String chunk_sql = "insert into chunk_list values('" + chunk_name + "','" + ip_address + "'," + port + ",'" + hash + "')";
                stmt.executeUpdate(chunk_sql);
                System.out.println("Chunk " + i + "is registered");
           }
               
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void list_Record(){
        try{
            stmt = connection.createStatement();
            String sql = "Select * from file_list group by hash order by file_name desc";
            resultSet = stmt.executeQuery(sql);
            
            resultSet.last();
            rows_returned = resultSet.getRow();
            
            resultSet.first();
            
            
            while(resultSet.next()){
                    HashMap map = new HashMap();
                    String file_path = resultSet.getString(1);
                    String ip_address = resultSet.getString(2);
                    int port = resultSet.getInt(3);
                    String hash = resultSet.getString(4);
                    int file_size = resultSet.getInt(5);
                    map.put("row", rows_returned);
                    map.put("file_path", file_path);
                    map.put("ip_address",ip_address);
                    map.put("port", port);
                    map.put("hash",hash);
                    map.put("file_size",file_size);
                   // oos = new ObjectOutputStream(soc.getOutputStream());
                    oos.writeObject(map);
                    //oos.flush();
                    //oos.reset();
                    map.clear();
            }
        }catch(Exception e){
            e.printStackTrace();
        }  
    }
    private void return_file_chunk_path(String client_hash) {
            try{
               
                stmt = connection.createStatement();
                //String sql = "Select chunk_name from chunk_list where hash = \"" + client_hash + "\"";
                String sql = "Select chunk_name, ip_address, port from chunk_list where hash = \"" + client_hash + "\"";
                resultSet = stmt.executeQuery(sql);
                //List chunk_list = new ArrayList();
                List<Map<String,String>> chunk_list = new ArrayList<Map<String,String>>();
                path_map = new ArrayList();
                while(resultSet.next()){
                    Map<String,String> m = new HashMap<String,String>();
                    
                    String chunk_path = resultSet.getString("chunk_name");
                    String chunk_ip_address = resultSet.getString("ip_address");
                    Integer chunk_port = resultSet.getInt("port");
                    //chunk_list.add(chunk_path);
                   // m.put("chunk_path", chunk_path);
                    m.put("chunk_ip",chunk_ip_address);
                    m.put("chunk_port",chunk_port.toString());
                    path_map.add(chunk_path);
                    chunk_list.add(m);
                   
                }
                Map map[] = chunk_list.toArray(new HashMap[chunk_list.size()]);
                oos.writeObject(map);
                oos.writeObject(path_map);
               // oos.reset();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
   
    public String delete_Record(String ip_address){
        String status = null;
        try{
            stmt = connection.createStatement();
            String sql = "Delete from file_list where ip_address = '" + ip_address + "'";
            stmt.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("record Deleted successfully");
        status = "Deleted";
        return status;
    }
    
    
    public static void main(String[] args){
        new Server();
        
    }
    class HandleAclient implements Runnable{
        
        HandleAclient(){
            try{
             oos = new ObjectOutputStream(soc.getOutputStream());
             ois = new ObjectInputStream(soc.getInputStream());
             
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        @Override
        public /*synchronized*/ void run(){
            try{     
                while(true){
                    Map<Object,Object> t = (Map<Object,Object>) ois.readObject();
                    String controller = t.get("controller").toString();
                    if(controller.equals("")){
                        System.err.println("File Empty");
                    }else{
                        if(controller.equalsIgnoreCase("REGISTER")){
                           try{
                                System.out.println("File path: " + t.get("path").toString());
                                System.out.println("File size: " + t.get("size").toString());
                                System.out.println("Ip: " + t.get("ip").toString());
                                System.out.println("Port: " + t.get("port").toString());
                                String path = t.get("path").toString().replace("\\", "\\\\");
                                int size = Integer.parseInt(t.get("size").toString()) / 1024;
                                String ip = t.get("ip").toString();
                                int port = Integer.parseInt(t.get("port").toString());
                                String hash = t.get("hash").toString();
                                Register(path, ip, port,hash, size);
                                t.clear();
                                 System.out.println("**********************************************");
                                 
                           }catch(Exception l){
                               l.printStackTrace();
                           }
                       }else if(controller.equalsIgnoreCase("LIST")){
                           try{
                               list_Record();
                               
                           }catch(Exception eg){
                               eg.printStackTrace();
                           }
                           
                           
                       }else if(controller.equalsIgnoreCase("DELETE")){
                           
                           try{
                                String ip_add = (String)t.get("ip").toString();
                                String status = delete_Record(ip_add);
                                HashMap status_map = new HashMap();
                                status_map.put("status", status);
                                oos.writeObject(status_map);
                                //oos.reset();
                           }catch(Exception k){
                               k.printStackTrace();
                           }
                           
                       }else if(controller.equalsIgnoreCase("IP_PORT")){
                           String client_hash = t.get("download_hash").toString();
                           int ip_count = ip_count(client_hash);
                           //dos = new DataOutputStream(soc.getOutputStream());
                           //dos.writeInt(ip_count);
                           return_file_chunk_path(client_hash); 
                       }
                    }
                    t.clear();
                }
               
            }catch(Exception e){
                System.out.println("sdfsfssssssssssss");
                e.printStackTrace();
            }
        }

        private int ip_count(String client_hash) {
            try{
                stmt = connection.createStatement();
                String sql = "Select ip_address, port from chunk_list WHERE hash = \"" + client_hash + "\" group by port";
                resultSet = stmt.executeQuery(sql);
                resultSet.last();
                rows_returned = resultSet.getRow();
                System.out.println("rows Returned: " + rows_returned);
            }catch(Exception e){
                e.printStackTrace();
            }
            return rows_returned;
        }

       /* private void return_ip_port_list(String client_hash) {
            try{
                stmt = connection.createStatement();
                String sql = "select ip_address,port from chunk_list where hash = \""+ client_hash + "\" group by port";
                resultSet = stmt.executeQuery(sql);
                List<Map<String,String>> list = new ArrayList<Map<String,String>>();
                
                while(resultSet.next()){
                    Map<String,String> map = new HashMap<String,String>();
                    String ip_address = resultSet.getString("ip_address");
                    Integer port = resultSet.getInt("port");
                    map.put("ip",ip_address);
                    map.put("port", port.toString());
                    list.add(map);        
                }
                Map map[] = list.toArray(new HashMap[list.size()]);
                //oos.reset();
                oos.writeObject(map);
               //oos.reset();
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }*/

        
    }
    
}
