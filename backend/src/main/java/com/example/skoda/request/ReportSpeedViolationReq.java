package com.example.skoda.request;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportSpeedViolationReq {

	@NotNull
	@Size(max = 17)
	private String vin;

	@NotNull
	private int speedLimit;
	@NotNull
	private String violatedDate;
	@NotNull
	private Float latitude;
	@NotNull
	private Float langitude;

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

	@Override
	public String toString() {
		return "ReportSpeedViolationReq [vin=" + vin + ", speedLimit=" + speedLimit + ", violatedDate=" + violatedDate
				+ ", latitude=" + latitude + ", langitude=" + langitude + "]";
	}

}
