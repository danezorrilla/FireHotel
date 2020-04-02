package com.bb.firehotel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.firehotel.R;
import com.bb.firehotel.model.Guest;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestListViewHolder> {

    private List<Guest> guestList;

    public GuestListAdapter(List<Guest> guestList){
        this.guestList = guestList;
    }

    @NonNull
    @Override
    public GuestListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.guest_list_layout, parent, false);
        return new GuestListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestListViewHolder holder, int position) {
        holder.guestNameTextView.setText(guestList.get(position).getGuestName());
        holder.guestRoomNumTextView.setText(guestList.get(position).getGuestRoomNum());
    }

    @Override
    public int getItemCount() {
        return guestList.size();
    }

    public class GuestListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.guest_name_textView)
        TextView guestNameTextView;

        @BindView(R.id.guest_room_num_textView)
        TextView guestRoomNumTextView;

        public GuestListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
