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
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.wiringpi.Gpio;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


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
    private SensorReader[] sensorArray = new SensorReader[4];
    
    
   /* Default consturctor for the robot contorller */
    public BotController() {
        
        // 0,1,2,3 = left, center, right, back
        //for(int i=0 ; i < 4 ; i++ ){
            //sensorArray [i]= new SensorReader(i);
        //}
    }
    
    /* Method to show that the clementine robot is turned on. Shows the user
    that the robot is working */
    public void runClem(){
    }
    
    /* Method to turn the robot to the left */
    public void turnLeft(){
        motor.left(200);
        
    }
    
    /* Method to turn the robot to the right */
    public void turnRight(){
        motor.right(200);
    }
    
    /* Method to move the robot forward */
    public void moveForward(){
        motor.fwd(200);
    }
    
    /* Method to move the robot reverse */
    public void moveReverse(){
        motor.reverse(200);
    }
    
    public void forwardLeft(){
        motor.fwdLeft(200);
    }
    
    public void forwardRight(){
        motor.fwdRight(200);
    }
    
    public void reverseLeft(){
        motor.rvsLeft(200);
    }
    
    public void reverseRight(){
        motor.rvsRight(200);
    }
    
    public void brakingStop(){
        try {
            motor.brakingStop();
        } catch (InterruptedException ex) {
            Logger.getLogger(BotController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
