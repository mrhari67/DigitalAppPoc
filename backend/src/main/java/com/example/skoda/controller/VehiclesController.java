package com.example.skoda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.skoda.request.SetSpeedThresholdReq;
import com.example.skoda.response.SetSpeedThresholdResp;
import com.example.skoda.service.VehiclesService;

import jakarta.validation.Valid;

/**
 * APIs to capture and get Speed threshold for Vehicles
 */
@RestController
public class VehiclesController {

	@Autowired
	VehiclesService vehicleService;

	/**
	 * This is Default Home end point Controller Accepts no args and returns About this API
	 * 
	 * @return
	 */
	@GetMapping("/")
	public String helloWorld() {
		return "Hi, its V1 of SKODA PoC API";
	}

	/**
	 * This is a Rest end point which sets the Speed Threshold for given VIN
	 * 
	 * @param Request JSON with VIN,Speed Limit
	 * @return Response JSON which returns Updated Speed Limit set,VIN along with
	 *         HTTP Status code
	 */
	@PostMapping("/setSpeedThreshold")
	public ResponseEntity<SetSpeedThresholdResp> setSpeedThreshold(@RequestBody @Valid SetSpeedThresholdReq req) {

		return new ResponseEntity<SetSpeedThresholdResp>(vehicleService.setSpeedThreshold(req), HttpStatus.OK);
	}

	/**
	 * This is a Rest end point which gets the Speed Threshold set for given VIN
	 * 
	 * @param VIN
	 * @return VIN, Speed Limit
	 */
	@GetMapping("/getSpeedThreshold/{vin}")
	public ResponseEntity<SetSpeedThresholdResp> getSpeedThreshold(@PathVariable("vin") String vin) {

		return new ResponseEntity<SetSpeedThresholdResp>(vehicleService.getSpeedThreshold(vin), HttpStatus.OK);

	}

}
