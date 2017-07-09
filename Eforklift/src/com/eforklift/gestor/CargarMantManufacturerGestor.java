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

public class CargarMantManufacturerGestor {
	
	Window winModel;
	private String manufacturerName="";
	
	
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW)Component View){
		Selectors.wireComponents(View, this, false);
		winModel = (Window) View;
		
	}
	@NotifyChange("manufacturerName")
	@Command
	public void save(){
		if(manufacturerName==null){
			Messagebox.show("Please fill the required fields *","Attention",Messagebox.OK,Messagebox.INFORMATION);
			return;
		}
		if(manufacturerName.trim().isEmpty()){
			Messagebox.show("Please fill the required fields *","Attention",Messagebox.OK,Messagebox.INFORMATION);
			manufacturerName="";
			return;
		}
		
		int respuesta = Messagebox.show("Are you sure you save the changes?","Attention!!", Messagebox.YES | Messagebox.NO,Messagebox.INFORMATION);
		if (respuesta != 16)
			return;
		
		CargarListaItemsDao datos = new CargarListaItemsDao();
		Map<String, Object> mapResult = datos.crearManufacturer(manufacturerName);
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


	public String getManufacturerName() {
		return manufacturerName;
	}


	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
}
