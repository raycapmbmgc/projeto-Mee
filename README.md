# Sistema Acadêmico em Java

Projeto desenvolvido com Programação Orientada a Objetos utilizando:
- Abstração
- Herança
- Encapsulamento
- Polimorfismo
- Interfaces
- Singleton
- Factory Method

## Como executar

```bash
javac -d out src/br/edu/instituicao/interfaces/*.java src/br/edu/instituicao/model/*.java src/br/edu/instituicao/factory/*.java src/br/edu/instituicao/service/*.java src/br/edu/instituicao/main/*.java
java -cp out br.edu.instituicao.main.Main
```

## Por que Pessoa é abstrata?

Porque Pessoa representa apenas um conceito genérico. No sistema, toda pessoa deve ser Aluno, Professor ou Coordenador.
