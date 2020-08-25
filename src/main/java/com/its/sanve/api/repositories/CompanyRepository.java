/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.repositories;

import com.its.sanve.api.entities.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.List;

/**
 *
 * @author quangdt
 * @param <T>
 * @param <P>
 */
@Repository
public interface CompanyRepository extends JpaRepository<CompanyInfo, String>{
//    @Query("SELECT c.id, c.name FROM CompanyInfo c WHERE c.phoneNumber = :phone")
//    Object findPhone(@Param("phone") String phone);
@Query("SELECT c.id, c.name, c.nameShort FROM CompanyInfo c WHERE c.phoneNumber = :phone")
String findPhone(@Param("phone") String phone);
}
