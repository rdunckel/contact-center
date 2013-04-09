package edu.wctc.android.contactcenter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button contactsButton = (Button) findViewById(R.id.button1);
		contactsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, ContactList.class);
				startActivity(intent);
			}

		});

		doStuff();
	}

	@SuppressWarnings("unused")
	private void doStuff() {
		Cursor people = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		int nameIndex = 0;
		String name = "";

		while (people.moveToNext()) {
			nameIndex = people.getColumnIndex(PhoneLookup.DISPLAY_NAME);
			name = people.getString(nameIndex);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
