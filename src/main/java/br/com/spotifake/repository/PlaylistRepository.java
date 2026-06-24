package br.com.spotifake.repository;

import br.com.spotifake.model.Playlist;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PlaylistRepository {

    @PersistenceContext
    EntityManager em;

    public void salvar(Playlist playlist) {
        em.persist(playlist);
    }

    public Optional<Playlist> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(Playlist.class, id));
    }

    public List<Playlist> listarTodas() {
        return em.createQuery(
            "SELECT DISTINCT p FROM Playlist p " +
            "JOIN FETCH p.usuario " +
            "LEFT JOIN FETCH p.musicas m " +
            "LEFT JOIN FETCH m.artista " +
            "ORDER BY p.nome",
            Playlist.class
        ).getResultList();
    }

    public List<Playlist> listarPorUsuario(Long usuarioId) {
        TypedQuery<Playlist> query = em.createQuery(
            "SELECT DISTINCT p FROM Playlist p " +
            "JOIN FETCH p.usuario u " +
            "LEFT JOIN FETCH p.musicas m " +
            "LEFT JOIN FETCH m.artista " +
            "WHERE u.id = :usuarioId ORDER BY p.nome",
            Playlist.class
        );
        query.setParameter("usuarioId", usuarioId);
        return query.getResultList();
    }

    public Playlist atualizar(Playlist playlist) {
        return em.merge(playlist);
    }

    public boolean remover(Long id) {
        Playlist playlist = em.find(Playlist.class, id);
        if (playlist != null) {
            em.remove(playlist);
            return true;
        }
        return false;
    }
}
