package clementine;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Charles Williams
 */
public class DCMotor{
    
//     Server application = new Server();
//        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        application.runServer();
         
        //create gpio controller
         GpioController gpio = GpioFactory.getInstance();
        
//          final GpioPinAnalogOutput enablePin = gpio.provisionAnalogOutputPin(RaspiPin.GPIO_01, 40);
//        final GpioPinDigitalOutput enablePin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.HIGH);
//        final GpioPinDigitalOutput enablePin2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_14, "PWM", PinState.LOW);

//        final GpioPinAnalogOutput enablePin = gpio.provisionAnalogOutputPin(RaspiPin.GPIO_18, "PWM", speed);
        
        GpioPinDigitalOutput motorFwd = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02);
        GpioPinDigitalOutput motorRev = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03);
        
        GpioPinDigitalOutput motor2Fwd = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05);
        GpioPinDigitalOutput motor2Rev = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06);
        
        private int speedLeft = 0;
        private int speedRight = 0;

//        final GpioPinDigitalOutput motor2Fwd = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, "Motor 2 Forward", PinState.LOW);
//        final GpioPinDigitalOutput motor2Rev = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24, "Motor 2 Reverse", PinState.LOW);
 
//        motorFwd.setState(true);
//        motor2Fwd.setState(true);
//        Gpio.delay(500);
//        motorRev.setState(false);
//        motor2Rev.setState(false);



//        Gpio.pinMode(7, Gpio.PWM_OUTPUT);
//        Gpio.pwmSetMode(Gpio.PWM_MODE_MS);
//        Gpio.pwmSetClock(384);
//        Gpio.pwmSetRange(1000);
//        Gpio.pwmWrite(7, 75);
//        
        

    
    public DCMotor(){
        Gpio.pinMode(1, Gpio.PWM_OUTPUT);
        Gpio.pwmSetMode(Gpio.PWM_MODE_MS);
        Gpio.pwmSetClock(384);
        Gpio.pwmSetRange(1000);
        
         Gpio.pinMode(26, Gpio.PWM_OUTPUT);
        Gpio.pwmSetMode(Gpio.PWM_MODE_MS);
        Gpio.pwmSetClock(384);
        Gpio.pwmSetRange(1000);
    }
    
    public void disableMotors(){
        motorFwd.setState(false);
        motor2Fwd.setState(false);
        motorRev.setState(false);
        motor2Rev.setState(false);
        setSpeedLeft(0);
        setSpeedRight(0);
    }
    
    public void brakingStop() throws InterruptedException{
        for(int i = speedLeft; i > 0; i--){
            Thread.sleep(10);
            setSpeedLeft(i);
            setSpeedRight(i);
        }
        motorFwd.setState(false);
        motor2Fwd.setState(false);
        motorRev.setState(false);
        motor2Rev.setState(false);
        speedLeft = 0;
        speedRight = 0;
    }
    
    public void fwd(int speed){
        speed = (int)(speed * 2.55);
        motorFwd.setState(true);
        motor2Fwd.setState(true);
        motorRev.setState(false);
        motor2Rev.setState(false);
        setSpeedLeft(speed);
        setSpeedRight(speed);
        speedLeft = speed;
        speedRight = speed;
    }
    
    public void reverse(int speed){
        speed = (int)(speed * 2.55);
        motorFwd.setState(false);
        motor2Fwd.setState(false);
        motorRev.setState(true);
        motor2Rev.setState(true);
        setSpeedLeft(speed);
        setSpeedRight(speed);
        speedLeft = speed;
        speedRight = speed;
    }
    
    public void fwdLeft(int ltMtrReduc){
        ltMtrReduc = (int)(ltMtrReduc * 2.55);
        motorFwd.setState(true);
        motor2Fwd.setState(true);
        motorRev.setState(false);
        motor2Rev.setState(false);
        if(speedLeft - ltMtrReduc > 0){
        speedLeft = speedLeft -  ltMtrReduc;
        setSpeedLeft(speedLeft - ltMtrReduc);
        setSpeedRight(speedRight);
        }
    }
    
     public void fwdRight(int rtMtrReduc){
        rtMtrReduc = (int)(rtMtrReduc * 2.55);
        motorFwd.setState(true);
        motor2Fwd.setState(true);
        motorRev.setState(false);
        motor2Rev.setState(false);
        speedRight = speedRight - rtMtrReduc;
        setSpeedRight(speedLeft - rtMtrReduc);
        setSpeedLeft(speedLeft);
    }
     
     public void rvsLeft(int rtMtrReduc){
        rtMtrReduc = (int)(rtMtrReduc * 2.55);
        motorFwd.setState(true);
        motor2Fwd.setState(true);
        motorRev.setState(false);
        motor2Rev.setState(false);
        speedRight = speedRight - rtMtrReduc;
        setSpeedRight(speedLeft - rtMtrReduc);
        setSpeedLeft(speedLeft);    }
     
     public void rvsRight(int ltMtrReduc){
        ltMtrReduc = (int)(ltMtrReduc * 2.55);
        motorFwd.setState(true);
        motor2Fwd.setState(true);
        motorRev.setState(false);
        motor2Rev.setState(false);
        setSpeedRight(ltMtrReduc);
        speedLeft = speedLeft -  ltMtrReduc;
        setSpeedLeft(speedLeft - ltMtrReduc);
        setSpeedRight(speedRight);
    }
     
    public void setSpeedLeft(int speed){
        
        Gpio.pwmWrite(1, speed);
        
    }
    
    public void setSpeedRight(int speed){
       
        Gpio.pwmWrite(24, speed);
    }
     
     
    
    
    
}
