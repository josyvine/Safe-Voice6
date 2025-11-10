package com.safevoice.app.ui.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safevoice.app.R;
import com.safevoice.app.models.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * The Adapter for the RecyclerView in ContactsFragment.
 * It takes a list of Contact objects and binds them to the item_contact.xml layout.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    private List<Contact> contactList;
    private final OnContactOptionsClickListener optionsClickListener;

    /**
     * Interface to handle clicks on the options menu (three dots) for a contact item.
     * The Fragment will implement this to show an Edit/Delete menu.
     */
    public interface OnContactOptionsClickListener {
        void onContactOptionsClicked(Contact contact);
    }

    public ContactsAdapter(List<Contact> contactList, OnContactOptionsClickListener listener) {
        this.contactList = contactList;
        this.optionsClickListener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item_contact.xml layout for each new item in the list.
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        // Get the contact at the current position.
        Contact currentContact = contactList.get(position);

        // Bind the contact's data to the views in the ViewHolder.
        holder.nameTextView.setText(currentContact.getName());
        holder.phoneTextView.setText(currentContact.getPhoneNumber());

        // Set up the click listener for the options button.
        // This will call the interface method implemented by the fragment.
        holder.optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionsClickListener != null) {
                    optionsClickListener.onContactOptionsClicked(currentContact);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the total number of contacts in the list.
        return contactList.size();
    }

    /**
     * Updates the list of contacts in the adapter and refreshes the RecyclerView.
     *
     * @param newContactList The new list of contacts to display.
     */
    public void updateContacts(List<Contact> newContactList) {
        this.contactList = newContactList;
        // Notify the RecyclerView that the data has changed and it needs to redraw itself.
        notifyDataSetChanged();
    }

    /**
     * The ViewHolder class holds references to the UI views for a single list item.
     * This improves performance by avoiding repeated calls to findViewById().
     */
    static class ContactViewHolder extends RecyclerView.ViewHolder {
        final TextView nameTextView;
        final TextView phoneTextView;
        final ImageButton optionsButton;

        ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.text_contact_name);
            phoneTextView = itemView.findViewById(R.id.text_contact_phone);
            optionsButton = itemView.findViewById(R.id.button_contact_options);
        }
    }
}
