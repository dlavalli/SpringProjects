package payroll;

import org.springframework.data.jpa.repository.JpaRepository;

// 	JpaRepository is JPA specific extension of Repository.
//	It contains the full API of CrudRepository and PagingAndSortingRepository.
//	So it contains API for basic CRUD operations and also API for pagination and sorting.

// JpaRepository<Employee, Long> A JPA repository handing objects of type Employee identified by a Long type ID
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
