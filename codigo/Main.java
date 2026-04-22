package codigo;
public class Main {
    public static void main(String[] args) {
        int[] recursosIniciais;
        if (args.length == 0) {
            recursosIniciais = new int[] {10, 5, 7};
            System.out.println("Nenhum argumento informado. Usando recursos padrao: [10, 5, 7]");
        } else {
            recursosIniciais = new int[args.length];
            for (int i = 0; i < args.length; i++) {
                try {
                    recursosIniciais[i] = Integer.parseInt(args[i]);
                } catch (NumberFormatException e) {
                    System.out.println("Erro: todos os argumentos devem ser inteiros.");
                    return;
                }
            }
        }

        int totalClientes = 5; 
        Banqueiro banqueiro = new Banqueiro(totalClientes, recursosIniciais);

        for (int i = 0; i < totalClientes; i++) {
            final int idCliente = i;
            
            Thread threadCliente = new Thread(() -> {
                java.util.Random gerador = new java.util.Random();
                
                while (true) { 
                    int[] solicitacao = new int[recursosIniciais.length];
                    for (int r = 0; r < recursosIniciais.length; r++) {
                        solicitacao[r] = gerador.nextInt(recursosIniciais[r] / 2 + 1);
                    }

                    banqueiro.solicitar_recursos(idCliente, solicitacao);

                    try { Thread.sleep(gerador.nextInt(3000)); } catch (InterruptedException e) {}

                    banqueiro.liberar_recursos(idCliente, solicitacao);

                    try { Thread.sleep(gerador.nextInt(3000)); } catch (InterruptedException e) {}
                }
            });

            threadCliente.start(); 
        }
    }
}