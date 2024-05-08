package com.lukas.sistemabancario;

import javax.swing.JOptionPane;

/*CLASSE DA CONTA DO USUÁRIO COM TODAS AS FUNCIONALIDADES IMPLEMENTADAS ATRAVÉS
DOS MÉTODOS ABSTRATOS DA INTERFACE 'Menu'*/
public class Conta extends Cadastro implements Menu {

    //ATRIBUTOS:
    private boolean beneficiario;
    private boolean dadoInvalido;
    private boolean emprestimoAtivo;
    private int saquesDisponiveis;

    //CONSTRUTOR:
    public Conta() {
        super();
        this.dadoInvalido = false;
        this.beneficiario = false;
        this.emprestimoAtivo = false;
        this.saquesDisponiveis = 2;
    }

    //MENU DE OPÇÕES DA CONTA DO USUÁRIO:
    public void Menu() {
        if (this.getStatusDaConta() == true) {
            int opcao = 0;
            do {
                dadoInvalido = false;
                try {
                    opcao = Integer.parseInt(JOptionPane.showInputDialog(
                            null,
                            "DIGITE [1] PARA VER DADOS CADASTRAIS" + "\n"
                            + "DIGITE [2] PARA SALDO" + "\n"
                            + "DIGITE [3] PARA SAQUE" + "\n"
                            + "DIGITE [4] PARA DEPÓSITO" + "\n"
                            + "DIGITE [5] PARA PIX" + "\n"
                            + "DIGITE [6] PARA PACOTES" + "\n"
                            + "DIGITE [7] PARA EMPRESTIMO" + "\n"
                            + "DIGITE [8] PARA CANCELAR CONTA" + "\n"
                            + "DIGITE [9] PARA SAIR" + "\n"));
                } catch (NumberFormatException e) {
                    dadoInvalido = true;
                    JOptionPane.showMessageDialog(null, "info: OPÇÃO INVÁLIDA!");
                }
                switch (opcao) {
                    case 1:
                        this.dadosCadastrais();
                        break;
                    case 2:
                        this.saldo();
                        break;
                    case 3:
                        this.saque();
                        break;
                    case 4:
                        this.deposito();
                        break;
                    case 5:
                        this.pix();
                        break;
                    case 6:
                        this.pacoteDeBeneficios();
                        break;
                    case 7:
                        this.emprestimo();
                        break;
                    case 8:
                        this.cancelarConta();
                        break;
                    case 9:
                        JOptionPane.showMessageDialog(null, "info: ENCERRANDO OPERAÇÃO...");
                        break;
                    default:
                        dadoInvalido = true;
                        break;
                }
            } while (dadoInvalido);
        } else {
            this.abrirContaModificado();
        }
    }

    //ESSE MÉTODO É ACIONADO CASO O USUÁRIO NÃO TENHA ABERTO UMA CONTA: 
    private void abrirContaModificado() {
        JOptionPane.showMessageDialog(null, "info: CONTA NÃO LOCALIZADA");
        do {
            dadoInvalido = false;
            String opcao = JOptionPane.showInputDialog(null, "GOSTARIA DE ABRIR UMA CONTA?" + "\n"
                    + "DIGITE [1] PARA SIM " + "\n"
                    + "DIGITE [2] PARA NÃO" + "\n");
            if (opcao.equals("1")) {
                this.abrirConta();
                if (this.getStatusDaConta()) {
                    this.Menu();
                }
            } else if (opcao.equals("2")) {
                JOptionPane.showMessageDialog(null, "info: ENCERRANDO OPERAÇÃO...");
            } else {
                dadoInvalido = true;
                JOptionPane.showMessageDialog(null, "info: OPÇÃO INVÁLIDA!");
            }
        } while (dadoInvalido);
    }

    /*MÉTODOS ABSTRATOS DA INTERFACE*/
    //MÉTODO PARA VER OS DADOS CADASTRAIS DO USUÁRIO:
    @Override
    public void dadosCadastrais() {
        if (this.getStatusDaConta() == true) {
            String mensagem = "<html>"
                    + "NOME: " + this.getNomeDoUsuario() + "<br>"
                    + "CPF: " + this.getCpfDoUsuario() + "<br>"
                    + "CONTA: " + this.getNumeroDaConta() + "<br>"
                    + "TIPO DA CONTA: " + this.getTipoDaConta() + "<br>"
                    + "STATUS DA CONTA: " + this.getStatusDaConta() + "<br>"
                    + "EMPRÉSTIMO ATIVO: " + this.getEmprestimoAtivo() + "<br>"
                    + "</html>";

            JOptionPane.showMessageDialog(
                    null,
                    mensagem,
                    "DADOS CADASTRAIS",
                    JOptionPane.INFORMATION_MESSAGE);
            this.Menu();
        } else {
            this.abrirContaModificado();
        }
    }

    //MÉTODO PARA VER O SALDO DA CONTA DO USUÁRIO:
    @Override
    public void saldo() {
        if (this.getStatusDaConta()) {
            JOptionPane.showMessageDialog(null, "SALDO: " + this.getSaldoDaConta() + " R$");
            this.Menu();
        } else {
            this.abrirContaModificado();
        }
    }

    //MÉTODO PARA O USUÁRIO REALIZAR UM SAQUE EM SUA CONTA:
    @Override
    public void saque() {
        if (this.getStatusDaConta()) {
            String senhaDigitada = JOptionPane.showInputDialog(null, "DIGITE SUA SENHA: ");
            if (senhaDigitada.equals(this.getSenhaDoUsuario())) {
                do {
                    dadoInvalido = false;
                    try {
                        double valorDoSaque = Double.parseDouble(JOptionPane.showInputDialog(null, "VALOR DO SAQUE: " + "\n" + "[ 0 para retornar ]"));
                        if (valorDoSaque > 0 && valorDoSaque <= this.getSaldoDaConta() && this.getSaquesDisponiveis() > 0) {
                            this.setSaldoDaConta(this.getSaldoDaConta() - valorDoSaque);
                            this.saquesDisponiveis--;
                            JOptionPane.showMessageDialog(null, "info: SAQUE EFETUADO COM SUCESSO!");
                        } else if (valorDoSaque > this.getSaldoDaConta()) {
                            JOptionPane.showMessageDialog(null, "info: SALDO INSUFICIENTE!");
                        } else if (valorDoSaque < 0) {
                            JOptionPane.showMessageDialog(null, "info: VALOR INVÁLIDO!");
                        } else if (valorDoSaque == 0) {
                            break;
                        } else if (this.getSaquesDisponiveis() == 0) {
                            this.setSaldoDaConta((this.getSaldoDaConta() - valorDoSaque) - 7.90); //7.90 remete ao valor da taxa de saque

                        }
                    } catch (NumberFormatException e) {
                        dadoInvalido = true;
                        JOptionPane.showMessageDialog(null, "info: VALOR INVÁLIDO!");
                    }
                } while (dadoInvalido);
            } else {
                JOptionPane.showMessageDialog(null, "info: SENHA INVÁLIDA!");
            }
            this.Menu();
        } else {
            this.abrirContaModificado();
        }
    }

    //MÉTODO PARA O USUÁRIO REALIZAR UM DEPÓSITO EM SUA CONTA:
    @Override
    public void deposito() {
        if (this.getStatusDaConta()) {
            do {
                dadoInvalido = false;
                try {
                    double valorDoDeposito = Double.parseDouble(JOptionPane.showInputDialog(null, "VALOR DO DEPÓSITO:" + "\n" + "[ 0 para retornar ]"));
                    if (valorDoDeposito > 0) {
                        this.setSaldoDaConta(this.getSaldoDaConta() + valorDoDeposito);
                        JOptionPane.showMessageDialog(null, "info: DEPÓSITO EFETUADO COM SUCESSO!");
                    } else if (valorDoDeposito == 0) {
                        break;
                    } else {
                        dadoInvalido = true;
                        JOptionPane.showMessageDialog(null, "info: VALOR INVÁLIDO!");
                    }
                } catch (NumberFormatException e) {
                    dadoInvalido = true;
                    JOptionPane.showMessageDialog(null, "info: VALOR INVÁLIDO!");
                }
            } while (dadoInvalido);
            this.Menu();
        } else {
            this.abrirContaModificado();
        }
    }

    //MÉTODO PARA REALIZAR TRANSFERÊNCIAS VIA PIX:
    @Override
    public void pix() {
        if (this.getStatusDaConta()) {
            String senhaDigitada = JOptionPane.showInputDialog(null, "DIGITE SUA SENHA: ");
            if (senhaDigitada.equals(this.getSenhaDoUsuario())) {
                do {
                    dadoInvalido = false;
                    try {
                        Double valorDoPix = Double.parseDouble(JOptionPane.showInputDialog(null, "VALOR DO PIX:" + "\n" + "[ 0 para retornar ]"));
                        if (valorDoPix > 0 && valorDoPix <= this.getSaldoDaConta()) {
                            this.setSaldoDaConta(this.getSaldoDaConta() - valorDoPix);
                            JOptionPane.showMessageDialog(null, "info: TRANSFERÊNCIA REALIZADA COM SUCESSO!");
                        } else if (valorDoPix > this.getSaldoDaConta()) {
                            JOptionPane.showMessageDialog(null, "info: SALDO INSUFICIENTE!");
                        } else if (valorDoPix < 0) {
                            dadoInvalido = true;
                            JOptionPane.showMessageDialog(null, "info: VALOR INVÁLIDO!");
                        } else if (valorDoPix == 0) {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        dadoInvalido = true;
                        JOptionPane.showMessageDialog(null, "info: VALOR INVÁLIDO!");
                    }
                } while (dadoInvalido);
            } else {
                JOptionPane.showMessageDialog(null, "info: SENHA INVÁLIDA!");
            }
            this.Menu();
        } else {
            this.abrirContaModificado();
        }
    }

    //MÉTODO PARA ADERIR OU CANCELAR O PACOTE DE BENEFÍCIOS DO BANCO:
    @Override
    public void pacoteDeBeneficios() {
        if (this.getStatusDaConta()) {
            boolean resposta = false;
            String mensagem = "<html>"
                    + " - PACOTE DE BENEFÍCIOS - " + "<br>"
                    + "<br>"
                    + "*SAQUES ILIMITADOS" + "<br>"
                    + "*OPÇÕES DE EMPRÉSTIMO" + "<br>"
                    + "*CARTÃO DE CRÉDITO GOLD" + "<br>"
                    + "*2 SORTEIOS A CADA QUINZENA" + "<br>"
                    + "</html>";

            if (!this.getBeneficiario()) {
                JOptionPane.showMessageDialog(
                        null,
                        mensagem,
                        "PACOTE",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            if (this.getBeneficiario()) {
                JOptionPane.showMessageDialog(null, "info: PARABÉNS! VOCÊ JÁ É UM BENEFICIÁRIO DO BANCO.");
            }

            do {
                dadoInvalido = false;
                String opcao = JOptionPane.showInputDialog(null, "DIGITE [1] PARA ADERIR O PACOTE DE BENEFÍCIOS POR APENAS 29.90 MENSAIS" + "\n"
                        + "DIGITE [2] PARA CANCELAR O PACOTE DA SUA CONTA" + "\n"
                        + "DIGITE [3] PARA VOLTAR AO MENU PRINCIPAL");
                if (opcao.equals("1") && this.getSaldoDaConta() >= 29.90 && !this.getBeneficiario()) {
                    String senhaDigitada = JOptionPane.showInputDialog(null, "DIGITE SUA SENHA:" + "\n" + "[ 0 para retornar ]");
                    if (senhaDigitada.equals(this.getSenhaDoUsuario())) {
                        this.setBeneficiario(true);
                        this.setSaldoDaConta(this.getSaldoDaConta() - 29.90);
                    } else if (senhaDigitada.equals("0")) {
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "info: SENHA INVÁLIDA!");
                        break;
                    }
                    JOptionPane.showMessageDialog(null, "info: ADESÃO DO PACOTE EFETUADO COM SUCESSO!");
                } else if (opcao.equals("2") && this.getBeneficiario()) {
                    String senhaDigitada = JOptionPane.showInputDialog(null, "DIGITE SUA SENHA: ");
                    if (senhaDigitada.equals(this.getSenhaDoUsuario())) {
                        this.setBeneficiario(false);
                        JOptionPane.showMessageDialog(null, "info: PACOTE CANCELADO COM SUCESSO!" + "\n"
                                + "OBS: NÃO SERÃO MAIS COBRADAS TARIFAS EM SUA CONTA.");
                    } else {
                        JOptionPane.showMessageDialog(null, "info: SENHA INVÁLIDA!");
                        break;
                    }
                } else if (opcao.equals("3")) {
                    break;
                } else if (opcao.equals("2") && !this.getBeneficiario()) {
                    JOptionPane.showMessageDialog(null, "info: NÃO HÁ UM PACOTE ATIVO EM SUA CONTA");
                    String opcao2 = "";
                    do {
                        opcao2 = JOptionPane.showInputDialog(null, "GOSTARIA DE CONHECER OS BENEFÍCIOS? [1] PARA SIM / [2] PARA SAIR");
                        dadoInvalido = false;
                        if (opcao2.equals("1")) {
                            resposta = true;
                        } else if (opcao2.equals("2")) {
                            break;
                        } else {
                            dadoInvalido = true;
                            JOptionPane.showMessageDialog(null, "info: OPÇÃO INVÁLIDA!");
                        }
                    } while (dadoInvalido);
                } else if (opcao.equals("1") && this.getSaldoDaConta() < 29.90) {
                    JOptionPane.showMessageDialog(null, "info: SALDO INSUFICIENTE!");
                } else if (opcao.equals("1") && this.getBeneficiario()) {
                    JOptionPane.showMessageDialog(null, "VOCÊ JÁ É UM BENEFICIÁRIO DO BANCO.");
                } else {
                    dadoInvalido = true;
                    JOptionPane.showMessageDialog(null, "info: OPÇÃO INVÁLIDA!");
                }
            } while (dadoInvalido);
            if (resposta) {
                this.pacoteDeBeneficios();
            } else {
                this.Menu();
            }
        } else {
            this.abrirContaModificado();
        }
    }

    @Override
    public void emprestimo() {
        if (this.getStatusDaConta()) {
            boolean resposta = false;
            if (this.getBeneficiario() && !this.getEmprestimoAtivo()) {
                do {
                    dadoInvalido = false;
                    String opcao = JOptionPane.showInputDialog(null, "HÁ OPÇÕES DE EMPRÉSTIMO DISPONÍVEIS [1] PARA VERIFICAR / [2] PARA SAIR");
                    if (opcao.equals("1")) {
                        resposta = true;
                    } else if (opcao.equals("2")) {
                        break;
                    } else {
                        dadoInvalido = true;
                        JOptionPane.showMessageDialog(null, "info: OPÇÃO INVÁLIDA!");
                    }
                } while (dadoInvalido);
            } else if (this.getBeneficiario() && getEmprestimoAtivo()) {
                JOptionPane.showMessageDialog(null, "info: HÁ UM CONTRATO VIGENTE EM ANDAMENTO");
            } else {
                JOptionPane.showMessageDialog(null, "info: NÃO HÁ OPÇÕES DE EMPRÉSTIMO DISPONÍVEIS PARA VOCÊ NO MOMENTO.");
            }
            if (resposta) {
                Emprestimo emprestimo = new Emprestimo(this);
                emprestimo.contratarEmprestimo();
            } else {
                this.Menu();
            }
        } else {
            this.abrirContaModificado();
        }
    }

    @Override
    public void cancelarConta() {
        if (this.getStatusDaConta() && this.getSaldoDaConta() == 0) {
            JOptionPane.showMessageDialog(null, "*PROCEDIMENTO DE CANCELAMENTO DA CONTA INICIADO*");
            do {
                dadoInvalido = false;
                try {
                    String cpfDigitado = JOptionPane.showInputDialog(null, "DIGITE SEU CPF [somente números]: ");
                    String senhaDigitada = JOptionPane.showInputDialog(null, "DIGITE SUA SENHA: ");
                    String cpfString = cpfDigitado.substring(0, 9) + "-" + cpfDigitado.substring(9);
                    if (cpfString.equals(this.getCpfDoUsuario()) && senhaDigitada.equals(this.getSenhaDoUsuario())) {
                        do {
                            dadoInvalido = false;
                            String opcao = JOptionPane.showInputDialog(null, "VOCÊ CONFIRMA O CANCELAMENTO? [1] PARA SIM / [2] NÃO");
                            if (opcao.equals("1")) {
                                this.setNomeDoUsuario("");
                                this.setCpfString("");
                                this.setNumeroDaConta("");
                                this.setSenhaDoUsuario("");
                                this.setTipoDaConta("");
                                this.setStatusDaConta(false);
                                JOptionPane.showMessageDialog(null, "info: CANCELAMENTO EFETUADO COM SUCESSO!");
                            } else if (opcao.equals("2")) {
                                JOptionPane.showMessageDialog(null, "info: OPERAÇÃO CANCELADA!");
                                this.Menu();
                            } else {
                                dadoInvalido = true;
                                JOptionPane.showMessageDialog(null, "info: OPÇÃO INVÁLIDA!");
                            }
                        } while (dadoInvalido);
                    } else if (!cpfString.equals(this.getCpfDoUsuario())) {
                        dadoInvalido = true;
                        JOptionPane.showMessageDialog(null, "info: CPF INCORRETO!");
                    } else if (!senhaDigitada.equals(this.getSenhaDoUsuario())) {
                        dadoInvalido = true;
                        JOptionPane.showMessageDialog(null, "info: SENHA INCORRETA!");
                    }
                } catch (Exception e) {
                    dadoInvalido = true;
                    JOptionPane.showMessageDialog(null, "ERRO: CPF INVÁLIDO!" + "\n"
                            + "OBS: SOMENTE NÚMEROS COM 11 DÍGITOS!");
                }
            } while (dadoInvalido);
        } else if (this.getSaldoDaConta() > 0) {
            JOptionPane.showMessageDialog(null, "info: HÁ VALORES PENDENTES EM SUA CONTA, REALIZE O RESGATE" + "\n" + "TOTAL DO SALDO PARA PROSSEGUIR COM A OPERAÇÃO.");
            this.Menu();
        } else {
            this.abrirContaModificado();
        }
    }

    //MÉTODOS ACESSORES E MODIFICADORES DA CLASSE (GETTERS/SETTERS):
    private boolean getBeneficiario() {
        return this.beneficiario;
    }

    private void setBeneficiario(boolean beneficiario) {
        this.beneficiario = beneficiario;
    }

    private int getSaquesDisponiveis() {
        return this.saquesDisponiveis;
    }

    private void setSaquesDisponiveis(int saquesDisponiveis) {
        this.saquesDisponiveis = saquesDisponiveis;
    }
    
    protected boolean getEmprestimoAtivo() {
        return this.emprestimoAtivo;
    }
    
    protected void setEmprestimoAtivo(boolean emprestimoAtivo) {
        this.emprestimoAtivo = emprestimoAtivo;
    }

}
