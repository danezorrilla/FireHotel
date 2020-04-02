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
import io.reactivex.subjects.PublishSubject;

public class GuestViewModel extends AndroidViewModel {

    private DatabaseReference reference;

    static List<Guest> guestList = new ArrayList<>();

    private PublishSubject<List<Guest>> guestObservable = PublishSubject.create();

    public GuestViewModel(@NonNull Application application) {
        super(application);

        reference = FirebaseDatabase.getInstance().getReference().child("guests");

    }

    public Observable<List<Guest>> getGuestList(){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Guest currentGuest = ds.getValue(Guest.class);
                    guestList.add(currentGuest);
                }
                guestObservable.onNext(guestList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return guestObservable;

    }


    public void addNewGuest(Guest guest){
        String databaseKey = reference.push().getKey();
        if(databaseKey != null)
            reference.child(databaseKey).setValue(guest);
    }
}
