package br.edu.instituicao.model;
import br.edu.instituicao.interfaces.Autenticavel;
public class Professor extends Pessoa implements Autenticavel {
    private String siape, senha;
    public Professor(String nome, String cpf, String email, String siape, String senha) {
        super(nome, cpf, email);
        this.siape = siape;
        this.senha = senha;
    }
    public String getSiape() { return siape; }
    @Override
    public boolean login(String senha) {
        return this.senha.equals(senha);
    }
    @Override
    public String toString() {
        return "[Professor] " + super.toString() + " | SIAPE: " + siape;
    }
}
