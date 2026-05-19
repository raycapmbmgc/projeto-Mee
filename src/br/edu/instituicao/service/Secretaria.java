package br.edu.instituicao.service;
import br.edu.instituicao.model.*;
import java.util.ArrayList;
/** [PADRAO] Singleton. */
public class Secretaria {
    private static Secretaria instancia;
    private ArrayList<Pessoa> pessoas = new ArrayList<>();
    private Secretaria() {}
    public static Secretaria getInstance() {
        if (instancia == null) instancia = new Secretaria();
        return instancia;
    }
    public void cadastrarPessoa(Pessoa pessoa) { pessoas.add(pessoa); }
    public ArrayList<Pessoa> getPessoas() { return pessoas; }
    public void listarPessoas() {
        if (pessoas.isEmpty()) {
            System.out.println("Nenhum cadastro encontrado.");
            return;
        }
        for (Pessoa p : pessoas) System.out.println(p);
    }
    public Aluno buscarAlunoPorMatricula(String matricula) {
        for (Pessoa p : pessoas) {
            if (p instanceof Aluno a && a.getMatricula().equalsIgnoreCase(matricula)) {
                return a;
            }
        }
        return null;
    }
}
