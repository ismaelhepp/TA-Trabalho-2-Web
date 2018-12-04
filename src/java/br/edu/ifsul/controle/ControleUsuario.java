package br.edu.ifsul.controle;

import br.edu.ifsul.dao.PermissaoDAO;
import br.edu.ifsul.dao.UsuarioDAO;
import br.edu.ifsul.modelo.SystemUser;
import br.edu.ifsul.modelo.Permission;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named(value = "controleUsuario")
@ViewScoped
public class ControleUsuario implements Serializable {
    
    @EJB
    private UsuarioDAO<SystemUser> dao;
    private SystemUser objeto;
    private Boolean editando;
    
    private Boolean novoObjeto;

    @EJB
    private PermissaoDAO<Permission> daoPermissao;
    private Permission permissao;
    private Boolean editandoPermissao;
    
    public ControleUsuario(){
        editando = false;
        editandoPermissao = false;
    }

    public String listar() {
        setEditando((Boolean) false);
        return "/privado/usuario/listar?faces-redirect=true";
    }
    
    public void novo() {
        setObjeto(new SystemUser());
        editando = true;
        novoObjeto = true;
        editandoPermissao = false;
    }
    
    public void alterar(Object id) {
        try {
            setObjeto(dao.getObjectById(id));
            editando = true;
            novoObjeto = false;
            editandoPermissao = false;
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar o objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void excluir(Object id) {
        try {
            setObjeto(dao.getObjectById(id));
            dao.remove(getObjeto());
            Util.mensagemInformacao("Objeto removido com sucesso!");
        } catch (Exception e) {
            Util.mensagemErro("Erro ao remover o objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void verificaUnicidadeUsuario() {
        if(!novoObjeto) {
            return;
        }
        
        try {
            if(!dao.verificaUnicidadeNomeUsuario(objeto.getLogin())){
                Util.mensagemErro("Login '" + objeto.getLogin() +
                        "' já existe no banco de dados!");

                // capturando o componente que chamou o método
                UIComponent comp = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance());
                
                if (comp == null) {
                    return;
                }
                
                // se não for nulo seto o componente como valid false => ficar em vermelho
                UIInput input = (UIInput) comp;
                input.setValid(false);
            }
        } catch (Exception e) {
            Util.mensagemErro("Erro do sistema: " + Util.getMensagemErro(e));
        }
    }
    
    public void salvar(){
        try {            
            if(novoObjeto) { 
                dao.persist(getObjeto());
            } else {
                dao.merge(getObjeto());
            }
            Util.mensagemInformacao("Objeto persistido com sucesso!");
            editando = false;
            
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void novaPermissao() {
        editandoPermissao = true;
    }
    
    public void salvarPermissao() {
        if (objeto.getPermissions().contains(permissao)) {
            Util.mensagemErro("Usuário já possui essa permissão!");
        } else {
            objeto.getPermissions().add(permissao);
            Util.mensagemInformacao("Permissão adicionada com sucesso!");
        }
        
        editandoPermissao = false;
    }
    
    public void removerPermissao(Permission obj) {
        objeto.getPermissions().remove(obj);
        Util.mensagemInformacao("Permissão removida com sucesso!");
    }
    
    public UsuarioDAO<SystemUser> getDao() {
        return dao;
    }

    public void setDao(UsuarioDAO<SystemUser> dao) {
        this.dao = dao;
    }

    public SystemUser getObjeto() {
        return objeto;
    }

    public void setObjeto(SystemUser objeto) {
        this.objeto = objeto;
    }

    public Boolean getEditando() {
        return editando;
    }

    public void setEditando(Boolean editando) {
        this.editando = editando;
    }

    public Boolean getNovoObjeto() {
        return novoObjeto;
    }

    public void setNovoObjeto(Boolean novoObjeto) {
        this.novoObjeto = novoObjeto;
    }

    public PermissaoDAO<Permission> getDaoPermissao() {
        return daoPermissao;
    }

    public void setDaoPermissao(PermissaoDAO<Permission> daoPermissao) {
        this.daoPermissao = daoPermissao;
    }

    public Permission getPermissao() {
        return permissao;
    }

    public void setPermissao(Permission permissao) {
        this.permissao = permissao;
    }

    public Boolean getEditandoPermissao() {
        return editandoPermissao;
    }

    public void setEditandoPermissao(Boolean editandoPermissao) {
        this.editandoPermissao = editandoPermissao;
    }
}
