package com.example.skoda.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="vehicle_violations")
public class VehiclesViolations {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id",nullable = false)
	private Long id;
	
	@Column(name="vin",nullable = false)
	private String vin;
	
	@Column(name="speed_limit",nullable = false)
	private int speed_limit;

	@Column(name="violoated_date",nullable = false)
//	@Temporal(TemporalType.TIMESTAMP)
	private String violatedDate;
	
	@Column(name="latitude",nullable = false)
	private Float latitude;
	
	@Column(name="langitude",nullable = false)
	private Float langitude;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public int getSpeed_limit() {
		return speed_limit;
	}

	public void setSpeed_limit(int speed_limit) {
		this.speed_limit = speed_limit;
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

	public VehiclesViolations() {
		super();
	}

	@Override
	public String toString() {
		return "VehiclesViolations [id=" + id + ", vin=" + vin + ", speed_limit=" + speed_limit + ", violatedDate="
				+ violatedDate + ", latitude=" + latitude + ", langitude=" + langitude + "]";
	}

	
	
	
	

}
