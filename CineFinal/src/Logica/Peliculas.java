/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import datos.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author AUXSISTEMAS
 */
public class Peliculas extends Conexion {
    
    private int IdPelicula;
    private String Nombre;
    private String Categoria;
    private String Duracion;
    
    public int getIdPelicula() {
        return IdPelicula;
    }
    
    public void setIdPelicula(int IdPelicula) {
        this.IdPelicula = IdPelicula;
    }
    
    public String getNombre() {
        return Nombre;
    }
    
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public String getCategoria() {
        return Categoria;
    }
    
    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }
    
    public String getDuracion() {
        return Duracion;
    }
    
    public void setDuracion(String Duracion) {
        this.Duracion = Duracion;
    }
    
    public Peliculas() {
        Collector.listaPeliculas = new ArrayList<>();
    }
    
    public Peliculas(int IdPelicula, String Nombre, String Categoria, String Duracion) {
        this.IdPelicula = IdPelicula;
        this.Nombre = Nombre;
        this.Categoria = Categoria;
        this.Duracion = Duracion;
    }
    
    public void registrar() {
        try {
            String Val = validar(this.getNombre()) ;
            if (Val.equals("")) {//Validamos que la pelicula no exista.
                Connection con = abrir(); //Abrimos y obtenemos el valor de la Conexión
                con.setAutoCommit(false); //Indicamos que "no" se "auto" confirmará la transacción

                PreparedStatement ps = con.prepareStatement("insert into peliculas (Nombre, Categoria, Duracion) values (?,?,?)");
                ps.setString(1, this.getNombre().toUpperCase()); //Asignar valor a los símbolos : "?"
                ps.setString(2, this.getCategoria());
                ps.setString(3, this.getDuracion());
                
                ejecutarTS(ps, con); //Ejecutar la transacción

                con.commit(); //Confirmar la transacción
                con.close(); //Liberar de la memoria
                ps.close();
            }
            else{
                JOptionPane.showMessageDialog(null,"la pelicula ya existe con ese nombre :"+Val);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void actualizar() {
        try {
            //Preparamos un script
            String sql = " update peliculas set Nombre = ?, Categoria = ?,  Duracion = ? where IdPelicula = ?";
            Connection con = abrir(); //Abrimos y obtenemos el valor de la Conexión
            con.setAutoCommit(false); //Indicamos que "no" se "auto" confirmará la transacción

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, this.getNombre().toUpperCase()); //Asignar valor a los símbolos : "?"
            ps.setString(2, this.getCategoria());
            ps.setString(3, this.getDuracion());
            ps.setInt(4, this.getIdPelicula());
            
            ejecutarTS(ps, con); //Ejecutar la transacción

            con.commit(); //Confirmar la transacción
            con.close(); //Liberar de la memoria
            ps.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void listar() {
        try {
            //Preparar la consulta 
            PreparedStatement ps = abrir().prepareStatement("select * from peliculas");
            //La consulta devuelve filas y columnas. Por lo tanto, lo almacenamos en una variable ResultSet
            ResultSet rs = ejecutarSP(ps); //Ejecutar procedimiento almacenado o vista

            //Mientras existan filas (rs.next())
            while (rs.next()) {
                //Agregar a la Colección de Objetos
                Collector.listaPeliculas.add(new Peliculas(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4))
                );
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Método para eliminar
    public void eliminar() {
        try {
            Connection con = abrir(); //Abrimos y obtenemos el valor de la Conexión
            con.setAutoCommit(false); //Indicamos que "no" se "auto" confirmará la transacción

            PreparedStatement ps = con.prepareStatement("delete from peliculas where IdPelicula = ?");
            ps.setInt(1, this.getIdPelicula()); //Asignar valor a los símbolos : "?"

            ejecutarTS(ps, con); //Ejecutar la transacción

            con.commit(); //Confirmar la transacción
            con.close(); //Liberar de la memoria
            ps.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public String validar(String Nombre) {
        try {
            //Preparar la consulta 
            PreparedStatement ps = abrir().prepareStatement("select Nombre from peliculas where Nombre like '%" + Nombre + "%'");
            ResultSet rs = ejecutarSP(ps);
            boolean Valida = false;
            String NmPel ="";
            while (rs.next()) {
                NmPel= rs.getString("Nombre");
                // son iguales
                if (Nombre.equalsIgnoreCase(NmPel)) {
                    Valida = true;
                    break;
                }
            }
            if(Valida == true){
                return NmPel;
            }
            else{
                return "";
            }
        } catch (Exception e) {
            JOptionPane.showInputDialog((e));
            return "";
        }
    }
    
    /**
     * Desc: retorna falso si la pelicula que se va a eliminar esta en cartelera.
     * @return 
     */
    public boolean PeliculaEnCartelera(){
        try {
            //Preparar la consulta 
            boolean ValidaCar = true;
            PreparedStatement ps = abrir().prepareStatement("select IdPelicula from cartelera where IdPelicula = "+getIdPelicula());
            //La consulta devuelve filas y columnas. Por lo tanto, lo almacenamos en una variable ResultSet
            ResultSet rs = ejecutarSP(ps); //Ejecutar procedimiento almacenado o vista

            //Mientras existan filas (rs.next())
            int Valida=0;
            while (rs.next()) {
                //Agregar a la Colección de Objetos
                Valida = rs.getInt(1);
            }
            if(Valida >0){
                ValidaCar = false;
            }
            return ValidaCar;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
