package com.example.login.Membership.OurUser;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> tossUserid = new MutableLiveData<>();

    public void setTossUserid(String userId) {
        tossUserid.setValue(userId);
    }

    public LiveData<String> getTossUserid() {
        return tossUserid;
    }
}
