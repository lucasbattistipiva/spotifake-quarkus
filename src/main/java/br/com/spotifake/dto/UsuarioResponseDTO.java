package br.com.spotifake.dto;

import br.com.spotifake.model.Usuario;

public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String plano;
    private int totalPlaylists;

    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.id = usuario.getId();
        dto.nome = usuario.getNome();
        dto.email = usuario.getEmail();
        dto.plano = usuario.getPlano();
        dto.totalPlaylists = usuario.getPlaylists() != null ? usuario.getPlaylists().size() : 0;
        return dto;
    }


    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getPlano() { return plano; }
    public int getTotalPlaylists() { return totalPlaylists; }
}
