/**
 * Representa um atendimento da clínica, com paciente, profissional,
 * data, horário, duração e status.
 */
public class Agendamento {

    private String paciente;
    private String profissional;
    private String data;
    private int horario;
    private int duracao;
    private Status status;

    /**
     * Cria um agendamento ainda sem status definido (o status é atribuído
     * no momento em que é efetivamente marcado na agenda).
     *
     * @param paciente     nome do paciente
     * @param profissional nome do profissional
     * @param data         data no formato AAAA-MM-DD
     * @param horario      hora de início (0-23)
     * @param duracao      duração em minutos
     */
    public Agendamento(String paciente, String profissional, String data, int horario, int duracao) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.horario = horario;
        this.duracao = duracao;
    }

    public Agendamento() {
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getProfissional() {
        return profissional;
    }

    public void setProfissional(String profissional) {
        this.profissional = profissional;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}