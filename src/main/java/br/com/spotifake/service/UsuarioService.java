package br.com.spotifake.service;

import br.com.spotifake.dto.UsuarioRequestDTO;
import br.com.spotifake.dto.UsuarioResponseDTO;
import br.com.spotifake.model.Usuario;
import br.com.spotifake.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        usuarioRepository.buscarPorEmail(dto.getEmail()).ifPresent(u -> {
            throw new BadRequestException("Já existe um usuário com o e-mail: " + dto.getEmail());
        });

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setPlano(dto.getPlano() != null ? dto.getPlano().toUpperCase() : "FREE");

        usuarioRepository.salvar(usuario);
        return UsuarioResponseDTO.fromEntity(usuario);
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.buscarPorId(id)
            .orElseThrow(() -> new NotFoundException("Usuário com ID " + id + " não encontrado"));
        return UsuarioResponseDTO.fromEntity(usuario);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.listarTodos()
            .stream()
            .map(UsuarioResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.buscarPorId(id)
            .orElseThrow(() -> new NotFoundException("Usuário com ID " + id + " não encontrado"));


        usuarioRepository.buscarPorEmail(dto.getEmail()).ifPresent(outro -> {
            if (!outro.getId().equals(id)) {
                throw new BadRequestException("E-mail já está em uso por outro usuário");
            }
        });

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        if (dto.getPlano() != null) {
            usuario.setPlano(dto.getPlano().toUpperCase());
        }

        return UsuarioResponseDTO.fromEntity(usuarioRepository.atualizar(usuario));
    }

    @Transactional
    public void remover(Long id) {
        boolean removido = usuarioRepository.remover(id);
        if (!removido) {
            throw new NotFoundException("Usuário com ID " + id + " não encontrado");
        }
    }
}
