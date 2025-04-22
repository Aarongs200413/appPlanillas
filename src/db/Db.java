package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Db {
	
	Connection conn=null;
	PreparedStatement ps=null;
	String _IP="localhost",_PORT="3306",_USER="root",_PASSWORD="",_DB=null,_SQL=null;
	
    public Db(String db) {
    	this._DB=db;
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		conn=DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s",_IP,_PORT,_DB),_USER,_PASSWORD);
    	}catch (ClassNotFoundException | SQLException e ){ e.printStackTrace();}
    }

    public void Sentencia(String sql) {
    	if(conn == null) return;
    	this._SQL=sql;
    	try {
    		ps=conn.prepareStatement(sql);
    	}catch (SQLException e ){e.printStackTrace();}
    }

    public String[] getRegistro() {
        if(conn == null || ps==null) return null;
       
        try {
        	ResultSet rs = ps.executeQuery();
        	if(rs.next()) {
        		String[] aRegistro=new String[rs.getMetaData().getColumnCount()];
        		for(int i=0, columnas = aRegistro.length; i< columnas; i++)
        			aRegistro[i]=rs.getString(i+1);
        		
        		return aRegistro;
        	}
        	
        }catch(SQLException e) {e.printStackTrace();}
        
        return null;
    }
}