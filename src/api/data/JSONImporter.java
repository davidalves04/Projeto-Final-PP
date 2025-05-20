/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.data;

import com.fasterxml.jackson.databind.ObjectMapper; //Biblioteca para dar import ou export de JSON
import java.io.File;
import java.io.IOException;


/**
 *
 * @author david
 */
public class JSONImporter {

    private ObjectMapper mapper;

    public JSONImporter() {
        this.mapper = new ObjectMapper();
    }

        public void ImportJSON(String caminho, Class classe) {
        try {
            Object obj = mapper.readValue(new File(caminho), classe);
            System.out.println("Objeto importado: " + obj);
        } catch (IOException e) {
            e.printStackTrace(); // ou logar de outra forma
        }
    }

}