package com.example.fastqueue;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    private List<Contact> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private HashMap<String, Boolean> contactMap; // use for tick v

    private void initMap() {
   //     if(mData == null)
        this.contactMap = new HashMap<>();
        for(Contact contact : this.mData) {
            contactMap.put(contact.getName(), false);
        }
    }


    public HashMap<String, Boolean> getMap() {
        return this.contactMap;
    }

    public void setMapValByKey(String contactName, boolean isChecked) {
        this.contactMap.put(contactName, isChecked);
    }

    // data is passed into the constructor
    ContactListAdapter(Context context, List<Contact> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        initMap();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.contacts_items, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = mData.get(position);
        holder.contactNameTextView.setText(contact.getName());
        holder.contactEmailTextView.setText(contact.getEmail());
        holder.contactPhoneTextView.setText(contact.getPhone_number());
        if(!this.contactMap.get(contact.getName())) {
            holder.contactNameTextView.setTypeface(null, Typeface.NORMAL);
            holder.contactEmailTextView.setTypeface(null, Typeface.NORMAL);
            holder.contactPhoneTextView.setTypeface(null, Typeface.NORMAL);
        } else {
            holder.contactNameTextView.setTypeface(null, Typeface.BOLD_ITALIC);
            holder.contactEmailTextView.setTypeface(null, Typeface.BOLD_ITALIC);
            holder.contactPhoneTextView.setTypeface(null, Typeface.BOLD_ITALIC);
        }
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView contactNameTextView;
        TextView contactPhoneTextView;
        TextView contactEmailTextView;


        ViewHolder(View itemView) {
            super(itemView);
            contactNameTextView = itemView.findViewById(R.id.contact_name);
            contactEmailTextView = itemView.findViewById(R.id.contact_email);
            contactPhoneTextView = itemView.findViewById(R.id.contact_phone);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Contact getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
