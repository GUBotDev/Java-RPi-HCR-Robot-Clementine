/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clementine;

import java.io.IOException;

/**
 *
 * @author JamesFox
 */
public class Clementine {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        
        Server clementineServer = new Server();
        clementineServer.runServer();
        
    }
    
}
