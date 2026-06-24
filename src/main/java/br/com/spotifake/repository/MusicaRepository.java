package br.com.spotifake.repository;

import br.com.spotifake.model.Musica;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class MusicaRepository  {

    @PersistenceContext
    EntityManager em;

    public void salvar(Musica musica) {
        em.persist(musica);
    }

    public Optional<Musica> buscarPorId(Long id) {
        Musica musica = em.find(Musica.class, id);
        return Optional.ofNullable(musica);
    }

    public List<Musica> listarTodas() {
        TypedQuery<Musica> query = em.createQuery(
            "SELECT m FROM Musica m JOIN FETCH m.artista ORDER BY m.titulo",
            Musica.class
        );
        return query.getResultList();
    }

    public List<Musica> listarPorArtista(Long artistaId) {
        TypedQuery<Musica> query = em.createQuery(
            "SELECT m FROM Musica m JOIN FETCH m.artista a WHERE a.id = :artistaId ORDER BY m.titulo",
            Musica.class
        );
        query.setParameter("artistaId", artistaId);
        return query.getResultList();
    }

    public Musica atualizar(Musica musica) {
        return em.merge(musica);
    }

    public boolean remover(Long id) {
        Musica musica = em.find(Musica.class, id);
        if (musica != null) {
            em.remove(musica);
            return true;
        }
        return false;
    }
}
