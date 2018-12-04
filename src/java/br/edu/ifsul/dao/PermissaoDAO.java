package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Permission;
import java.io.Serializable;
import javax.ejb.Stateful;

@Stateful
public class PermissaoDAO<TIPO> extends DAOGenerico<Permission> implements Serializable {
    
    public PermissaoDAO(){
        super();
        classePersistente = Permission.class;
    }

}
