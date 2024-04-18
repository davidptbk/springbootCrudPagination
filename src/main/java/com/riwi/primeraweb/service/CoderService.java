package com.riwi.primeraweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.riwi.primeraweb.entity.Coder;
import com.riwi.primeraweb.repository.CoderRepository;
//el service se encarga de mandarle la pelota al repository que haga las acciondes de CRUD
@Service
public class CoderService {
//@Autowired se usa para deifnir una inyeccion de dependencias (llamar un objeto de clase CoderRepository en CoderService)
    @Autowired
    private CoderRepository objCoderRepository;

    public List<Coder> findAll(){
        
        return this.objCoderRepository.findAll();
    }

    public Page<Coder> findAllPaginate(int page, int size){
        //validar que la pagina no sea menor a 0
        if(page <0){
            page=0;
        }

        //crear la paginacion
        Pageable objPage = PageRequest.of(page, size);

        return this.objCoderRepository.findAll(objPage);
    }
//Metodo para crear un nuevo coder, se hace uso del repositorio y del metodo save
    public Coder create(Coder objCOder){
        //el metood save se encarga de insertar en la base de datos y es por defecto de Spring
        return this.objCoderRepository.save(objCOder);
    }
//Metodo para elimina run coder (deleteById)
    public void delete(Long id){
//el metodo deleteById se encarga de insertar en la base de datos y es por defecto de Spring
        this.objCoderRepository.deleteById(id);
    }
/** 
 * Metodo para obtener por Id el
 */ 
    public Coder findById(Long id){
        //busca un coder por id y lo retorna y si no lo encuentra devuelve null
        return this.objCoderRepository.findById(id).orElse(null);
    }
//metodo para actualizar un coder
    public Coder update(Long id, Coder coder){
        //1 buscar el coder por id
        Coder objCoderDB = this.findById(id);
        //2 verificamos que el coder exista
        if (objCoderDB != null) {
            //3 Actualizamos el coder antiguo
            objCoderDB = coder;
            //El metodo save verifica si el objeto tiene la llave primaria Actualiza
            //Si la tiene vacia crea un nuevo registro
            return this.objCoderRepository.save(objCoderDB);
        }else{
            return null;
        }
    }

}
