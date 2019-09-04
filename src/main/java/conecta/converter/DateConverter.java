/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conecta.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@FacesConverter("dateConverter")
public class DateConverter implements Converter {
	private DateFormat formata = new SimpleDateFormat("dd/MM/yyyy");	
	///Converte o objeto da jspx para gravar no banco de dados.
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		Date data = new Date(); 
		//pegar o label do componente
		String label = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map = component.getAttributes();
		label = (String) map.get("label");		
		try{
			if (value.equals("")  || value == null || value.equals(null)){				
				return null;	//isto devolve ao jsf o controle de campos em branco (required=true/false)			
			}
			//isto força o formatador a lançar uma exception se a data for inválida
			formata.setLenient(false);
			data = formata.parse(value);
		}catch (ParseException e) { 			
			throw new ConverterException("Data invalida");
		}catch (Exception e){			
			throw new ConverterException("Data invalida");
		}
		return data;  
	}  
  	//Converte o objeto que vem do banco de dados para ser apresentado na xhtml.
    public String getAsString(FacesContext context, UIComponent component, Object value)throws ConverterException {
    	if(value == null) return "";  
    	String dataFormatada = null;  
    	dataFormatada = formata.format(((Date) value).getTime());  
    	return dataFormatada;  
    } 

    
}