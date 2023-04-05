package com.example.lambdajavatemplate.web;

import com.example.lambdajavatemplate.paramstore.AWSParamStoreInformationDAO;
import com.example.lambdajavatemplate.paramstore.AWSParamStoreRetriver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ParamStoreRest {



    @GetMapping(value = "/getAppConfiguration")
    public ResponseEntity<AWSParamStoreInformationDAO> getAppConfiguration(@RequestHeader Map<String, String> headers){
        return ResponseEntity.ok(AWSParamStoreRetriver.getApplicationProperties());
        //return ResponseEntity.ok(headers);
    }
}
