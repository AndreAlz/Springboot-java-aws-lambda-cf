package com.example.lambdajavatemplate.web;

import com.example.lambdajavatemplate.dao.UsuarioResponse;
import com.example.lambdajavatemplate.model.Usuario;
import com.example.lambdajavatemplate.service.UsuarioService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class UsuarioRest {

    private UsuarioService usuarioService;

    public UsuarioRest(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<UsuarioResponse> findAll(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Requested-With", "*");
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Api-Key,x-requested-with");
        List<Usuario> data = usuarioService.findAll();
        UsuarioResponse<List<Usuario>> response = UsuarioResponse.<List<Usuario>>builder().result(data).mensaje("Resultado de la busqueda").build();
        return ResponseEntity.ok().headers(headers).body(response);
    }
}
