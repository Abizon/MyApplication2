package com.dstu.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dstu.myapplication.R;
import com.dstu.myapplication.models.Abiturient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileInformationFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_profile, container, false);

        //ImageView imageView = (ImageView)view.findViewById(R.id.imageView5);
        //Glide.with(this).load("https://ec.donstu.ru/site/ci/base/captcha").into(imageView);
        myRef = FirebaseDatabase.getInstance().getReference();
        final TextView fi = (TextView)view.findViewById(R.id.textView31);
        TextView school = (TextView)view.findViewById(R.id.textView12);
        TextView email = (TextView)view.findViewById(R.id.textView14);
        TextView date = (TextView)view.findViewById(R.id.textView16);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                try{

                    //listProducts.add(dataSnapshot.getValue(<Класс объекта со полями как в БД>.class));
                    //productListAdapter = new ProductListAdapter(listProducts);
                    //rv.setAdapter(productListAdapter);
                }
                catch (Exception e){

            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            fi.setText(dataSnapshot.child(user.getUid()).getValue(Abiturient.class).getName());
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            //listProducts.remove(dataSnapshot.getValue(<Класс объекта со полями как в БД>.class));
            //productListAdapter=new ProductListAdapter(listProducts);
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


        Query recentPostsQuery = myRef.limitToLast(100); // Устанавливает максимальное количество элементов для возврата из конца упорядоченного списка результатов

        recentPostsQuery.addChildEventListener(childEventListener);//задаем метод, который будет отвечать за обработку полученных данных


        //String string = myRef.child("abiturients").child(user.getUid()).child("name");
            return view;
        }
}
