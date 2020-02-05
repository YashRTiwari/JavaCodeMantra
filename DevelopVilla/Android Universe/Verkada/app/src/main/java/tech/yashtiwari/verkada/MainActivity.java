package tech.yashtiwari.verkada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    HomePageFragment homePageFragment;
    BottomSheetDateDailog bottomSheetDateDailog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");

        FragmentManager manager = getSupportFragmentManager();
        bottomSheetDateDailog = (BottomSheetDateDailog) manager.findFragmentByTag(BottomSheetDateDailog.TAG);
        homePageFragment = (HomePageFragment) manager.findFragmentByTag(HomePageFragment.TAG);

        if (bottomSheetDateDailog != null){
            Log.d(TAG, "onCreate: bottomSheetDateDailog");
        }

        if (homePageFragment != null){
            Log.d(TAG, "onCreate: homePageFragment");
        } else {
            moveToBDSSFragment(null);
        }

        MotionZonesDatabase db = App.getDatabaseInstance();
    }


    @Override
    public void moveToBDSSFragment(Bundle bundle) {
        bottomSheetDateDailog = new BottomSheetDateDailog(this);
        if (bundle != null)
            bottomSheetDateDailog.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, bottomSheetDateDailog, BottomSheetDateDailog.TAG).commitAllowingStateLoss();
    }

    @Override
    public void moveToHomeFragment(Bundle bundle) {

        homePageFragment = HomePageFragment.newInstance(this);

        if (bundle != null)
            homePageFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(HomePageFragment.TAG);
        transaction.replace(R.id.frame, homePageFragment, HomePageFragment.TAG).commitAllowingStateLoss();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
        else {
            while(getSupportFragmentManager().getBackStackEntryCount() != 0){
                getSupportFragmentManager().popBackStackImmediate();
            }
        }
    }
}

