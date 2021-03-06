package com.dstu.myapplication.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dstu.myapplication.R;
import com.dstu.myapplication.fragments.EventFragment;
import com.dstu.myapplication.fragments.FacultieListFragment;
import com.dstu.myapplication.fragments.FeedbackFragment;
import com.dstu.myapplication.fragments.NewsListFragment;
import com.dstu.myapplication.fragments.ProfileFragment;
import com.dstu.myapplication.models.Abiturient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentTransaction ft;
    Menu menu_main;
    Fragment fragment;
    DatabaseReference myRef;
    TextView nav_header_fi;
    TextView nav_header_mail;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentSelector(0);
        nav_header_fi =(TextView)findViewById(R.id.nav_header_fi);
        nav_header_mail=(TextView)findViewById(R.id.nav_header_mail);

        updateNavHeader(navigationView.getHeaderView(0));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu_main=menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        fragmentSelector(item.getItemId());
        return true;
    }

    public void fragmentSelector(int id){
        ft = getSupportFragmentManager().beginTransaction();
        switch (id){
            case R.id.action_settings:

                break;
            case R.id.account:
                menu_main.getItem(2).setVisible(false);
                menu_main.getItem(4).setVisible(true);
                fragment = new ProfileFragment();

            break;
            case R.id.nav_portfolio:
                menu_main.getItem(2).setVisible(false);
                menu_main.getItem(4).setVisible(false);
                fragment = new ProfileFragment();
                break;
            case R.id.nav_news:
                menu_main.getItem(2).setVisible(true);
                menu_main.getItem(4).setVisible(false);
                fragment = new NewsListFragment();
            break;
            case R.id.nav_directions:
                menu_main.getItem(2).setVisible(true);
                menu_main.getItem(4).setVisible(false);
                fragment = new FacultieListFragment();
            break;
            case R.id.nav_ivent:
                menu_main.getItem(2).setVisible(true);
                menu_main.getItem(4).setVisible(false);
                fragment = new EventFragment();
                break;
            case R.id.nav_feedback:
                menu_main.getItem(2).setVisible(false);
                menu_main.getItem(4).setVisible(false);
                fragment = new FeedbackFragment();
                break;
            case R.id.nav_signup:
                startActivity(new Intent(MainActivity.this, PhoneAuthActivity.class));
                finish();
                break;
            case R.id.edit:
                startActivity(new Intent(MainActivity.this,ProfileEditActivity.class));
                menu_main.getItem(3).setVisible(false);
                menu_main.getItem(4).setVisible(true);
                break;
            case 0:
                fragment = new NewsListFragment();
                break;
        }
        ft.replace(R.id.fragment_container,fragment);
        ft.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fragmentSelector(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader(View header_view){
        if (Build.VERSION.SDK_INT >= 23){
            nav_header_fi = (TextView)header_view.findViewById(R.id.nav_header_fi);
            nav_header_mail = (TextView)header_view.findViewById(R.id.nav_header_mail);
        }
        myRef = FirebaseDatabase.getInstance().getReference();
        if(user!=null) {
            myRef.child("abiturients").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Abiturient abiturient = dataSnapshot.getValue(Abiturient.class);
                    nav_header_fi.setText(abiturient.getName() + " " + abiturient.getSurname());
                    nav_header_mail.setText(abiturient.getMail());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
