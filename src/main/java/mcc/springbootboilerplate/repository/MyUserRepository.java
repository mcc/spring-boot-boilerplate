package mcc.springbootboilerplate.repository;

import mcc.springbootboilerplate.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RestResource
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    @RestResource(exported = true)
    MyUser getById(Long id);

    @RestResource(exported=false)
    Optional<MyUser> findByUsername(String username);
}
