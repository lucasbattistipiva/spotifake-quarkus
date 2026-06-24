package br.com.spotifake.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class PlaylistRequestDTO {

    @NotBlank(message = "O nome da playlist é obrigatório")
    private String nome;

    private String descricao;

    private Boolean publica = true;

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long usuarioId;

    private List<Long> musicaIds;


    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Boolean getPublica() { return publica; }
    public void setPublica(Boolean publica) { this.publica = publica; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public List<Long> getMusicaIds() { return musicaIds; }
    public void setMusicaIds(List<Long> musicaIds) { this.musicaIds = musicaIds; }
}
