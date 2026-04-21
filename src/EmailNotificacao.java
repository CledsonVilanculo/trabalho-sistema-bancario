/** Observador concreto: simula envio de e-mail */
public class EmailNotificacao implements NotificacaoObserver {

    private final String email;

    public EmailNotificacao(String email) {
        this.email = email;
    }

    @Override
    public void notificar(String numeroConta, String evento, double valor) {
        System.out.printf("  [EMAIL para %s] \nConta %s fez %s de %.2f%n MT",
                email, numeroConta, evento, valor);
    }
}