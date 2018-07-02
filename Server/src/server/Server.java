
package server;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server extends javax.swing.JFrame {

    private ServerSocket server;
    public Hashtable<String, Connected> listUser;
    
    public Server() {
        initComponents();
        this.setTitle("ButWhy Messanger");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.taStatus.append("Welcome to ButWhy Messager\n");
        
        taStatus.setEditable(false);
    }

    public void setStatus(String mes){
        this.taStatus.append(mes);
    }
            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        taStatus = new javax.swing.JTextArea();
        btClose = new javax.swing.JButton();
        btStart = new javax.swing.JButton();
        lbStatus = new javax.swing.JLabel();
        lbServerIP = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnFile = new javax.swing.JMenu();
        mnNew = new javax.swing.JMenuItem();
        mnOpen = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnClose = new javax.swing.JMenuItem();
        mnEdit = new javax.swing.JMenu();
        mnCopy = new javax.swing.JMenuItem();
        mnPaste = new javax.swing.JMenuItem();
        mnAbout = new javax.swing.JMenu();
        mnaboutus = new javax.swing.JMenuItem();
        mnUpdate = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        taStatus.setColumns(20);
        taStatus.setRows(5);
        jScrollPane1.setViewportView(taStatus);

        btClose.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btClose.setText("Close");
        btClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCloseActionPerformed(evt);
            }
        });

        btStart.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btStart.setText("Start");
        btStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStartActionPerformed(evt);
            }
        });

        lbStatus.setFont(new java.awt.Font("Times New Roman", 2, 18)); // NOI18N
        lbStatus.setText("Server Status:");

        lbServerIP.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        lbServerIP.setText("Server IP:");

        mnFile.setText("File");

        mnNew.setText("New");
        mnFile.add(mnNew);

        mnOpen.setText("Open");
        mnFile.add(mnOpen);
        mnFile.add(jSeparator1);

        mnClose.setText("Close");
        mnFile.add(mnClose);

        jMenuBar1.add(mnFile);

        mnEdit.setText("Edit");

        mnCopy.setText("Copy");
        mnEdit.add(mnCopy);

        mnPaste.setText("Paste");
        mnEdit.add(mnPaste);

        jMenuBar1.add(mnEdit);

        mnAbout.setText("About");

        mnaboutus.setText("About Us");
        mnAbout.add(mnaboutus);

        mnUpdate.setText("Update");
        mnAbout.add(mnUpdate);

        jMenuBar1.add(mnAbout);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbStatus)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btStart, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btClose, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbServerIP, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbServerIP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btStart))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void client_conected(){
      
    try {
        listUser = new Hashtable<String, Connected>();
        server = new ServerSocket(2422);
        
        while(true){
                    Socket client = server.accept();
                    new Connected(this,client);
		}
        } catch (IOException e) {
		taStatus.append("Can't start Server\n");
	}
    }
    
    private String getIP(){
    InetAddress myHost = null;
        try {
            myHost = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    return myHost.getHostAddress();
    }
  
    
    private void btStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStartActionPerformed
      
            // TODO add your handling code here:
            
            lbServerIP.setText("Server IP: " + getIP());
            
            this.taStatus.append("Server ready to serves\n");
       
       
    }//GEN-LAST:event_btStartActionPerformed

    private void btCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCloseActionPerformed
        try {
            // TODO add your handling code here:
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
	System.exit(0);
        this.dispose();
    }//GEN-LAST:event_btCloseActionPerformed

public void sendAll(String from, String msg){
    Enumeration e = listUser.keys();
    String name=null;
    while(e. hasMoreElements()){
            name=(String) e.nextElement();
            //System.out.println(name);
	if(name.compareTo(from)!=0) listUser.get(name).sendMSG("3",msg);
    }
}
public void sendAllUpdate(String from){
    Enumeration e = listUser.keys();
    String name=null;
    while(e. hasMoreElements()){
	name=(String) e.nextElement();
	//System.out.println(name);
	if(name.compareTo(from)!=0) listUser.get(name).sendMSG("4",getAllName());
}
}

public String getAllName(){
	Enumeration e = listUser.keys();
	String name="";
	while(e. hasMoreElements()){
		name+=(String) e.nextElement()+"\n";
	}
	return name;
}
    
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
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        Server demo = new Server();
        demo.setVisible(true);
        demo.client_conected();
        
        //demo.go();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btClose;
    private javax.swing.JButton btStart;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbServerIP;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JMenu mnAbout;
    private javax.swing.JMenuItem mnClose;
    private javax.swing.JMenuItem mnCopy;
    private javax.swing.JMenu mnEdit;
    private javax.swing.JMenu mnFile;
    private javax.swing.JMenuItem mnNew;
    private javax.swing.JMenuItem mnOpen;
    private javax.swing.JMenuItem mnPaste;
    private javax.swing.JMenuItem mnUpdate;
    private javax.swing.JMenuItem mnaboutus;
    private javax.swing.JTextArea taStatus;
    // End of variables declaration//GEN-END:variables
}
