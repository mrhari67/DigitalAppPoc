package com.example.skoda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.skoda.model.Vehicles;
import com.example.skoda.repository.VehiclesRepository;
import com.example.skoda.request.SetSpeedThresholdReq;
import com.example.skoda.response.SetSpeedThresholdResp;

@Service
public class VehiclesService {

	@Autowired
	VehiclesRepository vehiclesRepo;

	@Autowired
	VehiclesRepository vehicleRepo;

	@Autowired
	SetSpeedThresholdResp setSpeedThresholdResp;

	/**
	 * Captures the vehicle speed limit into database using data jpa
	 * 
	 * @param req speed limit json
	 * @return updated data as json response along with status message
	 */
	public SetSpeedThresholdResp setSpeedThreshold(SetSpeedThresholdReq req) {

		Vehicles vehicle = new Vehicles();
		Vehicles result = new Vehicles();
		
		vehicle.setVin(req.getVin());
		vehicle.setSpeed_limit(req.getSpeedLimit());
		

		result = vehicleRepo.findByVIN(req.getVin());

		if (result != null) {
			System.out.println("Record already exist..so perform update...");

			if (result.getVin() != null) {
				System.out.println("Record already exist and vin is not null.Value of VIN : " + result.getVin());

				vehicle.setSpeed_limit(req.getSpeedLimit());

				result = vehicleRepo.save(vehicle);

				setSpeedThresholdResp.setStatus("Speed Limit got updated for shared VIN ");
				setSpeedThresholdResp.setSpeedLimit(result.getSpeed_limit());
				setSpeedThresholdResp.setVin(result.getVin());

			}

		} else {
			System.out.println("Record doesnt  exist.. so perform insert");
			result = vehicleRepo.save(vehicle);

			setSpeedThresholdResp.setStatus("Speed Limit set for given VIN ");
			setSpeedThresholdResp.setSpeedLimit(result.getSpeed_limit());
			setSpeedThresholdResp.setVin(result.getVin());
		}

		return setSpeedThresholdResp;

	}

	/**
	 * Get the vehicle speed limit from database using data jpa
	 * 
	 * @param req VIN
	 * @return return fetched data as json response along with status message
	 */
	public SetSpeedThresholdResp getSpeedThreshold(String vin) {

		Vehicles result = new Vehicles();

		System.out.println("From service class" + vin);

		result = vehicleRepo.findByVIN(vin);

		if (result != null) {
			setSpeedThresholdResp.setVin(result.getVin());
			setSpeedThresholdResp.setSpeedLimit(result.getSpeed_limit());
			setSpeedThresholdResp.setStatus("success");

		} else {

			setSpeedThresholdResp.setStatus("No Record Found for given VIN " + vin);
			setSpeedThresholdResp.setSpeedLimit(0);
		}

		return setSpeedThresholdResp;

	}

}
