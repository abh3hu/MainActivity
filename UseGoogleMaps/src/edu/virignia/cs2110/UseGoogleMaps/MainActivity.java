package edu.virignia.cs2110.UseGoogleMaps;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * 
 * @author jerryfiala
 * We used a lot of code from the Android Developers Site specifically from the section on GoogleMaps and Google Play services.
 * apple sprite: http://spritesandstuff.smackjeeves.com/comics/355436/food-sprites/
 * girl Sprite: Cs1110 project 2012
 * male sprite: http://www.unicornmax.com/%E0%B8%AA%E0%B8%A3%E0%B9%89%E0%B8%B2%E0%B8%87%E0%B9%80%E0%B8%81%E0%B8%A1%E0%B8%AA%E0%B9%8C-html5-%E0%B8%87%E0%B9%88%E0%B8%B2%E0%B8%A2%E0%B9%86-part2-%E0%B8%AA%E0%B8%A3%E0%B9%89%E0%B8%B2%E0%B8%87-p/
 * heart sprite: https://lh4.ggpht.com/5ph4Y-eEoyS70Mv5gujDhTEY7k4SZfeIJwgcv7Fmzc-A-sruArCzzbd3atJKmc8UsQXX1dA=s32
 * http://stackoverflow.com/questions/7356243/cant-override-onpostexecute-method-in-asynctask-class-or-get-it-to-trigger - getting our onPostExecute to work
 * bomb sprite: http://kclr96fm.com/news/bomb-hoax-at-carlow-shopping-centre/
 * http://androiddesk.wordpress.com/tag/how-to-move-from-one-activity-to-another-using-an-intent/
 *http://stackoverflow.com/questions/5385569/raising-a-toast-from-asynctask - info on getting context from Main Activity
 *This is where we got pacman ghost from (I'm calling academic fair use):  http://blog.jmorobinson.co.uk/
 */
public class MainActivity extends Activity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {
	

	private static GoogleMap mMap;
	private static Apple[] apple;
	private static Bomb[] bomb;
	private static Ghost[] g;
	private int j = 0;
	public static Ghost[] getG() {
		return g;
	}

	public static void setG(Ghost[] g) {
		MainActivity.g = g;
	}

	private static Player p;
	private final static int
	CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private static LocationClient mLocationClient;
	public static LocationClient getmLocationClient() {
		return mLocationClient;
	}

	public void setmLocationClient(LocationClient mLocationClient) {
		this.mLocationClient = mLocationClient;
	}

	static Location mCurrentLocation;

	public static Location getmCurrentLocation() {
		return mCurrentLocation;
	}

	public void setmCurrentLocation(Location mCurrentLocation) {
		this.mCurrentLocation = mCurrentLocation;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLocationClient = new LocationClient(this, this, this);
		setContentView(R.layout.activity_main);
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.setMyLocationEnabled(true);
		UiSettings settings = mMap.getUiSettings();
		settings.setMyLocationButtonEnabled(true);
		settings.setZoomGesturesEnabled(false);
		settings.setScrollGesturesEnabled(false);






	}

	public static GoogleMap getmMap() {
		return mMap;
	}

	public void setmMap(GoogleMap mMap) {
		this.mMap = mMap;
	}

	protected void onStart() {
		super.onStart();
		// Connect the client.

		mLocationClient.connect();

	}
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
					.getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				// The Map is verified. It is now safe to manipulate the map.

			}
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(
						this,
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			/*
			 * If no resolution is available, display a dialog to the
			 * user with the error.
			 */
			showDialog(connectionResult.getErrorCode());
		}


	}
	public static class ErrorDialogFragment extends DialogFragment {
		// Global field to contain the error dialog
		private Dialog mDialog;
		// Default constructor. Sets the dialog field to null
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}
		// Set the dialog to display
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}
		// Return a Dialog to the DialogFragment.
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		setP(new Player());
		Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
		mCurrentLocation = mLocationClient.getLastLocation();
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), (float) 19));

		/*for(int j = 0; j < 10; j++) {
			mMap.addMarker(new MarkerOptions()
			.anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
			.position(new LatLng(Ghost.spawnPoints(false), Ghost.spawnPoints(true))));
		}*/
		Chronometer c =
				((Chronometer) findViewById(R.id.chronometer1));
		c.start();
		g = new Ghost[6];
		for(int i = 0; i < 3; i++) {
			g[i] = new Ghost();
		}
		g[3] = new Ghost(true);
		g[4] = new Ghost(true);
		g[5] = new Ghost(true);
		bomb = new Bomb[3];
		for(int i = 0; i < 3; i++) {
			bomb[i] = new Bomb();
		}
		apple = new Apple[2];
		for(int i = 0; i < 2; i++) {
			apple[i] = new Apple();
		}
		new MakeThingsMove().execute(this.getApplicationContext());

	}

	public static Apple[] getApple() {
		return apple;
	}

	public static void setApple(Apple[] apple) {
		MainActivity.apple = apple;
	}

	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();

	}
	@Override
	protected void onActivityResult(
			int requestCode, int resultCode, Intent data) {
		// Decide what to do based on the original request code
		switch (requestCode) {
		case CONNECTION_FAILURE_RESOLUTION_REQUEST :
			/*
			 * If the result code is Activity.RESULT_OK, try
			 * to connect again
			 */
			switch (resultCode) {
			case Activity.RESULT_OK :
				/*
				 * Try the request again
				 */

				break;
			}
		}
	}
	

	public static Bomb[] getBomb() {
		return bomb;
	}

	public static void setBomb(Bomb[] bomb) {
		MainActivity.bomb = bomb;
	}



	public void toKill(View v) {


		Location iAmHere = mLocationClient.getLastLocation();
		if(p.getBombs() > 0){
			boolean slain = false;
			for(Ghost ghost : g) {
				double distance = Math.abs(ghost.getLatitude() - iAmHere.getLatitude()) + Math.abs(ghost.getLongitude() - iAmHere.getLongitude());
				if (distance < .0005) {
					ghost.departed();
					slain = true;
				}

				
				//p.setHasBomb(false);	
			}
			if(slain) {
				Toast.makeText(this, bombText(j), Toast.LENGTH_SHORT).show();
				j++;
			}
			p.setBombs(p.getBombs() - 1);
		}

		/*for(int i = 0; i < g.length; i++) {
			//checks each individual ghost 
			if ( killProx().get(i) <= .00015 ) {
				//removes each individual ghost
				g[i].departed();
			}
		}*/
	}

	public ArrayList<Double> killProx() {
		mCurrentLocation = mLocationClient.getLastLocation();


		double curFrom = 0;
		double preFrom = 0;

		ArrayList<Double> check = new ArrayList<Double>();

		for (int i = 0; i <= g.length; i++) {
			// your current location
			double y2 = mCurrentLocation.getLatitude();
			double x2 = mCurrentLocation.getLongitude();

			// ghost's current location
			double y1 = g[i].getLatitude();
			double x1 = g[i].getLongitude();

			//distance formula
			curFrom = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

			//stores distance of every ghost into check array list
			check.add(curFrom);
		}
		return check;

	}

	public static Player getP() {
		return p;
	}
	public void displayHealth(View v) {
		Toast.makeText(this, "You have " + p.getHealth() + " health.", Toast.LENGTH_SHORT).show();
		
	}
	
	public void displayBomb(View v) {
		
			Toast.makeText(this, "You have " + p.getBombs() + " bombs.", Toast.LENGTH_SHORT).show();
		
		
	}
	

	public void setP(Player p) {
		this.p = p;
	}
	public void makeToast() {
		Toast.makeText(this, "Kill the Ghost!", Toast.LENGTH_LONG);
	}
	public String bombText(int i) {
		if(i == 0) {
			return "Slain!";
		}
		
		if(i == 1) {
			return "Double Kill!";
		}
		
		if(i == 2) {
			return "Triple Kill!";
		}
		
		if(i == 3) {
			return "Overkill!";
		}
		
		if(i == 4) {
			return "Killtacular!";
		}
		
		if(i == 5) {
			return "Killtrocity!";
		}
		if(i == 6) {
			return "Killmanjaro!";
		}
		if(i == 7) {
			return "Killtastrophe!";
		}
		if(i == 8) {
			return "Killionaire!";
		} else {
			return "Alright dude, stop playing";
		}
	}
	public void gameOver() {
		Toast.makeText(this, "you lost", Toast.LENGTH_LONG).show();
		setContentView(R.layout.gameover);
	}
	
	public void goToMainMenu(View v) {
		PackageManager pm = getPackageManager();
		Intent intent = pm.getLaunchIntentForPackage("edu.virignia.cs2110.Project");
		startActivity(intent);
	}
	private class MakeThingsMove extends AsyncTask<Context, Ghost, Void> {
		//List<Marker> mList = new ArrayList<Marker>();
		//GoogleMap mMap = MainActivity.getmMap();	
		


		protected Void doInBackground(Context... context) {
			LocationClient mLocationClient = MainActivity.getmLocationClient();
			Location myLocation;
			Location iAmHere = MainActivity.mCurrentLocation;
			GoogleMap myMap = MainActivity.getmMap();
			//List<Ghost> l = new ArrayList<Ghost>();
			//l.set(0, new Ghost());
			//l.set(1, new Ghost());
			//l.set(2, new Ghost());
			Ghost[] l = MainActivity.getG();
			Context c = context[0];

			while(!isCancelled()) {
				if(MainActivity.getP().getHealth() < 1) {
					break;
				}
				//MainActivity.getP().setHealth(MainActivity.getP().getHealth() - 1);
				iAmHere = MainActivity.getmLocationClient().getLastLocation();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				myLocation = mLocationClient.getLastLocation();
				this.publishProgress(l);
				for(Ghost g : l) {
					g.move(.00006);
					if(Math.abs(g.getLatitude() - myLocation.getLatitude()) < .00006 && Math.abs(g.getLongitude() - myLocation.getLongitude()) < .00006 ) {
						g.setAttacking(true);
					}

				}
				for(Apple a : MainActivity.getApple()) {
					LatLng appleLoc = a.getLocation();
					double distance = Math.abs(appleLoc.latitude - myLocation.getLatitude()) + Math.abs(appleLoc.longitude - myLocation.getLongitude());
					if(distance < .0002) {
						MainActivity.getP().findFood();
						a.found();

					}
				}
				for(Bomb b : MainActivity.getBomb()) {
					LatLng appleLoc = b.getLocation();
					double distance = Math.abs(appleLoc.latitude - myLocation.getLatitude()) + Math.abs(appleLoc.longitude - myLocation.getLongitude());
					if(distance < .0002) {
						MainActivity.getP().findBombs();
						b.found();

					}
				}
				for(Ghost ghost : l) {
					double distance = Math.abs(ghost.getLatitude() - iAmHere.getLatitude()) + Math.abs(ghost.getLongitude() - iAmHere.getLongitude());
					if (distance < .0005) {
						ghost.setAttacking(true);
					} else {
						ghost.setAttacking(false);
					}
				}
				
				for(Ghost ghost : l) {
					double distance = Math.abs(ghost.getLatitude() - iAmHere.getLatitude()) + Math.abs(ghost.getLongitude() - iAmHere.getLongitude());
					if (distance < .0003) {
						MainActivity.getP().takeDamage();
						ghost.hitAndRun();
					}
				}
				



			}


			
			return null;
		}
		protected void onCancelled() {
			mMap.addMarker(new MarkerOptions()
			.position(new LatLng(34, 118)));
			gameOver();
		}
		@Override
		protected void onPostExecute(Void v) {

			mMap.addMarker(new MarkerOptions()
			.position(new LatLng(40, 73)));
			gameOver();
		}

		protected void onProgressUpdate(Ghost... arg0) {
			Location iAmHere = MainActivity.getmLocationClient().getLastLocation();
			GoogleMap mMap = MainActivity.getmMap();
			List<Marker> mList = new ArrayList<Marker>();
			Ghost[] l = arg0;
			Bomb[] b = MainActivity.getBomb();
			Apple[] a = MainActivity.getApple();
			for(int i = 0; i < mList.size(); i++) {
				mList.get(i).setPosition(l[i].getLatLng());
			}
			mMap.clear();

			/*for(Ghost ghost : l) {
				double distance = Math.abs(ghost.getLatitude() - iAmHere.getLatitude()) + Math.abs(ghost.getLongitude() - iAmHere.getLongitude());
				if (distance < .0009) {
					MainActivity.makeToast();
				}
			}*/
			for(Apple apple : a) {
				mList.add(mMap.addMarker(new MarkerOptions()
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.apple))
				.position(new LatLng(apple.getLocation().latitude,apple.getLocation().longitude))));
			}

			for(Bomb bomb: b) {
				mList.add(mMap.addMarker(new MarkerOptions()
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.bomb))
				.position(new LatLng(bomb.getLocation().latitude,bomb.getLocation().longitude))));
			}
			for(Ghost g : arg0) {
				if(!g.isAttacking()) {
					mList.add(mMap.addMarker(new MarkerOptions()
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ghost_sprite))
					.position(new LatLng(g.getLatitude(), g.getLongitude()))));
				} else {
					mList.add(mMap.addMarker(new MarkerOptions()
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.close_ghost))
					.position(new LatLng(g.getLatitude(), g.getLongitude()))));
				}

			}

		}



	}

}
