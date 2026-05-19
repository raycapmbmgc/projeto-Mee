package br.edu.instituicao.main;
import br.edu.instituicao.factory.PessoaFactory;
import br.edu.instituicao.interfaces.Autenticavel;
import br.edu.instituicao.interfaces.Avaliavel;
import br.edu.instituicao.model.*;
import br.edu.instituicao.service.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Secretaria secretaria = Secretaria.getInstance();
        int opcao;
        do {
            System.out.println("\n===== SISTEMA ACADÊMICO =====");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Cadastrar Professor/Coordenador");
            System.out.println("3. Lançar Notas de Aluno");
            System.out.println("4. Listar Comunidade Acadêmica");
            System.out.println("5. Exibir Estatísticas (Média Geral)");
            System.out.println("6. Acesso Administrativo");
            System.out.println("7. Sair");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(sc.nextLine());
            switch (opcao) {
                case 1 -> {
                    System.out.print("Nome: "); String nome = sc.nextLine();
                    System.out.print("CPF: "); String cpf = sc.nextLine();
                    System.out.print("Email: "); String email = sc.nextLine();
                    System.out.print("Matrícula: "); String matricula = sc.nextLine();
                    secretaria.cadastrarPessoa(PessoaFactory.criarAluno(nome, cpf, email, matricula));
                    System.out.println("Aluno cadastrado com sucesso!");
                }
                case 2 -> {
                    System.out.print("1 - Professor | 2 - Coordenador: ");
                    int tipo = Integer.parseInt(sc.nextLine());
                    System.out.print("Nome: "); String nome = sc.nextLine();
                    System.out.print("CPF: "); String cpf = sc.nextLine();
                    System.out.print("Email: "); String email = sc.nextLine();
                    System.out.print("SIAPE: "); String siape = sc.nextLine();
                    System.out.print("Senha: "); String senha = sc.nextLine();
                    Pessoa p = (tipo == 2)
                        ? PessoaFactory.criarCoordenador(nome, cpf, email, siape, senha)
                        : PessoaFactory.criarProfessor(nome, cpf, email, siape, senha);
                    secretaria.cadastrarPessoa(p);
                    System.out.println("Cadastro realizado com sucesso!");
                }
             case 3 -> {
    System.out.print("Digite a matrícula do aluno: ");
    String matricula = sc.nextLine();

    Aluno a = secretaria.buscarAlunoPorMatricula(matricula);

    if (a != null) {
        a.getNotas().clear();
        for (int i = 1; i <= 4; i++) {
            double nota;
            do {
                System.out.print(i + "º Bimestre: ");
                nota = Double.parseDouble(sc.nextLine());

                if (nota < 0 || nota > 10) {
                    System.out.println("A nota deve estar entre 0 e 10.");
                }
            } while (nota < 0 || nota > 10);
            a.adicionarNota(nota);
        }
        System.out.printf(
            "Notas lançadas com sucesso! Média final: %.2f%n",
            a.getMediaFinal()
        );
    } else {
        System.out.println("Aluno não encontrado.");
    }
}
                case 4 -> secretaria.listarPessoas();
                case 5 -> {
                    RelatorioAcademico rel = new RelatorioAcademico();
                    for (Pessoa p : secretaria.getPessoas()) {
                        if (p instanceof Avaliavel av) rel.adicionarDados(av);
                    }
                    rel.exibirMediaGeral();
                }
                case 6 -> {
                    System.out.print("Informe o nome do funcionário: ");
                    String nomeBusca = sc.nextLine();
                    Pessoa encontrado = null;
                    for (Pessoa p : secretaria.getPessoas()) {
                        if (p.getNome().equalsIgnoreCase(nomeBusca)) {
                            encontrado = p; break;
                        }
                    }
                    if (encontrado instanceof Autenticavel aut) {
                        System.out.print("Senha: ");
                        System.out.println(aut.login(sc.nextLine())
                            ? "Login realizado com sucesso!"
                            : "Senha incorreta.");
                    } else {
                        System.out.println("Funcionário não encontrado ou não autenticável.");
                    }
                }
                case 7 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 7);
        sc.close();
    }
}
