/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clementine;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;


/**
 *
 * @author JamesFox
 */
public class BotController{
    
    private GpioController gpio;
    private GpioPinDigitalOutput leftLED;
    private GpioPinDigitalOutput rightLED;
    private GpioPinDigitalOutput forwardLED;
    private GpioPinDigitalOutput reverseLED;
    private GpioPinDigitalOutput clementineOnLED;
    
   /* Default consturctor for the robot contorller */
    public BotController(){
        leftLED = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
        rightLED = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);
        forwardLED = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05);
        reverseLED = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06);
        clementineOnLED = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08);
    }
    
    /* Method to show that the clementine robot is turned on. Shows the user
    that the robot is working */
    public void runClem(){
        clementineOnLED.setState(true);
    }
    
    /* Method to turn the robot to the left */
    public void turnLeft(){
        leftLED.setState(true);
        Gpio.delay(500);
        leftLED.setState(false);
    }
    
    /* Method to turn the robot to the right */
    public void turnRight(){
        rightLED.setState(true);
        Gpio.delay(500);
        rightLED.setState(false);
    }
    
    /* Method to move the robot forward */
    public void moveForward(){
        forwardLED.setState(true);
        Gpio.delay(500);
        forwardLED.setState(false);
    }
    
    /* Method to move the robot reverse */
    public void moveReverse(){
        reverseLED.setState(true);
        Gpio.delay(500);
        reverseLED.setState(false);
    }
    
}
