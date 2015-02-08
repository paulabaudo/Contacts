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
    String mAction;
    EditText mEditTextNickname;
    Button mButtonDelete;
    int mContactId;

    public AddContactFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_contact, container, false);
        getAction();
        wireUpButtons(rootView);
        prepareImageButton(rootView);
        wireUpEditTexts(rootView);
        prepareEditTexts();
        prepareButton(rootView);
        if (mAction.equals(ContactListFragment.ACTION_EDIT_DELETE)){
            prepareEditDeleteScreen(rootView);
        }

        return rootView;
    }

    private void prepareEditDeleteScreen(View rootView) {
        mEditTextFirstName.setText(getActivity().getIntent().getStringExtra(Contact.FIRSTNAME));
        mEditTextLastName.setText(getActivity().getIntent().getStringExtra(Contact.LASTNAME));
        mEditTextNickname.setText(getActivity().getIntent().getStringExtra(Contact.NICKNAME));
        mImageButtonContactPhoto.setImageBitmap(getBitmap(getActivity().getIntent().
            getByteArrayExtra(Contact.IMAGE)));
        mPhoto = getBitmap(getActivity().getIntent().getByteArrayExtra(Contact.IMAGE));
        mButtonDelete.setVisibility(View.VISIBLE);
        mButtonDone.setText(rootView.getResources().getString(R.string.button_edit_contact));
        mContactId = getActivity().getIntent().getIntExtra(Contact.ID,0);
    }

    private Bitmap getBitmap(byte[] image) {
        Bitmap bmp;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        bmp = BitmapFactory.decodeByteArray(image, 0, image.length, options);
        return bmp;
    }

    private void getAction() {
        String action = getActivity().getIntent().getStringExtra(ContactListFragment.ACTION);
        if (action.equals(ContactListFragment.ACTION_ADD)){
            mAction = ContactListFragment.ACTION_ADD;
        } else {
            mAction = ContactListFragment.ACTION_EDIT_DELETE;
        }
    }

    private void prepareImageButton(View rootView) {
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
        mImageButtonContactPhoto = (ImageButton) rootView.findViewById(R.id.image_button_contact_photo);
        mButtonDelete = (Button) rootView.findViewById(R.id.button_delete);
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
                Intent intentResult = new Intent();
                intentResult.putExtra(ContactListFragment.ACTION, mAction);
                intentResult.putExtra(Contact.FIRSTNAME,mEditTextFirstName.getText().toString());
                intentResult.putExtra(Contact.LASTNAME,mEditTextLastName.getText().toString());

                convertBitmapImageToByteArray();
                intentResult.putExtra(Contact.IMAGE,mImage);

                if (!TextUtils.isEmpty(mEditTextNickname.getText().toString())){
                    intentResult.putExtra(Contact.NICKNAME, mEditTextNickname.getText().toString());
                } else {
                    intentResult.putExtra(Contact.NICKNAME, "");
                }

                if (mAction.equals(ContactListFragment.ACTION_EDIT_DELETE)){
                    intentResult.putExtra(Contact.ID, mContactId);
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
        mEditTextNickname = (EditText) rootView.findViewById(R.id.edit_text_nickname);
    }
}
