public class Main{
    public static void main(String[] args) {
        AgendaBase.adicionarAgendamento(new Agendamento("Ana", "Dr. Silva", "2026-10-05", 9, 30));
        AgendaBase.adicionarAgendamento(new Agendamento("Bruno", "Dr. Silva", "2026-10-05", 9, 30));
        AgendaBase.adicionarAgendamento(new Agendamento("Carla", "Dra. Souza", "2026-10-05", 14, 60));
        AgendaBase.remarcarAgendamento(new AgendamentoRecord("Ana", "2026-10-05", 9), "2026-10-06", 10);
        AgendaBase.listarAgendamentosPorData("2026-10-05");
    }
}
