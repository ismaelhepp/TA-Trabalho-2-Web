package br.edu.ifsul.controle;

import br.edu.ifsul.dao.AlunoDAO;
import br.edu.ifsul.modelo.Student;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author camila
 */

@Named(value = "controleAluno")
@ViewScoped
public class ControleAluno implements Serializable {
    
    @EJB
    private AlunoDAO<Student> dao;
    private Student objeto;
    private Boolean editando;

    private Boolean novoObjeto;   
        
    public ControleAluno(){
        editando = false;
    }
    
    public String listar(){
        editando = false;
        return "/privado/student/listar?faces-redirect=true";
    }
    
    public void novo(){
        objeto = new Student();
        editando = true;
        novoObjeto= true;
    }
    
    public void alterar(Object nomeAluno){
        try {
            objeto = dao.getObjectById(nomeAluno);
            editando = true;
        } catch (Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void excluir(Object nomeAluno){
        try {
            objeto = dao.getObjectById(nomeAluno);
            dao.remove(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");
        } catch (Exception e){
            Util.mensagemErro("Erro ao remover objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public void salvar(){
        try {
            if (getObjeto().getId() == null) {
                dao.persist(getObjeto());
            } else {
                dao.merge(getObjeto());
            }
            Util.mensagemInformacao("Objeto persistido com sucesso!");
            setEditando((Boolean) false);
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir objeto: " + Util.getMensagemErro(e));
        }
    }

    public AlunoDAO<Student> getDao() {
        return dao;
    }

    public void setDao(AlunoDAO<Student> dao) {
        this.dao = dao;
    }

    public Student getObjeto() {
        return objeto;
    }

    public void setObjeto(Student objeto) {
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
}