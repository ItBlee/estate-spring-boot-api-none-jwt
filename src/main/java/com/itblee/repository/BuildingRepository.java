package com.itblee.repository;

import com.itblee.repository.custom.BuildingRepositoryCustom;
import com.itblee.repository.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long>, BuildingRepositoryCustom {
}
