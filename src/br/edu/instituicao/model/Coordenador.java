package br.edu.instituicao.model;
public class Coordenador extends Professor {
    public Coordenador(String nome, String cpf, String email, String siape, String senha) {
        super(nome, cpf, email, siape, senha);
    }
    @Override
    public String toString() {
        return "[Coordenador] " + super.toString();
    }
}
