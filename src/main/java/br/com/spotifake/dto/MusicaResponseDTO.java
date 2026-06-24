package br.com.spotifake.dto;

import br.com.spotifake.model.Musica;

public class MusicaResponseDTO {

    private Long id;
    private String titulo;
    private Integer duracaoSegundos;
    private String duracaoFormatada; 
    private String genero;
    private Integer anoLancamento;
    private Long artistaId;
    private String artistaNome;

    public static MusicaResponseDTO fromEntity(Musica musica) {
        MusicaResponseDTO dto = new MusicaResponseDTO();
        dto.id = musica.getId();
        dto.titulo = musica.getTitulo();
        dto.duracaoSegundos = musica.getDuracaoSegundos();
        dto.duracaoFormatada = formatarDuracao(musica.getDuracaoSegundos());
        dto.genero = musica.getGenero();
        dto.anoLancamento = musica.getAnoLancamento();
        dto.artistaId = musica.getArtista().getId();
        dto.artistaNome = musica.getArtista().getNome();
        return dto;
    }

    private static String formatarDuracao(Integer segundos) {
        if (segundos == null) return null;
        int min = segundos / 60;
        int seg = segundos % 60;
        return String.format("%d:%02d", min, seg);
    }


    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getDuracaoSegundos() {
        return duracaoSegundos;
    }

    public String getDuracaoFormatada() {
        return duracaoFormatada;
    }

    public String getGenero() {
        return genero;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public Long getArtistaId() {
        return artistaId;
    }

    public String getArtistaNome() {
        return artistaNome;
    }
}
