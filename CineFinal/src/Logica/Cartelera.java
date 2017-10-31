/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Mapeo de la entidad cartelera que vamos a persistir en la base de datos con el metodo get / set.
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
public class Cartelera extends Conexion {
    
    private int IdCartelera;
    private int IdSala;
    private int IdPelicula;
    private String AplicaProm;
    private String Activa;
    private int CantOcupadaSala;
    private int AsientosPref;
    private int NroSala;
    private int Capacidad;
    private String HoraApertura;
    private String Tecnologia;
    private String Nombre;
    private String Duracion;
    private String Categoria;

    public int getIdCartelera() {
        return IdCartelera;
    }

    public void setIdCartelera(int IdCartelera) {
        this.IdCartelera = IdCartelera;
    }

    public int getIdSala() {
        return IdSala;
    }

    public void setIdSala(int IdSala) {
        this.IdSala = IdSala;
    }

    public int getIdPelicula() {
        return IdPelicula;
    }

    public void setIdPelicula(int IdPelicula) {
        this.IdPelicula = IdPelicula;
    }

    public String getAplicaProm() {
        return AplicaProm;
    }

    public void setAplicaProm(String AplicaProm) {
        this.AplicaProm = AplicaProm;
    }

    public String getActiva() {
        return Activa;
    }

    public void setActiva(String Activa) {
        this.Activa = Activa;
    }

    public int getNroSala() {
        return NroSala;
    }

    public void setNroSala(int NroSala) {
        this.NroSala = NroSala;
    }

    public int getCapacidad() {
        return Capacidad;
    }

    public void setCapacidad(int Capacidad) {
        this.Capacidad = Capacidad;
    }

    public String getHoraApertura() {
        return HoraApertura;
    }

    public void setHoraApertura(String HoraApertura) {
        this.HoraApertura = HoraApertura;
    }

    public String getTecnologia() {
        return Tecnologia;
    }

    public void setTecnologia(String Tecnologia) {
        this.Tecnologia = Tecnologia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDuracion() {
        return Duracion;
    }

    public void setDuracion(String Duracion) {
        this.Duracion = Duracion;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCantOcupadaSala(int CantOcupadaSala) {
        this.CantOcupadaSala = CantOcupadaSala;
    }

    public int getCantOcupadaSala() {
        return CantOcupadaSala;
    }

    public void setAsientosPref(int AsientosPref) {
        this.AsientosPref = AsientosPref;
    }

    public int getAsientosPref() {
        return AsientosPref;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public Cartelera() {
        Collector.ListaCartelera = new ArrayList<>();
    }

    public Cartelera(int IdCartelera, int IdSala, int IdPelicula, String AplicaProm, String Activa, int NroSala, int Capacidad, String HoraApertura, String Tecnologia, String Nombre, String Duracion, String Categoria, int CantOcupadaSala, int AsientosPref) {
        this.IdCartelera = IdCartelera;
        this.IdSala = IdSala;
        this.IdPelicula = IdPelicula;
        this.AplicaProm = AplicaProm;
        this.Activa = Activa;
        this.NroSala = NroSala;
        this.Capacidad = Capacidad;
        this.HoraApertura = HoraApertura;
        this.Tecnologia = Tecnologia;
        this.Nombre = Nombre;
        this.Duracion = Duracion;
        this.Categoria = Categoria;
        this.CantOcupadaSala = CantOcupadaSala;
        this.AsientosPref = AsientosPref;
    }

    /**
     * Des:Llena el array list "Collector con el que se va a llenar la tabla de
     * cartelera"
     */
    public void listar(int Op) {
        try {
            //Preparar la consulta 
            PreparedStatement ps = null;
            if (Op == 1) {
                 ps = abrir().prepareStatement("SELECT IdCartelera,cartelera.IdSala,cartelera.IdPelicula,if(AplicaProm>0,'SI','NO'),if(Activa >0,'SI','NO'),NroSala,Capacidad,HoraApertura,Tecnologia,Nombre , Duracion, Categoria,(Capacidad - CantOcupadaSala) as CantOcupadaSala,AsientosPref\n"
                        + "FROM `cartelera` \n"
                        + "LEFT JOIN salascn ON salascn.IdSala = cartelera.IdSala\n"
                        + "LEFT JOIN peliculas ON peliculas.IdPelicula = cartelera.IdPelicula\n"
                        + "WHERE cartelera.Activa = 1");
            } else if(Op ==2){
                ps = abrir().prepareStatement("SELECT IdCartelera,cartelera.IdSala,cartelera.IdPelicula,if(AplicaProm>0,'SI','NO'),if(Activa >0,'SI','NO'),NroSala,Capacidad,HoraApertura,Tecnologia,Nombre , Duracion, Categoria,(Capacidad - CantOcupadaSala) as CantOcupadaSala,AsientosPref\n"
                        + "FROM `cartelera` \n"
                        + "LEFT JOIN salascn ON salascn.IdSala = cartelera.IdSala\n"
                        + "LEFT JOIN peliculas ON peliculas.IdPelicula = cartelera.IdPelicula\n"
                        + "WHERE 1");
            }
            //La consulta devuelve filas y columnas. Por lo tanto, lo almacenamos en una variable ResultSet
            ResultSet rs = ejecutarSP(ps); //Ejecutar procedimiento almacenado o vista

            //Mientras existan filas (rs.next())
            while (rs.next()) {
                //Agregar a la Colección de Objetos
                Collector.ListaCartelera.add(new Cartelera(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getInt(13), rs.getInt(14)));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void ListarSalas() {
        try {
            Collector.ListaSalas = new ArrayList<>();

            //Preparar la consulta 
            PreparedStatement ps = abrir().prepareStatement("select IdSala,NroSala from salascn");
            ResultSet rs = ejecutarSP(ps); //Ejecutar procedimiento almacenado o vista

            //Mientras existan filas (rs.next())
            while (rs.next()) {
                //Agregar a la Colección de Objetos
                Collector.ListaSalas.add(rs.getString(1) + "-#" + rs.getString(2));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Des: Actualiza la cantidad de sillas disponibles preferenciales y
     * normales.
     *
     * @param Cant
     * @param CantPref
     * @param IdSala
     * @return
     */
    public boolean actualizarSalas(String Cant, String CantPref, int IdSala) {
        try {
            //Preparamos un script
            String sql = "";
            if (CantPref != "0") {
                sql = " update salascn set CantOcupadaSala = CantOcupadaSala +" + Cant + " ,AsientosPref = AsientosPref-" + CantPref + " where IdSala =" + IdSala;
            } else {
                sql = " update salascn set CantOcupadaSala = CantOcupadaSala +" + Cant + "  where IdSala = " + IdSala;
            }
            Connection con = abrir(); //Abrimos y obtenemos el valor de la Conexión
            con.setAutoCommit(false); //Indicamos que "no" se "auto" confirmará la transacción
            PreparedStatement ps = con.prepareStatement(sql);
            ejecutarTS(ps, con); //Ejecutar la transacción
            con.commit(); //Confirmar la transacción
            con.close(); //Liberar de la memoria
            ps.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

     public void actualizar() {
        try {
            //Preparamos un script
            String sql = " update cartelera set IdSala = ?, IdPelicula = ?,  AplicaProm = ? ,Activa = ?  where IdCartelera = ?";
            Connection con = abrir(); //Abrimos y obtenemos el valor de la Conexión
            con.setAutoCommit(false); //Indicamos que "no" se "auto" confirmará la transacción

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, this.getIdSala()); //Asignar valor a los símbolos : "?"
            ps.setInt(2, this.getIdPelicula());
            ps.setString(3, this.getAplicaProm());
            ps.setString(4, this.getActiva());
            ps.setInt(5,this.getIdCartelera());
            
            ejecutarTS(ps, con); //Ejecutar la transacción

            con.commit(); //Confirmar la transacción
            con.close(); //Liberar de la memoria
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
}
