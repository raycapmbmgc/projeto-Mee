package br.edu.instituicao.model;

import br.edu.instituicao.interfaces.Avaliavel;
import java.util.ArrayList;

public class Aluno extends Pessoa implements Avaliavel {
    private String matricula;
    private ArrayList<Double> notas = new ArrayList<>();

    public Aluno(String nome, String cpf, String email, String matricula) {
        super(nome, cpf, email);
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public ArrayList<Double> getNotas() {
        return notas;
    }

    public void adicionarNota(double nota) {
        notas.add(nota);
    }

    @Override
    public double getMediaFinal() {
        if (notas.isEmpty()) return 0.0;

        double soma = 0;
        for (double n : notas) {
            soma += n;
        }

        return soma / notas.size();
    }

    @Override
    public String toString() {
        return "[Aluno] " + super.toString()
                + " | Matrícula: " + matricula
                + " | Média: " + String.format("%.2f", getMediaFinal());
    }
}