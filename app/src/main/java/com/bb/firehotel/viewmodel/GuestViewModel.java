package com.bb.firehotel.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.bb.firehotel.model.Guest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GuestViewModel extends AndroidViewModel {

    private DatabaseReference reference;

    static List<Guest> guestList = new ArrayList<>();

    Observable<List<Guest>> guestObservable;

    public GuestViewModel(@NonNull Application application) {
        super(application);

        reference = FirebaseDatabase.getInstance().getReference().child("guests");

    }

    public List<Guest> getGuestList(){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Guest currentGuest = ds.getValue(Guest.class);
                    guestList.add(currentGuest);
                }
                guestObservable = Observable.fromArray(guestList);
                guestObservable.subscribe(new Observer<List<Guest>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Guest> guestList) {
                        System.out.println("onNext: " + guestList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return guestList;

    }

    public Observable<List<Guest>> getGuestListRx(){
        return guestObservable.fromArray(guestList);
    }

    public void addNewGuest(Guest guest){
        String databaseKey = reference.push().getKey();
        if(databaseKey != null)
            reference.child(databaseKey).setValue(guest);
    }
}
