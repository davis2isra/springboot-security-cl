package com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.controllers;

import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.dao.ClienteDao;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.models.entity.Cliente;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.services.ClienteService;
import com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.util.paginator.PageRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String UPLOADS_FOLDER = "uploads";

    @Autowired
    private ClienteService clienteService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
        Path pathFoto = Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
        log.info("pathFoto: " + pathFoto);
        Resource recurso = null;
        try {
            recurso = new UrlResource(pathFoto.toUri());
            if(!recurso.exists() || !recurso.isReadable()) {
                throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        ResponseEntity<Resource> resource = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);

        return resource;
    }


    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Cliente cliente = clienteService.fetchByIdWithFacturas(id);

        if(cliente == null){
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }

        model.put("cliente", cliente);
        model.put("titulo", "Detalle cliente: " + cliente.getNombre());

        return "ver";
    }

    @RequestMapping(value = {"/listar", "/"}, method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page,
                         Model model,
                         Authentication authentication,
                         HttpServletRequest request) {

        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null) {
            logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(hasRole("ROLE_ADMIN")) {
            logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso"));
        } else {
            logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso"));
        }

        SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");

        if(securityContext.isUserInRole("ADMIN")) {
            logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" tienes acceso"));
        } else {
            logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" NO tienes acceso"));
        }

        if(request.isUserInRole("ROLE_ADMIN")) {
            logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" tienes acceso"));
        } else {
            logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" NO tienes acceso"));
        }

        Pageable pageRequest = PageRequest.of(page, 2);
        Page<Cliente> clientes = clienteService.findAll(pageRequest);

        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pageRender);
        return "listar";
    }

    @GetMapping(value = "/listar-rest")
    public @ResponseBody List<Cliente> listarRest() {
        return clienteService.findAll();
    }

    @GetMapping("/form")
    public String crear(Model model) {

        Cliente cliente = new Cliente();

        model.addAttribute("titulo", "Formulario de Cliente");
        model.addAttribute("cliente", cliente);

        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto,
                          RedirectAttributes flash, SessionStatus status){

        if(result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Cliente");
            return "form";
        }

        if(!foto.isEmpty()) {

            if(cliente.getId() != null
              && cliente.getId() > 0
              && cliente.getFoto() != null
              && cliente.getFoto().length() > 0) {

                Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
                File archivo = rootPath.toFile();

                if(archivo.exists() && archivo.canRead()) {
                    archivo.delete();
                }
            }


            String uniqueFilename = UUID.randomUUID().toString() + "_"+ foto.getOriginalFilename();
            Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(uniqueFilename);
            Path rootAbsolutePath = rootPath.toAbsolutePath();
            log.info("rootPath: " + rootPath);
            log.info("rootAbsolutePath: " + rootAbsolutePath);
            //String  rootPath = "C://Temp//uploads";
            try {
                Files.copy(foto.getInputStream(), rootAbsolutePath);
                flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "' ");

                cliente.setFoto(uniqueFilename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con éxito" : "Cliente creado con éxito";

        clienteService.save(cliente);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/listar";
    }

    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Cliente cliente = null;

        if(id > 0) {
            cliente = clienteService.findOne(id);
            if(cliente == null ) {
                //flash.addFlashAttribute("success", "El ID del ciente no existe en el sistema");
                return "redirect:/listado";
            }
        } else {
            //flash.addFlashAttribute("success", "El ID del ciente no puede ser cero");
            return "redirect:/listado";
        }

        model.put("cliente", cliente);
        model.put("titulo", "Editar Cliente");
        return "form";
    }

    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash){

        if(id > 0){
            Cliente cliente = clienteService.findOne(id);

            clienteService.delete(id);
            flash.addFlashAttribute("success", "Cliente eliminado con éxito");

            Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
            File archivo = rootPath.toFile();

            if(archivo.exists() && archivo.canRead()) {
                if(archivo.delete()) {
                    flash.addFlashAttribute("info", "Foto" + cliente.getFoto() + " eliminada con éxito.");
                }
            }
        }

        return "redirect:/listar";
    }

    private boolean hasRole(String role) {
        SecurityContext context = SecurityContextHolder.getContext();

        if(context == null) {
            return false;
        }

        Authentication auth = context.getAuthentication();

        if(auth == null) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        for ( GrantedAuthority authority : authorities) {

            if(role.equals(authority.getAuthority())) {
                logger.info("Hola usuario ".concat(auth.getName()).concat(" tu role es: ").concat(authority.getAuthority()));
                return true;
            }
        }
        return false;
    }
}
