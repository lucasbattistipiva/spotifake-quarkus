package br.com.spotifake.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MusicaRequestDTO {

    @NotBlank(message = "O título da música é obrigatório")
    private String titulo;

    @NotNull(message = "A duração em segundos é obrigatória")
    @Min(value = 1, message = "A duração deve ser maior que zero")
    private Integer duracaoSegundos;

    private String genero;

    private Integer anoLancamento;

    @NotNull(message = "O ID do artista é obrigatório")
    private Long artistaId;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDuracaoSegundos() {
        return duracaoSegundos;
    }

    public void setDuracaoSegundos(Integer duracaoSegundos) {
        this.duracaoSegundos = duracaoSegundos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public Long getArtistaId() {
        return artistaId;
    }

    public void setArtistaId(Long artistaId) {
        this.artistaId = artistaId;
    }
}
