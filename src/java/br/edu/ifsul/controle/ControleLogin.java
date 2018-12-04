package br.edu.ifsul.controle;

import br.edu.ifsul.dao.UsuarioDAO;
import br.edu.ifsul.modelo.SystemUser;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named(value = "controleLogin")
@SessionScoped
public class ControleLogin implements Serializable {
    
    private SystemUser usuarioAutenticado;
    @EJB
    private UsuarioDAO<SystemUser> dao;
    private String usuario;
    private String senha;

    public ControleLogin() {
    
    }
    
    public String irPaginaLogin() {
        return "/login?faces-redirect=true";
    }
    
    public String efetuarLogin() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext
                    .getCurrentInstance().getExternalContext().getRequest();
            
            // realizar o login
            request.login(this.usuario, this.senha);
            
            if(request.getUserPrincipal() != null) {
                usuarioAutenticado = 
                        dao.getObjectById(request.getUserPrincipal().getName());
                Util.mensagemInformacao("Login realizado com sucesso!");
                usuario = "";
                senha = "";
            }
            return "/index?faces-redirect=true";
        } catch (Exception ex) {
            Util.mensagemErro("Usuário ou senha inválidos! " + Util.getMensagemErro(ex));
            return "/login?faces-redirect=true";
        }
    }
    
    public String efetuarLogout() {
        try {
            usuarioAutenticado = null;
            HttpServletRequest request = (HttpServletRequest) FacesContext
                    .getCurrentInstance().getExternalContext().getRequest();
            request.logout();
            Util.mensagemInformacao("Logout realizado com sucesso!");
            return "/index?faces-redirect=true";
        } catch (Exception ex) {
            Util.mensagemErro("Erro ao efetuar logout! " + Util.getMensagemErro(ex));
            return "/index?faces-redirect=true";
        }
    }
    
    public SystemUser getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void setUsuarioAutenticado(SystemUser usuarioAutenticado) {
        this.usuarioAutenticado = usuarioAutenticado;
    }

    public UsuarioDAO<SystemUser> getDao() {
        return dao;
    }

    public void setDao(UsuarioDAO<SystemUser> dao) {
        this.dao = dao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
