package conecta.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.text.Normalizer;

@FacesConverter(value = "onlyTextConverter")
public class OnlyTextConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
                              String value) {

        if (value == null || value.isEmpty())
            return null;

        String str = value.toUpperCase();

        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        str = str.replaceAll("[?!@#$%&~<>{}]", "");

        return str;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
                              Object value) {

        if (value == null)
            return null;

        return value.toString();
    }

}
