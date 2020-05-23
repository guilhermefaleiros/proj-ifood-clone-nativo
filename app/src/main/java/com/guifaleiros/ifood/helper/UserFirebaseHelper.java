package com.guifaleiros.ifood.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserFirebaseHelper {

    public static String getIdUser(){
        FirebaseAuth auth = FirebaseHelper.getFirebaseAuth();
        return auth.getCurrentUser().getUid();
    }

    public static FirebaseUser getCurrentUser(){
        FirebaseAuth user = FirebaseHelper.getFirebaseAuth();
        return user.getCurrentUser();
    }

    public static Boolean updateUserType(String type){
        try{
            FirebaseUser user = getCurrentUser();
            UserProfileChangeRequest profile = new UserProfileChangeRequest
                    .Builder()
                    .setDisplayName(type)
                    .build();

            user.updateProfile(profile);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
