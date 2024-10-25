package com.example.skoda.response;

import org.springframework.stereotype.Component;

@Component
public class ReportSpeedViolationResp {

	private String vin;
	private int speedLimit;
	private String violatedDate;
	private Float latitude;
	private Float langitude;
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

	public String getViolatedDate() {
		return violatedDate;
	}

	public void setViolatedDate(String violatedDate) {
		this.violatedDate = violatedDate;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLangitude() {
		return langitude;
	}

	public void setLangitude(Float langitude) {
		this.langitude = langitude;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ReportSpeedViolationResp [vin=" + vin + ", speedLimit=" + speedLimit + ", violatedDate=" + violatedDate
				+ ", latitude=" + latitude + ", langitude=" + langitude + ", status=" + status + "]";
	}

}
