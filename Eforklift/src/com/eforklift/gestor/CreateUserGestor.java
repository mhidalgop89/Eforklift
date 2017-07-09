package com.eforklift.gestor;

import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.eforklift.dao.CargarListaItemsDao;
import com.eforklift.dto.UsuarioSistema;

public class CreateUserGestor {
	
	Window winModel;
	private String fullName="";
	private String user="";
	private String password="";
	private String repeatPass="";
	private String email="";
	@Wire
	private Textbox txtPass= new Textbox();
	



	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW)Component View){
		Selectors.wireComponents(View, this, false);
		winModel = (Window) View;
		
	}
	
	@NotifyChange({"user","password"})
	@Command
	public void save(){
		if(fullName==null||user==null||password==null||repeatPass==null||email==null){
			Messagebox.show("Please fill the required fields *","Attention",Messagebox.OK,Messagebox.INFORMATION);
			return;
		}
		if(fullName.trim().isEmpty()||user.trim().isEmpty()||password.trim().isEmpty()||repeatPass.trim().isEmpty()||email.trim().isEmpty()){
			Messagebox.show("Please fill the required fields *","Attention",Messagebox.OK,Messagebox.INFORMATION);
			return;
		}
		if(!password.equals(repeatPass)){
			Messagebox.show("Please retype the password","Attention",Messagebox.OK,Messagebox.INFORMATION);
			txtPass.setFocus(true);
			password="";
			return;
		}
		CargarListaItemsDao datos = new CargarListaItemsDao();
		Map<String, Object> mapResult2 = datos.existeUser(user.toUpperCase()); 
		if(mapResult2.get("error")==null){
			if(mapResult2.get("existe").equals("S")){
				Messagebox.show("User already exists, please enter a new one","Attention",Messagebox.OK,Messagebox.INFORMATION);
				user="";
				return;
			}
			
		}else{
			Messagebox.show(mapResult2.get("error").toString(),"Attention",Messagebox.OK,Messagebox.INFORMATION);
			return;
		}
				
		
		UsuarioSistema newUser= new UsuarioSistema();
		newUser.setName(fullName);
		newUser.setMail(email);
		newUser.setLogin(user.toUpperCase());
		newUser.setPassword(password);
				
		Map<String, Object> mapResult = datos.guardaUsuario(newUser);
		if(mapResult.get("error") == null){
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getRepeatPass() {
		return repeatPass;
	}

	public void setRepeatPass(String repeatPass) {
		this.repeatPass = repeatPass;
	}
	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}

}
