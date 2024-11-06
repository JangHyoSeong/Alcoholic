package com.e206.alcoholic.domain.refrigerator.repository;

import com.e206.alcoholic.domain.refrigerator.entity.Refrigerator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Integer> {
    List<Refrigerator> findByUserId(Integer userId);

    Optional<Refrigerator> findById(int id);

    Optional<Refrigerator> findBySerialNumber(String serialnumber);

    int countByUserId(int userId);

    boolean existsBySerialNumber(String serialNumber);
}