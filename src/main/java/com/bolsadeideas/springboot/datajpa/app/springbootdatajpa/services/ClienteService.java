package com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.services;

import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Factura;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService {

    public List<Cliente> findAll();

    public Page<Cliente> findAll(Pageable pageable);

    public Cliente findOne(Long id);

    public Cliente fetchByIdWithFacturas(Long id);

    public void save(Cliente cliente);

    public void delete(Long id);

    public List<Producto> findByNombre(String term);

    public void saveFactura(Factura factura);

    public Producto findProductoById(Long id);

    public Factura findFacturaByid(Long id);

    public void deleteFactura(Long id);

    public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
}
