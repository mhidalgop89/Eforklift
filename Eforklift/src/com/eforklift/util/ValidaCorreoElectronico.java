package com.eforklift.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaCorreoElectronico {
	

	public static boolean validaMail(String strMail){  
		//asignamos la expresion
		   Pattern p = Pattern.compile("(.+@.+\\..[a-z]+)?");    
		   //".+\\@.+\\..+"
		   //constraint="/(.+@.+\.[a-z]+)?/: Mail no válido"
		   //comparamos con nuestro valor
		   Matcher m = p.matcher(strMail);    
		//si el correo es correcto devuelve TRUE o de lo contrario FALSE   
		return m.matches();
	 }

}
