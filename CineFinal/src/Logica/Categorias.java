/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;
import datos.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author AUXSISTEMAS
 */
public class Categorias extends Conexion{
    private int IdCategoria;
    private String NmCategoria;
    
    public int getIdCategoria() {
        return IdCategoria;
    }
    
    public void setIdCategoria(int IdCategoria) {
        this.IdCategoria = IdCategoria;
    }
    
    
    public String getNmCategoria() {
        return NmCategoria;
    }
    
    public void setNmCategoria(String NmCategoria) {
        this.NmCategoria = NmCategoria;
    }
    
    public Categorias(){
        Collector.listaCategorias = new ArrayList<>();
    }
    
    public Categorias(String NmCategoria){
        this.NmCategoria = NmCategoria;
    }
    
    public  Categorias(int IdCategoria, String NmCategoria){
        this.IdCategoria = IdCategoria ;
        this.NmCategoria = NmCategoria;
    }
    
    //Método para Listar
    public void listar() {
        try {
            //Ejecutar un consulta en la base de datos. 
            //La consulta devuelve filas y columnas. Por lo tanto, lo almacenamos en una variable ResultSet
            ResultSet rs = ejecutarSQL("select * from categorias");

            //Mientras existan filas (rs.next())
            while (rs.next()) {
                //Entonces agregamos a la Colección de Objetos
                Collector.listaCategorias.add(new Categorias(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
