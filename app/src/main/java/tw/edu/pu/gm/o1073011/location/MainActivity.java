package tw.edu.pu.gm.o1073011.location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    //可自行設定請求代碼，區別用
    final int REQUEST_FINE_LOCATION_PERMISSION = 102;

    LocationManager locationManager;

    private void CheckPermission(){
        //如果使用者尚未允許權限
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            //Toast.makeText(this, "本App需允許位置授權，才能定位",
            //        Toast.LENGTH_LONG).show();
            //finish();

            // 如果裝置版本是6.0（包含）以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 請求授權：第一個參數是請求授權的名稱，第二個參數是請求代碼
                requestPermissions(
                        //new String[]{Manifest.permission.RECORD_AUDIO},
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_FINE_LOCATION_PERMISSION);
            }
        }
        else{
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckPermission();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
        //    String msg = "緯度：" + location.getLatitude() +
        //            "\n經度：" + location.getLongitude();
        //    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Intent it = new Intent();
        it.setAction(Intent.ACTION_VIEW);
        Uri u = Uri.parse("geo:" + String.valueOf(location.getLatitude())
                + "," + String.valueOf(location.getLongitude()) );
        it.setData(u);
        startActivity(it);
    }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "GPS已經開啟", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "GPS已關閉", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 用 requestCode 來判斷這是哪一個權限
        if (requestCode == REQUEST_FINE_LOCATION_PERMISSION) {
            //如果使用者拒絕權限
            if (grantResults.length > 0 &&
                    grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this, "請設定允許位置資訊授權，才能定位",
                            Toast.LENGTH_LONG).show();
                    Intent it = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri u = Uri.fromParts("package", getPackageName(), null);
                    it.setData(u);
                    startActivity(it);
                }
                else{
                    Toast.makeText(this, "本App需允許位置授權，才能定位",
                            Toast.LENGTH_LONG).show();

                }
                finish();
            }
        }
    }
}
