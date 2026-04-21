/** Observador concreto: simula envio de SMS */
public class SmsNotificacao implements NotificacaoObserver {

    private final String telefone;

    public SmsNotificacao(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public void notificar(String numeroConta, String evento, double valor) {
        System.out.printf("  [SMS para %s] \n Conta %s fez %s %.2f%n MT",
                telefone, numeroConta, evento, valor);
    }
}