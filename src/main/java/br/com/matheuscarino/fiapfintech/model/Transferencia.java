package br.com.matheuscarino.fiapfintech.model;

import java.time.LocalDateTime;

public class Transferencia {

    private Long id;
    private Long contaOrigemId;
    private Long contaDestinoId;
    private double valor;
    private LocalDateTime dataTransferencia;

    public Transferencia() {
        super();
    }

    public Transferencia(Long contaOrigemId, Long contaDestinoId, double valor, LocalDateTime dataTransferencia) {
        super();
        this.contaOrigemId = contaOrigemId;
        this.contaDestinoId = contaDestinoId;
        this.valor = valor;
        this.dataTransferencia = dataTransferencia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContaOrigemId() {
        return contaOrigemId;
    }

    public void setContaOrigemId(Long contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    public Long getContaDestinoId() {
        return contaDestinoId;
    }

    public void setContaDestinoId(Long contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(LocalDateTime dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }
}