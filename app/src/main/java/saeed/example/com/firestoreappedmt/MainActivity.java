package saeed.example.com.firestoreappedmt;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
         db = FirebaseFirestore.getInstance();

         //init ui
        textView = findViewById( R.id.textView );

         //init map

//        addRealTimeData();

        deleteInfo();

//addData();
//        updateInfo();

//        deleteInfo();


//        readFireStore();
//        readFireStoreSaveToMyClass();

    }

    private void addRealTimeData() {
        final DocumentReference documentReference = db.collection( "userInfor" ).document("personal info");
        documentReference.addSnapshotListener( new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
            if(documentSnapshot != null)
            {
              textView.setText(documentSnapshot.getData().toString());
            }
            else
            {
                Toast.makeText( MainActivity.this,  e.getMessage(), Toast.LENGTH_LONG ).show();
            }
            }
        } );
    }

    private void deleteInfo() {

        DocumentReference documentReference = db.collection( "userInfor" ).document("personal info");
        documentReference.delete().addOnSuccessListener( new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            Toast.makeText( MainActivity.this, "has been successfully deleted", Toast.LENGTH_LONG).show();
            }
        } );

    }

    private void updateInfo()
    {
        DocumentReference documentReference = db.collection( "userInfor" ).document("personal info");
        documentReference.update( "name", "sara" ).addOnSuccessListener( new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText( MainActivity.this, "the update has been successful", Toast.LENGTH_LONG ).show();
            }
        } );


    }

    private void readFireStoreSaveToMyClass()
    {
        final DocumentReference documentReference =  db.collection( "userInfor" ).document("personal info");
        documentReference.get().addOnSuccessListener( new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                AddressBook addressBook = documentSnapshot.toObject( AddressBook.class );

                StringBuilder stringBuilder = new StringBuilder(  );

                stringBuilder.append( "name: " ).append(addressBook.getName(  ));
                stringBuilder.append( "\nfamily: " ).append( addressBook.getFamily(  ) );
                stringBuilder.append( "\nemailAddress: " ).append( addressBook.getEmailAddress(  ) );

                textView.setText( stringBuilder );

            }
        } );



    }


    //method declaration
    private void readFireStore()
    {
        final DocumentReference documentReference = db.collection( "userInfor" ).document("personal info");
         documentReference.get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                 if(task.isSuccessful())
                 {
                     //
                    DocumentSnapshot documentSnapshot =   task.getResult();

                    StringBuilder stringBuilder = new StringBuilder(  );

                    stringBuilder.append( "name: " ).append(documentSnapshot.getString( "name" ));
                    stringBuilder.append( "\nfamily: " ).append( documentSnapshot.getString( "family" ) );
                    stringBuilder.append( "\nemailAddress: " ).append( documentSnapshot.getString( "emailAddress" ) );

                    textView.setText( stringBuilder );






                 }
                 else
                 {

                 }

             }
         } );

    }

    private void addData()
    {
        Map<String, Object> info = new HashMap<>(  );

        info.put( "name", "saeed" );
        info.put("family", "jafary");
        info.put("emailAddress", "saeed@example.com");

        // call the methods to push the information up to the server, calling collection and document method
        db.collection("userInfor").document("personal info").set( info ).addOnSuccessListener( new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText( MainActivity.this , "the info has been successfully sent to the server", Toast.LENGTH_LONG ).show();

            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("the error is ", e.getMessage());

            }
        } );
    }
}
