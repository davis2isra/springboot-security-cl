package com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.services;

import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.dao.UsuarioDao;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Role;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioDao usuarioDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findUsuarioByUsername(username);

        if(usuario == null ) {
            logger.error("Error Login: no existe el usuario '" + username +"'");
            throw new UsernameNotFoundException("Username " + username + " no existe en el sistema");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for(Role role : usuario.getRoles()) {
            logger.error("Role: ".concat(role.getAuthority()));
            authorities.add( new SimpleGrantedAuthority(role.getAuthority()));
        }

        if( authorities.isEmpty() ) {
            logger.error("Error Login: el usuario '" + username +"' no tiene roles asignados");
            throw new UsernameNotFoundException("Error Login: el usuario '" + username + "' no tiene roles asignados");
        }

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
    }
}
