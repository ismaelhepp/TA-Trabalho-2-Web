package br.edu.ifsul.converters;

import br.edu.ifsul.modelo.Permission;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@FacesConverter(value = "converterPermissao")
public class ConverterPermissao implements Serializable, Converter {

    @PersistenceContext(unitName = "Trabalho-WebPU")
    private EntityManager em;
    
    @Override // método que converte da tela para o objeto
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if(string == null || string.equals("Selecione um registro")){
            return null;
        }
        return em.find(Permission.class, string);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null){
            return null;
        }
        Permission obj = (Permission) o;
        return obj.getName();
    }

}
