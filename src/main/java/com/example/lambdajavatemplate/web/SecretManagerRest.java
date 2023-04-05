package com.example.lambdajavatemplate.web;



import com.example.lambdajavatemplate.scrtmanager.AWSSecretManagerInformatioDAO;
import com.example.lambdajavatemplate.scrtmanager.AWSSecretManagerRetriver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/aws/sm")
public class SecretManagerRest {

    @GetMapping(value = "/dbCredentials")
    public ResponseEntity<AWSSecretManagerInformatioDAO> dbCredencials(){
        return ResponseEntity.ok(AWSSecretManagerRetriver.getSecret());
    }
}
