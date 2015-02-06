package com.globant.paulabaudo.contacts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddContactFragment extends Fragment {

    Button mButtonDone;
    EditText mEditTextFirstName;
    EditText mEditTextLastName;
    ImageButton mImageButtonContactPhoto;
    Bitmap mPhoto;
    byte[] mImage;

    public AddContactFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_contact, container, false);
        wireUpButtons(rootView);
        prepareImageButton(rootView);
        wireUpEditTexts(rootView);
        prepareEditTexts();
        prepareButton(rootView);

        return rootView;
    }

    private void prepareImageButton(View rootView) {
        mImageButtonContactPhoto = (ImageButton) rootView.findViewById(R.id.image_button_contact_photo);
        mPhoto = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.placeholder_contact);

        mImageButtonContactPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, ContactListFragment.REQUEST_CODE);
            }
        });
    }

    private void wireUpButtons(View rootView) {
        mButtonDone = (Button) rootView.findViewById(R.id.button_done);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ContactListFragment.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mPhoto = (Bitmap) data.getExtras().get("data");
            mImageButtonContactPhoto.setImageBitmap(mPhoto);
        }
    }

    private void convertBitmapImageToByteArray() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mPhoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
        mImage = stream.toByteArray();
    }

    private void prepareButton(final View rootView) {
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

                convertBitmapImageToByteArray();
                intentResult.putExtra(Contact.IMAGE,mImage);

                if (!TextUtils.isEmpty(editTextNickname.getText().toString())){
                    intentResult.putExtra(Contact.NICKNAME, editTextNickname.getText().toString());
                } else {
                    intentResult.putExtra(Contact.NICKNAME, "");
                }
                return intentResult;
            }
        });
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
