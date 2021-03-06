package com.dstu.myapplication.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dstu.myapplication.R;
import com.dstu.myapplication.models.Abiturient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileEditActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference myRef;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar2);
        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Button button = (Button) findViewById(R.id.button9);
        myRef = FirebaseDatabase.getInstance().getReference();
        final EditText name = (EditText)findViewById(R.id.textView7);
        final EditText surname = (EditText)findViewById(R.id.textView12);
        final EditText mail = (EditText)findViewById(R.id.textView13);
        final EditText password = (EditText)findViewById(R.id.textView14);
        final EditText verificate_password = (EditText)findViewById(R.id.textView16);
        final EditText date = (EditText)findViewById(R.id.textView17);
        final EditText city = (EditText) findViewById(R.id.editText3);
        final EditText directions = (EditText) findViewById(R.id.editText4);
        final EditText school = (EditText)findViewById(R.id.textView18);
        myRef.child("abiturients").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Abiturient abiturient = dataSnapshot.getValue(Abiturient.class);
                name.setText(abiturient.getName());
                surname.setText(abiturient.getSurname());
                mail.setText(abiturient.getMail());
                date.setText(abiturient.getDate());
                city.setText(abiturient.getCity());
                school.setText(abiturient.getSchool());
                directions.setText(abiturient.getDirections());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("abiturients").child(user.getUid()).child("name").setValue(name.getText().toString());
                myRef.child("abiturients").child(user.getUid()).child("surname").setValue(surname.getText().toString());
                myRef.child("abiturients").child(user.getUid()).child("mail").setValue(mail.getText().toString());
                myRef.child("abiturients").child(user.getUid()).child("password").setValue(password.getText().toString());
                myRef.child("abiturients").child(user.getUid()).child("verificate_password").setValue(verificate_password.getText().toString());
                myRef.child("abiturients").child(user.getUid()).child("date").setValue(date.getText().toString());
                myRef.child("abiturients").child(user.getUid()).child("city").setValue(city.getText().toString());
                myRef.child("abiturients").child(user.getUid()).child("school").setValue(school.getText().toString());
                myRef.child("abiturients").child(user.getUid()).child("directions").setValue(directions.getText().toString());
                Toast.makeText(ProfileEditActivity.this, "Данные сохранены", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            //Отмена
            finish();
        }
        return true;
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
