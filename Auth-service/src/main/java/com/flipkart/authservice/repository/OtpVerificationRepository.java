package com.flipkart.authservice.repository;

import com.flipkart.authservice.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {

    @Query("select o from OtpVerification o where o.mobileNumber=:mobileNumber")
    Optional<OtpVerification>  findByMobileNumber(@Param("mobileNumber")String mobileNumber);
}
