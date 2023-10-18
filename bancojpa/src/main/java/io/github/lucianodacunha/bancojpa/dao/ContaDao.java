package io.github.lucianodacunha.bancojpa.dao;

import io.github.lucianodacunha.bancojpa.model.Agencia;
import io.github.lucianodacunha.bancojpa.model.Conta;

import javax.persistence.EntityManager;

public class ContaDao {

    private EntityManager em;

    public ContaDao(EntityManager em){
        this.em = em;
    }

    public void cadastrar(Conta conta){
        this.em.persist(conta);
    }
}
