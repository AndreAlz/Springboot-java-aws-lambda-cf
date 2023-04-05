package com.example.lambdajavatemplate.scrtmanager;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AWSSecretManagerInformatioDAO {
    private String username;
    private String password;
    private String host;
    private String engine;
    private String port;
    private String dbInstanceIdentifier;
}
