/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clementineclient;

import javax.swing.JFrame;

/**
 *
 * @author JamesFox
 */
public class ClementineClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClemClient application;
        
        application = new ClemClient("192.168.0.101");// IP of the rPi
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.runClient();
    }
    
}
