import java.util.Scanner;

/**
 * Candidato: Vitor Gledison Oliveira de Souza
 *
 * Problema de programação de nível Sênior, Fase 1: Chuva
 * https://olimpiada.ic.unicamp.br/pratique/pu/2019/f1/chuva/
 *
 */
public class Solucao {

    public static void main(String[] args) {
        /**
         * Restrições de tamanho dos inteiros N e M
         */
        final int MIN = 3;
        final int MAX = 500;

        Scanner in = new Scanner(System.in);

        char[][] parede;

        String linha;

        int i, j;

        int n = in.nextInt();
        int m = in.nextInt();

        /**
         * Verfica se os inteiros N e M estão dentro do intervalo minimo e máximo,
         * assim como verfica se o número de linhas (N) é par, caso alguma dessas
         * restrições for violada o programa é encerrado.
         */
        if ( (n < MIN || n > MAX) || (m < MIN || m > MAX) || (n % 2 == 0)) {
            return;
        }

        parede = new char[n][m];

        // ********************** Entrada da matriz **********************
        for (i = 0; i < n; i++) {
            /**
             * ler uma linha inteira da entrada e armazena na variável 'linha'
             */
            linha = in.next();
            for (j = 0; j < m; j++) {
                /**
                 * Percorre uma linha inteira da matriz 'parede' atribuindo a cada posição
                 * um dos elementos de 'linha'
                 */
                parede[i][j] = linha.charAt(j);
            }
        }

        // ********************** Restrições da matriz **********************
        /**
         * Variável para determinar a quantidade de 'o' na primeira linha da matriz
         */
        int quantidadeGotasPrimeiraLinha = 0;

        for (i = 0; i < n; i++) {
            /**
             * O módulo de 'i' determina se a linha é ímpar, caso seja, verifica-se a
             * primeira e última coluna para confirmar que não existem prateleiras
             * nessas posições, caso exista o programa encerra.
             */
            if ((i % 2 != 0) && (parede[i][0] == '#') || (parede[i][(m-1)] == '#')) {
                return;
            }

            for (j = 0; j < m; j++) {
                /**
                 * Caso esteja na primeira linha (i = 0), será verificada a quantidade de 'o',
                 * contendo o elemento a variável 'quantidadeGotasPrimeiraLinha' é incrementada.
                 */
                if ( (i == 0) && (parede[i][j] == 'o') ) {
                    quantidadeGotasPrimeiraLinha++;
                }

                /**
                 * Verifica primeiramente se existem prateleiras (#) em linhas pares, ou se nas colunas
                 * de cada linha existe caracteres diferentes de '#', 'o' e '.', caso alguma das condições
                 * for verdadeira o programa é encerrado.
                 */
                if (((i % 2 == 0) && (parede[i][j] == '#')) || ((parede[i][j] != '#') && (parede[i][j] != '.') && (parede[i][j] != 'o'))) {
                    return;
                }
            }
            /**
             * Caso a quantidade de gotas de água na primeira coluna for maior que 1, o programa se encerra.
             */
            if (i == 0 && quantidadeGotasPrimeiraLinha != 1) {
                return;
            }
        }

        // ********************** Lógica do escoamento da água **********************
        /**
         * A variável 'houveMudanca' serve como sinalizador para alterações na matriz, porque
         * se ainda acontecem mudanças ao percorrer a matriz, isso indica que a escoamento da
         * água ('o') não completo, caso contrário, a matriz já está pronta para ser impressa.
         */
        boolean houveMudanca;
        do {
            /**
             * Sempre começa a varíável 'houveMudanca' com false para indicar que a partir desse
             * ponto nenhuma nova mudança foi feita na matriz.
             */
            houveMudanca = false;

            /**
             * Os dois laços for, servem para pecorrer cada elemento da matriz.
             */
            for (i = 0; i < n; i++) {
                for (j = 0; j < m; j++) {
                    /**
                     * Verifica se o elemento em questão é um ponto ('.'), caso seja ele dar inicio as
                     * validações para mudar o '.' por 'o'. Caso não seja o programa continua a percorrer
                     * a matriz.
                     */
                    if (parede[i][j] == '.') {
                        if ( (i != 0) && (parede[i-1][j] == 'o') ) {
                            /**
                             * Essa condição verifica c(i-1,j)= "o", contudo deve ser verificado se o 'i' é
                             * diferente de zero, pois no caso dele ser igual a zero, o indice passado a matriz
                             * seria -1, que é um indice inválido para arrays. Caso seja verdadeira ambas as condições
                             * o valor do elemento presente na posição (i,j) é trocado por 'o', e a varíável 'houveMudanca'
                             * tem o valor true atribuído a ela, indicando que ocorreu uma alteração na matriz.
                             */
                            parede[i][j] = 'o';
                            houveMudanca = true;
                        } else if ( (j != 0) && (parede[i][j-1] == 'o') && ( (i != n-1) && (parede[i+1][j-1] == '#') ) ) {
                            /**
                             * Esse if verifica as condições c(i,j-1)= "o" e c(i+1,j-1)= "#", porém para evitar erros o valor
                             * de 'j' não pode ser zero, logo que j-1 pode gerar um índice -1, que é inválido. Outra restrição
                             * é que i não pode ser o último indice, pois i+1 estaria se referenciando a uma posição inválida da
                             * matriz gerando um erro, para resolver isso, é verificado se 'i' é diferente do número de linhas menos
                             * um (n-1), logo que os índices começam com 0 e não com 1.
                             * Caso tudo seja verdadeiro o elemento na posição (i,j) é trocado por 'o' e 'houveMudanca' é atribuído
                             * com o valor true.
                             */
                            parede[i][j] = 'o';
                            houveMudanca = true;
                        } else if ( (j != m-1) && (parede[i][j+1] == 'o') && ( (i != n-1) && (parede[i+1][j+1] == '#') ) ) {
                            /**
                             * Esse bloco verifica as condições c(i,j+1)= "o" e c(i+1,j+1)= "#", porém para evitar erros o valor
                             * de 'j' não pode ser referenciar a última coluna (m-1), logo que j+1 é um índice que não pertence a matriz.
                             * Outra restrição é que i não pode ser o último indice, pois i+1 estaria se referenciando a uma posição inválida da
                             * matriz gerando um erro, para resolver isso, é verificado se 'i' é diferente do número de linhas menos
                             * um (n-1), logo que os índices começam com 0 e não com 1.
                             * Caso tudo seja verdadeiro o elemento na posição (i,j) é trocado por 'o' e 'houveMudanca' é sinalizado como
                             * verdadeiro.
                             */
                            parede[i][j] = 'o';
                            houveMudanca = true;
                        }
                    }
                }
            }
            /**
             * Caso seja verificado alguma alteração na matriz a repetição ocorre mais uma vez.
             */
        } while(houveMudanca);

        // ********************** Saída da matriz **********************
        /**
         * Percorre cada linha da matriz.
         */
        for (i = 0; i < n; i++) {
            /**
             * Primeiramente o vetor de char de cada linha é convertido para uma string e então
             * impresso para o usuário.
             */
            System.out.println(String.valueOf(parede[i]));
        }
    }
}
