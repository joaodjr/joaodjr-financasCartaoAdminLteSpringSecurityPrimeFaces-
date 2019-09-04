package conecta.converter;

import conecta.model.Situacao;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by Joao on 28/12/2018.
 */
@FacesConverter("situacaoConverter")
public class SituacaoConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
            if (value != null && !value.isEmpty()) {
                return (Situacao) uiComponent.getAttributes().get(value);
            }
            return null;
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
            if (value instanceof Situacao) {
                Situacao entity= (Situacao) value;
                if (entity != null && entity instanceof Situacao && entity.getId() != null) {
                    uiComponent.getAttributes().put( entity.getId().toString(), entity);
                    return entity.getId().toString();
                }
            }
            return "";
        }
    }

