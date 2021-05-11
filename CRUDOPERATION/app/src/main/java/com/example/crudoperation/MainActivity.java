package com.example.crudoperation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.crudoperation.Model.Notes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton btnAdd;
    private RecyclerView recyclerView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Notes");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnAdd = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.recycler_View);
        //Button click
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddNote();
                readData();
            }

            private void showDialogAddNote() {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                dialog.setContentView(R.layout.dialog_add_note);

                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);
                WindowManager.LayoutParams p = new WindowManager.LayoutParams();
                p.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
                p.width  = WindowManager.LayoutParams.MATCH_PARENT;
                p.height  = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes(p);
                //

                ImageButton btnClose = dialog.findViewById(R.id.btn_close);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                EditText et1 = dialog.findViewById(R.id.note);
                Button btnAdd = dialog.findViewById(R.id.btn_add);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(et1.getText())){
                            et1.setError("This field is required");
                        }else{
                            addDataToFirebase(et1.getText().toString());
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }

            private void addDataToFirebase(String text){
                String id = myRef.push().getKey();
                Notes notes = new Notes(id,text);

                myRef.child(id).setValue(notes).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Successfully Saved", Toast.LENGTH_SHORT).show();
                    }
                });

            }
            private void readData(){
                //Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and
                        //whenever data at this location is updated
                        String value = dataSnapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("TAG", "Falid to read Value", error.toException());

                    }
                });
            }
        });
    }
}