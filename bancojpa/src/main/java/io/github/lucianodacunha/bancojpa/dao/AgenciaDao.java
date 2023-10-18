package io.github.lucianodacunha.bancojpa.dao;

import io.github.lucianodacunha.bancojpa.model.Agencia;

import javax.persistence.EntityManager;

public class AgenciaDao {

    private EntityManager em;

    public AgenciaDao(EntityManager em){
        this.em = em;
    }

    public void cadastrar(Agencia agencia){
        this.em.persist(agencia);
    }
}
