package br.com.spotifake.dto;

import jakarta.validation.constraints.NotBlank;

public class ArtistaRequestDTO {

    @NotBlank(message = "O nome do artista é obrigatório")
    private String nome;

    private String paisOrigem;

    private Integer anoInicio;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }

    public Integer getAnoInicio() {
        return anoInicio;
    }

    public void setAnoInicio(Integer anoInicio) {
        this.anoInicio = anoInicio;
    }
}
