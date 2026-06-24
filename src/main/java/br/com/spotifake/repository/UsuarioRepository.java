package br.com.spotifake.repository;

import br.com.spotifake.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioRepository  {

    @PersistenceContext
    EntityManager em;

    public void salvar(Usuario usuario) {
        em.persist(usuario);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(Usuario.class, id));
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        TypedQuery<Usuario> query = em.createQuery(
            "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class
        );
        query.setParameter("email", email);
        return query.getResultStream().findFirst();
    }

    public List<Usuario> listarTodos() {
        return em.createQuery("SELECT u FROM Usuario u ORDER BY u.nome", Usuario.class)
                 .getResultList();
    }

    public Usuario atualizar(Usuario usuario) {
        return em.merge(usuario);
    }

    public boolean remover(Long id) {
        Usuario usuario = em.find(Usuario.class, id);
        if (usuario != null) {
            em.remove(usuario);
            return true;
        }
        return false;
    }
}
