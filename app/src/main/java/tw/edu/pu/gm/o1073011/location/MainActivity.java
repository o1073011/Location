package tw.edu.pu.gm.o1073011.location;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LocationListener {
    //可自行設定請求代碼，區別用
    final int REQUEST_FINE_LOCATION_PERMISSION = 102;

    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckPermission();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
