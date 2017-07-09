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
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Window;

import com.eforklift.dao.CargarListaItemsDao;
import com.eforklift.dao.CargarListaItemsSeleniumDao;
import com.eforklift.dto.Item;
import com.eforklift.dto.ItemOffRoad;
import com.eforklift.dto.UsuarioSistema;


public class ListaItemsGestor {
	
	List<Item> listItems = new ArrayList<Item>();
	private String param;
	
	@Wire
	Menu menUser;
	@Wire
	Window winItems;
	@Wire
	Menuitem menItem;
	@Wire
	Menuitem menConf;
	@Wire
	Menuitem menLogout;
	@Wire
	Menuitem menLogin;
	@Wire
	Menuitem menChangePass;
	
	UsuarioSistema objUsuarioSistema = new UsuarioSistema();
	
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW)Component View){
		Selectors.wireComponents(View, this, false);
		
		cargarLista();
		objUsuarioSistema = (UsuarioSistema) Executions.getCurrent().getSession().getAttribute("usuario");
		if(objUsuarioSistema!=null){
			menUser.setLabel(objUsuarioSistema.getName());
			menItem.setVisible(true);
			menConf.setVisible(true);
			menLogout.setVisible(true);
			menLogin.setVisible(false);
		}
		else{
			menUser.setLabel("User");
			menItem.setVisible(false);
			menConf.setVisible(false);
			menLogout.setVisible(false);
			menLogin.setVisible(true);
			
		}
		menUser.invalidate();
	}
	
	@NotifyChange("listItems")
	public void cargarLista(){
		CargarListaItemsDao listaItemsDao = new CargarListaItemsDao();
		listItems = listaItemsDao.cargarListaItemsDao();
	}
	
	@NotifyChange("listItems")
	@Command
	public void buscar(){
		CargarListaItemsDao listaItemsDao = new CargarListaItemsDao();
		String partSelenium="";
		System.out.println("paramxD: "+param);
		listItems = new ArrayList<Item>();
		if(param.isEmpty())
		{
			System.out.println("param.isEmpty()?");
			cargarLista();
			return;
		}
		if(param.contains("-"))
			param=param.replace("-","");
		partSelenium=param;
		param = param+"*";
		
//		CargarListaItemsSeleniumDao listaSelenium= new CargarListaItemsSeleniumDao();
//		List<ItemOffRoad> listItemsSelenium= new ArrayList<ItemOffRoad>();
//		
//		listItemsSelenium = listaSelenium.buscarPartes(partSelenium);
//		for(ItemOffRoad itemSelenium : listItemsSelenium){
//			Item item = new Item();
//			item.setDescripcion(itemSelenium.getDescription());
//			item.setName(itemSelenium.getDescription());
//			item.setPartNumber(itemSelenium.getPartnumber());
//			item.setPrice(Double.valueOf(itemSelenium.getPrice()));
//			item.setModels(itemSelenium.getBrand());
//			listItems.add(item);
//			
//			
//		}
		listItems.addAll( listaItemsDao.cargarListaItemsParamDao(param));
//		listItems.addAll(listItems.size()-1, listaItemsDao.cargarListaItemsParamDao(param));
//		listItems = listaItemsDao.cargarListaItemsParamDao(param);
	}
	
	@Command
	public void configuration(){
		
		Window win= (Window) Executions.getCurrent().createComponents("mantConfiguration.zul", null,null);
		win.doModal();
		win.setClosable(true);	
		
	}
	
	@Command
	public void items(){
		Window win= (Window) Executions.getCurrent().createComponents("mantItems.zul", null,null);
		win.doModal();
		win.setClosable(true);	
	}
	
	@Command
	public void login(){
		
		Window win= (Window) Executions.getCurrent().createComponents("login.zul", null,null);
		win.doModal();
		win.setClosable(true);
		objUsuarioSistema = (UsuarioSistema) Executions.getCurrent().getSession().getAttribute("usuario");
		
		if(objUsuarioSistema!=null){
			menUser.setLabel(objUsuarioSistema.getName());
			menItem.setVisible(true);
			menConf.setVisible(true);
			menLogout.setVisible(true);
			menLogin.setVisible(false);
			menChangePass.setVisible(true);
		}
		else{
			menUser.setLabel("User");
			menItem.setVisible(false);
			menConf.setVisible(false);
			menLogout.setVisible(false);
			menLogin.setVisible(true);
			menChangePass.setVisible(false);
			
		}
		menUser.invalidate();
		if(objUsuarioSistema!=null){
			menUser.setLabel(objUsuarioSistema.getName());
			System.out.println("name2: "+objUsuarioSistema.getName());
			winItems.invalidate();
		}
		
		
		
	}
	
	@Command
	public void logout(){
		
		Executions.getCurrent().getSession().removeAttribute("usuario");
		objUsuarioSistema=null;
		objUsuarioSistema = (UsuarioSistema) Executions.getCurrent().getSession().getAttribute("usuario");
		System.out.println("objUsuarioSistema: "+objUsuarioSistema);
		if(objUsuarioSistema!=null){
			menUser.setLabel(objUsuarioSistema.getName());
			menItem.setVisible(true);
			menConf.setVisible(true);
			menLogout.setVisible(true);
			menLogin.setVisible(false);
			menChangePass.setVisible(true);
		}
		else{
			menUser.setLabel("User");
			menItem.setVisible(false);
			menConf.setVisible(false);
			menLogout.setVisible(false);
			menLogin.setVisible(true);
			menChangePass.setVisible(false);
			
		}
		menUser.invalidate();
		winItems.invalidate();
		
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
	
	@Command
	public void changePass(){
		
		Window win= (Window) Executions.getCurrent().createComponents("changePass.zul", null,null);
		win.doModal();
		win.setClosable(true);
		logout();
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

	public UsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}

	public void setObjUsuarioSistema(UsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}

	public Menu getMenUser() {
		return menUser;
	}

	public void setMenUser(Menu menUser) {
		this.menUser = menUser;
	}

	public Window getWinItems() {
		return winItems;
	}

	public void setWinItems(Window winItems) {
		this.winItems = winItems;
	}

	public Menuitem getMenItem() {
		return menItem;
	}

	public void setMenItem(Menuitem menItem) {
		this.menItem = menItem;
	}

	public Menuitem getMenConf() {
		return menConf;
	}

	public void setMenConf(Menuitem menConf) {
		this.menConf = menConf;
	}

	public Menuitem getMenLogout() {
		return menLogout;
	}

	public void setMenLogout(Menuitem menLogout) {
		this.menLogout = menLogout;
	}

	public Menuitem getMenLogin() {
		return menLogin;
	}

	public void setMenLogin(Menuitem menLogin) {
		this.menLogin = menLogin;
	}
	

}
