package com.eforklift.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.eforklift.dto.Item;



public class ConexionUtil {
	
	public Connection getConnection()// throws ClassNotFoundException, SQLException
    {
        //Change these settings according to your local configuration
		Connection conn =null;
        try{

       
		String driver =ReadPropertiesUtil.obtenerProperty("com.eforklift.util.driver");// "com.mysql.jdbc.Driver";
        String connectString = ReadPropertiesUtil.obtenerProperty("com.eforklift.util.connString");//"jdbc:mysql://localhost:3306/eforkliftitems";
        String user = ReadPropertiesUtil.obtenerProperty("com.eforklift.util.user");//"root";
        String password = ReadPropertiesUtil.obtenerProperty("com.eforklift.util.pass");//"admin";
        System.out.println("driver: "+driver+" connectString: "+connectString+" user: "+user+" password: "+password);
        Class.forName(driver);
        conn = DriverManager.getConnection(connectString, user, password);
      System.out.println("llega mySql");

//        	String driver = "com.mysql.jdbc.Driver";
//            String connectString = "jdbc:mysql://localhost:3306/eforkliftitems";
//            String user = "root";
//            String password = "admin";
//            Class.forName(driver);
//            conn = DriverManager.getConnection(connectString, user, password);
          System.out.println("llega mySql");
      
        }
        
        
        catch(Exception e)
        {e.printStackTrace();}
          return conn;
    }
	
	public static void main(String args[]){
		ConexionUtil conn = new ConexionUtil();
//		conn.getConnection();
		conn.cargarListaItemsDao2();
		
	}
	
public void  cargarListaItemsDao2(){
		
		List<Item> listItems= new ArrayList<Item>();
		CallableStatement clsCall ;
		PreparedStatement prs = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			conn= getConnection();
			if(conn != null){
				System.out.println("connection is null??"+conn);
//				clsCall = conn.prepareCall("{call EFORK_LISTA_ITEMS}");//
				prs = conn.prepareStatement("{call EFORK_LISTA_ITEMS}");//
//				clsCall.setInt(1, Pv_id_veh);
				
				prs.execute();
				rs = prs.getResultSet();

				Item item = new Item();
				
				while (rs.next()) {
					System.out.println("name: "+rs.getString("NAME"));
//					item= new Item();
//					
//					
//					item.setIdItem(rs.getInt("ID"));
//					item.setName(rs.getString("NAME"));
//					item.setPartNumber(rs.getString("PARTNUMBER"));
//					item.setDescripcion(rs.getString("DESCRIPTION"));
//					item.setPrice(rs.getDouble("PRICE"));
//					item.setShowPrice(rs.getInt("SHOWPRICE"));
//					item.setManufactrer(rs.getInt("MANUFACTURER"));
//					item.setSerie(rs.getString("SERIE"));
//					item.setModels(rs.getString("MODELS"));
//					item.setNote(rs.getString("NOTE"));
//					item.setPartNumber2(rs.getString("PARTNUMBER2"));
//					
//					listItems.add(item);
					
				}
				//if(rs != null) rs.close();
				//if(clsCall != null) clsCall.close();
				
			}		
		}
		catch(Exception e)
		{e.printStackTrace();
		}finally {
			if(conn != null){
				try {
					conn.close();
//					 if(clsCall!=null)clsCall.close();
					if(rs!=null)rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	
		
	}
	

}
