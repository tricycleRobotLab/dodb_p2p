/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_p2p;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFileChooser;
import java.sql.*;
import java.net.*;
import java.util.*;
import java.io.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.*;
import javax.swing.JOptionPane;
import java.security.MessageDigest;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author BiNI
 */
public class ClientJFrame extends javax.swing.JFrame implements ActionListener {

    /**
     * *****************************************************
     */
    private Socket soc;
    private final String lookup_server_host = "localhost";
    private final int lookup_server_port = 4700;
    /**
     * *************************************************
     */
    private int this_asAserver_port;
    private String this_asAserver_ip;
    private String file_path;
    private int file_size;
    /**
     * ***********************************************
     */
    //private String client_file_path;
    private String client_file_path;
    private static int client_file_size;
    private String client_ip_address;
    private int client_port;
    private String this_hash_value;
    private String client_hash_value;
    /**
     * ************************************************
     */
    private DataInputStream lookup_in;
    private DataOutputStream lookup_out;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private FileInputStream fis;
    private FileOutputStream fos;

    private static final String Register_controller = "REGISTER";
    private static final String List_controller = "LIST";
    private static final String Delete_request = "DELETE";
    private ServerSocket s_soc;
    private Socket server_soc;

    private DataInputStream dis;
    private DataOutputStream dos;

    private List rand_chunk;
    private Map rand_map[];
    private ArrayList path_rand_list;
    private ExecutorService executor;

    public ClientJFrame() {

        initComponents();
        jTable1.getSelectionModel().addListSelectionListener(new SelectionListener());
        jButton1.addActionListener(this);
        jButton3.addActionListener(this);
        jButton4.addActionListener(this);
        jButton5.addActionListener(this);
        jButton6.addActionListener(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setBackground(new java.awt.Color(102, 102, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Connect");
        jButton1.setToolTipText("Connect to the Server");

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jTextField1.setToolTipText("");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton2.setText("Browse");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Register");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("File/Folder");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(9, 9, 9)
                .addComponent(jTextField1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addGap(12, 12, 12))
        );

        jButton3.setEnabled(false);

        jButton4.setText("List");

        jButton5.setText("Download");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "File Name", "File Size", "Hash"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setCellSelectionEnabled(false);
        jScrollPane1.setViewportView(jTable1);

        jButton6.setText("Disconnect");
        jButton6.setToolTipText("disconnect from the server");

        jMenu1.setText("Refresh");
        jMenu1.setToolTipText("");
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(33, 33, 33)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(this);
        try {

            jTextField1.setText(jfc.getSelectedFile().getPath());
            if (!"".equals(jTextField1.getText())) {
                jButton3.setEnabled(true);
            }
            file_path = jfc.getSelectedFile().getPath();
            file_size = (int) jfc.getSelectedFile().length();

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

    }//GEN-LAST:event_jButton3ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jButton2) {
            JFileChooser jfc = new JFileChooser();
            jfc.showOpenDialog(jPanel1);
        } else if (e.getSource() == jButton3) {//Register Button
            try {
                this_hash_value = getMD5Checksum(file_path);
                HashMap map = new HashMap();
                map.put("controller", Register_controller);
                map.put("path", file_path);
                map.put("size", file_size);
                map.put("ip", this_asAserver_ip);
                map.put("port", this_asAserver_port);
                map.put("hash", this_hash_value);
                oos.writeObject(map);
                oos.flush();
                //oos.reset();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            /**
             * ****************************SERVER****************************
             */
            /* new Thread(){
             @Override
             public void run(){
             try{
             s_soc = new ServerSocket(this_asAserver_port);
             System.out.println("Peer statrted at " + new java.util.Date());
             System.out.println("SERVER PORT = " + s_soc.getLocalPort());
             System.out.println("Waiting for other peers to connect...");
             int count_client = 0;
             while(true){
             server_soc = s_soc.accept();
             BeAserver handle = new BeAserver(/*new File(file_path)*//*file_path,server_soc);
             new Thread(handle).start();
             count_client++;
             System.out.println("Client " + count_client + "Connected");
             }
             }catch(Exception ex){
             JOptionPane.showMessageDialog(null,ex.getMessage());
             // ex.printStackTrace();

             }
             }
             }.start();*/

            /**
             * ***************************END SERVER*************************
             */
        } else if (e.getSource() == jButton4) {//List Button
            Thread t = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        HashMap map = new HashMap();
                        map.put("controller", List_controller);
                        oos.writeObject(map);
                        oos.flush();
                        oos.reset();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, ex.getMessage());

                    }
                    try {
                        String header[] = new String[]{
                            "File Name", "File Size", "Hash"
                        };
                        int count = 0;
                        Object[][] o = {{null, null, null}};
                        DefaultTableModel model = new DefaultTableModel(o, header);
                        jTable1.setModel(model);
                        jTable1.setRowSelectionAllowed(true);
                        boolean allowed = jTable1.getRowSelectionAllowed();

                        System.err.println("Row Selection Allowed? " + allowed);
                        do {
                            HashMap input = (HashMap) ois.readObject();
                            rows_returned = Integer.parseInt(input.get("row").toString());
                            file_name = input.get("file_path").toString();
                            ip_address = input.get("ip_address").toString();
                            port = Integer.parseInt(input.get("port").toString());
                            client_hash_value = input.get("hash").toString();
                            file_size1 = Integer.parseInt(input.get("file_size").toString());
                            System.out.println("&&&&&&&&&&&&&&&&&&File List&&&&&&&&&&&&&&&&&&&&&&&");
                            System.out.println("file name: " + file_name);

                            System.out.println("file size: " + file_size1);
                            System.out.println("Ip: " + ip_address);
                            System.out.println("Port: " + port);
                            System.out.println("Hash: " + client_hash_value);
                            System.out.println("**************************************************");
                            Object[] o1 = {
                                // file_name.substring(file_name.lastIndexOf("\\") + 1), file_size1, client_hash_value
                                file_name, file_size1, client_hash_value
                            };

                            model.addRow(o1);
                            jTable1.setRowSelectionAllowed(true);
                            input.clear();
                            count++;
                            System.err.println("Rows returned " + rows_returned);
                            System.err.println("Count " + count);

                        } while (count != rows_returned - 1);
                        System.err.println("Out of loop ");

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            });
            t.start();
        } else if (e.getSource() == jButton5) {//download button 

            Thread t = new Thread(new Runnable() {
                private ArrayList l;
                Map<String, String> map[];

                //private ExecutorService executor;
                @Override
                public void run() {
                    System.out.println("Download start-----1");
                    HashMap ip_port = new HashMap();
                    ip_port.put("download_hash", client_hash_value);
                    ip_port.put("controller", "ip_port");
                    int no_of_chunks = client_file_size / 512 + 1; //get the number of chunks
                    Random rand = new Random();

                    try {
                        oos.writeObject(ip_port);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }//sends ip_port request to get combo of ip and port containing the hash value

                    System.out.println(client_hash_value.substring(0, 5));

                    List chunk_list;

                    System.out.println("Download start-----2");

                    try {
                        //Map<String,String> map[] = (Map<String,String>[])ois.readObject();
                        map = (Map<String, String>[]) ois.readObject();
                        //ArrayList l = (ArrayList)ois.readObject();
                        l = (ArrayList) ois.readObject();
                        System.out.println("Chunk_name \t\t\t ip_address \t\t port\t\t\t\twith arraylist");
                        for (int i = 0; i < map.length; i++) {
                            System.out.println(l.get(i) + "\t\t\t" + map[i].get("chunk_ip") + "\t\t" + map[i].get("chunk_port"));
                        }
                        System.out.println("shids;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");

                        List<Map<String, String>> rand_list = new ArrayList();
                        path_rand_list = new ArrayList();
                        System.out.println("Fiiiiiiiiiiixxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;;;;;;;;;;;;;;;;;;;;;;;;;");
                        System.out.println("no of chunks:::: " + no_of_chunks);
                        System.out.println("map length:::::: " + map.length);
                        for (int i = 0; i < no_of_chunks + 1; i++) {
                            int rand_map_list = 0 + rand.nextInt(map.length);
                            rand_list.add(map[rand_map_list]);
                        }
                        for (int i = 0; i < no_of_chunks + 1; i++) {
                            int rand_map_list = 0 + rand.nextInt(l.size());
                            String k = l.get(rand_map_list).toString().substring(l.get(rand_map_list).toString().lastIndexOf("."));
                            if (path_rand_list.contains(k)) {
                                i--;
                            } else {
                                path_rand_list.add(l.get(rand_map_list));
                            }
                        }
                        System.out.println("***************************random map list*******************************");
                        rand_map = rand_list.toArray(new HashMap[rand_list.size()]);
                        System.out.println("Chunk_name \t\t\t ip_address \t\t port");
                        for (int i = 0; i < rand_map.length; i++) {
                            System.out.println(path_rand_list.get(i) + "\t\t\t" + rand_map[i].get("chunk_ip") + "\t\t" + rand_map[i].get("chunk_port"));
                        }
                    } catch (Exception es) {
                        es.printStackTrace();
                    }
                    /* for(int i = 0; i < no_of_chunks + 1 ;i++){
                     int rand_chunk_map = 0 + rand.nextInt(map.length); 
                     String k = l.get(rand_chunk_map).toString().substring(l.get(rand_chunk_map).toString().lastIndexOf("."));
                     if((rand_list.contains(map[rand_chunk_map]))&&(path_rand_list.toString().contains(k))){
                     i--;
                     }else{
                     rand_list.add(map[rand_chunk_map]); 
                     path_rand_list.add(l.get(rand_chunk_map));
                     }

                     }
                     System.out.println("***************************random map list*******************************");
                     rand_map = rand_list.toArray(new HashMap[rand_list.size()]);
                     System.out.println("Chunk_name \t\t\t ip_address \t\t port");
                     for(int i = 0; i < rand_map.length; i++){
                     System.out.println(path_rand_list.get(i) + "\t\t\t" + rand_map[i].get("chunk_ip") + "\t\t" + rand_map[i].get("chunk_port"));
                     }
                     }catch(IOException | ClassNotFoundException et){
                     et.printStackTrace();
                     }*/

                    System.out.println("Download start-----3");
                    try {
                        System.out.println("within try");
                        /*try{
                         dos.writeChars(client_file_path);
                         }catch(Exception et){
                         et.printStackTrace();
                         }*/

                        final JP it = new JP(no_of_chunks - 1);
                        JProgressBar j = new JProgressBar();
                        //j.setMaximum(100);
                        //j.setMinimum(0);
                        JFrame frame = new JFrame("Download Progress...");
                        //frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                        frame.setContentPane(it);
                        frame.pack();
                        frame.setLocation(600, 300);
                        frame.setVisible(true);

                        frame.add(j);
                        j.setVisible(true);

                        executor = Executors.newFixedThreadPool(no_of_chunks);

                        for (int i = 0; i < no_of_chunks + 1; i++) {
                            //String path = path_rand_list.get(i).toString();
                   /* file_path = path_rand_list.get(i).toString();
                             String ip = rand_map[i].get("chunk_ip").toString();
                             int portt = Integer.parseInt(rand_map[i].get("chunk_port").toString());*/
                            file_path = l.get(i).toString();
                            String ip = map[i].get("chunk_ip");
                            int portt = Integer.parseInt(map[i].get("chunk_port"));
                            System.out.println("Cheeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeccccccccck");
                            System.out.println("Ip to be connected with " + ip);
                            System.out.println("portt...." + portt);
                            System.out.println("path...." + file_path);
                            System.out.println("EnnnnnnnnnnnnnnndCChhhhhhhhhhhhhhheeeeeeeeeecccccccccccck");
                            //oos.writeObject(file_path);
                            executor.submit(new BeAclient(ip, portt, file_path));
                            final int percent = i;
                            try {
                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        it.updateBar(percent);
                                    }
                                });
                                Thread.sleep(1000);
                            } catch (Exception es) {
                                es.printStackTrace();
                            }
                            /*if(i == no_of_chunks + 1){
                             break;
                             }  */
                        }
                        //j.setVisible(false);
                        //frame.remove(0);

                        executor.shutdown();

                    } catch (Exception et) {
                        et.printStackTrace();
                    }

                    Thread tt = new Thread(new Runnable() {

                        public void run() {
                            //String j = file_path.substring(file_path.lastIndexOf("//"));
                            System.err.println("jjjjjjjjjjjjjjjjjjjjj" + file_path);
                            File ft = new File(file_path);
                            String j = ft.getName();
                            String t = j.substring(0, j.lastIndexOf("."));
                            System.err.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeee" + t);
                            System.err.println("ssssssssssssssssssssssj" + file_path);

                            String d_path = "C:\\Users\\BiNI\\Desktop\\Download\\" + t;
                            System.err.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkwwwww" + d_path);
                            try {
                                join(d_path);
                            } catch (Exception et) {
                                et.printStackTrace();
                            }
                        }
                    });
                    try {
                        Thread.sleep(3333);
                    } catch (Exception et) {
                        et.printStackTrace();
                    }
                    tt.start();

                    Thread tt2 = new Thread(new Runnable(){

                        @Override
                        public void run() {
                            File ft = new File(file_path);
                            String j = ft.getName();
                            String t = j.substring(0, j.lastIndexOf("."));
                            String d_path = "C:\\Users\\BiNI\\Desktop\\Download\\" + t;
                            try {
                                d(d_path);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                       
                    });
                    try{Thread.sleep(5000);}catch(Exception ex){ex.printStackTrace();}
                    tt2.start();

                }

            });
            t.start();

        } else if (e.getSource() == jButton6) {//Disconnect Button
            try {
                HashMap map = new HashMap();
                map.put("controller", Delete_request);
                map.put("ip", ip_address);
                oos.writeObject(map);
                soc.close();
                JOptionPane.showMessageDialog(null, "Disconnected from the server");
            } catch (Exception ef) {
                ef.printStackTrace();
                JOptionPane.showMessageDialog(null, ef.getMessage());
            }

        } else if (e.getSource() == jButton1) {//Connect Button

            try {
                soc = new Socket(lookup_server_host, lookup_server_port);
                JOptionPane.showMessageDialog(null, "Connected to the Lookup Server");
                System.out.println("Connected to the server");
            } catch (Exception ej) {
                ej.printStackTrace();
                JOptionPane.showMessageDialog(null, ej.getMessage());
            }
            try {
                oos = new ObjectOutputStream(soc.getOutputStream());
                ois = new ObjectInputStream(soc.getInputStream());
                dis = new DataInputStream(soc.getInputStream());
                dos = new DataOutputStream(soc.getOutputStream());
            } catch (Exception ej) {
                ej.printStackTrace();
                JOptionPane.showMessageDialog(null, ej.getMessage());
            }
            Random rand = new Random();
            try {
                if (!new File("port_config.txt").exists() && true) {
                    DataOutputStream dOs = new DataOutputStream(new FileOutputStream("port_config.txt"));
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(dOs));
                    this_asAserver_port = 1025 + rand.nextInt(65536);
                    Integer k = (Integer) this_asAserver_port;
                    bw.write(k.toString());
                    bw.flush();
                    System.out.println("Port Registration Successful");
                } else {
                    new File("port_config.txt").setReadOnly();
                    DataInputStream dIs = new DataInputStream(new FileInputStream("port_config.txt"));
                    BufferedReader br = new BufferedReader(new InputStreamReader(dIs));
                    this_asAserver_port = Integer.parseInt(br.readLine());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            /**
             * ****************************SERVER****************************
             */
            new Thread() {
                @Override
                public void run() {
                    try {
                        s_soc = new ServerSocket(this_asAserver_port);
                        System.out.println("Peer statrted at " + new java.util.Date());
                        System.out.println("SERVER PORT = " + s_soc.getLocalPort());
                        System.out.println("Waiting for other peers to connect...");
                        int count_client = 0;
                        while (true) {
                            server_soc = s_soc.accept();
                            ObjectInputStream s = new ObjectInputStream(server_soc.getInputStream());
                            String k = (String) s.readObject().toString();
                            System.err.println("received k is :" + k);
                            ObjectInputStream si = new ObjectInputStream(server_soc.getInputStream());
                            String c = (String) si.readObject();
                            System.out.println("C is ........" + c);
                            BeAserver handle = new BeAserver(k, c, server_soc);
                            new Thread(handle).start();
                            count_client++;
                            System.out.println("Client " + count_client + "Connected");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                        // ex.printStackTrace();

                    }
                }
            }.start();
            /**
             * ***************************END SERVER*************************
             */

            //this_asAserver_port = 1025 + rand.nextInt(65536);
            try {
                this_asAserver_ip = InetAddress.getLocalHost().toString().substring(InetAddress.getLocalHost().toString().lastIndexOf("/") + 1);
            } catch (UnknownHostException ej) {
                ej.printStackTrace();
                JOptionPane.showMessageDialog(null, ej.getMessage());
            }
        }
    }
    private String file_name;
    private int file_size1;
    private String ip_address;
    private int port;
    private int rows_returned;
    private int ip_rowCount;

    class SelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                return;
            }
            int selected_row = jTable1.getSelectedRow();
            if (selected_row < 0) {
                return;
            }
            int selected_column = jTable1.getSelectedColumn();
            if (selected_column < 0) {
                return;
            }
            client_file_path =/* file_name;*/ (String) jTable1.getModel().getValueAt(selected_row, 0).toString();
            /*try{
             oos.writeObject(client_file_path);
             }catch(Exception es){
             es.printStackTrace();
             }*/

            client_file_size = Integer.parseInt((String) jTable1.getModel().getValueAt(selected_row, 1).toString());
            //client_ip_address = (String)jTable1.getModel().getValueAt(selected_row, 2).toString();
            client_hash_value = (String) jTable1.getModel().getValueAt(selected_row, 2).toString();
            // client_port = Integer.parseInt((String)jTable1.getModel().getValueAt(selected_row, 3).toString());
            //jTable1.clearSelection();
            System.out.println("********************Selection details***************************************");
            System.out.println("file Path: ----> " + /*file_name*/ client_file_path);

            System.out.println("file size: ----> " + client_file_size);
            System.out.println("hash_value: ----> " + client_hash_value);
            System.out.println("************************End Selection Details *******************************");
            jTable1.clearSelection();
        }
    }

    public static byte[] createChecksum(String filename) throws Exception {
        InputStream fis = new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        return complete.digest();
    }

    public static String getMD5Checksum(String filename) throws Exception {
        byte[] b = createChecksum(filename);
        String result = "";

        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public static void join(String baseFilename) throws IOException {
        int numberParts = getNumberParts(baseFilename);
        System.out.println("nummmmmmmmmmmmmmmmmmmmmmmmmmmmmmParts " + (client_file_size / 512 + 1));
        // now, assume that the files are correctly numbered in order (that some joker didn't delete any part)
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(baseFilename));

        for (int part = 0; part < (client_file_size / 512 + 1); part++) {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(baseFilename + ".chunk" + part));
            //System.err.println("within innnnnnnnnnnnnnnnnnnnn join basefilename " + baseFilename);
            System.out.println("appending... chunk" + part);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }

            in.close();
        }
        out.close();
    }

    /*private static int getNumberParts(String baseFilename) throws IOException
     {
     // list all files in the same directory
     File directory = new File(baseFilename).getAbsoluteFile().getParentFile();
     final String justFilename = new File(baseFilename).getName();
     String[] matchingFiles = directory.list(new FilenameFilter()
     {
     public boolean accept(File dir, String name)
     {
     return name.startsWith(justFilename) && name.substring(justFilename.length()).matches("^\\.\\d+$");
     }
     });
     return matchingFiles.length;
     }*/
    private static int getNumberParts(String baseFilename) throws IOException {
        // list all files in the same directory
        File directory = new File(baseFilename).getAbsoluteFile().getParentFile();
        System.err.println("************************************************** " + directory);
        final String justFilename0 = new File(baseFilename).getName();
        //final String justFilename1 = justFilename0.substring(0,justFilename0.lastIndexOf("."));
        System.err.println("jusssstFillllllllllllllllllllllllllllllllllleeeeeeeeenaeme " + justFilename0);

        String[] matchingFiles = directory.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(justFilename0) && name.substring(justFilename0.length()).matches("^\\.\\d+$");
            }
        });
        return matchingFiles.length;
    }

    private static void d(String basefile_name) throws IOException {
        File directory = new File(basefile_name).getAbsoluteFile().getParentFile();
        final String justfilename = new File(basefile_name).getName();
        System.out.println("File Name: " + justfilename);
        ArrayList<File> me = new ArrayList<>();
        File[] lists = directory.listFiles();
        System.err.println("how many files " + lists.length);
        for (int i = 0; i < directory.listFiles().length; i++) {
            if (lists[i].getName().startsWith(justfilename) && lists[i].getName().contains("chunk")) {
                me.add(lists[i]);
            }
        }
        System.err.println(me.size());
        for (File f : me) {
            System.out.println("Deleting..." + f.getName());
            f.delete();
        }
    }

}
