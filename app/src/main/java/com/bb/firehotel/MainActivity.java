package com.bb.firehotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.bb.firehotel.adapter.GuestListAdapter;
import com.bb.firehotel.model.Guest;
import com.bb.firehotel.viewmodel.GuestViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    GuestViewModel guestViewModel;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.guest_name_editText)
    EditText guestNameEditText;

    @BindView(R.id.guest_room_num_editText)
    EditText guestRoomNumEditText;

    @BindView(R.id.guest_list_recyclerView)
    RecyclerView guestListRecyclerView;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        guestViewModel = ViewModelProviders.of(this).get(GuestViewModel.class);

        reference = FirebaseDatabase.getInstance().getReference().child("guests");

         List<Guest> guestList = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Guest currentGuest = ds.getValue(Guest.class);
                    guestList.add(currentGuest);
                }
                //displayGuestList(guestList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        compositeDisposable.add(guestViewModel.getGuestList().subscribe(GuestList -> {
//            displayGuestListRx(GuestList);
//                },
//                throwable -> {Log.d("TAG_XX", "Error: " + throwable.getLocalizedMessage());}));

        displayGuestList(guestViewModel.getGuestList());

        compositeDisposable.add(guestViewModel.getGuestListRx().subscribe(GuestList ->
        {displayGuestListRx(GuestList);}));


        Observable.fromArray(new String[]{"A","B","C"})
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String string) {
                        System.out.println("onNext: " + string);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @OnClick(R.id.add_new_guest_button)
    public void addGuest(){
        Log.d("TAG_X", "Button is Clicked");
        String guestName = guestNameEditText.getText().toString();
        String guestRoomNum = guestRoomNumEditText.getText().toString();

        Log.d("TAG_X", "Guest: " + guestName + " " + guestRoomNum);
        Guest newGuest = new Guest(guestName, guestRoomNum);
        Log.d("TAG_X", "Guest: " + newGuest);
        //guestViewModel.addNewGuest(newGuest);

        guestNameEditText.setText("");
        guestRoomNumEditText.setText("");
    }

    private void displayGuestList(List<Guest> guestList){
        for( int i = 0; i < guestList.size(); i++){
            Log.d("TAG_XX", guestList.get(i).toString());
        }
        GuestListAdapter guestListAdapter = new GuestListAdapter(guestList);
        guestListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        guestListRecyclerView.setAdapter(guestListAdapter);
    }

    private void displayGuestListRx(List<Guest> guestList){
        Log.d("TAG_XX", "This is called");
        Log.d("TAG_XX", "Guest List Size: " + guestList.size());
        }

}
