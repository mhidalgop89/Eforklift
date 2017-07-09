package com.eforklift.gestor;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.eforklift.dao.CargarListaItemsDao;
import com.eforklift.dto.Item;
import com.eforklift.util.ConfiguracionDto;
import com.eforklift.util.Mail;
import com.eforklift.util.ValidaCorreoElectronico;

public class RequestQuote {
	Window winModel;
	private Item item;
	private String partNumberName;
	private String quantity;
	private String name;
	private String company;
	private String email;
	private String city;
	private String phone;
	private String country;
	private String postalCode;
	private String stateProvince;
	private String fax;
	private String address;

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW)Component View,@ExecutionArgParam("item") Item objItem){
		Selectors.wireComponents(View, this, false);
		winModel = (Window) View;
		this.item=objItem;
		if(objItem!=null)
		partNumberName = objItem.getPartNumber()+" - "+objItem.getName();
		
	}
	
	@Command
	public void enviarCorreo(){
		
		boolean envio=false;
		if(email==null || quantity==null){
			Messagebox.show("please fill required fields.",
					"Attention",
					Messagebox.OK,Messagebox.INFORMATION);
			return;
		}
		
		if(email!=null)
			if(email.trim()==null){
					Messagebox.show("please fill required fields.",
							"Attention",
							Messagebox.OK,Messagebox.INFORMATION);
					return;
				}
					
		
		if(quantity!=null)
			if(quantity.trim()==null){
					Messagebox.show("please fill required fields.",
							"Attention",
							Messagebox.OK,Messagebox.INFORMATION);
					return;
				}
					
		
		String subject="Request Quote";
		CargarListaItemsDao datos = new CargarListaItemsDao();
		ConfiguracionDto conf = datos.cargarConfigurationDao(1);
		String to[]= new String[1];
		to[0]=conf.getCompanyEmail();

		String cc[]= new String[1];
		cc[0]="mario.hidalgo89@hotmail.com";
					
		String bcc[]= new String[1];
		bcc[0]="mario.hidalgo89@hotmail.com";

//		String msgToSend =  "<p>You have a request for a quote on the following part:"+"<br><br>"+
//				"Part Name "+this.item.getName()+"<br>"+
//				"Part Number "+this.item.getPartNumber()+"<br><br>"+
//				"With a Quantity of "+quantity+"<br><br>"+
//				"From: <br>"+
//				"Name: "+((name!=null)?name:"")+"<br>"+
//				"Company: "+((company!=null)?company:"")+"<br>"+
//				"Email: "+email+"<br>"+
//				"City: "+((city!=null)?city:"")+"<br>"+
//				"Country: "+((country!=null)?country:"")+"<br>"+
//				"Phone: "+((phone!=null)?phone:"")+"<br>"+
//				"Postalcode: "+((postalCode!=null)?postalCode:"")+"<br>"+
//				"State/Province: "+((stateProvince!=null)?stateProvince:"")+"<br>"+
//				"Fax: "+((fax!=null)?fax:"")+"<br>"+
//				"Address: "+((address!=null)?address:"")+"<br>"+
//				"</p>";	
		
		String msgToSend="<div style='padding:10px;background: url('http://172.16.0.140/portaldeinformacion/images/top.png') repeat-x;'><div width='300px' style='width:500px;margin:0 auto;border:1px solid #6f6f6f;height:auto;padding: 30px;'>"+
                "<img src='http://168.144.129.15:8080/Eforklift/images/newLogo.png' width='267px' height='80px' /><br><br>"+
                
                "<br><br><b>You have a request for a quote on the following part:  </b><br><br>"+
                "Part Name:   <b>"+this.item.getName() +"</b><br>"+
                "Part Number:  <b>"+this.item.getPartNumber()+"</b><br>"+
                "Manufacturer:  <b>"+this.item.getManufacturerName()+"</b><br>"+
                "With a Quantity of  <b>"+quantity+"</b><br><br>"+
                "<b>From: </b><br>"+
                "Name: <b>"+((name!=null)?name:"") +"</b><br>"+
                "Company: <b>"+((company!=null)?company:"") +"</b><br>"+
                "Email: <b>"+email +"</b><br>"+
                "City: <b>"+((city!=null)?city:"") +"</b><br>"+
                "Country: <b>"+((country!=null)?country:"") +"</b><br>"+
                "Phone: <b>"+((phone!=null)?phone:"") +"</b><br>"+
                "Postalcode: <b>"+((postalCode!=null)?postalCode:"") +"</b><br>"+
                "State/Province: <b>"+((stateProvince!=null)?stateProvince:"") +"</b><br>"+
                "Fax: <b>"+((fax!=null)?fax:"")+"</b><br>";
		msgToSend=msgToSend+"<br><br>Best regards,<br>"+
				"All Parts Systems"+
                "</div>"+
                "</div>";
		
		Mail mailUtil= new Mail();
//		envio=mailUtil.send("sales@allparts.expert", to, subject,cc , bcc, msgToSend,null);
		envio=mailUtil.enviarMail(msgToSend);
		
		if(!envio)
			Messagebox.show("Failed to send request quote.",
					"Attention",
					Messagebox.OK,Messagebox.INFORMATION);
		else{
			
			Messagebox.show("Request quote sent successfully.",
					"Attention",
					Messagebox.OK,Messagebox.INFORMATION);
			winModel.detach();
		}
			
		
		
	}

	@Command
	public void cancelar(){
		winModel.detach();
	}
	
	
	@SuppressWarnings("static-access")
	@NotifyChange("email")
	@Command
	public void validaCorreo(){
		try {
			ValidaCorreoElectronico validaCo = new ValidaCorreoElectronico();
			if(email==null)
				return;
			if (!email.trim().isEmpty()) {
				if (!validaCo.validaMail(email.trim().toString())) {
					email="";
					
					Messagebox.show("Invalid email.",
							"Attention",
							Messagebox.OK,Messagebox.INFORMATION);
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPartNumberName() {
		return partNumberName;
	}

	public void setPartNumberName(String partNumberName) {
		this.partNumberName = partNumberName;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Window getWinModel() {
		return winModel;
	}

	public void setWinModel(Window winModel) {
		this.winModel = winModel;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
