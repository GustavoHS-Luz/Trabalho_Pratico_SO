# Trabalho_Pratico_SO

# Introdução

Este projeto consiste na implementação do Algoritmo do Banqueiro, desenvolvido para a disciplina de Sistemas Operacionais. O objetivo é simular um sistema que gerencia a alocação de recursos entre múltiplos clientes (threads), garantindo que o sistema nunca entre em estado de deadlock.

# Desenvolvimento

A implementação foi realizada na linguagem Java, para estruturar a lógica do banqueiro e das threads clientes.

- *Multithreading:* Foram criadas 5 threads independentes para representar os clientes, operando em um loop contínuo de solicitação e liberação de recursos.
- *Estruturas de Dados:* Foram utilizados vetores e matrizes para representar o montante disponível, a demanda máxima, a alocação atual e a necessidade remanescente de cada cliente.
- *Controle de Concorrência:* Para prevenir condições de corrida no acesso aos dados compartilhados, utilizou-se a classe ReentrantLock, que atua como o mutex solicitado.
- *Algoritmo de Segurança:* Implementou-se uma função que simula a concessão de recursos e percorre os processos para verificar se, com o que resta no "banco", é possível satisfazer ao menos um cliente até que todos terminem. Caso o estado se torne inseguro, a solicitação é negada e os valores são revertidos.

# Resultado

O programa é executado via linha de comando
- Durante a execução, o terminal exibe o log de cada cliente solicitando quantidades aleatórias de recursos.
- É possível observar o Banqueiro intervindo: solicitações que respeitam o limite de segurança são marcadas como "aceitas", enquanto aquelas que poderiam levar ao travamento do sistema são marcadas como "recusadas".
- O log demonstra a alternância das threads, comprovando a execução paralela e a integridade dos dados mantida pelo mutex.

# Conclusão

Embora seja um algoritmo que pode negar solicitações que não necessariamente causariam um deadlock imediato, ele garante a estabilidade operacional exigida em sistemas críticos. O projeto permitiu consolidar conceitos práticos de programação multithreaded, sincronização de processos e gerenciamento de estados em tempo de execução.
