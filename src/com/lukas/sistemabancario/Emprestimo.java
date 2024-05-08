package com.lukas.sistemabancario;

import javax.swing.JOptionPane;

public final class Emprestimo {

    // Atributos da classe Emprestimo
    private Conta conta;
    private int numeroDeTentativas;
    private boolean emprestimoValidado;

    // Construtor
    public Emprestimo(Conta conta) {
        this.conta = conta;
        numeroDeTentativas = 3;
        emprestimoValidado = false;
    }

    // Método para contratar um empréstimo
    public void contratarEmprestimo() {
        int opcao = selecionarOpcaoEmprestimo();
        validarEmprestimo(opcao);
    }

    // Mostra as opções de empréstimo disponíveis para o usuário
    private int selecionarOpcaoEmprestimo() {
        int opcao = 0;
        do {
            try {
                opcao = Integer.parseInt(JOptionPane.showInputDialog(
                        null,
                        "<html>"
                        + "DIGITE [1] PARA 1.000 R$" + "<br>"
                        + "DIGITE [2] PARA 5.000 R$" + "<br>"
                        + "DIGITE [3] PARA 10.000 R$" + "<br>"
                        + "DIGITE [4] PARA 25.000 R$" + "<br>"
                        + "DIGITE [5] PARA VOLTAR AO MENU PRINCIPAL" + "<br>"
                        + "</html>"));
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "ERRO! OPÇÃO INVÁLIDA!");
            }
        } while (opcao != 1 && opcao != 2 && opcao != 3 && opcao != 4 && opcao != 5);
        return opcao;
    }

    // Valida o empréstimo com base nas credenciais corretas do usuário
    private void validarEmprestimo(int opcao) {
        do {
            if (opcao == 5) {
                emprestimoValidado = true;
                conta.Menu();
                break;
            }
            String senhaDigitada = JOptionPane.showInputDialog(null, "DIGITE SUA SENHA:" + "\n" + "[ 0 para retornar ]");
            if (senhaDigitada.equals(conta.getSenhaDoUsuario())) {
                switch (opcao) {
                    case 1:
                        contratarEmprestimo(1000.00);
                        break;
                    case 2:
                        contratarEmprestimo(5000.00);
                        break;
                    case 3:
                        contratarEmprestimo(10000.00);
                        break;
                    case 4:
                        contratarEmprestimo(25000.00);
                        break;
                }
            } else if (senhaDigitada.equals("0")) {
                conta.Menu();
                break;
            } else {
                SenhaIncorreta();
            }
        } while (numeroDeTentativas > 0 && !emprestimoValidado);

        if (numeroDeTentativas == 0) {
            JOptionPane.showMessageDialog(null, "info: NÚMERO DE TENTATIVAS EXCEDIDO, ENCERRANDO OPERAÇÃO...");
        }
    }

    // Método privado para fazer a contratação do empréstimo
    private void contratarEmprestimo(double valor) {
        emprestimoValidado = true;
        conta.setEmprestimoAtivo(true);
        conta.setSaldoDaConta(conta.getSaldoDaConta() + valor);
        JOptionPane.showMessageDialog(null, "info: EMPRÉSTIMO CONTRATADO COM SUCESSO!");
        conta.saldo();
    }

    // Método privado para validar o número de tentativas disponíveis para o usuário realizar a operação
    private void SenhaIncorreta() {
        numeroDeTentativas--;
        String tentativasMessage = numeroDeTentativas == 1 ? " TENTATIVA!" : " TENTATIVAS!";
        JOptionPane.showMessageDialog(null, "info: SENHA INCORRETA! " + "\n" + "[ " + numeroDeTentativas + tentativasMessage + " ]");
    }
}