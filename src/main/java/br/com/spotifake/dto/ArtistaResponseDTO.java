package br.com.spotifake.dto;

import br.com.spotifake.model.Artista;


public class ArtistaResponseDTO {

    private Long id;
    private String nome;
    private String paisOrigem;
    private Integer anoInicio;
    private int totalMusicas;

    public static ArtistaResponseDTO fromEntity(Artista artista) {
        ArtistaResponseDTO dto = new ArtistaResponseDTO();
        dto.id = artista.getId();
        dto.nome = artista.getNome();
        dto.paisOrigem = artista.getPaisOrigem();
        dto.anoInicio = artista.getAnoInicio();
        dto.totalMusicas = artista.getMusicas() != null ? artista.getMusicas().size() : 0;
        return dto;
    }


    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public Integer getAnoInicio() {
        return anoInicio;
    }

    public int getTotalMusicas() {
        return totalMusicas;
    }
}
