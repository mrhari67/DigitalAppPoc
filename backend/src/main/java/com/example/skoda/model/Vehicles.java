package com.example.skoda.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="vehicles")
public class Vehicles {
	
	@Id
	@Column(name="vin",nullable = false)
	private String vin;
	
	@Column(name="speed_limit",nullable = false)
	private int speed_limit;

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

	public Vehicles() {
		super();
	}

	@Override
	public String toString() {
		return "Vehicle [vin=" + vin + ", speed_limit=" + speed_limit + "]";
	}
	
	
	
	

}
