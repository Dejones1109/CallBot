package com.its.sanve.api.repositories;

import com.its.sanve.api.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRespository  extends JpaRepository<Trip,Long> {
}
