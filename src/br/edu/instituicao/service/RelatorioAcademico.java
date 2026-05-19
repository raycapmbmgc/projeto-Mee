package br.edu.instituicao.service;
import br.edu.instituicao.interfaces.Avaliavel;
import java.util.ArrayList;
public class RelatorioAcademico {
    private ArrayList<Avaliavel> dados = new ArrayList<>();
    public void adicionarDados(Avaliavel objeto) { dados.add(objeto); }
    public double calcularMediaGeral() {
        if (dados.isEmpty()) return 0.0;
        double soma = 0;
        for (Avaliavel a : dados) soma += a.getMediaFinal();
        return soma / dados.size();
    }
    public void exibirMediaGeral() {
        System.out.printf("Média Geral da Instituição: %.2f%n", calcularMediaGeral());
    }
}
