package com.example.lambdajavatemplate.scrtmanager;


import com.google.gson.Gson;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class AWSSecretManagerRetriver {

    @Value("${secretmanager.name}")
    private static String secretmanagerName;
    private static Gson gson = new Gson();

    public static AWSSecretManagerInformatioDAO getSecret() {
        Region region = Region.of("us-east-2");

        // Create a Secrets Manager client
        SecretsManagerClient secretsManagerClient = SecretsManagerClient.builder()
                .region(region)
                .build();


        String secret;

        var getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId("iprovider_saas_dev_java")
                .build();

        GetSecretValueResponse result = null;

        try {
            result = secretsManagerClient.getSecretValue(getSecretValueRequest);
        }
        catch (Exception e) {
            throw e;
        }
        if (result.secretString() != null) {
            secret = result.secretString();
            return gson.fromJson(secret, AWSSecretManagerInformatioDAO.class);
        }

        return null;
    }
}
