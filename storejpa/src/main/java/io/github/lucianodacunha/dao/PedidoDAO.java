package io.github.lucianodacunha.dao;

import io.github.lucianodacunha.vo.ItemRelatorioVenda;
import io.github.lucianodacunha.model.Pedido;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDAO {
    private EntityManager em;

    public PedidoDAO(EntityManager em){
        this.em = em;
    }

    public void cadastrar(Pedido pedido){
        this.em.persist(pedido);
    }

    public Pedido buscarPorId(Long id){
        return em.find(Pedido.class, id);
    }

    public List<Pedido> buscarTodos(){
        String jpql = "SELECT c FROM Pedido c";
        return em.createQuery(jpql, Pedido.class)
                .getResultList();
    }

    /**
     * Utilizando NamedQuery
     */
    public BigDecimal valorTotalVendido(){
             return em.createNamedQuery(
                     "Pedido.totalDeVendas",
                     BigDecimal.class)
                .getSingleResult();
    }

    /**
     * Utilizando SELECT new
     */
    public List<ItemRelatorioVenda> relatorioVendas(){
        String jpql = "SELECT new " +
                "io.github.lucianodacunha.vo.ItemRelatorioVenda " +
                "(pr.nome, SUM(it.quantidade), MAX(pe.data)) " +
                "FROM Pedido pe " +
                "JOIN pe.itens it " +
                "JOIN it.produto pr " +
                "GROUP BY pr.nome " +
                "ORDER BY it.quantidade DESC";
        return em.createQuery(jpql, ItemRelatorioVenda.class)
                .getResultList();
    }

    public Pedido buscarPedidoComClientePorId(Long id){
        String jpql = "SELECT p FROM Pedido p " +
                      "JOIN FETCH p.cliente WHERE p.id = :id";
        return em.createQuery(jpql, Pedido.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}

