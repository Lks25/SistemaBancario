package com.lukas.sistemabancario;

import javax.swing.JOptionPane;
import java.util.concurrent.ThreadLocalRandom; //instrução para gerar números aleatórios

//NESSA CLASSE É FEITO A ABERTURA DE CONTA DO USUÁRIO:
public abstract class Cadastro {

    //ATRIBUTOS:
    private String nomeDoUsuario;
    private String cpfDoUsuario;
    private String numeroDaConta;
    private String senhaDoUsuario;
    private String tipoDaConta;
    private double saldoDaConta;
    private boolean statusDaConta;

    //CONSTRUTOR:
    public Cadastro() {
        this.nomeDoUsuario = "";
        this.cpfDoUsuario = "";
        this.numeroDaConta = "";
        this.senhaDoUsuario = "";
        this.tipoDaConta = "";
        this.saldoDaConta = 0.00;
        this.statusDaConta = false;
    }

    //MÉTODO PARA ABRIR A CONTA DO USUÁRIO:
    public void abrirConta() {
        boolean dadoInvalido = false; //variável de suporte para fazer validações dentro do método
        String cpfString = "";
        String senhaString = "";
        
        //COLETA O NOME DO USUÁRIO:
        this.nomeDoUsuario = JOptionPane.showInputDialog(null, "DIGITE SEU NOME COMPLETO: ");
        
        //NESSE BLOCO É COLETADO O CPF DO USUÁRIO:
        do {
            dadoInvalido = false;
            cpfString = JOptionPane.showInputDialog(null, "DIGITE SEU CPF [somente números]: ");
            if (cpfString.length() == 11 && cpfString.matches("[0-9]+")) {
                this.cpfDoUsuario = cpfString.substring(0, 9) + "-" + cpfString.substring(9);
            } else if (cpfString.length() != 11 && cpfString.matches("[0-9]+")) {
                dadoInvalido = true;
                JOptionPane.showMessageDialog(null, "info: CPF INVÁLIDO! O CPF DEVE CONTER 11 DÍGITOS.");
            } else if (!cpfString.matches("[0-9]")) {
                dadoInvalido = true;
                JOptionPane.showMessageDialog(null, "info: CPF INVÁLIDO! [somente números]");
            }
        } while (dadoInvalido);

        //ALGORÍTMO PARA GERRAR O NÚMERO DA CONTA DO USUÁRIO DE FORMA ALEATÓRIA:
        for (int i = 0; i < 12; i++) {
            int numero = ThreadLocalRandom.current().nextInt(0, 9);
            this.numeroDaConta += numero;
        }
        
        //BLOCO PARA CADASTRAR A SENHA DO USUÁRIO:
        do {
            dadoInvalido = false;
            String senhaValidada = "";
            senhaString = JOptionPane.showInputDialog(null, "DEFINA SUA SENHA COM 6 DIGITOS: ");
            senhaValidada = JOptionPane.showInputDialog(null, "DIGITE NOVAMENTE: ");
            if (senhaString.equals(senhaValidada)) {
                if (senhaString.length() == 6 && senhaString.matches("[0-9]+")) {
                    this.senhaDoUsuario = senhaString;
                } else if (senhaString.length() != 6 && senhaString.matches("[0-9]+")) {
                    dadoInvalido = true;
                    JOptionPane.showMessageDialog(null, "info: SENHA INVÁLIDA! A SENHA DEVE CONTER 6 DÍGITOS.");
                } else if (!senhaString.matches("[0-9]")) {
                    dadoInvalido = true;
                    JOptionPane.showMessageDialog(null, "info: SENHA INVÁLIDA! DIGITE SOMENTE NÚMEROS.");
                }
            } else {
                dadoInvalido = true;
                JOptionPane.showMessageDialog(null, "info: SENHAS DIVERGENTES!");
            }
        } while (dadoInvalido);
        
        //BLOCO PARA DEFINIR O TIPO DE CONTA: CC = CONTA CORRENTE / CP = CONTA POUPANÇA:
        do {
            dadoInvalido = false;
            this.tipoDaConta = JOptionPane.showInputDialog(null, "DIGITE [1] PARA CONTA CORRENTE" + "\n" +
                    "DIGITE [2] PARA CONTA POUPANÇA");

            if (this.tipoDaConta.equals("1")) {
                this.tipoDaConta = "CC";
            } else if (this.tipoDaConta.equals("2")) {
                this.tipoDaConta = "CP";
            } else {
                dadoInvalido = true;
                JOptionPane.showMessageDialog(null, "info: OPÇÃO INVÁLIDA!");
            }
        } while (dadoInvalido);
        
        //FINALIZA O PROCEDIMENTO DE ABERTURA DA CONTA COM BASE NA ESCOLHA DO USUÁRIO:
        do {
            dadoInvalido = false;
            String opcao = JOptionPane.showInputDialog(null, "VOCÊ CONFIRMA A ABERTURA DA CONTA?" + "\n" + "[ [1] SIM / [2] NÃO ]");
            if (opcao.equals("1")) {
                this.statusDaConta = true;
                JOptionPane.showMessageDialog(null, "info: PROCEDIMENTO DE ABERTURA DE CONTA EFETUADO COM SUCESSO!");
            } else if (opcao.equals("2")) {
                this.nomeDoUsuario = "";
                this.cpfDoUsuario = "";
                this.numeroDaConta = "";
                this.senhaDoUsuario = "";
                this.tipoDaConta = "";
                this.saldoDaConta = 0.00;
                this.statusDaConta = false;
                JOptionPane.showMessageDialog(null, "info: OPERAÇÃO CANCELADA!");
            } else {
                dadoInvalido = true;
                JOptionPane.showMessageDialog(null, "info: OPÇÃO INVÁLIDA!");
            }
        } while (dadoInvalido);
    }

    //MÉTODOS ACESSORES E MODIFICADORES DA CLASSE (GETTERS/SETTERS):
    public String getNomeDoUsuario() {
        return nomeDoUsuario;
    }

    public void setNomeDoUsuario(String nomeDoUsuario) {
        this.nomeDoUsuario = nomeDoUsuario;
    }

    public String getCpfDoUsuario() {
        return cpfDoUsuario;
    }

    public void setCpfString(String cpfDoUsuario) {
        this.cpfDoUsuario = cpfDoUsuario;
    }

    public String getNumeroDaConta() {
        return numeroDaConta;
    }

    public void setNumeroDaConta(String numeroDaConta) {
        this.numeroDaConta = numeroDaConta;
    }

    public String getSenhaDoUsuario() {
        return senhaDoUsuario;
    }

    public void setSenhaDoUsuario(String senhaDoUsuario) {
        this.senhaDoUsuario = senhaDoUsuario;
    }

    public String getTipoDaConta() {
        return tipoDaConta;
    }

    public void setTipoDaConta(String tipoDaConta) {
        this.tipoDaConta = tipoDaConta;
    }

    public double getSaldoDaConta() {
        return saldoDaConta;
    }

    public void setSaldoDaConta(double saldoDaConta) {
        this.saldoDaConta = saldoDaConta;
    }

    public boolean getStatusDaConta() {
        return statusDaConta;
    }

    public void setStatusDaConta(boolean statusDaConta) {
        this.statusDaConta = statusDaConta;
    }

}
