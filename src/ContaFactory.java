/**
 * PATTERN: Factory Method
 * Centraliza a criação de contas. O cliente nunca usa "new ContaCorrente()"
 * diretamente — sempre passa pela factory, que garante numeração e registro.
 */
public class ContaFactory {

    public enum TipoConta { CORRENTE, POUPANCA }

    /**
     * Cria, registra no banco e retorna a conta correta
     * sem expor detalhes de construção ao cliente.
     */
    public static Conta criar(TipoConta tipo, String titular, double saldoInicial) {
        Banco banco = Banco.getInstance();
        String numero = banco.gerarNumeroConta();

        if (banco.buscarConta(titular).isPresent()) {
            throw new IllegalArgumentException("Titular já possui conta!");
        }

        Conta conta = switch (tipo) {
            case CORRENTE -> new ContaCorrente(numero, titular, saldoInicial);
            case POUPANCA -> new ContaPoupanca(numero, titular, saldoInicial);
        };

        //banco.adicionarConta(conta);
        return conta;
    }
}