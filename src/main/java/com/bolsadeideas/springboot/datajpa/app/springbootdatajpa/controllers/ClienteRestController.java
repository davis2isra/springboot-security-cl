package com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.controllers;

import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping(value = "/listar")
    public List<Cliente> listar() {
        return clienteService.findAll();
    }
}
