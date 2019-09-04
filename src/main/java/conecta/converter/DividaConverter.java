/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conecta.converter;

import conecta.model.Divida;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author joao
 */

@FacesConverter("dividaConverter")
public class DividaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (Divida) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof Divida) {
			Divida entity= (Divida) value;
			if (entity != null && entity instanceof Divida && entity.getId() != null) {
				uiComponent.getAttributes().put( entity.getId().toString(), entity);
				return entity.getId().toString();
			}
		}
		return "";
	}

}
