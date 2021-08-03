package com.bolsadeideas.springboot.form.app.controllers;



import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
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

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(validador);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(dateFormat, false) );
        binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor());
        binder.registerCustomEditor(String.class, "apellido", new NombreMayusculaEditor());
    }


    @ModelAttribute("listaPaises")
    public List<Pais> listaPaises(){
        return Arrays.asList(
        new Pais( 1, "ES","España"),
        new Pais( 2, "MX","Mexico"),
        new Pais( 3, "CL","Chile"),
        new Pais( 4, "AR","Argentina"),
        new Pais( 5, "PE","Perú"),
        new Pais( 6, "CO","Colombia"),
        new Pais( 7, "VE","Venezuela")
        );
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


        model.addAttribute("titulo", "Formulario de usuarios");
        model.addAttribute("user", usuario);

        return "form";
    }

    @PostMapping("/form")
    public String procesar(@Valid @ModelAttribute("user") Usuario usuario, BindingResult result, Model model, SessionStatus status){


        //validador.validate( usuario, result);

        model.addAttribute("titulo", "Resultado del form");


        if( result.hasErrors()){

            return "form";
        }

        model.addAttribute("user", usuario);
        status.setComplete();

        return "resultado";
    }

}
