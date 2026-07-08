package com.lavalliere.daniel.spring.jpasamples;


import com.lavalliere.daniel.spring.jpasamples.model.Customer;
import com.lavalliere.daniel.spring.jpasamples.model.CustomerOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class CriteriaApiTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testFindVipCustomersWithHighValueOrders() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = cb.createQuery(Customer.class);

        // Define root entity (FROM Customer)
        Root<Customer> customerRoot = query.from(Customer.class);

        // JOIN Customer with CustomerOrder
        Join<Customer, CustomerOrder> orderJoin = customerRoot.join("orders");

        // Construct dynamic predicates (WHERE tier = 'VIP' AND total_amount > 200)
        Predicate vipPredicate = cb.equal(customerRoot.get("tier"), "VIP");
        Predicate totalAmountPredicate = cb.greaterThan(orderJoin.get("totalAmount"), new BigDecimal("200.00"));

        // Combine conditions and apply to query
        query.select(customerRoot).where(cb.and(vipPredicate, totalAmountPredicate));

        // Execute query
        List<Customer> result = entityManager.createQuery(query).getResultList();

        // Verify "Alice" is returned due to her $450.50 order
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Alice Smith");
    }
}