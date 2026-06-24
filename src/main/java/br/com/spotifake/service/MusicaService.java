package br.com.spotifake.service;

import br.com.spotifake.dto.MusicaRequestDTO;
import br.com.spotifake.dto.MusicaResponseDTO;
import br.com.spotifake.model.Artista;
import br.com.spotifake.model.Musica;
import br.com.spotifake.repository.ArtistaRepository;
import br.com.spotifake.repository.MusicaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class MusicaService {

    @Inject
    MusicaRepository musicaRepository;

    @Inject
    ArtistaRepository artistaRepository;


    @Transactional
    public MusicaResponseDTO criar(MusicaRequestDTO dto) {
        Artista artista = artistaRepository.buscarPorId(dto.getArtistaId())
            .orElseThrow(() -> new NotFoundException(
                "Artista com ID " + dto.getArtistaId() + " não encontrado"
            ));

        Musica musica = new Musica();
        musica.setTitulo(dto.getTitulo());
        musica.setDuracaoSegundos(dto.getDuracaoSegundos());
        musica.setGenero(dto.getGenero());
        musica.setAnoLancamento(dto.getAnoLancamento());
        musica.setArtista(artista);

        musicaRepository.salvar(musica);

        return MusicaResponseDTO.fromEntity(musica);
    }


    public MusicaResponseDTO buscarPorId(Long id) {
        Musica musica = musicaRepository.buscarPorId(id)
            .orElseThrow(() -> new NotFoundException("Música com ID " + id + " não encontrada"));
        return MusicaResponseDTO.fromEntity(musica);
    }

    public List<MusicaResponseDTO> listarTodas() {
        return musicaRepository.listarTodas()
            .stream()
            .map(MusicaResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }


    public List<MusicaResponseDTO> listarPorArtista(Long artistaId) {
        artistaRepository.buscarPorId(artistaId)
            .orElseThrow(() -> new NotFoundException(
                "Artista com ID " + artistaId + " não encontrado"
            ));

        return musicaRepository.listarPorArtista(artistaId)
            .stream()
            .map(MusicaResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional
    public MusicaResponseDTO atualizar(Long id, MusicaRequestDTO dto) {
        Musica musica = musicaRepository.buscarPorId(id)
            .orElseThrow(() -> new NotFoundException("Música com ID " + id + " não encontrada"));

        Artista artista = artistaRepository.buscarPorId(dto.getArtistaId())
            .orElseThrow(() -> new NotFoundException(
                "Artista com ID " + dto.getArtistaId() + " não encontrado"
            ));

        musica.setTitulo(dto.getTitulo());
        musica.setDuracaoSegundos(dto.getDuracaoSegundos());
        musica.setGenero(dto.getGenero());
        musica.setAnoLancamento(dto.getAnoLancamento());
        musica.setArtista(artista);

        Musica atualizada = musicaRepository.atualizar(musica);
        return MusicaResponseDTO.fromEntity(atualizada);
    }

    @Transactional
    public void remover(Long id) {
        boolean removida = musicaRepository.remover(id);
        if (!removida) {
            throw new NotFoundException("Música com ID " + id + " não encontrada");
        }
    }
}
