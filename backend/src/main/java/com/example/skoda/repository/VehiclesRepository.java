package com.example.skoda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.skoda.model.Vehicles;


import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface VehiclesRepository extends JpaRepository<Vehicles,String>{

	
	@Query("select v from Vehicles v where v.vin=?1")
	Vehicles findByVIN(String vin);
}
