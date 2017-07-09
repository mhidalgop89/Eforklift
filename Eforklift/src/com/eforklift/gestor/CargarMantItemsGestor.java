package com.eforklift.gestor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.eforklift.dao.CargarListaItemsDao;
import com.eforklift.dto.Item;
import com.eforklift.dto.UsuarioSistema;
import com.eforklift.util.Manufacturer;

public class CargarMantItemsGestor {
	Window winModel;
	@Wire Checkbox chkShowPrice;
	UsuarioSistema objUsuarioSistema = new UsuarioSistema();
	private Item item;
	private Manufacturer objMAnufacturer;
	private List<Manufacturer> lsManufacturer = new ArrayList<Manufacturer>();
	
	
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW)Component View,@ExecutionArgParam("item") Item objItem){
		Selectors.wireComponents(View, this, false);
		winModel = (Window) View;
		objUsuarioSistema = (UsuarioSistema) Executions.getCurrent().getSession().getAttribute("usuario");
		this.item=objItem;
		if(this.item==null)
			this.item= new Item();
		cargarManufacturer();
				
	}
	
	@NotifyChange("objMAnufacturer")
	public void cargarManufacturer(){
		CargarListaItemsDao datos = new CargarListaItemsDao();
		lsManufacturer = datos.cargarManufacturer();
		if(item!=null)
			if(item.getIdItem()!=0)
				for(int i=0;i<lsManufacturer.size();i++){
					if(item.getManufactrer()==lsManufacturer.get(i).getId())
						objMAnufacturer=lsManufacturer.get(i);
				}
		
	} 
	
	@Command
	public void guardar(){
		if(item==null){
			Messagebox.show("Please enter the item information.", "Attention", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if(item.getName().isEmpty()|| item.getPartNumber().isEmpty() ){
			Messagebox.show("Please enter required fields.", "Attention", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if(objMAnufacturer==null){
			Messagebox.show("Please enter required fields.", "Attention", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		
		
		int respuesta = Messagebox.show("Are you sure you save the changes ?",
				"Attention!!", Messagebox.YES | Messagebox.NO,
				Messagebox.INFORMATION);
		if (respuesta != 16)
			return;
		
		
		String param="";
		
		if(item.getPartNumber().contains("-"))
			param=item.getPartNumber().replace("-","");
		else
			param=item.getPartNumber();
		item.setPartNumber2(param);
		
		if(chkShowPrice.isChecked())
			item.setShowPrice(1);
		else
			item.setShowPrice(0);
		item.setManufactrer(objMAnufacturer.getId());
		
		CargarListaItemsDao datos = new CargarListaItemsDao();
		item.setIdUser(objUsuarioSistema.getId());
		//actualiza
		if(item.getIdItem()!=0){
			Map<String, Object> mapResult = datos.editarItem(item);
			if(mapResult.get("error") == null){
				Messagebox.show("Update Successful.", "Attention", Messagebox.OK, Messagebox.INFORMATION);
				winModel.detach();				
			}else{
				Messagebox.show(mapResult.get("error").toString(), "Información", Messagebox.OK, Messagebox.ERROR);
			}
			
		}else{
			
			Map<String, Object> mapResult = datos.guatdarItem(item);
			if(mapResult.get("error") == null){
				Messagebox.show("Successful Insertion.", "Attention", Messagebox.OK, Messagebox.ERROR);
				winModel.detach();				
			}else{
				Messagebox.show(mapResult.get("error").toString(), "Información", Messagebox.OK, Messagebox.ERROR);
			}
			
		}		
	}
	
	@NotifyChange("lsManufacturer")
	@Command
	public void agregarManufacturer(){
		
		Window win= (Window) Executions.getCurrent().createComponents("cargarMantManufacturer.zul", null,null);
		win.doModal();
		win.setClosable(true);
		cargarManufacturer();
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Checkbox getChkShowPrice() {
		return chkShowPrice;
	}

	public void setChkShowPrice(Checkbox chkShowPrice) {
		this.chkShowPrice = chkShowPrice;
	}

	public UsuarioSistema getObjUsuarioSistema() {
		return objUsuarioSistema;
	}

	public void setObjUsuarioSistema(UsuarioSistema objUsuarioSistema) {
		this.objUsuarioSistema = objUsuarioSistema;
	}

	public Manufacturer getObjMAnufacturer() {
		return objMAnufacturer;
	}

	public void setObjMAnufacturer(Manufacturer objMAnufacturer) {
		this.objMAnufacturer = objMAnufacturer;
	}


	public List<Manufacturer> getLsManufacturer() {
		return lsManufacturer;
	}


	public void setLsManufacturer(List<Manufacturer> lsManufacturer) {
		this.lsManufacturer = lsManufacturer;
	}

	
	


}
