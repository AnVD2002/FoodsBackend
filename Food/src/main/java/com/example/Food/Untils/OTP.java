package com.example.Food.Untils;

import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class OTP {
    public String GenerateOTP(){
        Random random = new Random();
        int RandomNum = random.nextInt(999999);
        String output = Integer.toString(RandomNum);
        if (output.length()<6){
            output = "0"+output;
        }
        return output;
    }
}
