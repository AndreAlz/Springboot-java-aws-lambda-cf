package com.example.lambdajavatemplate.dao;


import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse<T> {
    private T result;
    private String mensaje;
}
