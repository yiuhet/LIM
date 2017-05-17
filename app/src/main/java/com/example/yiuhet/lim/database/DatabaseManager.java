package com.example.yiuhet.lim.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yiuhet.lim.app.lImApplication;
import com.example.yiuhet.lim.database.ContactDbSchema.ContactTable;
import com.example.yiuhet.lim.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yiuhet on 2017/5/17.
 */

public class DatabaseManager {

    private static DatabaseManager mDatabaseManager;

    private static SQLiteDatabase mDatabase;

    public static DatabaseManager getInstance(){
        if (mDatabase == null) {
            mDatabase = new ContactBaseHelper(lImApplication.getAppContext()).getWritableDatabase();
        }
        if (mDatabaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (mDatabaseManager == null) {
                    mDatabaseManager = new DatabaseManager();
                }
            }
        }
        return mDatabaseManager;
    }

    private ContentValues getContentValues(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(ContactTable.Cols.ID, contact.imageId);
        values.put(ContactTable.Cols.USER, contact.userName);
        return values;
    }

    public void addContact(Contact contact) {
        ContentValues values = getContentValues(contact);
        mDatabase.insert(ContactTable.NAME, null, values);
    }


    public void deleteAllContacts() {
        mDatabase.delete(ContactTable.NAME,null,null);
    }

    public void deleteContact(String userName) {
        mDatabase.delete(ContactTable.NAME,
                "user = ?",
                new String[]{userName.toString()});
    }

    public boolean isExits(String userName) {
        Cursor cursor = mDatabase.query(ContactTable.NAME,
                new String[] {ContactTable.Cols.USER},
                "user = ?",
                new String[] {userName.toString()},
                null,
                null,
                null
                );
        return cursor.getCount() > 0 ? true : false;
    }
    public List<Contact> queryAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        Cursor cursor = mDatabase.query(
                ContactTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        ContactCursorWrapper contactCursorWrapper = new ContactCursorWrapper(cursor);
        try {
            contactCursorWrapper.moveToFirst();
            while (!contactCursorWrapper.isAfterLast()) {
                contactList.add(contactCursorWrapper.getContact());
                contactCursorWrapper.moveToNext();
            }
        } finally {
            contactCursorWrapper.close();
        }
        return contactList;
    }

    class ContactCursorWrapper extends CursorWrapper {

        /**
         * Creates a cursor wrapper.
         *
         * @param cursor The underlying cursor to wrap.
         */
        public ContactCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Contact getContact() {
            String id = getString(getColumnIndex(ContactTable.Cols.ID));
            String user = getString(getColumnIndex(ContactTable.Cols.USER));
            return new Contact(id,user);
        }
    }

}
