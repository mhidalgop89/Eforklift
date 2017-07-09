package com.eforklift.gestor;

import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.eforklift.dao.CargarListaItemsDao;
import com.eforklift.dto.UsuarioSistema;

public class ChangePass {
	
	Window winModel;
	private String retPass="";
	private String password="";
	
	
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW)Component View){
		Selectors.wireComponents(View, this, false);
		winModel = (Window) View;
		
	}
	
	@NotifyChange({"retPass","password"})
	@Command
	public void save(){
		
		if(retPass==null||password==null){
			Messagebox.show("Please fill the required fields *","Attention",Messagebox.OK,Messagebox.INFORMATION);
			return;
		}
		if(!password.equals(retPass)){
			Messagebox.show("Please retype the password","Attention!!", Messagebox.OK ,Messagebox.INFORMATION);
			retPass="";
			password="";		
			return;
		}
			
		
		int respuesta = Messagebox.show("Are you sure you save the changes ?","Attention!!", Messagebox.YES | Messagebox.NO,Messagebox.INFORMATION);
		if (respuesta != 16)
			return;
		
		UsuarioSistema objUsuarioSistema;
		objUsuarioSistema = (UsuarioSistema) Executions.getCurrent().getSession().getAttribute("usuario");
		CargarListaItemsDao datos = new CargarListaItemsDao();
		Map<String, Object> mapResult = datos.cambiarPass(objUsuarioSistema, password);
		if(mapResult.get("error") == null){
			Executions.getCurrent().getSession().removeAttribute("usuario");
			Messagebox.show("Save Correctly.","Attention",Messagebox.OK,Messagebox.INFORMATION);
			winModel.detach();
		}else{
			Messagebox.show(mapResult.get("error").toString(),"Attention",Messagebox.OK,Messagebox.INFORMATION);			
		}
		
		
		
	}
	
	
	@Command
	public void cancelar(){
		winModel.detach();
	}

	public Window getWinModel() {
		return winModel;
	}


	public void setWinModel(Window winModel) {
		this.winModel = winModel;
	}


	public String getRetPass() {
		return retPass;
	}


	public void setRetPass(String retPass) {
		this.retPass = retPass;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

}
