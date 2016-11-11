/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clementine;

/**
 *
 * @author JamesFox
 */
public interface MtrControlerInterface {
    
    /* Engage both motors forward at speed (1-100) given by parameter*/
    public void fwd(int speed);
    
    /* Engage both motors backward at speed (1-100) given by parameter*/
    public void reverse(int speed);
    
    /* Bot must be moving forward, right motor speed is reduced by percentage
    given by parameter (1-100) */
    public void fwdRight(int rtMtrReduc);
    
    /* Bot must be moving forward, left motor speed is reduced by percentage
    given by parameter (1-100) */
    public void fwdLeft(int ltMtrReduc);
    
    /* Bot must be moving backward, right motor speed is reduced by percentage 
    given by parameter (1-100) */
    public void rvsRight(int lrMrtReduc);
    
    /* Bot must be moving backward, right motor speed is reduced by percentage 
    given by parameter (1-100) */
    public void rvsLeft(int rtMtrReduc);
    
    /* Since we dont want to stress the motors we might need a method to beging 
    to slow down instead of just going from 100 to 0. This will be better for 
    the motors and not break them with the weight of the robot */
    public void brakingStop();
    
    /* A method to make the motors stop moving. The bot can be in any state to 
    call this function*/
    public void disableMotors();
}
