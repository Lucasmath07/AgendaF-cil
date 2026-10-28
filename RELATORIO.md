# Relatório — Refatoração da Agenda da Clínica

**Autor:** [Seu nome] — **Disciplina:** [Disciplina / Turma]

## 1. Diagnóstico (Etapa 1)

| # | Problema identificado | Onde | Solução aplicada |
|---|---|---|---|
| 1 | Validações de regra de negócio espalhadas e repetidas | marcar e remarcar | Centralizadas em `AgendaBase` (`validarAgendamento`, `validarData`, `validarHorario`, `validarDuracao`) |
| 2 | Números mágicos (8, 18, 15, 120) | validação de expediente e duração | Constantes nomeadas (`HORA_INICIO_EXPEDIENTE`, `DURACAO_MAXIMA_MINUTOS` etc.) |
| 3 | Lógica de conflito de horário duplicada | marcar e remarcar | Método único `existeConflito`, com parâmetro `ignorar` para o caso da remarcação |
| 4 | Remarcação identificava o atendimento por parâmetros soltos | `remarcarAgendamento` | `AgendamentoRecord` como chave de identificação (paciente, data, horário) |
| 5 | Status sem tipo definido, sujeito a valores inválidos | `Agendamento` | Enum `Status` |
| 6 | Dados e regras misturados | classes do domínio | `Agendamento` e `Status` viram apenas estruturas de dados; regras ficam em `AgendaBase` |
| 7 | Ausência de documentação | todo o código | Javadoc em classes e métodos públicos |

> **Atenção:** esta tabela foi montada a partir do código refatorado e dos comentários nele. Ajuste para refletir exatamente os problemas que você encontrou no código-base original.

## 2. Decisão de projeto relevante

O conflito de horário foi definido como "mesmo profissional, mesma data e mesmo horário de início", **sem considerar a duração**. Isso significa que sobreposições parciais (por exemplo, um atendimento de 60 min às 9h e outro às 9h30) não são detectadas. A escolha foi deliberada: o código-base já ignorava a sobreposição parcial, e a Regra de Ouro da refatoração exige manter exatamente a mesma saída do programa de demonstração. Corrigir esse comportamento seria uma mudança funcional, não uma refatoração, e fica registrado como melhoria futura. Na remarcação, o parâmetro `ignorar` evita que o atendimento seja considerado em conflito consigo mesmo.

## 3. Verificação

O código compila sem erros e o `Main` produz a mesma saída esperada (ver README). O Javadoc é gerado com `javadoc -encoding UTF-8 -d docs *.java`.
