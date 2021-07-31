package mcc.springbootboilerplate.repository;

import mcc.springbootboilerplate.entity.MyLoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MyLoginAttemptRepository extends JpaRepository<MyLoginAttempt, Long> {

}
