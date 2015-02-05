package com.globant.paulabaudo.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddContactFragment extends Fragment {

    Button mButtonDone;
    EditText mEditTextFirstName;
    EditText mEditTextLastName;

    public AddContactFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_contact, container, false);
        mButtonDone = (Button) rootView.findViewById(R.id.button_done);
        wireUpEditTexts(rootView);
        prepareEditTexts();

        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                Intent intentResult = getIntent();
                activity.setResult(Activity.RESULT_OK, intentResult);
                activity.finish();
            }

            private Intent getIntent() {
                EditText editTextNickname = (EditText) rootView.findViewById(R.id.edit_text_nickname);

                Intent intentResult = new Intent();
                intentResult.putExtra(Contact.FIRSTNAME,mEditTextFirstName.getText().toString());
                intentResult.putExtra(Contact.LASTNAME,mEditTextLastName.getText().toString());
                intentResult.putExtra(Contact.URL,"");

                if (!TextUtils.isEmpty(editTextNickname.getText().toString())){
                    intentResult.putExtra(Contact.NICKNAME, editTextNickname.getText().toString());
                } else {
                    intentResult.putExtra(Contact.NICKNAME, "");
                }
                return intentResult;
            }
        });

        return rootView;
    }

    private void prepareEditTexts() {
        TextWatcher listener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(mEditTextFirstName.getText()) &&
                    !TextUtils.isEmpty(mEditTextLastName.getText())){
                    mButtonDone.setEnabled(true);
                } else {
                    mButtonDone.setEnabled(false);
                }
            }
        };

        mEditTextFirstName.addTextChangedListener(listener);
        mEditTextLastName.addTextChangedListener(listener);
    }

    private void wireUpEditTexts(View rootView) {
        mEditTextFirstName = (EditText) rootView.findViewById(R.id.edit_text_first_name);
        mEditTextLastName = (EditText) rootView.findViewById(R.id.edit_text_last_name);
    }
}
