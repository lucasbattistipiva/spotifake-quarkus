package br.com.spotifake.dto;

import br.com.spotifake.model.Musica;
import br.com.spotifake.model.Playlist;

import java.util.List;
import java.util.stream.Collectors;

public class PlaylistResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Boolean publica;
    private Long usuarioId;
    private String usuarioNome;
    private int totalMusicas;
    private List<MusicaResponseDTO> musicas;

    public static PlaylistResponseDTO fromEntity(Playlist playlist) {
        PlaylistResponseDTO dto = new PlaylistResponseDTO();
        dto.id = playlist.getId();
        dto.nome = playlist.getNome();
        dto.descricao = playlist.getDescricao();
        dto.publica = playlist.getPublica();
        dto.usuarioId = playlist.getUsuario().getId();
        dto.usuarioNome = playlist.getUsuario().getNome();

        List<Musica> musicas = playlist.getMusicas();
        dto.totalMusicas = musicas != null ? musicas.size() : 0;
        dto.musicas = musicas != null
            ? musicas.stream().map(MusicaResponseDTO::fromEntity).collect(Collectors.toList())
            : List.of();

        return dto;
    }

    

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public Boolean getPublica() { return publica; }
    public Long getUsuarioId() { return usuarioId; }
    public String getUsuarioNome() { return usuarioNome; }
    public int getTotalMusicas() { return totalMusicas; }
    public List<MusicaResponseDTO> getMusicas() { return musicas; }
}
