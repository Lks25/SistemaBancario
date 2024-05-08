package com.lukas.sistemabancario;

//SWING É IMPLEMENTADO DE FORMA SIMPLES PARA ILUSTRAR MELHOR A LÓGICA DO PROGRAMA:
import javax.swing.JOptionPane; 

public class Main {
    
    public static void main(String[] args) {
        
        JOptionPane.showMessageDialog(null, "BEM-VINDO AO SISTEMA BANCÁRIO");
        
        Conta conta = new Conta();
        conta.Menu();
        
    }
    
}