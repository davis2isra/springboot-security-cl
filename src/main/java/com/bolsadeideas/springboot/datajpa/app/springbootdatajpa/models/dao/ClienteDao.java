package com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.dao;

import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClienteDao extends PagingAndSortingRepository<Cliente, Long> {

        @Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")
        public Cliente fetchByIdWithFacturas(Long id);

}
