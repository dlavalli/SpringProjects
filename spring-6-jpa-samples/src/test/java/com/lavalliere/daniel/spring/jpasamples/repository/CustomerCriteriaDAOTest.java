package com.lavalliere.daniel.spring.jpasamples.repository;

import com.lavalliere.daniel.spring.jpasamples.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// Instead of using @SpringBootTest which load the full context,
// @DataJpaTest loads exactly a slice: JPA repositories, EntityManager, and an embedded database.
// No web layer, no services — it's a perfect fit for this DAO.
@DataJpaTest

// @DataJpaTest only picks up @Repository-annotated beans that extend Spring Data interfaces.
// Since CustomerCriteriaDAOImpl is a plain @Repository using EntityManager directly, you must explicitly import it:
@Import(CustomerCriteriaDAOImpl.class)

// The issue is that @DataJpaTest creates its own embedded H2 instance and then @Sql runs your schema.sql + data.sql
// for every test method (before each test). But since @DataJpaTest is @Transactional and rolls back after each test,
// the schema itself (CREATE TABLE IF NOT EXISTS) persists across tests while the data rows get rolled back — that part is fine for a single test.

// @DataJpaTest by default sets spring.sql.init.mode=always and picks up schema.sql/data.sql from the classpath once at context startup.
// So the data is already there when the context is reused — and your @Sql runs on top of it, causing duplicates.

// @DataJpaTest auto-configures the embedded H2, runs your schema.sql and data.sql scripts at context startup
// (because spring.sql.init is enabled by default in the slice), and each test rolls back via @Transactional. No @Sql needed.
// @Sql(scripts = {"/db/schema.sql", "/db/data.sql"})
public class CustomerCriteriaDAOTest {

    @Autowired
    private CustomerCriteriaDAO customerCriteriaDAO;

    @Test
    public void testFindVipCustomersWithHighValueOrders() {
        List<Customer> result = customerCriteriaDAO.findCustomersWithHighValueOrders("VIP", "200.00");

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Alice Smith");

        result = customerCriteriaDAO.findCustomersWithHighValueOrders("STANDARD", "200.00");
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Peter Criss");
    }

    @Test
    public void testFindCustomersByTier() {
        List<Customer> result = customerCriteriaDAO.findCustomersByTier("VIP");
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Alice Smith");

        List<String> names = List.of("Bob Jones", "Peter Criss");
        result = customerCriteriaDAO.findCustomersByTier("STANDARD");
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.getFirst().getName()).isIn(names);
        assertThat(result.getLast().getName()).isIn(names);
    }

    @Test
    public void testFindCustomersWithOrdersAbove() {
        List<String> names = List.of("Alice Smith", "Peter Criss");
        List<Customer> result = customerCriteriaDAO.findCustomersWithOrdersAbove(BigDecimal.valueOf(200.00));
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.getFirst().getName()).isIn(names);
        assertThat(result.getLast().getName()).isIn(names);

    }
}