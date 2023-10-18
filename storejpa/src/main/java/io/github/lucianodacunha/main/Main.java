package io.github.lucianodacunha.main;

import com.github.javafaker.Faker;
import io.github.lucianodacunha.dao.ClienteDAO;
import io.github.lucianodacunha.dao.PedidoDAO;
import io.github.lucianodacunha.model.*;
import io.github.lucianodacunha.util.JPAUtil;
import io.github.lucianodacunha.dao.CategoriaDAO;
import io.github.lucianodacunha.dao.ProdutoDAO;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;

public class Main {

    private static final Faker faker = new Faker();
    private static Scanner entrada = new Scanner(System.in);
    private static int opcao = 0;

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        popularDB();

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        Produto produto1 = new ProdutoDAO(em).buscarPorId(1L); // em.find(Produto.class, 1L);
        Produto produto2 = new ProdutoDAO(em).buscarPorId(2L); // em.find(Produto.class, 1L);
        Cliente cliente = new ClienteDAO(em).buscarPorId(1L); // em.find(Cliente.class, 1L);
        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, produto1));
        pedido.adicionarItem(new ItemPedido(2, pedido, produto2));
        PedidoDAO pedidoDAO = new PedidoDAO(em);
        pedidoDAO.cadastrar(pedido);

        em.getTransaction().commit();

//        System.out.println("Pedido: " + pedido);
//        BigDecimal valorTotalVendido = pedidoDAO.valorTotalVendido();
//        System.out.println("Valor total vendido: " + valorTotalVendido);
//        pedidoDAO.relatorioVendas().forEach(System.out::println);
//        System.out.println(pedidoDAO.buscarPedidoComClientePorId(1L));
        Pedido p = em.find(Pedido.class, 1L);
        System.out.println(p.getCliente().getNome());

        em.close();
    }

    static void testarRelacionamentos(){}

    static void menuDeEntidades(){
        System.out.println("""
                Entre com a opção desejada:
                1 - Clientes
                2 - Produtos
                3 - Categorias
                4 - Pedidos
                5 - Sair
                """);

        while (true) {
            opcao = Integer.parseInt(entrada.nextLine());
            switch (opcao) {
                case 1 -> menuDeOperacaos("Clientes");
                case 2 -> menuDeOperacaos("Produtos");
                case 3 -> menuDeOperacaos("Categorias");
                case 4 -> menuDeOperacaos("Pedidos");
                case 5 -> System.exit(0);
                default -> System.out.println("Opção inválida");
            }
        }
    }

    static void popularDB(){
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        ClienteDAO clienteDAO = new ClienteDAO(em);
        getClientes(3).forEach(clienteDAO::cadastrar);

        CategoriaDAO categoriaDAO = new CategoriaDAO(em);
        ProdutoDAO produtoDao = new ProdutoDAO(em);

        // inserts
        getProdutos(3).forEach(p -> {
            categoriaDAO.cadastrar(p.getCategoria());
            produtoDao.cadastrar(p);
        });
        em.flush();

        em.getTransaction().commit();
        em.close();
    }

    static void menuDeOperacaos(String entidade){
        System.out.println("""
                Entre com a opção desejada para %s:
                1 - Listar
                2 - Cadastrar
                3 - Atualizar
                4 - Remover
                5 - Sair
                """.formatted(entidade));

        while (true) {
            opcao = Integer.parseInt(entrada.nextLine());
            switch (opcao) {
                case 1 -> menuDeListagem();
                case 2 -> System.out.println(2);
                case 3 -> System.out.println(3);
                case 4 -> System.out.println(4);
                case 5 -> System.exit(0);
                default -> System.out.println("Opção inválida");
            }
        }
    }

    static void menuDeListagem(){
        System.out.println("""
                Entre com a opção desejada:
                1 - Listar todos
                2 - Listar por id
                3 - Listar por preços (valores menores que)
                4 - Listar por preços (valores maiores que)
                5 - Sair
                """);

        while (true) {
            opcao = Integer.parseInt(entrada.nextLine());
            switch (opcao) {
                case 1 -> System.exit(0);
                case 2 -> System.exit(0);
                case 3 -> System.exit(0);
                case 4 -> System.exit(0);
                case 5 -> System.exit(0);
                default -> System.out.println("Opção inválida");
            }
        }
    }

    static void listarProduto(Produto produto){
        System.out.println(
                "%-3s %-30s %-7s %-10s %s"
                        .formatted(
                                "ID", "NOME", "PREÇO $", "DATA", "CATEGORIA"));
        System.out.println(produto);
    }

    static void listarProdutos(List<Produto> produtos){
        System.out.println(
                "%-3s %-30s %-7s %-10s %s"
                        .formatted(
                                "ID", "NOME", "PREÇO $", "DATA", "CATEGORIA"));
        produtos.forEach(System.out::println);
    }

    static List<Produto> getProdutos(int quantidade){
        List<Produto> produtos = new ArrayList<>();
        for(int i = 0; i < quantidade; i++){
            produtos.add(getProduto());
        }

        return produtos;
    }

    static Produto getProduto(){
        return new Produto(
                faker.commerce().productName(),
                faker.commerce().material(),
                BigDecimal.valueOf(faker.number().randomDouble(2, 10, 1000)),
                getCategoria());
    }

    static Categoria getCategoria(){
        return new Categoria(
                (faker.options().option(Categorias.values())).toString());
    }

    static List<Cliente> getClientes(int quantidade){
        List<Cliente> clientes= new ArrayList<>();
        for (int i = 0; i < quantidade; i++){
            clientes.add(getCliente());
        }

        return clientes;
    }

    private static Cliente getCliente() {
        return new Cliente(
                faker.name().fullName(),
                faker.idNumber().ssnValid());
    }
}