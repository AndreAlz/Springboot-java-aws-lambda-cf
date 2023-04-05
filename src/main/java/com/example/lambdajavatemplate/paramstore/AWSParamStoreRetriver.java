package com.example.lambdajavatemplate.paramstore;

import com.google.gson.Gson;
import lombok.*;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

public class AWSParamStoreRetriver {
    private static AWSParamStoreInformationDAO awsParamStoreInformationDAO;
    public static AWSParamStoreInformationDAO getApplicationProperties()
    {
        if (awsParamStoreInformationDAO != null)
        {
            return awsParamStoreInformationDAO;
        }

        awsParamStoreInformationDAO = getConfiguration();

        return awsParamStoreInformationDAO;
    }
    private static AWSParamStoreInformationDAO getConfiguration(){
        final Gson gson = new Gson();
        String configName = "CONFIGURATION_NAME";
        String parameterName = System.getenv(configName);
        if (parameterName == null || parameterName == "") {
            return new AWSParamStoreInformationDAO();
        }
        SsmClient ssmClient = SsmClient.create();

        String parameter;
        GetParameterRequest getParameterRequest = GetParameterRequest.builder()
                .name(parameterName)
                .build();

        GetParameterResponse result = null;

        try {
            result = ssmClient.getParameter(getParameterRequest);
        }
        catch (Exception e) {
            throw e;
        }
        if (result.parameter().value() != null) {
            parameter = result.parameter().value();
            return gson.fromJson(parameter, AWSParamStoreInformationDAO.class);
        }

        return null;
    }
}
