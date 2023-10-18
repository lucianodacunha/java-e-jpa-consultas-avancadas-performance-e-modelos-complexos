package io.github.lucianodacunha.bancojpa.dao;

import io.github.lucianodacunha.bancojpa.model.Cliente;

import javax.persistence.EntityManager;

public class ClienteDao {

    private EntityManager em;

    public ClienteDao(EntityManager em){
        this.em = em;
    }

    public void cadastrar(Cliente cliente) {
        em.persist(cliente);
    }
}
