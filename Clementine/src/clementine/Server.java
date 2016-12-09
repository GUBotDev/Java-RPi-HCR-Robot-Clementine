/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clementine;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author JamesFox
 */
public class Server extends JFrame{
    private JTextField enterField; // Inputs from user
    private JTextArea displayArea; // Display information to the user
    private ObjectOutputStream output; // Output stream to client
    private ObjectInputStream input; // Input stream from the client
    private ServerSocket serverSock; // Server socket
    private Socket connection; // Connection to the client
    private int counter = 1; // Counter for number connections
    
    public BotController clementineBotController;
    
    
    // Constructor
    public Server(){
        super("Server");
        initComponents();
    }
    
    private void initComponents(){
        enterField = new JTextField();
        enterField.setEditable(false);
        enterField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                sendData(event.getActionCommand());
                enterField.setText("");
            }
        });
        add(enterField,BorderLayout.NORTH);
        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        setSize(300,150);
        setVisible(true);
        clementineBotController = new BotController();
        
    }
    
    public void runServer() throws InterruptedException{
        try {
            serverSock = new ServerSocket(12345, 100);
            while(true){
                try{
                    waitForConnection();
                    getStreams(); // Get Input and output sreams
                    processConnection();
                }catch(EOFException eof){
                    displayMessage("\nServer Terminated connection");
                }finally{
                    closeConnection();
                    counter++;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private void waitForConnection() throws IOException{
        displayMessage("\nWaiting for connection...\n");
        connection = serverSock.accept(); // Allow server to accept connection
        displayMessage("Connection "+counter+" recived from "+connection.getInetAddress().getHostName());
        
    }
    
    private void getStreams() throws IOException{
        // Setup output stream for objects
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();// Flush output buffer to send header information
        
        // setup input stream
        input = new ObjectInputStream(connection.getInputStream());
        displayMessage("\nGot I/O Streams\n");
        
    }
    
    private void processConnection() throws IOException, InterruptedException{
        String message = "Connection succesful";
        sendData(message);
        
        // Enable enterfield
        setTextFieldEditable(true);
        
        do{
            try {
                message = (String)input.readObject();
                mannageCommand(message);// Manages the message sent by the client
                displayMessage("\n"+message);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                displayMessage("\nUnknown object type recived");
            }
        }while(!message.equals("CLIENT>>> TERMINATE"));
        
    }
    
    private void displayMessage(final String messageToDisplay){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                displayArea.append(messageToDisplay);
            }
        });
    }
    
    private void setTextFieldEditable(final boolean editable){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                enterField.setEditable(true);
            }
            
        });
    }
    
    private void closeConnection(){
        displayMessage("\nTerminating connection");
        setTextFieldEditable(false);
        
        try {
            output.close();
            input.close();
            connection.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sendData(String message){
        try {
            output.writeObject("SERVER>>> "+message);
            output.flush();
            displayMessage("\nServer>>> "+message);
        } catch (IOException ex) {
            //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            displayArea.append("\nError writing object");
        }
    }
    
    /* Method that takes what the user sends through key listeners and hadles 
    what the robot should do through the BotController class */
    private void mannageCommand(String message) throws InterruptedException {
        if(message.contains(",")){
            String[] splitMessage = message.split(",");
            if(splitMessage[0].equals("CLIENT>>> true")){
                clementineBotController.turnLeft();
        
            }else if(splitMessage[1].equals("true")){
                clementineBotController.turnRight();
            }else if(splitMessage[2].equals("true")){
            
                clementineBotController.moveForward();
            
            }else if(splitMessage[3].equals("true")){
            
                clementineBotController.moveReverse();
            
            }else if(splitMessage[0].equals("CLIENT>>> true")&&splitMessage[2].equals("true")){
                clementineBotController.forwardLeft();
            }else if(splitMessage[1].equals("true")&&splitMessage[2].equals("true")){
                clementineBotController.forwardRight();
            }else if(splitMessage[0].equals("CLIENT>>> true")&&splitMessage[3].equals("true")){
                clementineBotController.reverseLeft();
            }else if(splitMessage[1].equals("true")&&splitMessage[3].equals("true")){
                clementineBotController.reverseRight();
            }else{
                clementineBotController.brakingStop();
                System.out.println("lol");
            }
        }
    }
    
    
}
