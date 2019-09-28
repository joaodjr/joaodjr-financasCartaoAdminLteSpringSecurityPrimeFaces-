package conecta.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "maskConverter")
public class ConverterMask implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
                              String value) {

        if (value == null || value.isEmpty()) {
            return null;
        }

        return value.replace("(", "").replace(")", "").replace("-", "")
                .replace(".", "").trim().replace(" ", "").replace("/", "")
                .replace("\\", "");
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
                              Object value) {
        return value == null ? null : value.toString();
    }

}
