package com.example.fastqueue;

import android.provider.ContactsContract;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FindView extends ViewModel {

////
//    private MutableLiveData<String> mText;
//
//    public interface OnContactsCallback{
//        void onResult(List<Contact> contacts);
//    }
//
//    public void search(String text, OnContactsCallback callback){
//        String[] data = text.split(" ");
//        String fName = data[0];
//        String lName = data.length > 1 ? data[1] : ".*";
//        Database.getInstance().findProfiles((err, result) -> {
//            if(err)
//                callback.onResult(new ArrayList<>());
//            else
//                callback.onResult(result);
//        },fName,lName);
//    }
//
//    public FindViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
//    }
////
//    public LiveData<String> getText() {
//        return mText;
//    }
//}
}
