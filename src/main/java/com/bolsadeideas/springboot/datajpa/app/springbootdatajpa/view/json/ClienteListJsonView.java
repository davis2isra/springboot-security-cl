package com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.view.json;

import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.view.xml.ClienteList;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

@Component("listar.json")
public class ClienteListJsonView extends MappingJackson2JsonView {

    @Override
    protected Object filterModel(Map<String, Object> model) {
        model.remove("titulo");
        model.remove("page");

        Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");

        model.remove("clientes");
        model.put("clienteList", clientes.getContent());

        return super.filterModel(model);
    }
}
