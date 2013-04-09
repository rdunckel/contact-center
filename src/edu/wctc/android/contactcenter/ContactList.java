package edu.wctc.android.contactcenter;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.CursorJoiner.Result;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class ContactList extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// We'll define a custom screen layout here (the one shown above),
		// but
		// typically, you could just use the standard ListActivity layout.
		setContentView(R.layout.activity_contact_list);

		CursorLoader contactsLoader = new CursorLoader(this,
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		CursorLoader phoneLoader = new CursorLoader(this, Phone.CONTENT_URI,
				null, null, null, null);

		Cursor contacts = contactsLoader.loadInBackground();
		Cursor phones = phoneLoader.loadInBackground();

		CursorJoiner joiner = new CursorJoiner(contacts,
				new String[] { Contacts._ID }, phones,
				new String[] { Phone.CONTACT_ID });

		MatrixCursor joinedCursor = new MatrixCursor(new String[] {
				Contacts.DISPLAY_NAME, Phone.NUMBER });

		for (CursorJoiner.Result joinerResult : joiner) {
			switch (joinerResult) {
			case LEFT:
				break;

			case RIGHT:
				break;

			case BOTH:
				break;
			}
		}

		joinedCursor.addRow(new Object[] { 1,
				contacts.getInt(contacts.getColumnIndex("colA")),
				phones.getString(phones.getColumnIndex("colB")) });

		// ListAdapter listAdapter = new SimpleCursorAdapter(this,
		// android.R.layout.two_line_list_item, contacts, new String[] {
		// Contacts.DISPLAY_NAME, Contacts.HAS_PHONE_NUMBER },
		// new int[] { android.R.id.text1, android.R.id.text2 }, 0);

		ListAdapter listAdapter = new SimpleCursorAdapter(this,
				android.R.layout.two_line_list_item, contacts, new String[] {
						Contacts.DISPLAY_NAME, Phone.NUMBER }, new int[] {
						android.R.id.text1, android.R.id.text2 }, 0);

		setListAdapter(listAdapter);
	}

	private String getPhoneNumbers(String contactId) {

		ContentResolver cr = getContentResolver();
		//
		// Get all phone numbers.
		//
		Cursor phones = cr.query(Phone.CONTENT_URI, null, Phone.CONTACT_ID
				+ " = " + contactId, null, null);

		String number = "";

		while (phones.moveToNext()) {
			number = phones.getString(phones.getColumnIndex(Phone.NUMBER));

			int type = phones.getInt(phones.getColumnIndex(Phone.TYPE));
			switch (type) {
			case Phone.TYPE_HOME:
				// do something with the Home number here...
				break;
			case Phone.TYPE_MOBILE:
				// do something with the Mobile number here...
				break;
			case Phone.TYPE_WORK:
				// do something with the Work number here...
				break;
			}

		}
		phones.close();

		return number;
	}
}
