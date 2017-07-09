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
import com.eforklift.util.ConfiguracionDto;

public class MantConfigurationGestor {
	
	Window winModel;
	UsuarioSistema objUsuarioSistema;
	private ConfiguracionDto conf = new ConfiguracionDto();
	
	
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW)Component View){
		Selectors.wireComponents(View, this, false);
		winModel = (Window) View;
		objUsuarioSistema = (UsuarioSistema) Executions.getCurrent().getSession().getAttribute("usuario");
		cargarConf();
				
	}
	
	@NotifyChange("conf")
	public void cargarConf(){
		CargarListaItemsDao datos = new CargarListaItemsDao();
		conf = datos.cargarConfigurationDao(1);
		
	}
	
	@Command
	public void guardar(){
		if(conf.getCompanyName().isEmpty()||conf.getCompanyAddress().isEmpty()||conf.getCompanyPhone().isEmpty()||
				conf.getCompanyEmail().isEmpty()||conf.getEmailEnvia().isEmpty()||conf.getEmailSmtp().isEmpty()||
				conf.getEmailPassword().isEmpty()){
			Messagebox.show("Please enter required fields.", "Attention", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		int respuesta = Messagebox.show("Are you sure you save the changes ?",
				"Attention!!", Messagebox.YES | Messagebox.NO,
				Messagebox.INFORMATION);
		if (respuesta != 16)
			return;
		
		CargarListaItemsDao datos = new CargarListaItemsDao();
		
		Map<String, Object> mapResult = datos.editarConfiguration(conf);
		if(mapResult.get("error") == null){
			Messagebox.show("Update Successfull.", "Información", Messagebox.OK, Messagebox.INFORMATION);
			winModel.detach();				
		}else{
			Messagebox.show(mapResult.get("error").toString(), "Información", Messagebox.OK, Messagebox.ERROR);
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

	public UsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}

	public void setObjUsuarioSistema(UsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}

	public ConfiguracionDto getConf() {
		return conf;
	}

	public void setConf(ConfiguracionDto conf) {
		this.conf = conf;
	}

}
