package io.github.lucianodacunha.bancojpa.main;

import com.github.javafaker.Faker;
import io.github.lucianodacunha.bancojpa.dao.AgenciaDao;
import io.github.lucianodacunha.bancojpa.dao.ClienteDao;
import io.github.lucianodacunha.bancojpa.model.Agencia;
import io.github.lucianodacunha.bancojpa.model.Cliente;
import io.github.lucianodacunha.bancojpa.model.Conta;
import io.github.lucianodacunha.bancojpa.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.Locale;
import java.util.logging.Level;

public class Main {
    static final Faker faker = new Faker();

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        java.util.logging.Logger.getLogger("org.hibernate")
                .setLevel(Level.SEVERE);

        testes();
    }

    static void testes(){

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        Cliente cliente = new Cliente(
                faker.name().fullName(),
                faker.idNumber().ssnValid(),
                faker.address().fullAddress(),
                faker.phoneNumber().cellPhone()
        );
        ClienteDao clienteDao = new ClienteDao(em);
        clienteDao.cadastrar(cliente);

        Agencia agencia = new Agencia(1, faker.address().cityName());
        AgenciaDao agenciaDao = new AgenciaDao(em);

        agencia.cadastrarConta(new Conta(1, agencia, cliente));

        agenciaDao.cadastrar(agencia);
        em.getTransaction().commit();

        var conta = em.find(Conta.class, 1L);
        System.out.println(cliente);
        System.out.println(agencia);
        System.out.println(conta);
        em.close();
    }
}
