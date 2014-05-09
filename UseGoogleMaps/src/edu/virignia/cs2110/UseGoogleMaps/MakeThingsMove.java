//package edu.virignia.cs2110.UseGoogleMaps;
//
//import java.util.*;
//
//import com.google.android.gms.location.LocationClient;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import android.content.Context;
//import android.location.Location;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;

///**
// * 
// * @author jerryfiala
// *http://stackoverflow.com/questions/5385569/raising-a-toast-from-asynctask - info on getting context from Main Activity
// *This is where we got pacman ghost from (I'm calling academic fair use):  http://blog.jmorobinson.co.uk/
// */
//public class MakeThingsMove extends AsyncTask<Context, Ghost, Double> {
//	//List<Marker> mList = new ArrayList<Marker>();
//	//GoogleMap mMap = MainActivity.getmMap();	
//	
//
//
//	protected Double doInBackground(Context... context) {
//		LocationClient mLocationClient = MainActivity.getmLocationClient();
//		Location myLocation;
//		Location iAmHere = MainActivity.mCurrentLocation;
//		GoogleMap myMap = MainActivity.getmMap();
//		//List<Ghost> l = new ArrayList<Ghost>();
//		//l.set(0, new Ghost());
//		//l.set(1, new Ghost());
//		//l.set(2, new Ghost());
//		Ghost[] l = MainActivity.getG();
//		Context c = context[0];
//
//		while(!isCancelled()) {
//			if(MainActivity.getP().getHealth() < 1) {
//				break;
//			}
//			MainActivity.getP().setHealth(MainActivity.getP().getHealth() - 1);
//			iAmHere = MainActivity.getmLocationClient().getLastLocation();
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			myLocation = mLocationClient.getLastLocation();
//			this.publishProgress(l);
//			for(Ghost g : l) {
//				g.move(.00006);
//				if(Math.abs(g.getLatitude() - myLocation.getLatitude()) < .00006 && Math.abs(g.getLongitude() - myLocation.getLongitude()) < .00006 ) {
//					g.setAttacking(true);
//				}
//
//			}
//			for(Apple a : MainActivity.getApple()) {
//				LatLng appleLoc = a.getLocation();
//				double distance = Math.abs(appleLoc.latitude - myLocation.getLatitude()) + Math.abs(appleLoc.longitude - myLocation.getLongitude());
//				if(distance < .0002) {
//					MainActivity.getP().findFood();
//					a.found();
//
//				}
//			}
//			for(Bomb b : MainActivity.getBomb()) {
//				LatLng appleLoc = b.getLocation();
//				double distance = Math.abs(appleLoc.latitude - myLocation.getLatitude()) + Math.abs(appleLoc.longitude - myLocation.getLongitude());
//				if(distance < .0002) {
//					MainActivity.getP().setHasBomb(true);
//					b.found();
//
//				}
//			}
//			for(Ghost ghost : l) {
//				double distance = Math.abs(ghost.getLatitude() - iAmHere.getLatitude()) + Math.abs(ghost.getLongitude() - iAmHere.getLongitude());
//				if (distance < .0005) {
//					ghost.setAttacking(true);
//				} else {
//					ghost.setAttacking(false);
//				}
//			}
//			
//			for(Ghost ghost : l) {
//				double distance = Math.abs(ghost.getLatitude() - iAmHere.getLatitude()) + Math.abs(ghost.getLongitude() - iAmHere.getLongitude());
//				if (distance < .0003) {
//					MainActivity.getP().takeDamage();
//					ghost.hitAndRun();
//				}
//			}
//
//
//
//		}
//
//
//		return 0.0;
//	}
//	protected void onCancelled() {
//		
//	}
//	
//	protected void onPostExecute(Double...arg0) {
//		new MainActivity().gameOver();
//	}
//
//	protected void onProgressUpdate(Ghost... arg0) {
//		Location iAmHere = MainActivity.getmLocationClient().getLastLocation();
//		GoogleMap mMap = MainActivity.getmMap();
//		List<Marker> mList = new ArrayList<Marker>();
//		Ghost[] l = arg0;
//		Bomb[] b = MainActivity.getBomb();
//		Apple[] a = MainActivity.getApple();
//		for(int i = 0; i < mList.size(); i++) {
//			mList.get(i).setPosition(l[i].getLatLng());
//		}
//		mMap.clear();
//
//		/*for(Ghost ghost : l) {
//			double distance = Math.abs(ghost.getLatitude() - iAmHere.getLatitude()) + Math.abs(ghost.getLongitude() - iAmHere.getLongitude());
//			if (distance < .0009) {
//				MainActivity.makeToast();
//			}
//		}*/
//		for(Apple apple : a) {
//			mList.add(mMap.addMarker(new MarkerOptions()
//			.icon(BitmapDescriptorFactory.fromResource(R.drawable.apple))
//			.position(new LatLng(apple.getLocation().latitude,apple.getLocation().longitude))));
//		}
//
//		for(Bomb bomb: b) {
//			mList.add(mMap.addMarker(new MarkerOptions()
//			.icon(BitmapDescriptorFactory.fromResource(R.drawable.bomb))
//			.position(new LatLng(bomb.getLocation().latitude,bomb.getLocation().longitude))));
//		}
//		for(Ghost g : arg0) {
//			if(!g.isAttacking()) {
//				mList.add(mMap.addMarker(new MarkerOptions()
//				.icon(BitmapDescriptorFactory.fromResource(R.drawable.ghost_sprite))
//				.position(new LatLng(g.getLatitude(), g.getLongitude()))));
//			} else {
//				mList.add(mMap.addMarker(new MarkerOptions()
//				.icon(BitmapDescriptorFactory.fromResource(R.drawable.close_ghost))
//				.position(new LatLng(g.getLatitude(), g.getLongitude()))));
//			}
//
//		}
//
//	}
//
//
//
//} 
