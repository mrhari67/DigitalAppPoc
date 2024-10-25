package com.example.skoda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.skoda.model.VehiclesViolations;
import com.example.skoda.request.ReportSpeedViolationReq;
import com.example.skoda.response.ReportSpeedViolationResp;
import com.example.skoda.service.VehiclesVioloationsService;

/**
 * APIs to capture and get Speed violations reported for Vehicle
 */
@RestController
public class ViolationsController {

	@Autowired
	VehiclesVioloationsService vehicleViolationsService;

	@Value("${deleteapi.success.msg}")
	private String deleteSuccessMsg;

	@Value("${deleteapi.error.msg}")
	private String deleteErrorMsg;

	/**
	 * This is a Rest end point which sets the Speed Violation reported
	 * 
	 * @param req Date, VIN, Speed Limit, Latitude, Langitude
	 * @return JSON reponse with updated date along with status message.
	 */
	@PostMapping("/reportSpeedViolation")
	public ResponseEntity<ReportSpeedViolationResp> reportSpeedViolation(@RequestBody ReportSpeedViolationReq req) {

		return new ResponseEntity<ReportSpeedViolationResp>(vehicleViolationsService.reportSpeedViolation(req),
				HttpStatus.OK);
	}

	/**
	 * This is a Rest end point which returns the Speed Violation for given VIN
	 * 
	 * @param req VIN
	 * @return JSON reponse with list of violated dates along with status message.
	 */
	@GetMapping("/getSpeedViolation/{vin}")
	public ResponseEntity<List<VehiclesViolations>> getSpeedViolation(@PathVariable("vin") String vin) {

		return new ResponseEntity<List<VehiclesViolations>>(vehicleViolationsService.getSpeedViolation(vin),
				HttpStatus.OK);
	}

	/**
	 * This is a Rest end point which Deletes the Speed Violations for given VIN
	 * 
	 * @param req VIN
	 * @return String as status message.
	 */
	@GetMapping("/clearData/{vin}")
	public ResponseEntity<String> clearspeedviolation(@PathVariable("vin") String vin) {

		boolean isDeleted = vehicleViolationsService.deleteViolationsByVin(vin);

		if (isDeleted) {
			return ResponseEntity.ok(deleteSuccessMsg);
		} else {
			return ResponseEntity.ok(deleteErrorMsg);
		}
	}

}
