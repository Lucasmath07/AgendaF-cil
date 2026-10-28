import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Agenda de atendimentos de uma clínica pequena.
 *
 * <p>Permite marcar, remarcar e listar atendimentos. Toda validação de
 * regra de negócio (expediente, duração, formato de data e conflito de
 * horário) acontece aqui; as classes {@link Agendamento} e
 * {@link Status} são apenas estruturas de dados.</p>
 */
public class AgendaBase {

    private static final int HORA_INICIO_EXPEDIENTE = 8;
    private static final int HORA_FIM_EXPEDIENTE = 18;
    private static final int DURACAO_MINIMA_MINUTOS = 15;
    private static final int DURACAO_MAXIMA_MINUTOS = 120;
    private static final int INTERVALO_DURACAO_MINUTOS = 15;
    private static final String FORMATO_DATA_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    private static final List<Agendamento> AGENDAMENTOS = new ArrayList<>();

    /**
     * Agenda um novo atendimento, validando expediente, duração, formato de
     * data e conflito de horário.
     *
     * @param agendamento atendimento a ser agendado
     * @throws IllegalArgumentException se algum campo estiver inválido
     */
    public static void adicionarAgendamento(Agendamento agendamento) {
        if (agendamento == null) {
            throw new IllegalArgumentException("Agendamento não pode ser nulo");
        }
        validarAgendamento(agendamento.getData(),
                agendamento.getHorario(),
                agendamento.getDuracao());

        if (existeConflito(agendamento.getProfissional(),
                agendamento.getData(),
                agendamento.getHorario(),
                null)) {
            System.out.println("Horario ocupado");
            return;
        }

        agendamento.setStatus(Status.MARCADO);
        AGENDAMENTOS.add(agendamento);
        System.out.println("Atendimento marcado para " + agendamento.getPaciente());
    }

    /**
     * Remarca um atendimento existente para uma nova data e horário,
     * aplicando as mesmas validações do agendamento.
     *
     * @param chave      identificação do atendimento atual
     * @param novaData   nova data no formato AAAA-MM-DD
     * @param novoHorario novo horário de início (0-23)
     * @throws IllegalArgumentException se os novos valores forem inválidos
     */
    public static void remarcarAgendamento(AgendamentoRecord chave,
                                           String novaData,
                                           int novoHorario) {
        if (chave == null) {
            throw new IllegalArgumentException("Chave do agendamento não pode ser nula");
        }
        validarData(novaData);
        validarHorario(novoHorario);

        for (Agendamento a : AGENDAMENTOS) {
            boolean encontrado = a.getPaciente().equals(chave.paciente())
                    && a.getData().equals(chave.data())
                    && a.getHorario() == chave.horario();
            if (!encontrado) {
                continue;
            }
            // Valida duração e conflito usando os mesmos critérios do agendamento.
            validarDuracao(a.getDuracao());
            if (existeConflito(a.getProfissional(), novaData, novoHorario, a)) {
                System.out.println("Horario ocupado");
                return;
            }
            a.setData(novaData);
            a.setHorario(novoHorario);
            System.out.println("Remarcado");
            return;
        }
    }

    /**
     * Lista os atendimentos de uma data, imprimindo horário, paciente,
     * profissional e status.
     *
     * @param data data no formato AAAA-MM-DD
     * @throws IllegalArgumentException se a data estiver em formato inválido
     */
    public static void listarAgendamentosPorData(String data) {
        validarData(data);
        for (Agendamento a : AGENDAMENTOS) {
            if (a.getData().equals(data)) {
                System.out.println(a.getHorario() + " " + a.getPaciente()
                        + " " + a.getProfissional() + " " + a.getStatus());
            }
        }
    }


    private static void validarAgendamento(String data, int horario, int duracao) {
        validarData(data);
        validarHorario(horario);
        validarDuracao(duracao);
    }

    private static void validarData(String data) {
        if (data == null || !data.matches(FORMATO_DATA_REGEX)) {
            throw new IllegalArgumentException(
                    "Data deve estar no formato AAAA-MM-DD: " + data);
        }
        try {
            LocalDate.parse(data);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data inválida: " + data, e);
        }
    }

    private static void validarHorario(int horario) {
        if (horario < HORA_INICIO_EXPEDIENTE || horario >= HORA_FIM_EXPEDIENTE) {
            throw new IllegalArgumentException(
                    "Horário fora do expediente (" + HORA_INICIO_EXPEDIENTE
                            + "h–" + HORA_FIM_EXPEDIENTE + "h): " + horario);
        }
    }

    private static void validarDuracao(int duracao) {
        boolean valida = duracao >= DURACAO_MINIMA_MINUTOS
                && duracao <= DURACAO_MAXIMA_MINUTOS
                && duracao % INTERVALO_DURACAO_MINUTOS == 0;
        if (!valida) {
            throw new IllegalArgumentException(
                    "Duração deve estar entre " + DURACAO_MINIMA_MINUTOS
                            + " e " + DURACAO_MAXIMA_MINUTOS
                            + " minutos, em múltiplos de "
                            + INTERVALO_DURACAO_MINUTOS + ": " + duracao);
        }
    }


    // Decisão: um conflito ocorre quando o MESMO profissional tem outro
    // atendimento na MESMA data e MESMO horário. A duração não entra na
    // chave porque o código-base ignora sobreposição parcial — mantemos o
    // comportamento atual para respeitar a Regra de Ouro (mesma saída).
    // O parâmetro 'ignorar' permite que o próprio agendamento sendo
    // remarcado não seja considerado conflito consigo mesmo.
    private static boolean existeConflito(String profissional,
                                          String data,
                                          int horario,
                                          Agendamento ignorar) {
        for (Agendamento b : AGENDAMENTOS) {
            if (b == ignorar) {
                continue;
            }
            if (b.getData().equals(data)
                    && b.getHorario() == horario
                    && b.getProfissional().equals(profissional)) {
                return true;
            }
        }
        return false;
    }
}