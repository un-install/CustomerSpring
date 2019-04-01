package springdata.repository;

import springdata.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, BigDecimal> {
    List<Customer> findByCreditLimitBetween(BigDecimal minLimit, BigDecimal maxLimit);
}
