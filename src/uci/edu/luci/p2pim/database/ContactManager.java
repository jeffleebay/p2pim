/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uci.edu.luci.p2pim.database;

import uci.edu.luci.p2pim.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public final class ContactManager extends Activity
{

    public static final String TAG = "ContactManager";

    private FriendDataSource friendDataSource;
    private ListView mContactList;

    /**
     * Called when the activity is first created. Responsible for initializing the UI.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.v(TAG, "Activity State: onCreate()");
        super.onCreate(savedInstanceState);
        setTitle("Contacts");
        setContentView(R.layout.contact_manager);

        // Obtain handles to UI objects
        mContactList = (ListView) findViewById(R.id.contactList);

        friendDataSource = new FriendDataSource(this);
		
        // Populate the contact list
		populateContactList();
		
        
        mContactList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				
				Cursor c = ((SimpleCursorAdapter)parent.getAdapter()).getCursor();
				String selectedName = "Contact Name";
				if(c.moveToPosition(position)){
					selectedName = c.getString(1);
				}
				
				friendDataSource.open();
				Friend friend = friendDataSource.createAFriend(selectedName,"");
				Toast.makeText(getApplicationContext(), "" + friend.getFriendName() + " is added to your contact list successfully",Toast.LENGTH_SHORT).show();
			}
		});
    }

    /**
     * Populate the contact list based on account currently selected in the account spinner.
     */
    private void populateContactList() {

    	// Build adapter with contact entries
        Cursor cursor = getContacts();
        String[] fields = new String[] {
                ContactsContract.Data.DISPLAY_NAME
        };
        @SuppressWarnings("deprecation")
//    		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.contact_entry, cursor,
//                    fields, new int[] {R.id.contactEntryText});
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.friend_template, cursor,
                fields, new int[] {R.id.txtName});
        mContactList.setAdapter(adapter);
    }


    /**
     * Obtains the contact list for the currently selected account.
     *
     * @return A cursor for for accessing the contact list.
     */
    @SuppressWarnings("deprecation")
	private Cursor getContacts()
    {
        // Run query
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '1'"; //mShowInvisible ? "0" : "1"
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        return managedQuery(uri, projection, selection, selectionArgs, sortOrder);
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_FrndMngr:
			Intent intentI = new Intent(this, FriendManager.class);
			startActivity(intentI);
			break;
		case R.id.action_MsgMngr:
			Intent intentJ = new Intent(this, MessageManager.class);
			startActivity(intentJ);
			break;
		case R.id.action_ContMngr:
			Intent intentK = new Intent(this, ContactManager.class);
			startActivity(intentK);
			break;
		}

		return true;
	}

}
