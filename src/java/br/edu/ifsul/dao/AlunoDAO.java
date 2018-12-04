package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Student;
import java.io.Serializable;
import javax.ejb.Stateful;

@Stateful
public class AlunoDAO<TIPO> extends DAOGenerico<Student> implements Serializable {
    
    public AlunoDAO(){
        super();
        classePersistente = Student.class;
    }

}
