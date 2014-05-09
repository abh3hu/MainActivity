package edu.virignia.cs2110.UseGoogleMaps;

import com.google.android.gms.maps.model.LatLng;


public abstract class Humanoid {
	private double longitude;
	private double latitude;
	private int health;
	
	
	public Humanoid() {
		this(0,0,1);
	}
	public Humanoid(double longi, double lati, int hearts) {
		this.longitude = longi;
		this.latitude = lati;
		this.health = hearts;
	}
	
	public boolean takeDamage(int h) {
		boolean died = false;
		this.health = this.health - h;
		return died;
	}
	public abstract void departed();
	
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	
	public LatLng move(double moveFactor) {
		double x = this.longitude;
		double y = this.latitude;
		
		double r = Math.random() * 2 * Math.PI;
		this.latitude = x + moveFactor * Math.sin(r);
		this.longitude = y + moveFactor * Math.cos(r);
		LatLng l = new LatLng(latitude, longitude);
		return l;
	}
}

