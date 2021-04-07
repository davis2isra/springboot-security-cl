package com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.dao;

import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {

    public Usuario findUsuarioByUsername(String username);

}
