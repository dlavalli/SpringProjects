package com.lavalliere.daniel.spring.jpasamples.repository;

import com.lavalliere.daniel.spring.jpasamples.model.Customer;
import com.lavalliere.daniel.spring.jpasamples.model.CustomerOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Repository
public class CustomerCriteriaDAOImpl implements CustomerCriteriaDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // -------------------------------------------------------------------------
    // Reusable building blocks
    // -------------------------------------------------------------------------

    private CriteriaBuilder getCriteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    private CriteriaQuery<Customer> createCustomerQuery(CriteriaBuilder cb) {
        return cb.createQuery(Customer.class);
    }

    private Root<Customer> getCustomerRoot(CriteriaQuery<Customer> query) {
        return query.from(Customer.class);
    }

    private Join<Customer, CustomerOrder> joinOrders(Root<Customer> customerRoot) {
        return customerRoot.join("orders");
    }

    private Predicate tierPredicate(CriteriaBuilder cb, Root<Customer> root, String tier) {
        return cb.equal(root.get("tier"), tier);
    }

    private Predicate minOrderAmountPredicate(CriteriaBuilder cb,
                                              Join<Customer, CustomerOrder> orderJoin,
                                              BigDecimal minAmount) {
        return cb.greaterThan(orderJoin.get("totalAmount"), minAmount);
    }

    // -------------------------------------------------------------------------
    // Query methods
    // -------------------------------------------------------------------------

    @Override
    public List<Customer> findCustomersWithHighValueOrders(String tier, String value) {
        CriteriaBuilder cb = getCriteriaBuilder();

        // Don’t reuse Criteria objects across methods/tests.
        CriteriaQuery<Customer> query = createCustomerQuery(cb);

        Root<Customer> customerRoot = getCustomerRoot(query);
        Join<Customer, CustomerOrder> orderJoin = joinOrders(customerRoot);

        Predicate vipPredicate = tierPredicate(cb, customerRoot, tier);
        Predicate totalAmountPredicate = minOrderAmountPredicate(cb, orderJoin, new BigDecimal(value));

        query.select(customerRoot).where(cb.and(vipPredicate, totalAmountPredicate));

        return entityManager.createQuery(query).getResultList();
    }

    // Example: query without join — find all customers by tier
    @Override
    public List<Customer> findCustomersByTier(String tier) {
        CriteriaBuilder cb = getCriteriaBuilder();

        // Don’t reuse Criteria objects across methods/tests.
        CriteriaQuery<Customer> query = createCustomerQuery(cb);

        Root<Customer> customerRoot = getCustomerRoot(query);

        query.select(customerRoot).where(tierPredicate(cb, customerRoot, tier));

        return entityManager.createQuery(query).getResultList();
    }

    // Example: query with join — find customers of any tier with orders above a threshold
    @Override
    public List<Customer> findCustomersWithOrdersAbove(BigDecimal minAmount) {
        CriteriaBuilder cb = getCriteriaBuilder();

        // Don’t reuse Criteria objects across methods/tests.
        CriteriaQuery<Customer> query = createCustomerQuery(cb);

        Root<Customer> customerRoot = getCustomerRoot(query);
        Join<Customer, CustomerOrder> orderJoin = joinOrders(customerRoot);

        query.select(customerRoot).where(minOrderAmountPredicate(cb, orderJoin, minAmount));

        return entityManager.createQuery(query).getResultList();
    }
}