/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conecta.converter;

import conecta.model.Parcela;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author joao
 */
@FacesConverter("parcelaConverter")
public class ParcelaConverter implements Converter {



	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return (Parcela) uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof Parcela) {
            Parcela entity= (Parcela) value;
			if (entity != null && entity instanceof Parcela && entity.getId() != null) {
				uiComponent.getAttributes().put( entity.getId().toString(), entity);
				return entity.getId().toString();
			}
		}
		return "";
	}

}
