package com.eforklift.gestor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Window;

import com.eforklift.dao.CargarListaItemsDao;
import com.eforklift.dto.Item;
import com.eforklift.dto.UsuarioSistema;

public class MantItemsGestor {

	private UsuarioSistema objUsuarioSistema = new UsuarioSistema();
	private List<Item> listItems = new ArrayList<Item>();
	private String param;
	Window winItem;
	
	
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW)Component View){		
		Selectors.wireComponents(View, this, false);
		winItem = (Window) View;
		objUsuarioSistema = (UsuarioSistema) Executions.getCurrent().getSession().getAttribute("usuario");
		
		cargarLista();
		
	}
	
	@Command
	public void requestQuote(@BindingParam("item") Item objItem){
		
		try{
			
			Map param= new HashMap();
			param.put("item", objItem);
			
			Window win= (Window) Executions.getCurrent().createComponents("requestQuote.zul", null,param);
			win.doModal();
			win.setClosable(true);
			
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	@NotifyChange("listItems")
	@Command
	public void editItem(@BindingParam("item") Item objItem){
		
		try{
			
			Map param= new HashMap();
			param.put("item", objItem);
			
			Window win= (Window) Executions.getCurrent().createComponents("cargarMantItems.zul", null,param);
			win.doModal();
			win.setClosable(true);
			cargarLista();
			
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	@NotifyChange("listItems")
	@Command
	public void buscar(){
		CargarListaItemsDao listaItemsDao = new CargarListaItemsDao();
		System.out.println("paramxD: "+param);
		if(param.isEmpty())
		{
			cargarLista();
			return;
		}
		if(param.contains("-"))
			param=param.replace("-","");
		param = param+"*";
		
		listItems = listaItemsDao.cargarListaItemsUserDao(param, objUsuarioSistema.getId());
	}
	
	@Command
	public void cancelar(){
		winItem.detach();
	}
	

	@NotifyChange("listItems")
	public void cargarLista(){
		CargarListaItemsDao listaItemsDao = new CargarListaItemsDao();
//		listItems = listaItemsDao.cargarListaItemsDao();
		listItems = listaItemsDao.cargarListaItemsUserDao(objUsuarioSistema.getId()); 
	}


	public UsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}


	public void setObjUsuarioSistema(UsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}


	public List<Item> getListItems() {
		return listItems;
	}


	public void setListItems(List<Item> listItems) {
		this.listItems = listItems;
	}


	public String getParam() {
		return param;
	}


	public void setParam(String param) {
		this.param = param;
	}
	
	
}
