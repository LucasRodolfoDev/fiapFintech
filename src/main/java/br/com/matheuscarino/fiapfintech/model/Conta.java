package br.com.matheuscarino.fiapfintech.model;

import java.time.LocalDateTime;

public class Conta {

    private Long id;
    private Long clienteId;
    private int tipoConta;
    private double saldo;
    private boolean status;
    private LocalDateTime dataCriacao;

    public Conta() {
        super();
    }

    public Conta(Long clienteId, int tipoConta, double saldo, boolean status, LocalDateTime dataCriacao) {
        super();
        this.clienteId = clienteId;
        this.tipoConta = tipoConta;
        this.saldo = saldo;
        this.status = status;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public int getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(int tipoConta) {
        this.tipoConta = tipoConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}

