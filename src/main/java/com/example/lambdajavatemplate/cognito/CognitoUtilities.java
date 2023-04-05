package com.example.lambdajavatemplate.cognito;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.nimbusds.jwt.SignedJWT;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

@NoArgsConstructor
public class CognitoUtilities {

    public AWSCognitoIdentityProvider createCognitoClient() {
        AWSCredentials cred = new BasicAWSCredentials("AKIAWJMYCEPKBDWGN27P", "JvMWYzMFLT9h+GmJ6t61xkxC0w5vCwAV4gRI3KZ4");
        AWSCredentialsProvider credProvider = new AWSStaticCredentialsProvider(cred);
        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(credProvider)
                .withRegion(Regions.US_EAST_2)
                .build();
    }

    public SignUpResult signUp(String name, String email, String password) {
        AWSCognitoIdentityProvider client = createCognitoClient();
        SignUpRequest request = new SignUpRequest().withClientId("dv2b5jqq6cnccgvnotfrolvp0")
                .withUsername(email)
                .withPassword(password);
        SignUpResult result = client.signUp(request);
        return result;
    }

    public ConfirmSignUpResult confirmSignUp(String email, String confirmationCode) {
        AWSCognitoIdentityProvider client = createCognitoClient();
        ConfirmSignUpRequest confirmSignUpRequest = new ConfirmSignUpRequest().withClientId("dv2b5jqq6cnccgvnotfrolvp0")
                .withUsername(email).withConfirmationCode(confirmationCode);
        return client.confirmSignUp(confirmSignUpRequest);
    }

    public ResendConfirmationCodeResult resendConfirmationCode(String username){
        AWSCognitoIdentityProvider client = createCognitoClient();
        ResendConfirmationCodeRequest codeRequest = new ResendConfirmationCodeRequest()
                .withClientId("dv2b5jqq6cnccgvnotfrolvp0")
                .withUsername(username);

        ResendConfirmationCodeResult response = client.resendConfirmationCode(codeRequest);
        return response;
    }

    public ChangePasswordResult changePassword(String password, String newPassword, String token){
        AWSCognitoIdentityProvider client = createCognitoClient();
        ChangePasswordRequest changeRequest = new ChangePasswordRequest()
                .withAccessToken(token)
                .withPreviousPassword(password)
                .withProposedPassword(newPassword);
        ChangePasswordResult response = client.changePassword(changeRequest);
        return response;
    }

    public Map<String, String> login(String email, String password) {
        AWSCognitoIdentityProvider client = createCognitoClient();
        Map<String, String> authParams = new LinkedHashMap<String, String>() {{
            put("USERNAME", email);
            put("PASSWORD", password);
        }};

        AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
                .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .withUserPoolId("us-east-2_gyxoIEuX8")
                .withClientId("dv2b5jqq6cnccgvnotfrolvp0")
                .withAuthParameters(authParams);
        AdminInitiateAuthResult authResult = client.adminInitiateAuth(authRequest);
        AuthenticationResultType resultType = authResult.getAuthenticationResult();
        return new LinkedHashMap<String, String>() {
            {
            put("idToken", resultType.getIdToken());
            put("accessToken", resultType.getAccessToken());
            put("refreshToken", resultType.getRefreshToken());
            put("message", "Successfully login");
        }
        };

    }

    public Map<String, String> refreshToken(String refreshToken) {
        AWSCognitoIdentityProvider client = createCognitoClient();
        Map<String, String> authParams = new LinkedHashMap<String, String>() {{
            put("REFRESH_TOKEN", refreshToken);
        }};
        InitiateAuthRequest authRequest = new InitiateAuthRequest()
                .withAuthFlow(AuthFlowType.REFRESH_TOKEN_AUTH)
                .withClientId("dv2b5jqq6cnccgvnotfrolvp0")
                .withAuthParameters(authParams);
        InitiateAuthResult authResult = client.initiateAuth(authRequest);
        AuthenticationResultType resultType = authResult.getAuthenticationResult();
        return new LinkedHashMap<String, String>() {{
            put("idToken", resultType.getIdToken());
            put("accessToken", resultType.getAccessToken());
            put("message", "Successfully login");
        }};
    }

    public Map<String, Claim> getTokenInformation(String token){
        String data = JWT.decode(token).getPayload();
        Map<String, Claim> claims = JWT.decode(token).getClaims();
        return claims;
    }

    public String getTokenPayload(String token){
        try {
            var decodedJWT = SignedJWT.parse(token);
            var header = decodedJWT.getHeader().toString();
            var payload = decodedJWT.getPayload().toString();
            var test = decodedJWT.getJWTClaimsSet();
            test.getClaims().toString();
            return header + "--" + payload + "--" + test.getClaims().toString();
        } catch (ParseException e) {
            throw new RuntimeException("Invalid token!");
        }
    }
}
