package tech.yashtiwari.verkada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import io.reactivex.disposables.CompositeDisposable;
import tech.yashtiwari.verkada.databinding.ActivityMainBinding;
import tech.yashtiwari.verkada.dialog.BottomSheetDateDailog;
import tech.yashtiwari.verkada.fragment.home.HomePageFragment;
import tech.yashtiwari.verkada.retrofit.RetrofitInterface;
import tech.yashtiwari.verkada.room.MotionZonesDatabase;

public class MainActivity extends AppCompatActivity implements Navigator {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        if (savedInstanceState == null)
            moveToBDSSFragment(null);
        MotionZonesDatabase db = App.getDatabaseInstance();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

        }
    }

    @Override
    public void moveToBDSSFragment(Bundle bundle) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        BottomSheetDateDailog bottomSheetDateDailog = new BottomSheetDateDailog(this);
        if (bundle != null)
            bottomSheetDateDailog.setArguments(bundle);
        transaction.add(R.id.frame, bottomSheetDateDailog, bottomSheetDateDailog.TAG);
        transaction.addToBackStack(bottomSheetDateDailog.TAG);
        transaction.commit();

    }

    @Override
    public void moveToHomeFragment(Bundle bundle) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        HomePageFragment homePageFragment = HomePageFragment.newInstance(this);
        if (bundle != null)
            homePageFragment.setArguments(bundle);
        transaction.add(R.id.frame, homePageFragment, homePageFragment.TAG);
        transaction.addToBackStack(homePageFragment.TAG);
        transaction.commit();

    }


}

