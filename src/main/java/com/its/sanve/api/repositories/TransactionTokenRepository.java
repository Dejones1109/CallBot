package com.its.sanve.api.repositories;

import com.its.sanve.api.entities.TransactionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTokenRepository extends JpaRepository<TransactionToken,String> {
}
