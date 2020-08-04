package com.example.version05;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.version05.Models.User;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Timetable  extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected static final String TAG = "ViewDatabase";

    String monday1,monday2,monday3,monday4,monday5,monday6,monday7,monday8;
    String tuesday1,tuesday2,tuesday3,tuesday4,tuesday5,tuesday6,tuesday7,tuesday8;
    String wednesday1,wednesday2,wednesday3,wednesday4,wednesday5,wednesday6,wednesday7,wednesday8;
    String thursday1,thursday2,thursday3,thursday4,thursday5,thursday6,thursday7,thursday8;
    String friday1,friday2,friday3,friday4,friday5,friday6,friday7,friday8;
    String saturday1,saturday2,saturday3,saturday4,saturday5,saturday6,saturday7,saturday8;
    protected TextView back;
    protected Activity mActivity;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    protected FirebaseDatabase mFirebaseDatabase;
    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener mAuthListener;
    protected DatabaseReference myRef;
    protected  String userID;
    private SectionsPagerAdapter sectionsPagerAdapter;


    static TextView mtext;
    protected ListView mListView2;
    protected ListView mListViewMonday;
@Nullable
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent intent = getIntent();
    monday1 =intent.getStringExtra("monday1");
    monday2 = intent.getStringExtra("monday2");
    monday3=intent.getStringExtra("monday3");
    monday4 =intent.getStringExtra("monday4");
    monday5 =intent.getStringExtra("monday5");
    monday6 =intent.getStringExtra("monday6");
    monday7=intent.getStringExtra("monday7");
    monday8=intent.getStringExtra("monday8");
    tuesday1=intent.getStringExtra("tuesday1");
    tuesday2 =intent.getStringExtra("tuesday2");
    tuesday3 =intent.getStringExtra("tuesday3");
    tuesday4 =intent.getStringExtra("tuesday4");
    tuesday5=intent.getStringExtra("tuesday5");
    tuesday6 =intent.getStringExtra("tuesday6");
    tuesday7=intent.getStringExtra("tuesday7");
    tuesday8=intent.getStringExtra("tuesday8");
    wednesday1=intent.getStringExtra("wednesday1");
    wednesday2=intent.getStringExtra("wednesday2");
    wednesday3=intent.getStringExtra("wednesday3");
    wednesday4=intent.getStringExtra("wednesday4");
    wednesday5=intent.getStringExtra("wednesday5");
    wednesday6=intent.getStringExtra("wednesday6");
    wednesday7 =intent.getStringExtra("wednesday7");
    wednesday8=intent.getStringExtra("wednesday8");
    thursday1=intent.getStringExtra("thursday1");
    thursday2=intent.getStringExtra("thursday2");
    thursday3=intent.getStringExtra("thursday3");
    thursday4 =intent.getStringExtra("thursday4");
    thursday5 =intent.getStringExtra("thursday5");
    thursday6=intent.getStringExtra("thursday6");
    thursday7=intent.getStringExtra("thursday7");
    thursday8 =intent.getStringExtra("thursday8");
    friday1=intent.getStringExtra("friday1");
    friday2 =intent.getStringExtra("friday2");
    friday3=intent.getStringExtra("friday3");
    friday4=intent.getStringExtra("friday4");
    friday5=intent.getStringExtra("friday5");
    friday6 =intent.getStringExtra("friday6");
    friday7=intent.getStringExtra("friday7");
    friday8=intent.getStringExtra("friday8");
    saturday1 =intent.getStringExtra("saturday1");
    saturday2 =intent.getStringExtra("saturday2");
    saturday3 =intent.getStringExtra("saturday3");
    saturday4=intent.getStringExtra("saturday4");
    saturday5=intent.getStringExtra("saturday5");
    saturday6=intent.getStringExtra("saturday6");
    saturday7 =intent.getStringExtra("saturday7");
    saturday8 =intent.getStringExtra("saturday8");
    mAuth = FirebaseAuth.getInstance();
    mFirebaseDatabase = FirebaseDatabase.getInstance();
    // s = "Govno";
    myRef = mFirebaseDatabase.getReference();
    FirebaseUser user = mAuth.getCurrentUser();
    userID = user.getUid();
    //  a.setS(userID);
    mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");

            }
            // ...
        }
    };

    mAuth.addAuthStateListener(mAuthListener);
    myRef.addValueEventListener(new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            showData(dataSnapshot);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    });
    setContentView(R.layout.timetable);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

   // BlankFragmentMonday fragment = BlankFragmentMonday.newInstance("example text");
    //BlankFragmentTuesday fragmentTuesday = new BlankFragmentTuesday();
    //getSupportFragmentManager().beginTransaction().replace(,fragment).commit();

    sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
    //sectionsPagerAdapter.setNu(s);
    //System.out.println(sectionsPagerAdapter.getNu());


    TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();
    navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    //declare the database reference object. This is what we use to access the database.
    //NOTE: Unless you are signed in, this will not be useable.






    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("я третий");

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




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.nav_profile:
                Intent h= new Intent(Timetable.this,Profile.class);
                startActivity(h);
                break;
            case R.id.nav_mark:
                Intent i= new Intent(Timetable.this,Mark.class);
                startActivity(i);
                break;
            case R.id.nav_timetable:
                Intent g= new Intent(Timetable.this,Timetable.class);
                startActivity(g);
                break;
            case R.id.nav_teacher:
                Intent s= new Intent(Timetable.this,Teacher.class);
                startActivity(s);
                break;
            case R.id.nav_exprofile:
                Intent main= new Intent(Timetable.this,MainActivity.class);
                startActivity(main);
                break;
            case R.id.nav_exit:
                finishAffinity();
                break;

            // after this lets start copying the above.
            // FOLLOW MEEEEE>>>
            //copy this now.
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
//String nu;

      //  public String getNu() {
       //     return nu;
       // }

       // public void setNu(String nu) {
        //    this.nu = nu;
        //}

        @StringRes
        private final int[] TAB_TITLES = new int[]{R.string.Понедельник, R.string.Вторник,
                R.string.Среда,R.string.Четверг,R.string.Пятница,R.string.Суббота};
        private final Context mContext;

        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);

            switch (position)
            {
                case 0:
                    BlankFragmentMonday blankFragmentMonday = new BlankFragmentMonday();
                    Bundle bundlemonday = new Bundle();
                    bundlemonday.putString("monday1",    monday1);
                    bundlemonday.putString("monday2",    monday2);
                    bundlemonday.putString("monday3",    monday3);
                    bundlemonday.putString("monday4",    monday4);
                    bundlemonday.putString("monday5",    monday5);
                    bundlemonday.putString("monday6",    monday6);
                    bundlemonday.putString("monday7",    monday7);
                    bundlemonday.putString("monday8",    monday8);
                    blankFragmentMonday.setArguments(bundlemonday);
                    return blankFragmentMonday;
                case 1:
                    BlankFragmentTuesday blankFragmentTuesday=new BlankFragmentTuesday();
                    Bundle bundletuesday = new Bundle();
                    bundletuesday.putString("tuesday1",    tuesday1);
                    bundletuesday.putString("tuesday2",    tuesday2);
                    bundletuesday.putString("tuesday3",    tuesday3);
                    bundletuesday.putString("tuesday4",    tuesday4);
                    bundletuesday.putString("tuesday5",    tuesday5);
                    bundletuesday.putString("tuesday6",    tuesday6);
                    bundletuesday.putString("tuesday7",    tuesday7);
                    bundletuesday.putString("tuesday8",    tuesday8);
                    blankFragmentTuesday.setArguments(bundletuesday);
                    return blankFragmentTuesday;
                case 2:
                    BlankFragmentWednesday blankFragmentWednesday = new BlankFragmentWednesday();
                    Bundle bundlewednesday = new Bundle();
                    bundlewednesday.putString("wednesday1",    wednesday1);
                    bundlewednesday.putString("wednesday2",    wednesday2);
                    bundlewednesday.putString("wednesday3",    wednesday3);
                    bundlewednesday.putString("wednesday4",    wednesday4);
                    bundlewednesday.putString("wednesday5",    wednesday5);
                    bundlewednesday.putString("wednesday6",    wednesday6);
                    bundlewednesday.putString("wednesday7",    wednesday7);
                    bundlewednesday.putString("wednesday8",    wednesday8);
                    blankFragmentWednesday.setArguments(bundlewednesday);
                    return blankFragmentWednesday;
                case 3:
                    BlankFragmentThursday blankFragmentThursday = new BlankFragmentThursday();
                    Bundle bundlethursday = new Bundle();
                    bundlethursday.putString("thursday1",    thursday1);
                    bundlethursday.putString("thursday2",    thursday2);
                    bundlethursday.putString("thursday3",    thursday3);
                    bundlethursday.putString("thursday4",    thursday4);
                    bundlethursday.putString("thursday5",    thursday5);
                    bundlethursday.putString("thursday6",    thursday6);
                    bundlethursday.putString("thursday7",    thursday7);
                    bundlethursday.putString("thursday8",    thursday8);
                    blankFragmentThursday.setArguments(bundlethursday);
                    return blankFragmentThursday;
                case 4:
                    BlankFragmentFriday blankFragmentFriday = new BlankFragmentFriday();
                    Bundle bundlefriday = new Bundle();
                    bundlefriday.putString("friday1",    friday1);
                    bundlefriday.putString("friday2",    friday2);
                    bundlefriday.putString("friday3",    friday3);
                    bundlefriday.putString("friday4",    friday4);
                    bundlefriday.putString("friday5",    friday5);
                    bundlefriday.putString("friday6",    friday6);
                    bundlefriday.putString("friday7",    friday7);
                    bundlefriday.putString("friday8",    friday8);
                    blankFragmentFriday.setArguments(bundlefriday);
                    return blankFragmentFriday;
                case 5:
                    BlankFragmentSaturday blankFragmentSaturday = new BlankFragmentSaturday();
                    Bundle bundlesaturday = new Bundle();
                    bundlesaturday.putString("saturday1",    saturday1);
                    bundlesaturday.putString("saturday2",    saturday2);
                    bundlesaturday.putString("saturday3",    saturday3);
                    bundlesaturday.putString("saturday4",    saturday4);
                    bundlesaturday.putString("saturday5",    saturday5);
                    bundlesaturday.putString("saturday6",    saturday6);
                    bundlesaturday.putString("saturday7",    saturday7);
                    bundlesaturday.putString("saturday8",    saturday8);
                    blankFragmentSaturday.setArguments(bundlesaturday);
                    return blankFragmentSaturday;
                default:
                    return null;
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mContext.getResources().getString(TAB_TITLES[position]);
        }

        @Override
        public int getCount() {
            // Show 6 total pages.
            return 6;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    protected void showData(DataSnapshot dataSnapshot) {
        // for(DataSnapshot ds : dataSnapshot.getChildren()){}
        User uInfo = new User();

        uInfo.setSurname(dataSnapshot.child("Users").child(userID).getValue(User.class).getSurname()); //set the surname
//a.setS(dataSnapshot.child("Users").child(userID).getValue(User.class).getSurname());

        //display all the information
        //  Log.d(TAG, "showData: surname: " + uInfo.getSurname());


        String blala = uInfo.getSurname();

        //   System.out.println(k);
        // back.setText(blala);
        //  System.out.println(a.getS());
       // s =  a.getS();//back.getText().toString();
        //sectionsPagerAdapter.setNu(a.getS());
      //  System.out.println(s);
    }

}