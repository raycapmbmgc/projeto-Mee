package br.edu.instituicao.factory;
import br.edu.instituicao.model.*;
public class PessoaFactory {
    public static Pessoa criarAluno(String nome, String cpf, String email, String matricula) {
        return new Aluno(nome, cpf, email, matricula);
    }
    public static Pessoa criarProfessor(String nome, String cpf, String email, String siape, String senha) {
        return new Professor(nome, cpf, email, siape, senha);
    }
    public static Pessoa criarCoordenador(String nome, String cpf, String email, String siape, String senha) {
        return new Coordenador(nome, cpf, email, siape, senha);
    }
}
