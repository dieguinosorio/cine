package datos;

import java.sql.*;

public class Conexion {

    //Creamos los atributos de conexión
    private String driver = "com.mysql.jdbc.Driver";
    private String jdbc = "jdbc:mysql://localhost:3306/cine";
    private String user = "root";
    private String pass = "";
    private Connection conex = null;

    /**
     * Des : Metodo abrir conexion
     * @return
     * @throws Exception 
     */
    public  Connection abrir() throws Exception {
        if (conex == null) { //Si la conexión está cerrada
            Class.forName(driver); //Registra el driver de conexión
            conex = DriverManager.getConnection(jdbc, user, pass); //Crear nueva conexión
        }

        return conex;
    }

    /**
     * Desc: Metodo cerrar conexion
     * @throws Exception 
     */
    public void cerrar() throws Exception {
        if (conex != null) { //Si la conexión está abierta
            conex = null; //Cerramos la conexión
        }
    }

    /**
     * Des:Metodo que realiza consultas: SELECT 
     * @param sql
     * @return
     * @throws Exception 
     */
    public ResultSet ejecutarSQL(String sql) throws Exception {
        ResultSet rs = null;
        Statement st = null;

        st = abrir().createStatement(); //Crear o preparar la sentencia
        rs = st.executeQuery(sql); //Ejecutar la sentencia

        cerrar();
        return rs;
    }

    /**
     * Des: Metodo que ejecuta acciones como: INSERT, UPDATE, DELETE Y además puede ejecutar procedimientos almacenados, vistas, y otros objetos.
     * @param sentencia
     * @return
     * @throws Exception 
     */
    public ResultSet ejecutarSP(PreparedStatement sentencia) throws Exception {
        ResultSet rs = null;
        abrir();

        rs = sentencia.executeQuery(); //Ejecutar la sentencia

        cerrar();
        return rs;
    }

    /**
     * Des: Metodo que ejecuta transacciones.
     * @param sentencia
     * @param con
     * @return
     * @throws Exception 
     */
    public int ejecutarTS(PreparedStatement sentencia, Connection con) throws Exception {
        int fila = 0;
        try {
            fila = sentencia.executeUpdate(); //Ejecutar la sentencia
            //con.commit(); //Confirmar la Transacción
        } catch (Exception e) {
            con.rollback(); //En caso ocurra algún error, entonces "deshacer" todos los cambios
            throw e;
        }
        return fila;
    }
}
