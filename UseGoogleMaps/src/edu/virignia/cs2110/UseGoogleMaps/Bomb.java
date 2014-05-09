package edu.virignia.cs2110.UseGoogleMaps;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class Bomb {
	private LatLng location;


	public Bomb() {
		double lat = spawnPoints(false);
		double longi = spawnPoints(true);
		this.setLocation(new LatLng(lat,longi));
	}


	public LatLng getLocation() {
		return location;
	}


	public void setLocation(LatLng location) {
		this.location = location;
	}


	public static double spawnPoints(boolean longitude) {
		Location mCurrentLocation = MainActivity.getmLocationClient().getLastLocation();
		double l;
		if(longitude) {
			l = mCurrentLocation.getLongitude();
		}

		else {
			l = mCurrentLocation.getLatitude();
		}
		double distance = .0005 * (Math.random() - .5);
		l = l + distance;

		return l;
	}
	public void found() {
		double lat = spawnPoints(false);
		double longi = spawnPoints(true);
		this.setLocation(new LatLng(lat,longi));
		
	}


}