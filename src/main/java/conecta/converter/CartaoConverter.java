package conecta.converter;


import conecta.model.Cartao;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("cartaoConverter")
public class CartaoConverter implements Converter<Object> {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
            if (value != null && !value.isEmpty()) {
                return (Cartao) uiComponent.getAttributes().get(value);
            }
            return null;
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
            if (value instanceof Cartao) {
                Cartao entity= (Cartao) value;
                if (entity != null && entity instanceof Cartao && entity.getId() != null) {
                    uiComponent.getAttributes().put( entity.getId().toString(), entity);
                    return entity.getId().toString();
                }
            }
            return "";
        }
    }

