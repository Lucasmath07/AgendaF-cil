# AgendaFácil

### Sistema de organização de consultas médicas

O **AgendaFácil** é uma aplicação simples desenvolvida em Java para auxiliar no
gerenciamento dos atendimentos de uma clínica de pequeno porte.

## 1. Objetivo

O sistema reúne funções básicas para organizar a agenda da clínica.
Entre elas, estão:

-   Cadastrar consultas;
-   Alterar o horário de uma consulta já registrada;
-   Consultar a relação de atendimentos.

Antes de realizar uma operação, o programa verifica condições
importantes, como o horário de funcionamento da clínica e a existência
de consultas no mesmo período. O projeto também procura manter o código
organizado, com responsabilidades separadas e validações para evitar
informações inválidas.

Este projeto foi desenvolvido como atividade da **Unidade 1** da
disciplina **DIM0501 -- Boas Práticas de Programação**, do DIMAP/UFRN,
no semestre **2026.1**.

## 2. Requisitos para utilizar

Para compilar e executar o programa, é necessário ter:

  Java: JDK 17 ou versão posterior

  Bibliotecas adicionais: Não são necessárias

  Banco de dados: Não utilizado; os dados ficam na memória durante a execução

  Interface: Terminal
 

## 3. Organização dos arquivos

Os arquivos Java foram separados conforme as funções que desempenham no
sistema.

  `AgendaBase.java`                   Mantém os agendamentos e verifica
                                      regras relacionadas a horários,
                                      expediente e conflitos.

  `Agendamento.java`                  Define os dados de uma consulta,
                                      incluindo paciente, profissional,
                                      data, hora, duração e situação.

  `AgendamentoRecord.java`            Representa os dados usados para
                                      identificar um agendamento durante
                                      a alteração de horário. Por ser um
                                      `record`, seus dados não podem ser
                                      modificados após a criação.

  `Status.java`                       Reúne as situações possíveis:
                                      `MARCADO`, `CANCELADO`,
                                      `CONCLUIDO`, `PENDENTE` e
                                      `ATRASADO`.

  `Main.java`                         Contém um exemplo de execução das
                                      funcionalidades do sistema.
  

## 4. Compilação e execução

Siga os passos abaixo no terminal, dentro da pasta que contém os
arquivos `.java`.

### Etapa 1 --- Verificar o Java

Confirme se o JDK 17 ou uma versão mais recente está instalado.

### Etapa 2 --- Compilar os arquivos

Execute o comando:

``` bash
javac *.java
```

### Etapa 3 --- Iniciar o programa

Depois que a compilação terminar, execute:

``` bash
java Main
```

## 5. Criar a documentação

O projeto utiliza comentários no código que podem ser organizados em uma
documentação HTML com o Javadoc.

Para gerar os arquivos, execute:

``` bash
javadoc -d docs *.java
```

O comando cria a pasta `docs`. Para consultar a documentação no
navegador, abra o arquivo `docs/index.html`.

## 6. Integrantes

-  Lucas Matheus Ferreira da Rocha, Caio Dantas Martins, Damião Felipe Oliveira de Lima
