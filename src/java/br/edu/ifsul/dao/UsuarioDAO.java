package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.SystemUser;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.persistence.Query;

@Stateful
public class UsuarioDAO<TIPO> extends DAOGenerico<SystemUser> implements Serializable {
    
    public UsuarioDAO(){
        super();
        classePersistente = SystemUser.class;
    }

    public boolean verificaUnicidadeNomeUsuario(String nomeUsuario) throws Exception {
        String jpql = "from SystemUser where login = :pLogin";
        Query query = em.createQuery(jpql);
        query.setParameter("pLogin", nomeUsuario);
        
        if(query.getResultList().size() > 0) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public SystemUser getObjectById(Object id) throws Exception {
        SystemUser obj = em.find(SystemUser.class, id);
        obj.getPermissions().size();
        return obj;
    }
    
}
