// Substitua TODO o conteúdo de TelaPrincipal.java por este código.
// Agora a interface terá menu lateral com:
// - Dashboard
// - Cadastrar Aluno
// - Cadastrar Professor
// - Lançar Notas
// - Relatórios
// - Listar Comunidade

package br.edu.instituicao.view;

import br.edu.instituicao.factory.PessoaFactory;
import br.edu.instituicao.model.Aluno;
import br.edu.instituicao.model.Pessoa;
import br.edu.instituicao.service.RelatorioAcademico;
import br.edu.instituicao.service.Secretaria;
import br.edu.instituicao.interfaces.Avaliavel;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private final Secretaria secretaria;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;

    // Campos Aluno
    private JTextField txtNomeAluno;
    private JTextField txtCpfAluno;
    private JTextField txtEmailAluno;
    private JTextField txtMatriculaAluno;

    // Campos Professor
    private JTextField txtNomeProfessor;
    private JTextField txtCpfProfessor;
    private JTextField txtEmailProfessor;
    private JTextField txtSiapeProfessor;
    private JPasswordField txtSenhaProfessor;
    private JComboBox<String> comboTipoProfessor;

    // Campos Notas
    private JTextField txtMatriculaNotas;
    private JTextField[] camposNotas;

    // Áreas
    private JTextArea areaLista;
    private JTextArea areaRelatorio;

    public TelaPrincipal() {
        secretaria = Secretaria.getInstance();

        setTitle("Sistema Acadêmico");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        criarInterface();
    }

    private void criarInterface() {
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(new Color(37, 99, 235));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("  🎓 Sistema Acadêmico");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 22));
        logo.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        sidebar.add(logo);

        sidebar.add(criarBotaoMenu("Dashboard", "dashboard"));
        sidebar.add(criarBotaoMenu("Cadastrar Aluno", "aluno"));
        sidebar.add(criarBotaoMenu("Cadastrar Professor", "professor"));
        sidebar.add(criarBotaoMenu("Lançar Notas", "notas"));
        sidebar.add(criarBotaoMenu("Relatórios", "relatorio"));
        sidebar.add(criarBotaoMenu("Listar Comunidade", "lista"));

        add(sidebar, BorderLayout.WEST);

        // ===== PÁGINAS =====
        contentPanel.add(criarTelaDashboard(), "dashboard");
        contentPanel.add(criarTelaAluno(), "aluno");
        contentPanel.add(criarTelaProfessor(), "professor");
        contentPanel.add(criarTelaNotas(), "notas");
        contentPanel.add(criarTelaRelatorio(), "relatorio");
        contentPanel.add(criarTelaLista(), "lista");

        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, "dashboard");
    }

    private JButton criarBotaoMenu(String texto, String card) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(37, 99, 235));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFont(new Font("Arial", Font.PLAIN, 16));

        btn.addActionListener(e -> {
            if (card.equals("lista")) atualizarLista();
            if (card.equals("relatorio")) atualizarRelatorio();
            cardLayout.show(contentPanel, card);
        });

        return btn;
    }

    // ===== DASHBOARD =====
    private JPanel criarTelaDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(248, 250, 252));

        JLabel titulo = new JLabel("Dashboard Acadêmico", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 34));
        titulo.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        JLabel subtitulo = new JLabel(
                "Gerencie alunos, professores, notas e relatórios.",
                SwingConstants.CENTER
        );
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 18));

        JPanel center = new JPanel(new GridLayout(2, 2, 20, 20));
        center.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        center.setBackground(new Color(248, 250, 252));

        center.add(criarCard("👨‍🎓 Alunos"));
        center.add(criarCard("👨‍🏫 Professores"));
        center.add(criarCard("📝 Notas"));
        center.add(criarCard("📊 Relatórios"));

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(subtitulo, BorderLayout.CENTER);
        panel.add(center, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel criarCard(String texto) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));

        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));

        card.add(label, BorderLayout.CENTER);
        return card;
    }

    // ===== CADASTRAR ALUNO =====
    private JComponent criarTelaAluno() {
        JPanel panel = criarPainelFormulario("Cadastrar Aluno");

        txtNomeAluno = new JTextField();
        txtCpfAluno = new JTextField();
        txtEmailAluno = new JTextField();
        txtMatriculaAluno = new JTextField();

        panel.add(new JLabel("Nome:"));
        panel.add(txtNomeAluno);

        panel.add(new JLabel("CPF:"));
        panel.add(txtCpfAluno);

        panel.add(new JLabel("Email:"));
        panel.add(txtEmailAluno);

        panel.add(new JLabel("Matrícula:"));
        panel.add(txtMatriculaAluno);

        JButton btn = new JButton("Cadastrar");
        btn.addActionListener(e -> cadastrarAluno());

        panel.add(new JLabel());
        panel.add(btn);

        return new JScrollPane(panel);
    }

    // ===== CADASTRAR PROFESSOR =====
    private JComponent criarTelaProfessor() {
        JPanel panel = criarPainelFormulario("Cadastrar Professor/Coordenador");

        comboTipoProfessor = new JComboBox<>(new String[]{"Professor", "Coordenador"});
        txtNomeProfessor = new JTextField();
        txtCpfProfessor = new JTextField();
        txtEmailProfessor = new JTextField();
        txtSiapeProfessor = new JTextField();
        txtSenhaProfessor = new JPasswordField();

        panel.add(new JLabel("Tipo:"));
        panel.add(comboTipoProfessor);

        panel.add(new JLabel("Nome:"));
        panel.add(txtNomeProfessor);

        panel.add(new JLabel("CPF:"));
        panel.add(txtCpfProfessor);

        panel.add(new JLabel("Email:"));
        panel.add(txtEmailProfessor);

        panel.add(new JLabel("SIAPE:"));
        panel.add(txtSiapeProfessor);

        panel.add(new JLabel("Senha:"));
        panel.add(txtSenhaProfessor);

        JButton btn = new JButton("Cadastrar");
        btn.addActionListener(e -> cadastrarProfessor());

        panel.add(new JLabel());
        panel.add(btn);

        return new JScrollPane(panel);
    }

    // ===== LANÇAR NOTAS =====
    private JComponent criarTelaNotas() {
        JPanel panel = criarPainelFormulario("Lançar Notas");

        txtMatriculaNotas = new JTextField();
        panel.add(new JLabel("Matrícula do Aluno:"));
        panel.add(txtMatriculaNotas);

        camposNotas = new JTextField[4];

        for (int i = 0; i < 4; i++) {
            camposNotas[i] = new JTextField();
            panel.add(new JLabel((i + 1) + "º Bimestre:"));
            panel.add(camposNotas[i]);
        }

        JButton btn = new JButton("Salvar Notas");
        btn.addActionListener(e -> salvarNotas());

        panel.add(new JLabel());
        panel.add(btn);

        return new JScrollPane(panel);
    }

    // ===== RELATÓRIO =====
    private JPanel criarTelaRelatorio() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        areaRelatorio = new JTextArea();
        areaRelatorio.setEditable(false);
        areaRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 16));

        panel.add(new JScrollPane(areaRelatorio), BorderLayout.CENTER);
        return panel;
    }

    // ===== LISTA =====
    private JPanel criarTelaLista() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        areaLista = new JTextArea();
        areaLista.setEditable(false);
        areaLista.setFont(new Font("Monospaced", Font.PLAIN, 14));

        panel.add(new JScrollPane(areaLista), BorderLayout.CENTER);
        return panel;
    }

    // ===== PAINEL BASE =====
    private JPanel criarPainelFormulario(String titulo) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        return panel;
    }

    // ===== AÇÕES =====
    private void cadastrarAluno() {
        secretaria.cadastrarPessoa(
                PessoaFactory.criarAluno(
                        txtNomeAluno.getText(),
                        txtCpfAluno.getText(),
                        txtEmailAluno.getText(),
                        txtMatriculaAluno.getText()
                )
        );
        JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
    }

    private void cadastrarProfessor() {
        String tipo = (String) comboTipoProfessor.getSelectedItem();
        Pessoa pessoa;

        if ("Coordenador".equals(tipo)) {
            pessoa = PessoaFactory.criarCoordenador(
                    txtNomeProfessor.getText(),
                    txtCpfProfessor.getText(),
                    txtEmailProfessor.getText(),
                    txtSiapeProfessor.getText(),
                    new String(txtSenhaProfessor.getPassword())
            );
        } else {
            pessoa = PessoaFactory.criarProfessor(
                    txtNomeProfessor.getText(),
                    txtCpfProfessor.getText(),
                    txtEmailProfessor.getText(),
                    txtSiapeProfessor.getText(),
                    new String(txtSenhaProfessor.getPassword())
            );
        }

        secretaria.cadastrarPessoa(pessoa);
        JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
    }

    private void salvarNotas() {
        Aluno aluno = secretaria.buscarAlunoPorMatricula(txtMatriculaNotas.getText());

        if (aluno == null) {
            JOptionPane.showMessageDialog(this, "Aluno não encontrado.");
            return;
        }

        aluno.getNotas().clear();

        try {
            for (int i = 0; i < 4; i++) {
                double nota = Double.parseDouble(camposNotas[i].getText());

                if (nota < 0 || nota > 10) {
                    JOptionPane.showMessageDialog(this,
                            "A nota deve estar entre 0 e 10.");
                    return;
                }

                aluno.adicionarNota(nota);
            }

            JOptionPane.showMessageDialog(this,
                    "Notas salvas! Média final: " +
                            String.format("%.2f", aluno.getMediaFinal()));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Digite apenas números válidos.");
        }
    }

    private void atualizarLista() {
        areaLista.setText("");

        for (Pessoa p : secretaria.getPessoas()) {
            areaLista.append(p.toString() + "\n");
        }
    }

    private void atualizarRelatorio() {
        RelatorioAcademico rel = new RelatorioAcademico();

        for (Pessoa p : secretaria.getPessoas()) {
            if (p instanceof Avaliavel av) {
                rel.adicionarDados(av);
            }
        }

        areaRelatorio.setText(
                "===== RELATÓRIO ACADÊMICO =====\n\n" +
                "Média Geral da Instituição: " +
                String.format("%.2f", rel.calcularMediaGeral())
        );
    }

   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TelaPrincipal().setVisible(true)
        );
    }
}