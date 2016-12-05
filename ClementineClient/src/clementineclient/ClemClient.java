/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clementineclient;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author JamesFox
 */
public class ClemClient extends JFrame implements KeyListener {
    
    private JPanel flowPanel = new JPanel(new FlowLayout());
    private String chatServer;
    private JButton turnLeft;
    private JButton turnRight;
    private JButton moveForward;
    private JButton moveReverse;
    private ObjectOutputStream output; // Output stream to server
    private ObjectInputStream input; // Input stream from the server
    private String message=""; //  String to hold message from server
    private Socket client; // Connection to the server
    
    private boolean left, right, forward, backwards;
        
    public ClemClient(String hostName){
        super("Clementine Client");
        
        chatServer = hostName;
        initComponents();
        
    }
    
    private void initComponents(){
        left = false;
        right = false;
        forward = false;
        backwards = false;
        
        turnLeft = new JButton();
        turnLeft.setText("Left");
        turnLeft.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                sendData("1,0,0,0");
            }
        });
        
        turnRight = new JButton();
        turnRight.setText("Right");
        turnRight.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                sendData("0,1,0,0");
            }
        });
        
        moveForward = new JButton();
        moveForward.setText("Forward");
        moveForward.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                sendData("0,0,1,0");
            }
        });
        
        moveReverse = new JButton();
        moveReverse.setText("Reverse");
        moveReverse.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                sendData("0,0,0,1");
            }
            
        });
        turnLeft.addKeyListener(this);
        flowPanel.addKeyListener(this);
        flowPanel.add(turnLeft);
        flowPanel.add(turnRight);
        flowPanel.add(moveForward);
        flowPanel.add(moveReverse);
        
        add(flowPanel,BorderLayout.CENTER);
        
        setSize(300,150);
        setVisible(true);
        
    }
    
    public void runClient(){
        
        try{
            connectToServer();
            getStreams(); // Get Input and output sreams
            processConnection();
        }catch(EOFException eof){
            // lol
        } catch (IOException ex) {
            Logger.getLogger(ClemClient.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeConnection();
        }
            
    }
    
    private void processConnection() throws IOException{
        String message = "Connection succesful";
        sendData(message);
        
        // Enable enterfield
        
        do{
            try {
                message = (String)input.readObject(); // Reading message from server
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClemClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(!message.equals("SERVER>>> TERMINATE"));
    }// End method processConnection
    
    private void getStreams() throws IOException{
        // Setup output stream for objects
        output = new ObjectOutputStream(client.getOutputStream());
        output.flush();// Flush output buffer to send header information
        
        // setup input stream
        input = new ObjectInputStream(client.getInputStream());
        
    }
    
    private void connectToServer() throws IOException{
        client = new Socket(InetAddress.getByName(chatServer), 12345);
    }// End of method connectToServer
    
    private void sendData(String message){
        try {
            output.writeObject("CLIENT>>> "+message);
            output.flush();
        } catch (IOException ex) {
            //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }// End method sendData
    
    
    
    private void closeConnection(){        
        try {
            output.close();
            input.close();
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(ClemClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// End method closeConnection
    
    /*
    Still testing this method
    
    private String convertBooleanToString(boolean leftBool, boolean rightBool,
            boolean forwardBool, boolean reverseBool){
        String stringToReturn = "";
        boolean[] holdBooleans = new boolean[4];
        holdBooleans[0] = leftBool;
        holdBooleans[1] = rightBool;
        holdBooleans[2] = forwardBool;
        holdBooleans[3] = reverseBool;
        
        for(int i=0; i<holdBooleans.length; i++){
            stringToReturn.concat(holdBooleans[i]+",");
        }
        
        return stringToReturn;
    }
*/

    @Override
    public void keyTyped(KeyEvent e) {
        //not using
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            
            left = true;
            sendData("1,0,0,0");
            //System.out.println(convertBooleanToString(left,right,forward,backwards));
            
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            
            right = true;
            sendData("0,1,0,0");
            
        }else if(e.getKeyCode() == KeyEvent.VK_UP){
            
            forward = true;
            sendData("0,0,1,0");
            
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            
            backwards = true;
            sendData("0,0,0,1");
        }
        //convertBooleanToString(left,right,forward,backwards);
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            left = false;
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            right = false;
        }else if(e.getKeyCode() == KeyEvent.VK_UP){
            forward = false;
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            backwards = false;
        }
        //convertBooleanToString(left,right,forward,backwards);
        
    }
}
