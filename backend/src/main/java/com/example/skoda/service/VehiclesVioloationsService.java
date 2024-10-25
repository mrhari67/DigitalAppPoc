package com.example.skoda.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.skoda.model.VehiclesViolations;
import com.example.skoda.repository.VehiclesViolationsRepository;
import com.example.skoda.request.ReportSpeedViolationReq;
import com.example.skoda.response.ReportSpeedViolationResp;

@Service
public class VehiclesVioloationsService {

	@Autowired
	VehiclesViolationsRepository vehicleViolationsRepo;

	@Autowired
	ReportSpeedViolationResp reportSpeedViolationResp;

	/**
	 * Captures the speed violation data into database using data jpa
	 * 
	 * @param req reported speed violations
	 * @return violated speed vehicle details
	 */
	public ReportSpeedViolationResp reportSpeedViolation(ReportSpeedViolationReq req) {

		// perform save

		VehiclesViolations vehicleViolations = new VehiclesViolations();
		VehiclesViolations result = new VehiclesViolations();

		vehicleViolations.setLangitude(req.getLangitude());
		vehicleViolations.setLatitude(req.getLatitude());
		vehicleViolations.setSpeed_limit(req.getSpeedLimit());
		vehicleViolations.setVin(req.getVin());
		vehicleViolations.setViolatedDate(req.getViolatedDate());

		result = vehicleViolationsRepo.save(vehicleViolations);

		reportSpeedViolationResp.setLangitude(result.getLangitude());
		reportSpeedViolationResp.setLatitude(result.getLatitude());
		reportSpeedViolationResp.setSpeedLimit(result.getSpeed_limit());
		reportSpeedViolationResp.setVin(result.getVin());
		reportSpeedViolationResp.setViolatedDate(result.getViolatedDate());
		reportSpeedViolationResp.setStatus("Success");

		return reportSpeedViolationResp;

	}

	/**
	 * Returns the List of speed violation data from database using data jpa
	 * 
	 * @param req VIN
	 * @return List of reported speed violations
	 */
	public List<VehiclesViolations> getSpeedViolation(String vin) {

		List<VehiclesViolations> result = new ArrayList();

		result = vehicleViolationsRepo.findAllByVIN(vin);

		return result;

	}

	/**
	 * Returns status of deleted speed violation vehicles data in database using
	 * data jpa
	 * 
	 * @param req VIN
	 * @return boolean
	 */
	public boolean deleteViolationsByVin(String vin) {
		int deletedCount = vehicleViolationsRepo.deleteByName(vin);
		System.out.println(deletedCount);
		return deletedCount > 0;
	}

}
