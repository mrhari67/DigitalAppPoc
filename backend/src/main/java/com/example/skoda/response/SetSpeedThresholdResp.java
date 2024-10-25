package com.example.skoda.response;

import org.springframework.stereotype.Component;

@Component
public class SetSpeedThresholdResp {

	private String vin;
	private int speedLimit;
	private String status;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public int getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
	}

	public SetSpeedThresholdResp() {
		super();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SetSpeedThresholdResp [vin=" + vin + ", speedLimit=" + speedLimit + ", status=" + status + "]";
	}

}
