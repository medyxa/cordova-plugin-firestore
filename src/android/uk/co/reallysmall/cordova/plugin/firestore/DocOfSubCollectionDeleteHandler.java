package uk.co.reallysmall.cordova.plugin.firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

public class DocOfSubCollectionDeleteHandler implements ActionHandler {

    private FirestorePlugin firestorePlugin;

    public DocOfSubCollectionDeleteHandler(FirestorePlugin firestorePlugin) {
        this.firestorePlugin = firestorePlugin;
    }

    @Override
    public boolean handle(JSONArray args, final CallbackContext callbackContext) {
        try {
            final String collectionPath = args.getString(0);
            final String doc = args.getString(1);
            final String subCollection = args.getString(2);
            final String docOfSubCollectionId = args.getString(3);

            FirestoreLog.d(FirestorePlugin.TAG, "Deleting document of sub collection");

            try {
                DocumentReference documentRef = firestorePlugin.getDatabase().collection(collectionPath).document(doc).collection(subCollection).document(docOfSubCollectionId);
                FirestoreLog.d(FirestorePlugin.TAG, "Get for document of sub collection" + collectionPath + "/" + doc);

                documentRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callbackContext.success(0);
                        FirestoreLog.d(FirestorePlugin.TAG, "Successfully deleted document of sub collection");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FirestoreLog.w(FirestorePlugin.TAG, "Error deleting document of sub collection", e);
                        callbackContext.error(e.getMessage());
                    }
                });
            } catch (Exception e) {
                FirestoreLog.e(FirestorePlugin.TAG, "Error processing document of sub collection delete in thread", e);
                callbackContext.error(e.getMessage());
            }

        } catch (JSONException e) {
            FirestoreLog.e(FirestorePlugin.TAG, "Error processing document of sub collection delete", e);
            callbackContext.error(e.getMessage());
        }

        return true;
    }
}