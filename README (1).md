# Agenda da Clínica

Sistema simples, em Java, para gerenciar os atendimentos de uma clínica pequena: **marcar**, **remarcar** e **listar** atendimentos por data, validando expediente, duração, formato de data e conflito de horário.

## Estrutura

| Arquivo | Função |
|---|---|
| `AgendaBase.java` | Regras de negócio e validações (marcar, remarcar, listar) |
| `Agendamento.java` | Dados de um atendimento (paciente, profissional, data, horário, duração, status) |
| `AgendamentoRecord.java` | Chave (paciente + data + horário) usada para localizar um atendimento a remarcar |
| `Status.java` | Enum com os status possíveis do atendimento |
| `Main.java` | Programa de demonstração |

## Requisitos

- JDK 17 ou superior (testado com JDK 21)

## Como compilar e executar

Na pasta com os arquivos `.java`:

```bash
javac -encoding UTF-8 *.java
java Main
```

Saída esperada:

```
Atendimento marcado para Ana
Horario ocupado
Atendimento marcado para Carla
Remarcado
14 Carla Dra. Souza MARCADO
```

## Como gerar a documentação (Javadoc)

```bash
javadoc -encoding UTF-8 -d docs *.java
```

A documentação é gerada na pasta `docs/`; abra `docs/index.html` no navegador.

## Regras de negócio

- Expediente: das 8h às 18h (horário de início de 8 a 17).
- Duração: de 15 a 120 minutos, em múltiplos de 15.
- Data no formato `AAAA-MM-DD` e válida no calendário.
- Conflito: o mesmo profissional não pode ter dois atendimentos na mesma data e no mesmo horário.

## Autoria

[Seu nome] — [Disciplina / Turma] — [Professor(a)]
