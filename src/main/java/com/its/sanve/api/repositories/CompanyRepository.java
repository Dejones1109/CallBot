/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.sanve.api.repositories;

import com.its.sanve.api.entities.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author quangdt
 * @param <T>
 * @param <P>
 */
@Service
@Repository

public interface CompanyRepository extends JpaRepository<CompanyInfo, String>{
    
}
