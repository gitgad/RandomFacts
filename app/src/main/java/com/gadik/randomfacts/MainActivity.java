package com.gadik.randomfacts;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by Gadi
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private boolean isPermissionGranted;
    private TabsAdapter tabsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isPermissionGranted = isStoragePermissionGranted();

        if(!isConnectedToNetwork()){
            Log.i(TAG, "No network connection. inform user.");
            Toast.makeText(this, getString(R.string.please_enable_network), Toast.LENGTH_SHORT).show();
        }

        initViewPager();
    }

    /***
     * Inflate share menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        // Sharing the fact screen - by taking a screenshot
        if (itemId == R.id.share_item) {
            if(!isPermissionGranted){
                Log.i(TAG, "User tries to use the share button without permissions");
                Toast.makeText(this, getString(R.string.please_activate_permissions), Toast.LENGTH_LONG).show();
            } else {
                File file = takeScreenshot();
                if(file != null){
                    Log.d(TAG, "File path: " + file.getAbsolutePath());
                    Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, R.string.check_this_out_hashtag);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    shareIntent.setType("image/*");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                    return true;
                } else {
                    Log.e(TAG, "Unable to create screenshot file");
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Take a Screenshot and saving it on the device
     */
    private File takeScreenshot() {
        Log.d(TAG, "Create screenshot");
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {

            // Try to create screenshot folder
            String folderPath = Environment.getExternalStorageDirectory() +
                    File.separator + "RandomFacts";
            File folder = new File(folderPath);
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }
            if (!success) {
                Log.e(TAG, "Unable to create screenshots folder");
                return null;
            }

            // Naming the image with the current time
            String mPath = folderPath + File.separator + "NewRandomFact-" + now + ".jpg";

            // create bitmap screen capture
            View view = getWindow().getDecorView().getRootView();
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            return imageFile;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {
                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            // permission is automatically granted on sdk lower than 23
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Log.w(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            Toast.makeText(this, getString(R.string.deactivate_share_btn_inform_user), Toast.LENGTH_LONG).show();
            isPermissionGranted = false;
        } else {
            isPermissionGranted = true;
            Log.i(TAG, "Permission: " + permissions[0] + " granted.");
        }
    }

    private boolean isConnectedToNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void initViewPager(){
        Log.i(TAG, "initViewPager() called");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager());

        tabsAdapter.addFragment(new RandomFactFragment(), getString(R.string.random_fact_frag_title));
        tabsAdapter.addFragment(new FavouriteFactsFragment(), getString(R.string.favorite_facts_frag_title));
        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
