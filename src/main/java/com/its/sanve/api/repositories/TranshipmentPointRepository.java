package com.its.sanve.api.repositories;

import com.its.sanve.api.entities.TranshipmentPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranshipmentPointRepository extends JpaRepository<TranshipmentPoint,String>{

}
