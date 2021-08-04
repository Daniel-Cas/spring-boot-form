package com.bolsadeideas.springboot.form.app.controllers;



import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.editors.PaisPropertyEditor;
import com.bolsadeideas.springboot.form.app.editors.RolesEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.models.domain.Role;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.services.PaisService;
import com.bolsadeideas.springboot.form.app.services.RoleService;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;


import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("usuario")
public class FormController {

    @Autowired
    private UsuarioValidador validador;

    @Autowired
    private PaisService paisService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PaisPropertyEditor paisEditor;

    @Autowired
    private RolesEditor roleEditor;



    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(validador);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(dateFormat, false) );
        binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor());
        binder.registerCustomEditor(String.class, "apellido", new NombreMayusculaEditor());
        binder.registerCustomEditor(Pais.class, "pais", paisEditor);
        binder.registerCustomEditor(Role.class, "roles",roleEditor);
    }

    @ModelAttribute("listaRoles")
    public List<Role> listaRoles(){
        return this.roleService.listar();
    }

    @ModelAttribute("listaPaises")
    public List<Pais> listaPaises(){
        return paisService.listar();
    }

    @ModelAttribute("listaRolesString")
    public List<String> listaRolesString(){
        List<String> roles = new ArrayList<>();

        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        roles.add("ROLE_MODERATOR");

        return roles;
    }

    @ModelAttribute("genero")
    public List<String> genero(){
        return Arrays.asList("Hombre","Mujer");
    }

    @ModelAttribute("listaRolesMap")
    public Map<String, String> listaRolesMap(){

        Map<String, String> roles = new HashMap<String, String>();

        roles.put("ROLE_ADMIN","Administrador");
        roles.put("ROLE_USER","Usuario");
        roles.put("ROLE_MODERATOR","Moderador");

        return roles;
    }

    @ModelAttribute("paises")
    public List<String> paises(){
        return Arrays.asList("España", "Mexico", "Chile", "Argentina","Peru", "Colombia","Venezuela");
    }

    @ModelAttribute("paisesMap")
    public Map<String, String> paisesMap(){

        Map<String, String> paises = new HashMap<String, String>();

        paises.put("ES","España");
        paises.put("MX","Mexico");
        paises.put("CL","Chile");
        paises.put("AR","Argentina");
        paises.put("PE","Perú");
        paises.put("CO","Colombia");
        paises.put("VE","Venezuela");

        return paises;
    }

    @GetMapping("/form")
    public String form(Model model){

        Usuario usuario = new Usuario();

        usuario.setNombre("Daniel");
        usuario.setApellido("Castle");
        usuario.setIdentificador("123.456.789-K");
        usuario.setHabilitar(true);
        usuario.setValorSecreto(" Algun valor secreto ****");
        usuario.setPais(  new Pais( 6, "CO","Colombia"));
        usuario.setRoles(Arrays.asList( new Role (2, "Usuario"," ROLE_USER")));


        model.addAttribute("titulo", "Formulario de usuarios");
        model.addAttribute("usuario", usuario);

        return "form";
    }

    @PostMapping("/form")
    public String procesar(@Valid  Usuario usuario, BindingResult result, Model model){
        //validador.validate( usuario, result);

        if( result.hasErrors()){
            model.addAttribute("titulo", "Resultado del form");
            return "form";
        }

        return "redirect:/ver";
    }
    
    @GetMapping("/ver")
    public String ver(@SessionAttribute(name="usuario", required = false) Usuario usuario, Model model, SessionStatus status){

        if( usuario == null ){
            return "redirect:/form";
        }

        model.addAttribute("titulo", "Resultado del form");
        status.setComplete();
        return "resultado";
    }

}
