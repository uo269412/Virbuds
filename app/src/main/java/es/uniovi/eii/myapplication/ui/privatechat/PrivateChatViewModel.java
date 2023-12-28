package es.uniovi.eii.myapplication.ui.privatechat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PrivateChatViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PrivateChatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}