package com.bb.firehotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import io.reactivex.disposables.CompositeDisposable;


public class MainActivity extends AppCompatActivity {

    GuestViewModel guestViewModel;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.guest_name_editText)
    EditText guestNameEditText;

    @BindView(R.id.guest_room_num_editText)
    EditText guestRoomNumEditText;

    @BindView(R.id.guest_list_recyclerView)
    RecyclerView guestListRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        guestViewModel = ViewModelProviders.of(this).get(GuestViewModel.class);

        compositeDisposable.add(guestViewModel.getGuestList().subscribe(GuestList ->
        {displayGuestList(GuestList);}));

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
        guestViewModel.addNewGuest(newGuest);

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


}
