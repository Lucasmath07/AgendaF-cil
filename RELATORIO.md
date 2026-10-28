# Relatório — Refatoração da Agenda da Clínica

**Autor:** LUCAS MATHEUS FERREIRA DA ROCHA, CAIO DANTAS MARTINS, DAMIÃO FELIPE DE OLIVEIRA — **Disciplina:** BOAS PRÁTICAS

## 1. Etapa 1 — Diagnóstico

| Categoria | Anomalia encontrada (e onde) | Por que é um problema |
|---|---|---|
| 1. Adequação e lógica | `rem` não valida o novo horário: aceita remarcar para as 3h. | A regra de expediente do `add` pode ser burlada. |
| 1. Adequação e lógica | `rem` compara o atendimento com ele mesmo: remarcar para o mesmo horário dá "Horario ocupado". | O resultado fica errado. |
| 2. Manutenibilidade | O laço de conflito está copiado em `add` e em `rem`. | Mudar a regra exige editar dois lugares. |
| 2. Manutenibilidade | Números mágicos (`8`, `18`) e status como texto solto (`"MARCADO"`). | Sem significado claro, e um erro de digitação não é detectado. |
| 3. Organização e estética | Nomes crípticos: `A`, `p`, `pr`, `d`, `h`, `add`, `rem`, `lst`. | Difícil de entender e de manter. |
| 3. Organização e estética | Três `if` aninhados em `add`, `main` dentro da classe, código comentado, TODO vago e comentário errado em `lst` ("do mes"). | Poluição visual e comentário que engana o leitor. |
| 4. Programação defensiva e segurança | `catch (Exception e) { }` vazio em `add`. | Esconde erros: com profissional nulo, o agendamento some sem aviso. |
| 4. Programação defensiva e segurança | `add` ignora entradas inválidas (hora 22, duração negativa, data "data-lixo") sem avisar. | Quem chama não sabe que deu errado, e dados ruins entram. |
| 5. Desempenho e recursos | Os laços de conflito não param ao achar o conflito, e `rem` continua após remarcar. | Trabalho desnecessário. |
| 5. Desempenho e recursos | Toda operação percorre a lista inteira. | Fica lento se a agenda crescer. |

## 2. O que foi resolvido e o que ficou como dívida técnica

**Resolvido:**
- Nomes claros, constantes no lugar dos números mágicos, enum `Status` e classe `Agendamento`.
- Conflito de horário em um método só, sem o atendimento conflitar consigo mesmo.
- Validação de data, horário e duração também na remarcação.
- Erros viram `IllegalArgumentException`, e o `catch` vazio foi removido.
- Código limpo (sem comentários mortos), `Main` separado e Javadoc.

**Dívida técnica assumida:**
- A busca continua linear na lista (um `Map` seria mais rápido).
- O conflito ignora a duração do atendimento (ver decisão abaixo).
- Remarcar um atendimento inexistente continua sem aviso.
- A agenda ainda é estática e sem proteção para uso simultâneo.
- Não há testes automatizados nem a função de desmarcar.

## 3. Decisões de projeto

**Separação em classes.** O original tinha tudo em um único arquivo, inclusive o `main`. Na refatoração, o `Main` ficou só com a chamada de demonstração. A classe `Agendamento` representa um atendimento específico (paciente, profissional, data, horário, duração e status) e guarda apenas dados. A `AgendaBase` mantém a lista com todos os agendamentos e concentra as regras de negócio.

**Métodos principais e validação separada.** A `AgendaBase` tem três métodos principais: `adicionarAgendamento` (salvar na lista), `remarcarAgendamento` e `listarAgendamentosPorData`. Cada um chama métodos menores só para validar (`validarData`, `validarHorario`, `validarDuracao` e `existeConflito`). Assim, a regra de cada validação fica escrita em um único lugar, e os métodos principais ficam curtos e fáceis de ler.

**Uso de `record` na remarcação.** Para remarcar, não é preciso receber todos os atributos de um `Agendamento`. Basta identificar qual atendimento mudar, e para isso bastam paciente, data e horário. O `AgendamentoRecord` reúne só esses três dados, o que evita passar parâmetros soltos (que podiam ser trocados de ordem) e deixa claro o que identifica um atendimento.

**Conflito sem considerar a duração.** O conflito de horário considera apenas "mesmo profissional, mesma data e mesmo horário de início", sem olhar a duração. Isso deixa passar sobreposições parciais (por exemplo, 60 min às 9h e outro atendimento às 9h30). A escolha foi proposital: o código original já fazia assim, e a refatoração não deve mudar a saída do programa. Corrigir isso seria uma mudança de comportamento, então ficou como melhoria futura.