package com.bolsadeideas.springboot.form.app.controllers;



import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("usuario")
public class FormController {

    @Autowired
    private UsuarioValidador validador;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(validador);
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
