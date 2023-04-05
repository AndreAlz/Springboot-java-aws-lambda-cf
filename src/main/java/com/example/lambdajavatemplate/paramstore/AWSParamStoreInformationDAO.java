package com.example.lambdajavatemplate.paramstore;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AWSParamStoreInformationDAO {
    private String sunatEndpoint;
}
