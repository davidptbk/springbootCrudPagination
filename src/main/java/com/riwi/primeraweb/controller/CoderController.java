package com.riwi.primeraweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.riwi.primeraweb.entity.Coder;
import com.riwi.primeraweb.service.CoderService;

@Controller
//@RequestMapping("/") crea la ruta donde se activara este controlador
@RequestMapping("/")
public class CoderController {
    //@Autowired inyeccion de dependencias
    @Autowired
    private CoderService objCoderService;

    //Metodo para mostrar la vista y enviarle toda la lista de coders
    @GetMapping
    public String showViewCoder(Model objModel, 
            @RequestParam(defaultValue = "1")int page,
            @RequestParam(defaultValue = "2")int size){
        //Obtenemos la lista de coders
       Page<Coder> listCoders = this.objCoderService.findAllPaginate(page -1,size);
        
        //Cargamos la lista en el modelo
        objModel.addAttribute("listCoders", listCoders);
        objModel.addAttribute("currentPage", page);
        objModel.addAttribute("totalPage", listCoders.getTotalPages());

        return "viewCoders";
    }

    //Metodo para mostrar la vista de formulario y ademas enviar una instancia de Coder vacia
    @GetMapping("form")
    public String showViewForm(Model model){
        model.addAttribute("coder", new Coder());
        model.addAttribute("action", "create-coder");
        return "viewForm";
    }

    //Metodo para recibir toda la info del formulario
    @PostMapping("create-coder")
    //@ModelAttribute lo utilizamos para reibir informacion de la vista
    public String createCoder(@ModelAttribute Coder objCoder){
        this.objCoderService.create(objCoder);
        return "redirect:/";
    }

//Creamos un controlador para eliminar que recibe como parameto el id por URL
    @GetMapping("/delete/{id}")
//@PathVariable funciona para obtener el valor de una variable en la url 
//solo si es de tipo path(ejemplo: /delete/10) donde el 10 es dinamico

    public String deleteCoder(@PathVariable Long id){
        this.objCoderService.delete(id);
        return "redirect:/";
    }
    @GetMapping("/update/{id}")
    public String updateCoder(@PathVariable Long id, Model model){
        Coder objCoder = this.objCoderService.findById(id);
        model.addAttribute("coder", objCoder);
        model.addAttribute("action", "/edit/"+ id);
        return "viewForm";
    }

    @PostMapping("edit/{id}")
    public String updateCoder(@PathVariable Long id, @ModelAttribute Coder obCoder){
        this.objCoderService.update(id, obCoder);
        return "redirect:/";
    }
}
