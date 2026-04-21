/**
 * PATTERN: Observer
 * Define o contrato para todos os observadores (listeners) de uma conta.
 * Sempre que uma operação ocorre, todos os observadores são notificados.
 */
public interface NotificacaoObserver {
    void notificar(String numeroConta, String evento, double valor);
}