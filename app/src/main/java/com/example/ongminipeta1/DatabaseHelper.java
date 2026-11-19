package com.example.ongminipeta1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Objects;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static fina.l String DATABASE_NAME = "FantasticAccounts.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_SALT = "salt";
    private static final String COLUMN_PASSWORD_HASH = "password_hash";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT UNIQUE NOT NULL, " +
                COLUMN_SALT + " TEXT NOT NULL, " +
                COLUMN_PASSWORD_HASH + " TEXT NOT NULL)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        return Base64.encodeToString(salt, Base64.NO_WRAP);
    }

    private String hashPassword(String password, String salt) {
        try {
            byte[] saltBytes = Base64.decode(salt, Base64.NO_WRAP);

            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    saltBytes,
                    120000,
                    256
            );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] hash = factory.generateSecret(spec).getEncoded();
            spec.clearPassword();

            return Base64.encodeToString(hash, Base64.NO_WRAP);

        } catch (Exception e) {
            return null;
        }
    }

    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();

        String salt = generateSalt();
        String passwordHash = hashPassword(password, salt);

        if (passwordHash == null) {
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_SALT, salt);
        values.put(COLUMN_PASSWORD_HASH, passwordHash);

        long result = db.insert(TABLE_USERS, null, values);

        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        try (Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_SALT, COLUMN_PASSWORD_HASH},
                COLUMN_USERNAME + " = ?",
                new String[]{username},
                null,
                null,
                null
        )) {
            if (cursor.moveToFirst()) {
                String salt = cursor.getString(0);
                String savedHash = cursor.getString(1);

                String enteredHash = hashPassword(password, salt);

                return Objects.equals(savedHash, enteredHash);
            }
        }

        return false;
    }

    public ArrayList<String> getAllUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        try (Cursor cursor = db.rawQuery(
                "SELECT " + COLUMN_USERNAME + " FROM " + TABLE_USERS + " ORDER BY " + COLUMN_USERNAME + " ASC",
                null
        )) {
            if (cursor.moveToFirst()) {
                do {
                    usernames.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        }

        return usernames;
    }
}