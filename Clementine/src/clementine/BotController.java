/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clementine;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;


/**
 *
 * @author JamesFox
 */
public class BotController{
     
    
    private GpioController gpio = GpioFactory.getInstance();
    private GpioPinDigitalOutput motorFwd = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02);
    private GpioPinDigitalOutput motorRev = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03);
        
    private GpioPinDigitalOutput motor2Fwd = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05);
    private GpioPinDigitalOutput motor2Rev = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06);
    
    private DCMotor motor = new DCMotor(gpio, motorFwd, motorRev, motor2Fwd, motor2Rev);
    
   /* Default consturctor for the robot contorller */
    public BotController(){
        
    }
    
    /* Method to show that the clementine robot is turned on. Shows the user
    that the robot is working */
    public void runClem(){
    }
    
    /* Method to turn the robot to the left */
    public void turnLeft(){
        motor.left(100);
    }
    
    /* Method to turn the robot to the right */
    public void turnRight(){
        motor.right(100);
    }
    
    /* Method to move the robot forward */
    public void moveForward(){
        motor.fwd(100);
    }
    
    /* Method to move the robot reverse */
    public void moveReverse(){
        motor.reverse(100);
    }
    
    public void forwardLeft(){
        motor.fwdLeft(100);
    }
    
    public void forwardRight(){
        motor.fwdRight(100);
    }
    
    public void reverseLeft(){
        motor.rvsLeft(100);
    }
    
    public void reverseRight(){
        motor.rvsRight(100);
    }
    
    public void brakingStop() throws InterruptedException{
        motor.brakingStop();
    }
    
}
