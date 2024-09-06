package com.example.demo;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllCoreLogic {
    @Autowired
CustomerRepository cRepository;
public boolean IsCorrectCardNumber(Long CardNumber){
 //logic to check valid credit card number
 final String Visa_Regex_Card="^4[0-9]{12}(?:[0-9]{3})?$";
 Pattern pattern=Pattern.compile(Visa_Regex_Card);
 java.util.regex.Matcher matcher=pattern.matcher(CardNumber.toString());
return matcher.matches();
}
}
