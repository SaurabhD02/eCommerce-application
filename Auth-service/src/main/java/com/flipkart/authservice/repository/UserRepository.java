package com.flipkart.authservice.repository;

import com.flipkart.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u where u.mobile=:mobile")
    Optional<User> findByMobile(@Param("mobile") String mobile);
}
