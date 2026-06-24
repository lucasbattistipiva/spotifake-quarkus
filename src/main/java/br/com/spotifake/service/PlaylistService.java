package br.com.spotifake.service;

import br.com.spotifake.dto.PlaylistRequestDTO;
import br.com.spotifake.dto.PlaylistResponseDTO;
import br.com.spotifake.model.Musica;
import br.com.spotifake.model.Playlist;
import br.com.spotifake.model.Usuario;
import br.com.spotifake.repository.MusicaRepository;
import br.com.spotifake.repository.PlaylistRepository;
import br.com.spotifake.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PlaylistService {

    @Inject
    PlaylistRepository playlistRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    MusicaRepository musicaRepository;

    @Transactional
    public PlaylistResponseDTO criar(PlaylistRequestDTO dto) {
        Usuario usuario = usuarioRepository.buscarPorId(dto.getUsuarioId())
            .orElseThrow(() -> new NotFoundException(
                "Usuário com ID " + dto.getUsuarioId() + " não encontrado"));

        Playlist playlist = new Playlist();
        playlist.setNome(dto.getNome());
        playlist.setDescricao(dto.getDescricao());
        playlist.setPublica(dto.getPublica() != null ? dto.getPublica() : true);
        playlist.setUsuario(usuario);

        if (dto.getMusicaIds() != null && !dto.getMusicaIds().isEmpty()) {
            List<Musica> musicas = resolverMusicas(dto.getMusicaIds());
            playlist.setMusicas(musicas);
        }

        playlistRepository.salvar(playlist);
        return PlaylistResponseDTO.fromEntity(playlist);
    }

    public PlaylistResponseDTO buscarPorId(Long id) {
        Playlist playlist = playlistRepository.buscarPorId(id)
            .orElseThrow(() -> new NotFoundException("Playlist com ID " + id + " não encontrada"));
        return PlaylistResponseDTO.fromEntity(playlist);
    }

    public List<PlaylistResponseDTO> listarTodas() {
        return playlistRepository.listarTodas()
            .stream()
            .map(PlaylistResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public List<PlaylistResponseDTO> listarPorUsuario(Long usuarioId) {
        usuarioRepository.buscarPorId(usuarioId)
            .orElseThrow(() -> new NotFoundException(
                "Usuário com ID " + usuarioId + " não encontrado"));

        return playlistRepository.listarPorUsuario(usuarioId)
            .stream()
            .map(PlaylistResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional
    public PlaylistResponseDTO atualizar(Long id, PlaylistRequestDTO dto) {
        Playlist playlist = playlistRepository.buscarPorId(id)
            .orElseThrow(() -> new NotFoundException("Playlist com ID " + id + " não encontrada"));

        Usuario usuario = usuarioRepository.buscarPorId(dto.getUsuarioId())
            .orElseThrow(() -> new NotFoundException(
                "Usuário com ID " + dto.getUsuarioId() + " não encontrado"));

        playlist.setNome(dto.getNome());
        playlist.setDescricao(dto.getDescricao());
        if (dto.getPublica() != null) playlist.setPublica(dto.getPublica());
        playlist.setUsuario(usuario);

        if (dto.getMusicaIds() != null) {
            playlist.setMusicas(resolverMusicas(dto.getMusicaIds()));
        }

        return PlaylistResponseDTO.fromEntity(playlistRepository.atualizar(playlist));
    }

    @Transactional
    public void remover(Long id) {
        boolean removida = playlistRepository.remover(id);
        if (!removida) {
            throw new NotFoundException("Playlist com ID " + id + " não encontrada");
        }
    }

    private List<Musica> resolverMusicas(List<Long> ids) {
        List<Musica> musicas = new ArrayList<>();
        for (Long musicaId : ids) {
            Musica musica = musicaRepository.buscarPorId(musicaId)
                .orElseThrow(() -> new NotFoundException(
                    "Música com ID " + musicaId + " não encontrada"));
            musicas.add(musica);
        }
        return musicas;
    }
    
    
}
