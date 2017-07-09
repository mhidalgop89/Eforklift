package com.eforklift.gestor;

import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.eforklift.dao.CargarListaItemsDao;
import com.eforklift.dto.UsuarioSistema;

public class LoginGestor {
	Window winModel;
	private String user;
	private String password;
	
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW)Component View){
		Selectors.wireComponents(View, this, false);
		winModel = (Window) View;
		
	}
	
	@Command
	public void cancelar(){
		winModel.detach();
	}
	
	@Command
	public void login(){
		CargarListaItemsDao datos = new CargarListaItemsDao();
		Map<String, Object> mapResult = datos.VerificaUsuarioDao(user, password);
		UsuarioSistema objUsuarioSistema;
		
		if(mapResult.get("error") == null){
			objUsuarioSistema = (UsuarioSistema) mapResult.get("usuarioSistema");		
			
			if(objUsuarioSistema != null){
				
				Executions.getCurrent().getSession().setAttribute("usuario", objUsuarioSistema);
				winModel.detach();
			}else{

				Messagebox.show("User or Password incorrect.","Attention",Messagebox.OK,Messagebox.INFORMATION);
			        System.out.println("Executions.getCurrent().getRemoteAddr()+ "+Executions.getCurrent().getRemoteAddr());
				limpiar();
			}	
		}else{
			Messagebox.show(mapResult.get("error").toString(),"Attention",Messagebox.OK,Messagebox.INFORMATION);
			
		}
		
	}
	
	
	@Command
	public void registerNow(){
		
		Window win= (Window) Executions.getCurrent().createComponents("crearUser.zul", null,null);
		win.doModal();
		win.setClosable(true);
	}
	
	public void limpiar(){
		user="";
		password="";
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
