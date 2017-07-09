package com.eforklift.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






import com.eforklift.dto.Item;
import com.eforklift.dto.UsuarioSistema;
import com.eforklift.util.ConexionUtil;
import com.eforklift.util.ConfiguracionDto;
import com.eforklift.util.Manufacturer;




public class CargarListaItemsDao extends ConexionUtil {
	
	
	
	
	public Map<String, Object>  editarConfiguration( ConfiguracionDto conf)
	{	
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		String param="";
//		CallableStatement clsCall = null;
		PreparedStatement prs = null;
		ResultSet rss = null;
		Connection conn = null;
		try {
			conn= getConnection();
			if(conn != null){

				prs = conn.prepareStatement("{call FORK_EDITAR_CONF(?,?,?,?,?,?,?,?)}");
				prs.setString(1, conf.getCompanyName());
				prs.setString(2, conf.getCompanyAddress());
				prs.setString(3, conf.getCompanyPhone());
				
				prs.setString(4, conf.getCompanyEmail());
				prs.setString(5, conf.getEmailEnvia());
				prs.setString(6, conf.getEmailSmtp());
				
				prs.setString(7, conf.getEmailPassword());
				prs.setInt(8, conf.getId());
				
				
				
				prs.execute();
				rss = prs.getResultSet();
				
				if(rss != null) rss.close();
				if(prs != null) prs.close();
				mapReturn.put("error", null);
				
			}else{
				mapReturn.put("error", "Error en la conexión, favor comuníquese con el departamento de Informática.");
			}
		
		}
		catch(Exception e)
		{e.printStackTrace();
		mapReturn.put("error", e.getMessage());}
		finally{
			try {conn.close();} catch (SQLException e1) {e1.printStackTrace();}
			try {if(rss!=null)rss.close();} catch (SQLException e) {	e.printStackTrace();}
			try {if(prs!=null)prs.close();} catch (SQLException e) {	e.printStackTrace();}
		}
		
		return mapReturn;
	}
	
	public List<Manufacturer> cargarManufacturer(){
		
		List<Manufacturer> listManufacturer= new ArrayList<Manufacturer>();
//		CallableStatement clsCall ;
		PreparedStatement prs = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			conn= getConnection();
			if(conn != null){
				System.out.println("connection is null??"+conn);
				prs = conn.prepareStatement("{call EFORK_LISTA_MANUFACTURER}");
				
				
				prs.execute();
				rs = prs.getResultSet();

				Manufacturer obj ;
				
				while (rs.next()) {
					obj= new Manufacturer();
					
					obj.setId(rs.getInt("ID"));
					obj.setManufacturerName(rs.getString("MANUFACTURER_NAME"));
					
					
					listManufacturer.add(obj);
					
				}
				if(rs != null) rs.close();
				if(prs != null) prs.close();
				
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
		
		return listManufacturer;
		
		
	}
	
	public List<Item>  cargarListaItemsUserDao(int idUser){
		
		List<Item> listItems= new ArrayList<Item>();
//		CallableStatement clsCall ;
		PreparedStatement prs = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			conn= getConnection();
			if(conn != null){
				System.out.println("connection is null??"+conn);
				prs = conn.prepareStatement("{call EFORK_LISTA_ITEMS_USERS(?)}");
				prs.setInt(1, idUser);
				
				prs.execute();
				rs = prs.getResultSet();

				Item item = new Item();
				
				while (rs.next()) {
					item= new Item();
					
					
					item.setIdItem(rs.getInt("ID"));
					item.setName(rs.getString("NAME"));
					item.setPartNumber(rs.getString("PARTNUMBER"));
					item.setDescripcion(rs.getString("DESCRIPTION"));
					item.setPrice(rs.getDouble("PRICE"));
					item.setShowPrice(rs.getInt("SHOWPRICE"));
					item.setManufactrer(rs.getInt("MANUFACTURER"));
					item.setSerie(rs.getString("SERIE"));
					item.setModels(rs.getString("MODELS"));
					item.setNote(rs.getString("NOTE"));
					item.setPartNumber2(rs.getString("PARTNUMBER2"));
					item.setManufacturerName(rs.getString("MANUFACTURER_NAME"));
					item.setIdUser(rs.getInt("ID_USER"));
					item.setQuantity(rs.getInt("QUANTITY"));
					
					listItems.add(item);
					
				}
				if(rs != null) rs.close();
				if(prs != null) prs.close();
				
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
		
		return listItems;
		
	}
	
public List<Item>  cargarListaItemsDao(){
		
		List<Item> listItems= new ArrayList<Item>();
//		CallableStatement clsCall ;
		PreparedStatement prs = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			conn= getConnection();
			if(conn != null){
				System.out.println("connection is null??"+conn);
				prs = conn.prepareStatement("{call EFORK_LISTA_ITEMS}");
//				clsCall.setInt(1, Pv_id_veh);
				
				prs.execute();
				rs = prs.getResultSet();

				Item item = new Item();
				
				while (rs.next()) {
					item= new Item();
					
					
					item.setIdItem(rs.getInt("ID"));
					item.setName(rs.getString("NAME"));
					item.setPartNumber(rs.getString("PARTNUMBER"));
					item.setDescripcion(rs.getString("DESCRIPTION"));
					item.setPrice(rs.getDouble("PRICE"));
					item.setShowPrice(rs.getInt("SHOWPRICE"));
					item.setManufactrer(rs.getInt("MANUFACTURER"));
					item.setSerie(rs.getString("SERIE"));
					item.setModels(rs.getString("MODELS"));
					item.setNote(rs.getString("NOTE"));
					item.setPartNumber2(rs.getString("PARTNUMBER2"));
					item.setManufacturerName(rs.getString("MANUFACTURER_NAME"));
					item.setQuantity(rs.getInt("QUANTITY"));
					
					listItems.add(item);
					
				}
				if(rs != null) rs.close();
				if(prs != null) prs.close();
				
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
		
		return listItems;
		
	}

public List<Item>  cargarListaItemsUserDao(String param, int id_user){
	
	List<Item> listItems= new ArrayList<Item>();
//	CallableStatement clsCall = null;
	PreparedStatement prs = null;
	ResultSet rs = null;
	Connection conn = null;
	
	try {
		conn= getConnection();
		if(conn != null){
			prs = conn.prepareStatement("{call EFORK_LISTA_ITEMS_USER(?,?)}");
			if(param!=null)param=param.toUpperCase();
			prs.setString(1, param);
			prs.setInt(2, id_user);
			
			prs.execute();
			rs = prs.getResultSet();

			Item item = new Item();
			
			while (rs.next()) {
				item= new Item();
				
				
				item.setIdItem(rs.getInt("ID"));
				item.setName(rs.getString("NAME"));
				item.setPartNumber(rs.getString("PARTNUMBER"));
				item.setDescripcion(rs.getString("DESCRIPTION"));
				item.setPrice(rs.getDouble("PRICE"));
				item.setShowPrice(rs.getInt("SHOWPRICE"));
				item.setManufactrer(rs.getInt("MANUFACTURER"));
				item.setSerie(rs.getString("SERIE"));
				item.setModels(rs.getString("MODELS"));
				item.setNote(rs.getString("NOTE"));
				item.setPartNumber2(rs.getString("PARTNUMBER2"));
				item.setManufacturerName(rs.getString("MANUFACTURER_NAME"));
				
				listItems.add(item);
				
			}
			if(rs != null) rs.close();
			if(prs != null) prs.close();
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
		if(conn != null){
			try {
				conn.close();
				if(prs != null) prs.close();
				if(rs!=null)rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	return listItems;
	
}
	

public List<Item>  cargarListaItemsParamDao(String param){
	
	List<Item> listItems= new ArrayList<Item>();
//	CallableStatement clsCall = null;
	PreparedStatement prs = null;
	ResultSet rs = null;
	Connection conn = null;
	
	try {
		conn= getConnection();
		if(conn != null){
			prs = conn.prepareStatement("{call EFORK_LISTA_ITEMS_PARAM(?)}");
			if(param!=null)param=param.toUpperCase();
			prs.setString(1, param);
			
			prs.execute();
			rs = prs.getResultSet();

			Item item = new Item();
			
			while (rs.next()) {
				item= new Item();
				
				
				item.setIdItem(rs.getInt("ID"));
				item.setName(rs.getString("NAME"));
				item.setPartNumber(rs.getString("PARTNUMBER"));
				item.setDescripcion(rs.getString("DESCRIPTION"));
				item.setPrice(rs.getDouble("PRICE"));
				item.setShowPrice(rs.getInt("SHOWPRICE"));
				item.setManufactrer(rs.getInt("MANUFACTURER"));
				item.setSerie(rs.getString("SERIE"));
				item.setModels(rs.getString("MODELS"));
				item.setNote(rs.getString("NOTE"));
				item.setPartNumber2(rs.getString("PARTNUMBER2"));
				item.setManufacturerName(rs.getString("MANUFACTURER_NAME"));
				
				listItems.add(item);
				
			}
			if(rs != null) rs.close();
			if(prs != null) prs.close();
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	}finally {
		if(conn != null){
			try {
				conn.close();
				if(prs != null) prs.close();
				if(rs!=null)rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	return listItems;
	
}



public Map<String, Object>  guatdarItem( Item item)
{	
	Map<String, Object> mapReturn = new HashMap<String, Object>();
	String param="";
//	CallableStatement clsCall = null;
	PreparedStatement prs = null;
	ResultSet rss = null;
	Connection conn = null;
	try {
		conn= getConnection();
		if(conn != null){

			prs = conn.prepareStatement("{call FORK_GUARDAR_ITEM(?,?,?,?,?,?,?,?,?,?,?,?)}");
			prs.setString(1, item.getName());
			prs.setString(2, item.getPartNumber());
			prs.setString(3, item.getDescripcion());
			
			prs.setString(4, item.getModels());
			prs.setString(5, item.getSerie());
			prs.setDouble(6, item.getPrice());
			
			prs.setInt(7, item.getShowPrice());
			prs.setString(8, item.getNote());
			prs.setString(9, item.getPartNumber2());
			
			prs.setInt(10, item.getQuantity());
			prs.setInt(11, item.getIdUser());
			prs.setInt(12, item.getManufactrer());
			
//			prs.setInt(13, item.getIdItem());
			
			
			prs.execute();
			rss = prs.getResultSet();
			
			if(rss != null) rss.close();
			if(prs != null) prs.close();
			mapReturn.put("error", null);
			
		}else{
			mapReturn.put("error", "Error en la conexión, favor comuníquese con el departamento de Informática.");
		}
	
	}
	catch(Exception e)
	{e.printStackTrace();
	mapReturn.put("error", e.getMessage());}
	finally{
		try {conn.close();} catch (SQLException e1) {e1.printStackTrace();}
		try {if(rss!=null)rss.close();} catch (SQLException e) {	e.printStackTrace();}
		try {if(prs!=null)prs.close();} catch (SQLException e) {	e.printStackTrace();}
	}
	
	return mapReturn;
}



public Map<String, Object>  editarItem( Item item)
{	
	Map<String, Object> mapReturn = new HashMap<String, Object>();
	String param="";
//	CallableStatement clsCall = null;
	PreparedStatement prs = null;
	ResultSet rss = null;
	Connection conn = null;
	try {
		conn= getConnection();
		if(conn != null){

			prs = conn.prepareStatement("{call FORK_EDITAR_ITEM(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			prs.setString(1, item.getName());
			prs.setString(2, item.getPartNumber());
			prs.setString(3, item.getDescripcion());
			
			prs.setString(4, item.getModels());
			prs.setString(5, item.getSerie());
			prs.setDouble(6, item.getPrice());
			
			prs.setInt(7, item.getShowPrice());
			prs.setString(8, item.getNote());
			prs.setString(9, item.getPartNumber2());
			
			prs.setInt(10, item.getQuantity());
			prs.setInt(11, item.getIdUser());
			prs.setInt(12, item.getManufactrer());
			
			prs.setInt(13, item.getIdItem());
			
			
			prs.execute();
			rss = prs.getResultSet();
			
			if(rss != null) rss.close();
			if(prs != null) prs.close();
			mapReturn.put("error", null);
			
		}else{
			mapReturn.put("error", "Error en la conexión, favor comuníquese con el departamento de Informática.");
		}
	
	}
	catch(Exception e)
	{e.printStackTrace();}
	finally{
		try {conn.close();} catch (SQLException e1) {e1.printStackTrace();}
		try {if(rss!=null)rss.close();} catch (SQLException e) {	e.printStackTrace();}
		try {if(prs!=null)prs.close();} catch (SQLException e) {	e.printStackTrace();}
	}
	
	return mapReturn;
}


public Map<String, Object>  existeUser( String user)
{	
	Map<String, Object> mapReturn = new HashMap<String, Object>();
	boolean existe=false;
	PreparedStatement prs = null;
	ResultSet rss = null;
	Connection conn = null;
	try {
		conn= getConnection();
		if(conn != null){
			prs = conn.prepareStatement("{call FORK_EXISTE_USER(?)}");
			prs.setString(1, user);
			
			prs.execute();
			rss = prs.getResultSet();
			UsuarioSistema objUsuarioSistemaUtil = null;
			while (rss.next()) {
				objUsuarioSistemaUtil = new UsuarioSistema();
				objUsuarioSistemaUtil.setId(rss.getInt("id"));
				objUsuarioSistemaUtil.setLogin(user);
				objUsuarioSistemaUtil.setName(rss.getString("name"));
				existe=true;
			}
			if(rss != null) rss.close();
			if(prs != null) prs.close();
			mapReturn.put("error", null);
			if(existe)
				mapReturn.put("existe", "S");
			else
				mapReturn.put("existe", "N");
		}else{
			mapReturn.put("error", "Error en la conexión, favor comuníquese con el departamento de Informática.");
		}
	
	}
	catch(Exception e)
	{e.printStackTrace();}
	finally{
		try {conn.close();} catch (SQLException e1) {e1.printStackTrace();}
		try {if(rss!=null)rss.close();} catch (SQLException e) {	e.printStackTrace();}
		try {if(prs!=null)prs.close();} catch (SQLException e) {	e.printStackTrace();}
	}
	
	return mapReturn;
}


public Map<String, Object>  crearManufacturer( String name){	
	Map<String, Object> mapReturn = new HashMap<String, Object>();
	PreparedStatement prs = null;
	
	Connection conn = null;
	try {
		conn= getConnection();
		if(conn != null){
			prs = conn.prepareStatement("{call FORK_CREATE_MANUFACTURER(?)}");
			prs.setString(1, name);
			
			prs.execute();
			if(prs != null) prs.close();
			mapReturn.put("error", null);
			
		}else{
			mapReturn.put("error", "Error en la conexión, favor comuníquese con el departamento de Informática.");
		}	
	}
	catch(Exception e)
	{e.printStackTrace();
	mapReturn.put("error", e.getMessage());}
	finally{
		try {conn.close();} catch (SQLException e1) {e1.printStackTrace();}
		try {if(prs!=null)prs.close();} catch (SQLException e) {	e.printStackTrace();}
	}
	
	return mapReturn;
}




public Map<String, Object>  cambiarPass( UsuarioSistema user,String newPass){	
	Map<String, Object> mapReturn = new HashMap<String, Object>();
	PreparedStatement prs = null;
	
	Connection conn = null;
	try {
		conn= getConnection();
		if(conn != null){
			prs = conn.prepareStatement("{call FORK_CHANGE_PASS(?,?)}");
			prs.setInt(1, user.getId());
			prs.setString(2, newPass);
			
			prs.execute();
			if(prs != null) prs.close();
			mapReturn.put("error", null);
			
		}else{
			mapReturn.put("error", "Error en la conexión, favor comuníquese con el departamento de Informática.");
		}	
	}
	catch(Exception e)
	{e.printStackTrace();
	mapReturn.put("error", e.getMessage());}
	finally{
		try {conn.close();} catch (SQLException e1) {e1.printStackTrace();}
		try {if(prs!=null)prs.close();} catch (SQLException e) {	e.printStackTrace();}
	}
	
	return mapReturn;
}




public Map<String, Object>  guardaUsuario( UsuarioSistema user){	
	Map<String, Object> mapReturn = new HashMap<String, Object>();
//	CallableStatement clsCall = null;
	PreparedStatement prs = null;
	
	Connection conn = null;
	try {
		conn= getConnection();
		if(conn != null){
//			clsCall = conn.prepareCall("{call FORK_VALIDA_USER(?,?)}");
			prs = conn.prepareStatement("{call FORK_SAVE_USER(?,?,?,?)}");
			prs.setString(1, user.getName());
			prs.setString(2, user.getLogin());
			prs.setString(3, user.getPassword());
			prs.setString(4, user.getMail());
			
			prs.execute();
			
			
			if(prs != null) prs.close();
			mapReturn.put("error", null);
			
		}else{
			mapReturn.put("error", "Error en la conexión, favor comuníquese con el departamento de Informática.");
		}
	
	}
	catch(Exception e)
	{e.printStackTrace();
	mapReturn.put("error", e.getMessage());}
	finally{
		try {conn.close();} catch (SQLException e1) {e1.printStackTrace();}
		try {if(prs!=null)prs.close();} catch (SQLException e) {	e.printStackTrace();}
	}
	
	return mapReturn;
}


public Map<String, Object>  VerificaUsuarioDao( String user, String pass)
{	
	Map<String, Object> mapReturn = new HashMap<String, Object>();
//	CallableStatement clsCall = null;
	PreparedStatement prs = null;
	ResultSet rss = null;
	Connection conn = null;
	try {
		conn= getConnection();
		if(conn != null){
//			clsCall = conn.prepareCall("{call FORK_VALIDA_USER(?,?)}");
			prs = conn.prepareStatement("{call FORK_VALIDA_USER(?,?)}");
			prs.setString(1, user);
			prs.setString(2, pass);
			
			prs.execute();
			rss = prs.getResultSet();
			UsuarioSistema objUsuarioSistemaUtil = null;
			while (rss.next()) {
				objUsuarioSistemaUtil = new UsuarioSistema();
				objUsuarioSistemaUtil.setId(rss.getInt("id"));
				objUsuarioSistemaUtil.setLogin(user);
				objUsuarioSistemaUtil.setName(rss.getString("name"));
				objUsuarioSistemaUtil.setPassword(rss.getString("password"));
				
			}
			if(rss != null) rss.close();
			if(prs != null) prs.close();
			mapReturn.put("error", null);
			mapReturn.put("usuarioSistema", objUsuarioSistemaUtil);
		}else{
			mapReturn.put("error", "Error en la conexión, favor comuníquese con el departamento de Informática.");
		}
	
	}
	catch(Exception e)
	{e.printStackTrace();
	mapReturn.put("error", e.getMessage());}
	finally{
		try {conn.close();} catch (SQLException e1) {e1.printStackTrace();}
		try {if(rss!=null)rss.close();} catch (SQLException e) {	e.printStackTrace();}
		try {if(prs!=null)prs.close();} catch (SQLException e) {	e.printStackTrace();}
	}
	
	return mapReturn;
}





public ConfiguracionDto  cargarConfigurationDao(Integer param){
	

	PreparedStatement prs = null;
	ResultSet rs = null;
	Connection conn = null;
	ConfiguracionDto conf = new ConfiguracionDto();
	
	try {
		conn= getConnection();
		if(conn != null){
			prs = conn.prepareStatement("{call EFORK_OBTENER_CONFIGURACION(?)}");
			
			prs.setInt(1, param);
			
			prs.execute();
			rs = prs.getResultSet();

			
			
			while (rs.next()) {
				conf= new ConfiguracionDto();
				
				conf.setId(rs.getInt("ID"));
				conf.setCompanyName(rs.getString("company_name"));
				conf.setCompanyEmail(rs.getString("company_email"));
				conf.setCompanyPhone(rs.getString("company_phone"));
				conf.setCompanyAddress(rs.getString("company_address"));
				
				conf.setEmailPassword(rs.getString("email_password"));
				conf.setEmailSmtp(rs.getString("email_smptp"));
				conf.setEmailPuerto(rs.getInt("email_puerto"));
				conf.setEmailEnvia(rs.getString("email_envia"));
				
				
			}
			if(rs != null) rs.close();
			if(prs != null) prs.close();
			
		}		
	}
	catch(Exception e)
	{e.printStackTrace();
	
	}finally {
		if(conn != null){
			try {
				conn.close();
				if(prs != null) prs.close();
				if(rs!=null)rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	return conf;
	
}


	

}
