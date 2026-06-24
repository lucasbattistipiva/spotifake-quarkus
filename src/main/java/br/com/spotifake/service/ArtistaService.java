package br.com.spotifake.service;

import br.com.spotifake.dto.ArtistaRequestDTO;
import br.com.spotifake.dto.ArtistaResponseDTO;
import br.com.spotifake.model.Artista;
import br.com.spotifake.repository.ArtistaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class ArtistaService {

    @Inject
    ArtistaRepository artistaRepository;

    @Transactional
    public ArtistaResponseDTO criar(ArtistaRequestDTO dto) {
        Artista artista = new Artista();
        artista.setNome(dto.getNome());
        artista.setPaisOrigem(dto.getPaisOrigem());
        artista.setAnoInicio(dto.getAnoInicio());

        artistaRepository.salvar(artista);
        return ArtistaResponseDTO.fromEntity(artista);
    }

    public ArtistaResponseDTO buscarPorId(Long id) {
        Artista artista = artistaRepository.buscarPorId(id)
            .orElseThrow(() -> new NotFoundException("Artista com ID " + id + " não encontrado"));
        return ArtistaResponseDTO.fromEntity(artista);
    }

    public List<ArtistaResponseDTO> listarTodos() {
        return artistaRepository.listarTodos()
            .stream()
            .map(ArtistaResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional
    public ArtistaResponseDTO atualizar(Long id, ArtistaRequestDTO dto) {
        Artista artista = artistaRepository.buscarPorId(id)
            .orElseThrow(() -> new NotFoundException("Artista com ID " + id + " não encontrado"));

        artista.setNome(dto.getNome());
        artista.setPaisOrigem(dto.getPaisOrigem());
        artista.setAnoInicio(dto.getAnoInicio());

        Artista atualizado = artistaRepository.atualizar(artista);
        return ArtistaResponseDTO.fromEntity(atualizado);
    }

    @Transactional
    public void remover(Long id) {
        boolean removido = artistaRepository.remover(id);
        if (!removido) {
            throw new NotFoundException("Artista com ID " + id + " não encontrado");
        }
    }
}
