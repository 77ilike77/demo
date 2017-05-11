package com.zjp.demo.impl.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zjp.demo.impl.entity.DepartmentImpl;

public interface DepartmentRepository extends JpaRepository<DepartmentImpl, Long> {

}
