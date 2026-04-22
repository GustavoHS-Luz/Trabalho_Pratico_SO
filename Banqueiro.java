import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Banqueiro {
    private final int n;  
    private final int m;  
    private final int[] disponivel;  
    private final int[][] maximo;  
    private final int[][] alocacao;  
    private final int[][] necessidade;  
    private final Lock travaMutex = new ReentrantLock();  

    public Banqueiro(int nClientes, int[] recursosIniciais) {
        this.n = nClientes;
        this.m = recursosIniciais.length;
        this.disponivel = Arrays.copyOf(recursosIniciais, m);
        this.maximo = new int[n][m];
        this.alocacao = new int[n][m];
        this.necessidade = new int[n][m];
        
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                maximo[i][j] = rand.nextInt(recursosIniciais[j] + 1);
                necessidade[i][j] = maximo[i][j];
            }
        }
    }

    public int solicitar_recursos(int numCliente, int[] solicitacao) {
        travaMutex.lock();  
        try {
            System.out.print("Cliente " + numCliente + " solicita: " + Arrays.toString(solicitacao));

            for (int i = 0; i < m; i++) {
                if (solicitacao[i] > necessidade[numCliente][i] || solicitacao[i] > disponivel[i]) {
                    System.out.println(" -> RECUSADA (Parâmetros inválidos)");
                    return -1;
                }
            }

            for (int i = 0; i < m; i++) {
                disponivel[i] -= solicitacao[i];
                alocacao[numCliente][i] += solicitacao[i];
                necessidade[numCliente][i] -= solicitacao[i];
            }

            if (estadoSeguro()) {
                System.out.println(" -> ACEITA (Estado Seguro)");
                return 0;
            } else {
                for (int i = 0; i < m; i++) {
                    disponivel[i] += solicitacao[i];
                    alocacao[numCliente][i] -= solicitacao[i];
                    necessidade[numCliente][i] += solicitacao[i];
                }
                System.out.println(" -> RECUSADA (Estado Inseguro)");
                return -1;
            }
        } finally {
            travaMutex.unlock();
        }
    }

    public void liberar_recursos(int numCliente, int[] liberacao) {
        travaMutex.lock();
        try {
            System.out.println("Cliente " + numCliente + " liberando: " + Arrays.toString(liberacao));
            for (int i = 0; i < m; i++) {
                disponivel[i] += liberacao[i];
                alocacao[numCliente][i] -= liberacao[i];
                necessidade[numCliente][i] += liberacao[i];
            }
        } finally {
            travaMutex.unlock();
        }
    }

    private boolean estadoSeguro() {
        int[] trabalho = Arrays.copyOf(disponivel, m);
        boolean[] finalizado = new boolean[n];

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                if (!finalizado[i]) {
                    boolean possivel = true;
                    for (int j = 0; j < m; j++) {
                        if (necessidade[i][j] > trabalho[j]) {
                            possivel = false;
                            break;
                        }
                    }
                    if (possivel) {
                        for (int j = 0; j < m; j++) trabalho[j] += alocacao[i][j];
                        finalizado[i] = true;
                    }
                }
            }
        }
        for (boolean f : finalizado) if (!f) return false;
        return true;
    }
}