package com.zjp.demo.impl.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zjp.demo.impl.entity.EmployeeImpl;

public interface EmployeeRepository extends JpaRepository<EmployeeImpl, Long> {

}
