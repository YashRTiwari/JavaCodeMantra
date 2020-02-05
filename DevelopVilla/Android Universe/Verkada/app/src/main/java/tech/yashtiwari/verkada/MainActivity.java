package tech.yashtiwari.verkada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;
import tech.yashtiwari.verkada.databinding.ActivityMainBinding;
import tech.yashtiwari.verkada.dialog.BottomSheetDateDailog;
import tech.yashtiwari.verkada.fragment.home.HomePageFragment;
import tech.yashtiwari.verkada.retrofit.RetrofitInterface;
import tech.yashtiwari.verkada.room.MotionZonesDatabase;

public class MainActivity extends AppCompatActivity implements Navigator {

    RetrofitInterface apiInterface;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private BottomSheetDateDailog dialog;

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
        BottomSheetDateDailog fragment = BottomSheetDateDailog.getInstance(this);
        if (bundle != null)
            fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, fragment, BottomSheetDateDailog.TAG).commit();
    }

    @Override
    public void moveToHomeFragment(Bundle bundle) {
        HomePageFragment fragment = HomePageFragment.newInstance(this);
        if (bundle != null)
            fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, fragment, HomePageFragment.TAG).commit();
    }

}

