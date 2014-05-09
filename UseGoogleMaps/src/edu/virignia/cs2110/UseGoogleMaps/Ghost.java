package edu.virignia.cs2110.UseGoogleMaps;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;



public class Ghost extends Humanoid {
	private Player target;
	private boolean hasTarget;
	private int iD;
	public static int ghostCount = 0;
	public static int nextID = 0;
	private boolean attacking;
	private boolean crippled;


	public Ghost(double a, double b) {
		super(a,b,1);// Again, this health assignment is arbitrary
		this.iD = nextID;
		ghostCount ++;
		nextID ++;
		this.setAttacking(false);


	}
	public void hitAndRun() {
		this.setLatitude(this.getLatitude() + .0006);
		this.setLongitude(this.getLongitude() + .0006);
	}

	public Ghost() {
		super(spawnPoints(true), spawnPoints(false), 1);
		this.setAttacking(false);
		this.crippled = false;
	}

	public Ghost(boolean noMove) {
		this();
		this.crippled = noMove;
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
		double distance = .001 * (Math.random() - .5);
		l = l + distance;

		return l;

	}

	@Override
	public void departed() {
		this.setLatitude(spawnPoints(false));
		this.setLongitude(spawnPoints(true));
		this.crippled = true;
		
		


	}

	public Player getTarget() {
		return target;
	}

	public void setTarget(Player target) {
		this.target = target;
	}

	public boolean isHasTarget() {
		return hasTarget;
	}

	public void setHasTarget(boolean hasTarget) {
		this.hasTarget = hasTarget;
	}

	public int getiD() {
		return iD;
	}
	public LatLng move(double moveFactor) {
		if(!crippled) {
			double x = this.getLongitude();
			double y = this.getLatitude();

			double r = Math.random() * 2 * Math.PI;
			this.setLatitude(x + moveFactor * Math.sin(r));
			this.setLongitude(y + moveFactor * Math.cos(r));
			LatLng l = new LatLng(this.getLatitude(), this.getLongitude());
			return l;
		}
		else {
			LatLng l = new LatLng(this.getLatitude(), this.getLongitude());
			return l;
		}
	}
	public LatLng getLatLng(){
		return new LatLng(this.getLatitude(),this.getLongitude());
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}



}
