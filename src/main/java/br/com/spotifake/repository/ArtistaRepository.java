package br.com.spotifake.repository;

import br.com.spotifake.model.Artista;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ArtistaRepository {

    @PersistenceContext
    EntityManager em;

    public void salvar(Artista artista) {
        em.persist(artista);
    }

    public Optional<Artista> buscarPorId(Long id) {
        Artista artista = em.find(Artista.class, id);
        return Optional.ofNullable(artista);
    }

    public List<Artista> listarTodos() {
        TypedQuery<Artista> query = em.createQuery(
            "SELECT a FROM Artista a ORDER BY a.nome",
            Artista.class
        );
        return query.getResultList();
    }

    public Artista atualizar(Artista artista) {
        return em.merge(artista);
    }

    public boolean remover(Long id) {
        Artista artista = em.find(Artista.class, id);
        if (artista != null) {
            em.remove(artista);
            return true;
        }
        return false;
    }
}
