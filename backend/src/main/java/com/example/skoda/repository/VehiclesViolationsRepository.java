package com.example.skoda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.skoda.model.VehiclesViolations;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface VehiclesViolationsRepository extends JpaRepository<VehiclesViolations,Long> {
	

	
	@Query(value="select * from vehicle_violations where vin=?1",nativeQuery=true)
	List<VehiclesViolations> findAllByVIN(String vin);
	
	@Modifying
	@Query(value="Delete from  VehiclesViolations v where v.vin=:vin")
	public int deleteByName(@Param ("vin")String vin);


}
