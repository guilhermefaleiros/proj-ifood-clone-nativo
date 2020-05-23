package com.guifaleiros.ifood.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class FirebaseHelper {

    private static FirebaseAuth sAuthReference;
    private static DatabaseReference sFirebaseReference;
    private static StorageReference sStorageReference;

    public static DatabaseReference getFirebase(){
        if( sFirebaseReference == null ){
            sFirebaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return sFirebaseReference;
    }

    public static FirebaseAuth getFirebaseAuth(){
        if( sAuthReference == null ){
            sAuthReference = FirebaseAuth.getInstance();
        }
        return sAuthReference;
    }

    public static StorageReference getFirebaseStorage(){
        if( sStorageReference == null ){
            sStorageReference = FirebaseStorage.getInstance().getReference();
        }
        return sStorageReference;
    }
}
