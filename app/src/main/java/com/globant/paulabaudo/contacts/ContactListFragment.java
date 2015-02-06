package com.globant.paulabaudo.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContactListFragment extends ListFragment {

    private final static String LOG_TAG = ContactListFragment.class.getSimpleName();
    final static Integer REQUEST_CODE = 0;
    ContactAdapter mAdapter;
    DatabaseHelper mDBHelper = null;

    public ContactListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    public DatabaseHelper getDBHelper() {
        if (mDBHelper == null){
            mDBHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return mDBHelper;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                String firstname = data.getStringExtra(Contact.FIRSTNAME);
                String lastname = data.getStringExtra(Contact.LASTNAME);
                String nickname = data.getStringExtra(Contact.NICKNAME);
                String url = data.getStringExtra(Contact.URL);
                addContact(firstname, lastname, nickname, url);
                Contact contact = getContact(firstname, lastname, nickname, url);
                saveContact(contact);
            }
        }
    }

    private Contact getContact(String firstname, String lastname, String nickname, String url) {
        Contact contact = new Contact();
        contact.setFirstName(firstname);
        contact.setLastName(lastname);
        contact.setNickname(nickname);
        contact.setUrl(url);
        return contact;
    }

    private void saveContact(Contact contact) {
        try {
            Dao<Contact,Integer> dao = getDBHelper().getContactDao();
            dao.create(contact);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Failed to create DAO.", e);
        }

    }

    private void addContact(String firstname, String lastname, String nickname, String url) {
        Contact contact = getContact(firstname, lastname, nickname, url);
        mAdapter.add(contact);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        if (mDBHelper!=null){
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareListView();
    }

    private void prepareListView() {
        List<Contact> entries = new ArrayList<>();
        mAdapter = new ContactAdapter(getActivity(), entries);
        setListAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Boolean handled = false;
        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                handled = true;
                break;
        }
        if (!handled) {
            handled = super.onOptionsItemSelected(item);
        }
        return handled;
    }
}
