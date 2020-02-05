package tech.yashtiwari.verkada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
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
        moveToBDSSFragment(null);
        MotionZonesDatabase db = App.getDatabaseInstance();
    }


    @Override
    public void moveToBDSSFragment(Bundle bundle) {
        BottomSheetDateDailog fragment = new BottomSheetDateDailog(this);
        if (bundle != null)
            fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment, BottomSheetDateDailog.TAG).commitAllowingStateLoss();
    }

    @Override
    public void moveToHomeFragment(Bundle bundle) {
        HomePageFragment fragment = new HomePageFragment(this);
        if (bundle != null)
            fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(fragment.TAG);
        transaction.add(R.id.frame, fragment, HomePageFragment.TAG).commit();
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

