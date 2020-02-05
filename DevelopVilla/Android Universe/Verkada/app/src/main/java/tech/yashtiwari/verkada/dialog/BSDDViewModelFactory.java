package tech.yashtiwari.verkada.dialog;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import tech.yashtiwari.verkada.Navigator;

public class BSDDViewModelFactory extends ViewModelProvider.NewInstanceFactory {


    private final CommunicateInterface communicateInterface;
    Context context;

    Navigator navigator;
    public BSDDViewModelFactory(Context context, Navigator navigator, CommunicateInterface communicateInterface){
        this.context = context;
        this.navigator = navigator;
        this.communicateInterface = communicateInterface;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BSDDViewModel(context, navigator, communicateInterface);
    }
}

