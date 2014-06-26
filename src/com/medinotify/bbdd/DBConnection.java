package com.medinotify.bbdd; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    static String host      = "192.168.0.201"; //"10.0.2.2"; //Poner 10.0.0.2 cuando el serv. MySQL es LocalHost
    static String baseDatos = "m1.infv.es";
    static String usuario   = "diego";
    static String password  = "12345";
    static String cadCon	= "jdbc:mysql://"+host+"/"+baseDatos;

    public static Connection c;
    public static Statement st;
    /**
     * Crea la conexion con la BBD
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws java.sql.SQLException
     */
    public static Connection crearConexion() throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        Class.forName( "com.mysql.jdbc.Driver").newInstance();

        try{
            c = DriverManager.getConnection(cadCon, usuario, password);
            st = c.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return c;

    }

    /**
     * Cierra la conexion con la BBDD
     */
    public static void cerrarConexion() {
        try {
            if (st != null) {
                st.close();
            }
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
        }
    }

    public ResultSet executeQuery(String q) {
        ResultSet rs = null;
        try {
             rs = st.executeQuery(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

	public PreparedStatement prepareStatement(String sQLGetAllMedicines) {
		// TODO Auto-generated method stub
		return null;
	}
}
