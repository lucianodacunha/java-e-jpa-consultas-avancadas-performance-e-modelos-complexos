package io.github.lucianodacunha.dao;

import io.github.lucianodacunha.model.Categoria;
import io.github.lucianodacunha.model.Cliente;
import io.github.lucianodacunha.model.Produto;

import javax.persistence.EntityManager;
import java.util.List;

public class ClienteDAO {
    private EntityManager em;

    public ClienteDAO(EntityManager em){
        this.em = em;
    }

    public void cadastrar(Cliente cliente){
        this.em.persist(cliente);
    }

    public Cliente buscarPorId(Long id){
        return em.find(Cliente.class, id);
    }

    public List<Cliente> buscarTodos(){
        String jpql = "SELECT c FROM Cliente c";
        return em.createQuery(jpql, Cliente.class)
                .getResultList();
    }
}
