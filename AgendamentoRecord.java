/**
 * Chave de identificação de um agendamento (paciente + data + horário),
 * usada para localizar o atendimento a ser remarcado.
 *
 * @param paciente nome do paciente
 * @param data     data no formato AAAA-MM-DD
 * @param horario  hora de início (0-23)
 */
public record AgendamentoRecord(String paciente, String data, int horario) {
}