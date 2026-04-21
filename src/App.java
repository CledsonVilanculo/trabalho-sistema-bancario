/**
 * Demonstraçao do Sistema Bancário com Design Patterns
 * ─────────────────────────────────────────────────────
 *  • Singleton  → Banco (instância única)
 *  • Factory    → ContaFactory (criação centralizada)
 *  • Observer   → Email e SMS notificam automaticamente
 *  • Strategy   → Taxa de saque trocável em runtime
 */

import java.util.Scanner;

/**
 * Interface Interativa do Sistema Bancário
 * Demonstra o uso de Singleton, Factory, Observer e Strategy.
 */
public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Banco banco = Banco.getInstance(); // Singleton

    public static void main(String[] args) {
        System.out.println("======== SISTEMA BANCARIO ========");
        
        int opcao = -1;
        while (opcao != 0) {
            exibirMenuPrincipal();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                processarOpcao(opcao);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        System.out.println("Encerrando sistema...");
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Criar Nova Conta (Factory)");
        System.out.println("2. Realizar Depósito");
        System.out.println("3. Realizar Saque");
        System.out.println("4. Transferência");
        System.out.println("5. Adicionar Notificaçoes (Observer)");
        System.out.println("6. Alterar Estratégia de Taxa (Strategy)");
        System.out.println("7. Listar Todas as Contas");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opçao: ");
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> criarConta();
            case 2 -> depositar();
            case 3 -> sacar();
            case 4 -> transferir();
            case 5 -> adicionarObserver();
            case 6 -> alterarStrategy();
            case 7 -> banco.listarContas();
            case 0 -> {}
            default -> System.out.println("Opçao inválida!");
        }
    }

    private static void criarConta() {
        System.out.print("Tipo (1-Corrente, 2-Poupança): ");
        int tipoInt = Integer.parseInt(scanner.nextLine());
        ContaFactory.TipoConta tipo = (tipoInt == 1) ? 
                // true                             // false
                ContaFactory.TipoConta.CORRENTE : ContaFactory.TipoConta.POUPANCA;

        System.out.print("Titular: ");
        String titular = scanner.nextLine();
        System.out.print("Saldo Inicial: ");
        double saldo = Double.parseDouble(scanner.nextLine());

        banco.adicionarConta(ContaFactory.criar(tipo, titular, saldo));
    }

    private static void depositar() {
        System.out.print("Número da Conta: ");
        String numero = scanner.nextLine();
        banco.buscarConta(numero).ifPresentOrElse(c -> {
            System.out.print("Valor do Depósito: ");
            double valor = Double.parseDouble(scanner.nextLine());
            c.depositar(valor);
        }, () -> System.out.println("Conta nao encontrada!"));
    }

    private static void sacar() {
        System.out.print("Número da Conta: ");
        String numero = scanner.nextLine();
        banco.buscarConta(numero).ifPresentOrElse(c -> {
            System.out.print("Valor do Saque: ");
            double valor = Double.parseDouble(scanner.nextLine());
            c.sacar(valor);
        }, () -> System.out.println("Conta nao encontrada!"));
    }

    private static void transferir() {
        System.out.print("Número da Conta Origem: ");
        String de = scanner.nextLine();
        System.out.print("Número da Conta Destino: ");
        String para = scanner.nextLine();

        var contaDe = banco.buscarConta(de);
        var contaPara = banco.buscarConta(para);

        if (contaDe.isPresent() && contaPara.isPresent()) {
            System.out.print("Valor da Transferência: ");
            double valor = Double.parseDouble(scanner.nextLine());
            contaDe.get().transferir(contaPara.get(), valor);
        } else {
            System.out.println("Uma ou ambas as contas nao foram encontradas!");
        }
    }

    private static void adicionarObserver() {
        System.out.print("Número da Conta: ");
        String numero = scanner.nextLine();
        banco.buscarConta(numero).ifPresentOrElse(c -> {
            System.out.println("Tipo de Alerta (1-Email, 2-SMS): ");
            int tipo = Integer.parseInt(scanner.nextLine());
            if (tipo == 1) {
                System.out.print("Digite o Email: ");
                c.adicionarObservador(new EmailNotificacao(scanner.nextLine()));
            } else {
                System.out.print("Digite o Telefone: ");
                c.adicionarObservador(new SmsNotificacao(scanner.nextLine()));
            }
            System.out.println("Observador registrado!");
        }, () -> System.out.println("Conta nao encontrada!"));
    }

    private static void alterarStrategy() {
        System.out.print("Número da Conta (Apenas Corrente): ");
        String numero = scanner.nextLine();
        banco.buscarConta(numero).ifPresentOrElse(c -> {
            if (c instanceof ContaCorrente cc) {
                System.out.println("Nova Estratégia (1-Standard 2%, 2-Premium 0.5%): ");
                int opt = Integer.parseInt(scanner.nextLine());
                cc.setTaxaStrategy(opt == 2 ? new TaxaPremium() : new TaxaStandard());
            } else {
                System.out.println("Esta conta nao permite troca de estratégia de taxas.");
            }
        }, () -> System.out.println("Conta nao encontrada!"));
    }
}
