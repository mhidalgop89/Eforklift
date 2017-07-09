package com.eforklift.util;

public class ConfiguracionDto {
	
	
	private int id;
	private String companyName;
	private String companyEmail;
	private String companyPhone;
	private String companyAddress;
	
	private String emailPassword;
	private String emailSmtp;
	private int emailPuerto;
	private String emailEnvia;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getEmailPassword() {
		return emailPassword;
	}
	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}
	public String getEmailSmtp() {
		return emailSmtp;
	}
	public void setEmailSmtp(String emailSmtp) {
		this.emailSmtp = emailSmtp;
	}

	public int getEmailPuerto() {
		return emailPuerto;
	}
	public void setEmailPuerto(int emailPuerto) {
		this.emailPuerto = emailPuerto;
	}
	public String getEmailEnvia() {
		return emailEnvia;
	}
	public void setEmailEnvia(String emailEnvia) {
		this.emailEnvia = emailEnvia;
	}
	
	

}
