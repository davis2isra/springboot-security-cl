package com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.services;

import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.dao.ClienteDao;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.dao.FacturaDao;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.dao.ProductoDao;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Factura;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private FacturaDao facturaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findOne(Long id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente fetchByIdWithFacturas(Long id) {
        return clienteDao.fetchByIdWithFacturas(id);
    }

    @Override
    @Transactional
    public void save(Cliente cliente) {
        clienteDao.save(cliente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByNombre(String term) {
        return productoDao.findByNombreLikeIgnoreCase("%" + term+ "%");
    }

    @Override
    @Transactional
    public void saveFactura(Factura factura) {
        facturaDao.save(factura);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findProductoById(Long id) {
        return productoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura findFacturaByid(Long id) {
        return facturaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteFactura(Long id) {
        facturaDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id) {
        return facturaDao.fetchByIdWithClienteWithItemFacturaWithProducto(id);
    }
}
