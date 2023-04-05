package com.example.lambdajavatemplate.web;

import com.amazonaws.services.cognitoidp.model.ChangePasswordResult;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import com.amazonaws.services.cognitoidp.model.ResendConfirmationCodeResult;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.auth0.jwt.interfaces.Claim;
import com.example.lambdajavatemplate.cognito.CognitoUtilities;
import com.example.lambdajavatemplate.dao.LoginUserCredentials;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CognitoRest {

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginUserCredentials payload){
        CognitoUtilities cognitoUtilities = new CognitoUtilities();
        return cognitoUtilities.login(payload.getUsername(), payload.getPassword());
    }

    @PatchMapping("/user/change-password")
    public ChangePasswordResult changePassword(@RequestBody Map<String, String> payload , @RequestHeader("Authorization") String token){
        String password = payload.get("password");
        String newPassword = payload.get("newPassword");
        CognitoUtilities cognitoUtilities = new CognitoUtilities();
        return cognitoUtilities.changePassword( password, newPassword, token);
    }

    @PostMapping("/user/sign-up/resend-code")
    public ResendConfirmationCodeResult resendCode(@RequestBody Map<String, String> payload){
        String username = payload.get("username");
        CognitoUtilities cognitoUtilities = new CognitoUtilities();
        return cognitoUtilities.resendConfirmationCode(username);
    }
    @PostMapping("/user/sign-up/confirm-email")
    public ConfirmSignUpResult confirmation(@RequestBody Map<String, String> payload){
        String username = payload.get("username");
        String code = payload.get("code");
        CognitoUtilities cognitoUtilities = new CognitoUtilities();

        return cognitoUtilities.confirmSignUp(username,code);
    }

    @PostMapping("/user/sign-up")
    public SignUpResult signUp(@RequestBody Map<String, String> payload){
        String username = payload.get("username");
        String password = payload.get("password");
        CognitoUtilities cognitoUtilities = new CognitoUtilities();

        return cognitoUtilities.signUp("Andre Alzamora",username, password);

    }

    @GetMapping("/token/payload")
    public String getTokenPayload(@RequestHeader("Authorization") String token){
        CognitoUtilities cognitoUtilities = new CognitoUtilities();
        return cognitoUtilities.getTokenPayload(token);
    }


    @GetMapping("/token/information")
    public Map<String, Claim> getTokenClaims(@RequestHeader("Authorization") String token){
        CognitoUtilities cognitoUtilities = new CognitoUtilities();
        return cognitoUtilities.getTokenInformation(token);
    }
}
