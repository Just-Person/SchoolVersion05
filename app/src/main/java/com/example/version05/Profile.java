package com.example.version05;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.version05.Models.Day;
import com.example.version05.Models.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "ViewDatabase";
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;
    private ListView mListView;
    String monday1,monday2,monday3,monday4,monday5,monday6,monday7,monday8;
    String tuesday1,tuesday2,tuesday3,tuesday4,tuesday5,tuesday6,tuesday7,tuesday8;
    String wednesday1,wednesday2,wednesday3,wednesday4,wednesday5,wednesday6,wednesday7,wednesday8;
    String thursday1,thursday2,thursday3,thursday4,thursday5,thursday6,thursday7,thursday8;
    String friday1,friday2,friday3,friday4,friday5,friday6,friday7,friday8;
    String saturday1,saturday2,saturday3,saturday4,saturday5,saturday6,saturday7,saturday8;
    private ListView mListView2;

@Nullable
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //We dont need this.




        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mListView = (ListView) findViewById(R.id.nameList);
        mListView2 = (ListView) findViewById(R.id.ClassList);

        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be useable.
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private void showData(DataSnapshot dataSnapshot) {
       // for(DataSnapshot ds : dataSnapshot.getChildren()){}
            User uInfo = new User();
            Day tuesday = new Day();
            uInfo.setSurname(dataSnapshot.child("Users").child(userID).getValue(User.class).getSurname()); //set the surname
            uInfo.setName(dataSnapshot.child("Users").child(userID).getValue(User.class).getName()); //set the name
            uInfo.setPatronymic(dataSnapshot.child("Users").child(userID).getValue(User.class).getPatronymic()); //set the patronymic

            //display all the information
            Log.d(TAG, "showData: surname: " + uInfo.getSurname());
            Log.d(TAG, "showData: name: " + uInfo.getName());
            Log.d(TAG, "showData: patronymic: " + uInfo.getPatronymic());

            ArrayList<String> array  = new ArrayList<>();
            array.add("Фамилия: " + uInfo.getSurname());
            array.add("Имя: " + uInfo.getName());
            array.add("Отчество: " + uInfo.getPatronymic());
            ArrayAdapter adapter = new ArrayAdapter(this,R.layout.mytextview,array);
            mListView.setAdapter(adapter);
            uInfo.setEmail(dataSnapshot.child("Users").child(userID).getValue(User.class).getEmail()); //set the email
            uInfo.setKl(dataSnapshot.child("Users").child(userID).getValue(User.class).getKl()); //set the klass
            uInfo.setLetter(dataSnapshot.child("Users").child(userID).getValue(User.class).getLetter()); //set the letter
            uInfo.setAddress(dataSnapshot.child("Users").child(userID).getValue(User.class).getAddress()); //set the address
            uInfo.setPhone(dataSnapshot.child("Users").child(userID).getValue(User.class).getPhone()); //set the phone
            Log.d(TAG, "showData: email: " + uInfo.getEmail());
            Log.d(TAG, "showData: klass: " + uInfo.getKl());
            Log.d(TAG, "showData: letter: " + uInfo.getLetter());
            Log.d(TAG, "showData: address: " + uInfo.getAddress());
            Log.d(TAG, "showData: phone: " + uInfo.getPhone());
            ArrayList<String> array2  = new ArrayList<>();
            array2.add("Почта: " + uInfo.getEmail());
            array2.add("Класс: " + uInfo.getKl());
            array2.add("Буква: " + uInfo.getLetter());
            array2.add("Адрес: " + uInfo.getAddress());
            array2.add("Телефон родителей: " + uInfo.getPhone());
            ArrayAdapter adapter1 = new ArrayAdapter(this,R.layout.mytextview,array2);
            mListView2.setAdapter(adapter1);
        monday1 = dataSnapshot.child("Расписание").child("11").child("Б").child("Понедельник").child("1").getValue(String.class);
        if (uInfo.getKl().equals("1")){
            if (uInfo.getLetter().equals("А"))
            {
                setTimeTable1A(dataSnapshot);
            } else if (uInfo.getLetter().equals("Б"))
            {
                setTimeTable1B(dataSnapshot);
            } else if (uInfo.getLetter().equals("В"))
            {
                setTimeTable1C(dataSnapshot);
            }
            else {
                setTimeTableNotFound(dataSnapshot);
            }
        } else
        if (uInfo.getKl().equals("2")){
            if (uInfo.getLetter().equals("А"))
            {
                setTimeTable2A(dataSnapshot);
            } else if (uInfo.getLetter().equals("Б"))
            {
                setTimeTable2B(dataSnapshot);
            } else if (uInfo.getLetter().equals("В"))
            {
                setTimeTable2C(dataSnapshot);}
            else {
                setTimeTableNotFound(dataSnapshot);
            }
        } else
        if (uInfo.getKl().equals("3")){
            if (uInfo.getLetter().equals("А"))
            {
                setTimeTable3A(dataSnapshot);
            } else if (uInfo.getLetter().equals("Б"))
            {
                setTimeTable3B(dataSnapshot);
            } else if (uInfo.getLetter().equals("В"))
            {
                setTimeTable3C(dataSnapshot);
            }
            else {
                setTimeTableNotFound(dataSnapshot);
            }
        } else
        if (uInfo.getKl().equals("4")){
            if (uInfo.getLetter().equals("А"))
            {
               setTimeTable4A(dataSnapshot);
            } else if (uInfo.getLetter().equals("Б"))
            {
               setTimeTable4B(dataSnapshot);
            } else if (uInfo.getLetter().equals("В"))
            {
                setTimeTable4C(dataSnapshot);
            }
            else {
                setTimeTableNotFound(dataSnapshot);
            }
        } else
        if (uInfo.getKl().equals("5")){
            if (uInfo.getLetter().equals("А"))
            {
                setTimeTable5A(dataSnapshot);
            } else if (uInfo.getLetter().equals("Б"))
            {
                setTimeTable5B(dataSnapshot);
            } else if (uInfo.getLetter().equals("В"))
            {
                setTimeTable5C(dataSnapshot);
            }
            else {
                setTimeTableNotFound(dataSnapshot);
            }
        } else
        if (uInfo.getKl().equals("6")){
            if (uInfo.getLetter().equals("А"))
            {
                setTimeTable6A(dataSnapshot);
            } else if (uInfo.getLetter().equals("Б"))
            {
                setTimeTable6B(dataSnapshot);
            } else if (uInfo.getLetter().equals("В"))
            {
                setTimeTable6C(dataSnapshot);
            }
            else {
                setTimeTableNotFound(dataSnapshot);
            }
        } else
        if (uInfo.getKl().equals("7")){
            if (uInfo.getLetter().equals("А"))
            {
                setTimeTable7A(dataSnapshot);
            } else if (uInfo.getLetter().equals("Б"))
            {
                setTimeTable7B(dataSnapshot);
            } else if (uInfo.getLetter().equals("В"))
            {
                setTimeTable7C(dataSnapshot);
            }
            else {
                setTimeTableNotFound(dataSnapshot);
            }
        } else
        if (uInfo.getKl().equals("8")){
            if (uInfo.getLetter().equals("А"))
            {
                setTimeTable8A(dataSnapshot);
            } else if (uInfo.getLetter().equals("Б"))
            {
                setTimeTable8B(dataSnapshot);
            } else if (uInfo.getLetter().equals("В"))
            {
                setTimeTable8C(dataSnapshot);
            }
            else {
                monday1 ="Не найдено";
                monday2 = "Не найдено";
                monday3 = "Не найдено";
                monday4 = "Не найдено";
                monday5 = "Не найдено";
                monday6 = "Не найдено";
                monday7 = "Не найдено";
                monday8 = "Не найдено";
                tuesday1 = "Не найдено";
                tuesday2 ="Не найдено";
                tuesday3 = "Не найдено";
                tuesday4 = "Не найдено";
                tuesday5 = "Не найдено";
                tuesday6 = "Не найдено";
                tuesday7 = "Не найдено";
                tuesday8 = "Не найдено";
                wednesday1 = "Не найдено";
                wednesday2 = "Не найдено";
                wednesday3 = "Не найдено";
                wednesday4 = "Не найдено";
                wednesday5 = "Не найдено";
                wednesday6 = "Не найдено";
                wednesday7 = "Не найдено";
                wednesday8 = "Не найдено";
                thursday1 = "Не найдено";
                thursday2 ="Не найдено";
                thursday3 = "Не найдено";
                thursday4 = "Не найдено";
                thursday5 = "Не найдено";
                thursday6 = "Не найдено";
                thursday7 = "Не найдено";
                thursday8 = "Не найдено";
                friday1 = "Не найдено";
                friday2 = "Не найдено";
                friday3 = "Не найдено";
                friday4 = "Не найдено";
                friday5 = "Не найдено";
                friday6 = "Не найдено";
                friday7 = "Не найдено";
                friday8 = "Не найдено";
                saturday1 = "Не найдено";
                saturday2 ="Не найдено";
                saturday3 = "Не найдено";
                saturday4 = "Не найдено";
                saturday5 ="Не найдено";
                saturday6 ="Не найдено";
                saturday7 = "Не найдено";
                saturday8 = "Не найдено";
            }
        } else
        if (uInfo.getKl().equals("9")){
            if (uInfo.getLetter().equals("А"))
            {
                setTimeTable9A(dataSnapshot);
            } else if (uInfo.getLetter().equals("Б"))
            {
                setTimeTable9B(dataSnapshot);
            } else if (uInfo.getLetter().equals("В"))
            {
                setTimeTable9C(dataSnapshot);
            }
            else {
                setTimeTableNotFound(dataSnapshot);
            }
        } else
        if (uInfo.getKl().equals("10")){
            if (uInfo.getLetter().equals("А"))
            {
                setTimeTable10A(dataSnapshot);
            } else if (uInfo.getLetter().equals("Б"))
            {
                setTimeTable10B(dataSnapshot);
            } else if (uInfo.getLetter().equals("В"))
            {
                setTimeTable10C(dataSnapshot);
            }
            else {
                setTimeTableNotFound(dataSnapshot);
            }
        } else
        if (uInfo.getKl().equals("11")){
            if (uInfo.getLetter().equals("А"))
            {
                setTimeTable11A(dataSnapshot);
            } else if (uInfo.getLetter().equals("Б"))
            {
                setTimeTable11B(dataSnapshot);
            } else if (uInfo.getLetter().equals("В"))
            {
                setTimeTable11C(dataSnapshot);
            }
            else {
                monday1 ="Не найдено";
                monday2 = "Не найдено";
                monday3 = "Не найдено";
                monday4 = "Не найдено";
                monday5 = "Не найдено";
                monday6 = "Не найдено";
                monday7 = "Не найдено";
                monday8 = "Не найдено";
                tuesday1 = "Не найдено";
                tuesday2 ="Не найдено";
                tuesday3 = "Не найдено";
                tuesday4 = "Не найдено";
                tuesday5 = "Не найдено";
                tuesday6 = "Не найдено";
                tuesday7 = "Не найдено";
                tuesday8 = "Не найдено";
                wednesday1 = "Не найдено";
                wednesday2 = "Не найдено";
                wednesday3 = "Не найдено";
                wednesday4 = "Не найдено";
                wednesday5 = "Не найдено";
                wednesday6 = "Не найдено";
                wednesday7 = "Не найдено";
                wednesday8 = "Не найдено";
                thursday1 = "Не найдено";
                thursday2 ="Не найдено";
                thursday3 = "Не найдено";
                thursday4 = "Не найдено";
                thursday5 = "Не найдено";
                thursday6 = "Не найдено";
                thursday7 = "Не найдено";
                thursday8 = "Не найдено";
                friday1 = "Не найдено";
                friday2 = "Не найдено";
                friday3 = "Не найдено";
                friday4 = "Не найдено";
                friday5 = "Не найдено";
                friday6 = "Не найдено";
                friday7 = "Не найдено";
                friday8 = "Не найдено";
                saturday1 = "Не найдено";
                saturday2 ="Не найдено";
                saturday3 = "Не найдено";
                saturday4 = "Не найдено";
                saturday5 ="Не найдено";
                saturday6 ="Не найдено";
                saturday7 = "Не найдено";
                saturday8 = "Не найдено";
            }
        } else
            {
                setTimeTableNotFound(dataSnapshot);
            }

System.out.println(monday1);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
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
                Intent h= new Intent(Profile.this,Profile.class);
                startActivity(h);
                break;
            case R.id.nav_mark:
               // Intent i= new Intent(Profile.this,Mark.class);
                //i.putExtra("name",s);
                startActivity(new Intent(Profile.this,Mark.class));
                break;
            case R.id.nav_timetable:
                Intent timetable = new Intent( Profile.this,Timetable.class);
                timetable.putExtra("monday1",monday1);
                timetable.putExtra("monday2",monday2);
                timetable.putExtra("monday3",monday3);
                timetable.putExtra("monday4",monday4);
                timetable.putExtra("monday5",monday5);
                timetable.putExtra("monday6",monday6);
                timetable.putExtra("monday7",monday7);
                timetable.putExtra("monday8",monday8);
                timetable.putExtra("tuesday1",tuesday1);
                timetable.putExtra("tuesday2",tuesday2);
                timetable.putExtra("tuesday3",tuesday3);
                timetable.putExtra("tuesday4",tuesday4);
                timetable.putExtra("tuesday5",tuesday5);
                timetable.putExtra("tuesday6",tuesday6);
                timetable.putExtra("tuesday7",tuesday7);
                timetable.putExtra("tuesday8",tuesday8);
                timetable.putExtra("wednesday1",wednesday1);
                timetable.putExtra("wednesday2",wednesday2);
                timetable.putExtra("wednesday3",wednesday3);
                timetable.putExtra("wednesday4",wednesday4);
                timetable.putExtra("wednesday5",wednesday5);
                timetable.putExtra("wednesday6",wednesday6);
                timetable.putExtra("wednesday7",wednesday7);
                timetable.putExtra("wednesday8",wednesday8);
                timetable.putExtra("thursday1",thursday1);
                timetable.putExtra("thursday2",thursday2);
                timetable.putExtra("thursday3",thursday3);
                timetable.putExtra("thursday4",thursday4);
                timetable.putExtra("thursday5",thursday5);
                timetable.putExtra("thursday6",thursday6);
                timetable.putExtra("thursday7",thursday7);
                timetable.putExtra("thursday8",thursday8);
                timetable.putExtra("friday1",friday1);
                timetable.putExtra("friday2",friday2);
                timetable.putExtra("friday3",friday3);
                timetable.putExtra("friday4",friday4);
                timetable.putExtra("friday5",friday5);
                timetable.putExtra("friday6",friday6);
                timetable.putExtra("friday7",friday7);
                timetable.putExtra("friday8",friday8);
                timetable.putExtra("saturday1",saturday1);
                timetable.putExtra("saturday2",saturday2);
                timetable.putExtra("saturday3",saturday3);
                timetable.putExtra("saturday4",saturday4);
                timetable.putExtra("saturday5",saturday5);
                timetable.putExtra("saturday6",saturday6);
                timetable.putExtra("saturday7",saturday7);
                timetable.putExtra("saturday8",saturday8);
                startActivity(timetable);
                break;
            case R.id.nav_teacher:
                Intent s= new Intent(Profile.this,Teacher.class);
                startActivity(s);
                break;
            case R.id.nav_exprofile:
                Intent main= new Intent(Profile.this,MainActivity.class);
                startActivity(main);
                break;
            case R.id.nav_exit:
                finish();
                break;

            // after this lets start copying the above.
            // FOLLOW MEEEEE>>>
            //copy this now.
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    private void setTimeTable1A(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("1").child("А").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("1").child("А").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("1").child("А").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("1").child("А").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("1").child("А").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("1").child("А").child("Понедельник").child("6").getValue(String.class);
        monday7 = " ";
        monday8 = " ";
        tuesday1 = dataSnapshot.child("Расписание").child("1").child("А").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("1").child("А").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("1").child("А").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("1").child("А").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("1").child("А").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("1").child("А").child("Вторник").child("6").getValue(String.class);
        tuesday7 = " ";
        tuesday8 = " ";
        wednesday1 = dataSnapshot.child("Расписание").child("1").child("А").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("1").child("А").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("1").child("А").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("1").child("А").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("1").child("А").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("1").child("А").child("Среда").child("6").getValue(String.class);
        wednesday7 = " ";
        wednesday8 = " ";
        thursday1 = dataSnapshot.child("Расписание").child("1").child("А").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("1").child("А").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("1").child("А").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("1").child("А").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("1").child("А").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("1").child("А").child("Четверг").child("6").getValue(String.class);
        thursday7 = " ";
        thursday8 = " ";
        friday1 = dataSnapshot.child("Расписание").child("1").child("А").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("1").child("А").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("1").child("А").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("1").child("А").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("1").child("А").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("1").child("А").child("Пятница").child("6").getValue(String.class);
        friday7 = " ";
        friday8 = " ";
        saturday1 = " ";
        saturday2 = " ";
        saturday3 = " ";
        saturday4 = " ";
        saturday5 = " ";
        saturday6 = " ";
        saturday7 = " ";
        saturday8 = " ";
    }
    private void setTimeTable1B(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("1").child("Б").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("1").child("Б").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("1").child("Б").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("1").child("Б").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("1").child("Б").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("1").child("Б").child("Понедельник").child("6").getValue(String.class);
        monday7 = " ";
        monday8 = " ";
        tuesday1 = dataSnapshot.child("Расписание").child("1").child("Б").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("1").child("Б").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("1").child("Б").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("1").child("Б").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("1").child("Б").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("1").child("Б").child("Вторник").child("6").getValue(String.class);
        tuesday7 = " ";
        tuesday8 = " ";
        wednesday1 = dataSnapshot.child("Расписание").child("1").child("Б").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("1").child("Б").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("1").child("Б").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("1").child("Б").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("1").child("Б").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("1").child("Б").child("Среда").child("6").getValue(String.class);
        wednesday7 = " ";
        wednesday8 = " ";
        thursday1 = dataSnapshot.child("Расписание").child("1").child("Б").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("1").child("Б").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("1").child("Б").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("1").child("Б").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("1").child("Б").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("1").child("Б").child("Четверг").child("6").getValue(String.class);
        thursday7 = " ";
        thursday8 = " ";
        friday1 = dataSnapshot.child("Расписание").child("1").child("Б").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("1").child("Б").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("1").child("Б").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("1").child("Б").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("1").child("Б").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("1").child("Б").child("Пятница").child("6").getValue(String.class);
        friday7 = " ";
        friday8 = " ";
        saturday1 = " ";
        saturday2 = " ";
        saturday3 = " ";
        saturday4 = " ";
        saturday5 = " ";
        saturday6 = " ";
        saturday7 = " ";
        saturday8 = " ";
    }
    private void setTimeTable1C(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("1").child("В").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("1").child("В").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("1").child("В").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("1").child("В").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("1").child("В").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("1").child("В").child("Понедельник").child("6").getValue(String.class);
        monday7 = " ";
        monday8 = " ";
        tuesday1 = dataSnapshot.child("Расписание").child("1").child("В").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("1").child("В").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("1").child("В").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("1").child("В").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("1").child("В").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("1").child("В").child("Вторник").child("6").getValue(String.class);
        tuesday7 = " ";
        tuesday8 = " ";
        wednesday1 = dataSnapshot.child("Расписание").child("1").child("В").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("1").child("В").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("1").child("В").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("1").child("В").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("1").child("В").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("1").child("В").child("Среда").child("6").getValue(String.class);
        wednesday7 = " ";
        wednesday8 = " ";
        thursday1 = dataSnapshot.child("Расписание").child("1").child("В").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("1").child("В").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("1").child("В").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("1").child("В").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("1").child("В").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("1").child("В").child("Четверг").child("6").getValue(String.class);
        thursday7 = " ";
        thursday8 = " ";
        friday1 = dataSnapshot.child("Расписание").child("1").child("В").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("1").child("В").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("1").child("В").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("1").child("В").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("1").child("В").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("1").child("В").child("Пятница").child("6").getValue(String.class);
        friday7 = " ";
        friday8 = " ";
        saturday1 = " ";
        saturday2 = " ";
        saturday3 = " ";
        saturday4 = " ";
        saturday5 = " ";
        saturday6 = " ";
        saturday7 = " ";
        saturday8 = " ";}
    private void setTimeTable2A(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("2").child("А").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("2").child("А").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("2").child("А").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("2").child("А").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("2").child("А").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("2").child("А").child("Понедельник").child("6").getValue(String.class);
        monday7 = " ";
        monday8 = " ";
        tuesday1 = dataSnapshot.child("Расписание").child("2").child("А").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("2").child("А").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("2").child("А").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("2").child("А").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("2").child("А").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("2").child("А").child("Вторник").child("6").getValue(String.class);
        tuesday7 = " ";
        tuesday8 = " ";
        wednesday1 = dataSnapshot.child("Расписание").child("2").child("А").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("2").child("А").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("2").child("А").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("2").child("А").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("2").child("А").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("2").child("А").child("Среда").child("6").getValue(String.class);
        wednesday7 = " ";
        wednesday8 = " ";
        thursday1 = dataSnapshot.child("Расписание").child("2").child("А").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("2").child("А").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("2").child("А").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("2").child("А").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("2").child("А").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("2").child("А").child("Четверг").child("6").getValue(String.class);
        thursday7 = " ";
        thursday8 = " ";
        friday1 = dataSnapshot.child("Расписание").child("2").child("А").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("2").child("А").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("2").child("А").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("2").child("А").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("2").child("А").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("2").child("А").child("Пятница").child("6").getValue(String.class);
        friday7 = " ";
        friday8 = " ";
        saturday1 = " ";
        saturday2 = " ";
        saturday3 = " ";
        saturday4 = " ";
        saturday5 = " ";
        saturday6 = " ";
        saturday7 = " ";
        saturday8 = " ";
    }
    private void setTimeTable2B(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("2").child("Б").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("2").child("Б").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("2").child("Б").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("2").child("Б").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("2").child("Б").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("2").child("Б").child("Понедельник").child("6").getValue(String.class);
        monday7 = " ";
        monday8 = " ";
        tuesday1 = dataSnapshot.child("Расписание").child("2").child("Б").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("2").child("Б").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("2").child("Б").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("2").child("Б").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("2").child("Б").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("2").child("Б").child("Вторник").child("6").getValue(String.class);
        tuesday7 = " ";
        tuesday8 = " ";
        wednesday1 = dataSnapshot.child("Расписание").child("2").child("Б").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("2").child("Б").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("2").child("Б").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("2").child("Б").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("2").child("Б").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("2").child("Б").child("Среда").child("6").getValue(String.class);
        wednesday7 = " ";
        wednesday8 = " ";
        thursday1 = dataSnapshot.child("Расписание").child("2").child("Б").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("2").child("Б").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("2").child("Б").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("2").child("Б").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("2").child("Б").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("2").child("Б").child("Четверг").child("6").getValue(String.class);
        thursday7 = " ";
        thursday8 = " ";
        friday1 = dataSnapshot.child("Расписание").child("2").child("Б").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("2").child("Б").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("2").child("Б").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("2").child("Б").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("2").child("Б").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("2").child("Б").child("Пятница").child("6").getValue(String.class);
        friday7 = " ";
        friday8 = " ";
        saturday1 = " ";
        saturday2 = " ";
        saturday3 = " ";
        saturday4 = " ";
        saturday5 = " ";
        saturday6 = " ";
        saturday7 = " ";
        saturday8 = " ";}
    private void setTimeTable2C(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("2").child("В").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("2").child("В").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("2").child("В").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("2").child("В").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("2").child("В").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("2").child("В").child("Понедельник").child("6").getValue(String.class);
        monday7 = " ";
        monday8 = " ";
        tuesday1 = dataSnapshot.child("Расписание").child("2").child("В").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("2").child("В").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("2").child("В").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("2").child("В").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("2").child("В").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("2").child("В").child("Вторник").child("6").getValue(String.class);
        tuesday7 = " ";
        tuesday8 = " ";
        wednesday1 = dataSnapshot.child("Расписание").child("2").child("В").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("2").child("В").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("2").child("В").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("2").child("В").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("2").child("В").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("2").child("В").child("Среда").child("6").getValue(String.class);
        wednesday7 = " ";
        wednesday8 = " ";
        thursday1 = dataSnapshot.child("Расписание").child("2").child("В").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("2").child("В").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("2").child("В").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("2").child("В").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("2").child("В").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("2").child("В").child("Четверг").child("6").getValue(String.class);
        thursday7 = " ";
        thursday8 = " ";
        friday1 = dataSnapshot.child("Расписание").child("2").child("В").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("2").child("В").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("2").child("В").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("2").child("В").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("2").child("В").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("2").child("В").child("Пятница").child("6").getValue(String.class);
        friday7 = " ";
        friday8 = " ";
        saturday1 = " ";
        saturday2 = " ";
        saturday3 = " ";
        saturday4 = " ";
        saturday5 = " ";
        saturday6 = " ";
        saturday7 = " ";
        saturday8 = " ";
    }
    private void setTimeTable3A(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("3").child("А").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("3").child("А").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("3").child("А").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("3").child("А").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("3").child("А").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("3").child("А").child("Понедельник").child("6").getValue(String.class);
        monday7 = " ";
        monday8 = " ";
        tuesday1 = dataSnapshot.child("Расписание").child("3").child("А").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("3").child("А").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("3").child("А").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("3").child("А").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("3").child("А").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("3").child("А").child("Вторник").child("6").getValue(String.class);
        tuesday7 = " ";
        tuesday8 = " ";
        wednesday1 = dataSnapshot.child("Расписание").child("3").child("А").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("3").child("А").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("3").child("А").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("3").child("А").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("3").child("А").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("3").child("А").child("Среда").child("6").getValue(String.class);
        wednesday7 = " ";
        wednesday8 = " ";
        thursday1 = dataSnapshot.child("Расписание").child("3").child("А").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("3").child("А").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("3").child("А").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("3").child("А").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("3").child("А").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("3").child("А").child("Четверг").child("6").getValue(String.class);
        thursday7 = " ";
        thursday8 = " ";
        friday1 = dataSnapshot.child("Расписание").child("3").child("А").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("3").child("А").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("3").child("А").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("3").child("А").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("3").child("А").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("3").child("А").child("Пятница").child("6").getValue(String.class);
        friday7 = " ";
        friday8 = " ";
        saturday1 = " ";
        saturday2 = " ";
        saturday3 = " ";
        saturday4 = " ";
        saturday5 = " ";
        saturday6 = " ";
        saturday7 = " ";
        saturday8 = " ";}
    private void setTimeTable3B(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("3").child("Б").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("3").child("Б").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("3").child("Б").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("3").child("Б").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("3").child("Б").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("3").child("Б").child("Понедельник").child("6").getValue(String.class);
        monday7 = " ";
        monday8 = " ";
        tuesday1 = dataSnapshot.child("Расписание").child("3").child("Б").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("3").child("Б").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("3").child("Б").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("3").child("Б").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("3").child("Б").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("3").child("Б").child("Вторник").child("6").getValue(String.class);
        tuesday7 = " ";
        tuesday8 = " ";
        wednesday1 = dataSnapshot.child("Расписание").child("3").child("Б").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("3").child("Б").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("3").child("Б").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("3").child("Б").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("3").child("Б").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("3").child("Б").child("Среда").child("6").getValue(String.class);
        wednesday7 = " ";
        wednesday8 = " ";
        thursday1 = dataSnapshot.child("Расписание").child("3").child("Б").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("3").child("Б").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("3").child("Б").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("3").child("Б").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("3").child("Б").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("3").child("Б").child("Четверг").child("6").getValue(String.class);
        thursday7 = " ";
        thursday8 = " ";
        friday1 = dataSnapshot.child("Расписание").child("3").child("Б").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("3").child("Б").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("3").child("Б").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("3").child("Б").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("3").child("Б").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("3").child("Б").child("Пятница").child("6").getValue(String.class);
        friday7 = " ";
        friday8 = " ";
        saturday1 = " ";
        saturday2 = " ";
        saturday3 = " ";
        saturday4 = " ";
        saturday5 = " ";
        saturday6 = " ";
        saturday7 = " ";
        saturday8 = " ";}
    private void setTimeTable3C(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("3").child("В").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("3").child("В").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("3").child("В").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("3").child("В").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("3").child("В").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("3").child("В").child("Понедельник").child("6").getValue(String.class);
        monday7 = " ";
        monday8 = " ";
        tuesday1 = dataSnapshot.child("Расписание").child("3").child("В").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("3").child("В").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("3").child("В").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("3").child("В").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("3").child("В").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("3").child("В").child("Вторник").child("6").getValue(String.class);
        tuesday7 = " ";
        tuesday8 = " ";
        wednesday1 = dataSnapshot.child("Расписание").child("3").child("В").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("3").child("В").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("3").child("В").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("3").child("В").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("3").child("В").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("3").child("В").child("Среда").child("6").getValue(String.class);
        wednesday7 = " ";
        wednesday8 = " ";
        thursday1 = dataSnapshot.child("Расписание").child("3").child("В").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("3").child("В").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("3").child("В").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("3").child("В").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("3").child("В").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("3").child("В").child("Четверг").child("6").getValue(String.class);
        thursday7 = " ";
        thursday8 = " ";
        friday1 = dataSnapshot.child("Расписание").child("3").child("В").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("3").child("В").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("3").child("В").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("3").child("В").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("3").child("В").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("3").child("В").child("Пятница").child("6").getValue(String.class);
        friday7 = " ";
        friday8 = " ";
        saturday1 = " ";
        saturday2 = " ";
        saturday3 = " ";
        saturday4 = " ";
        saturday5 = " ";
        saturday6 = " ";
        saturday7 = " ";
        saturday8 = " ";
    }
    private void setTimeTable4A(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("4").child("А").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("4").child("А").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("4").child("А").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("4").child("А").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("4").child("А").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("4").child("А").child("Понедельник").child("6").getValue(String.class);
        monday7 = " ";
        monday8 = " ";
        tuesday1 = dataSnapshot.child("Расписание").child("4").child("А").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("4").child("А").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("4").child("А").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("4").child("А").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("4").child("А").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("4").child("А").child("Вторник").child("6").getValue(String.class);
        tuesday7 = " ";
        tuesday8 = " ";
        wednesday1 = dataSnapshot.child("Расписание").child("4").child("А").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("4").child("А").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("4").child("А").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("4").child("А").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("4").child("А").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("4").child("А").child("Среда").child("6").getValue(String.class);
        wednesday7 = " ";
        wednesday8 = " ";
        thursday1 = dataSnapshot.child("Расписание").child("4").child("А").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("4").child("А").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("4").child("А").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("4").child("А").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("4").child("А").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("4").child("А").child("Четверг").child("6").getValue(String.class);
        thursday7 = " ";
        thursday8 = " ";
        friday1 = dataSnapshot.child("Расписание").child("4").child("А").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("4").child("А").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("4").child("А").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("4").child("А").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("4").child("А").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("4").child("А").child("Пятница").child("6").getValue(String.class);
        friday7 = " ";
        friday8 = " ";
        saturday1 = " ";
        saturday2 = " ";
        saturday3 = " ";
        saturday4 = " ";
        saturday5 = " ";
        saturday6 = " ";
        saturday7 = " ";
        saturday8 = " ";}
    private void setTimeTable4B(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("4").child("Б").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("4").child("Б").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("4").child("Б").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("4").child("Б").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("4").child("Б").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("4").child("Б").child("Понедельник").child("6").getValue(String.class);
        monday7 = " ";
        monday8 = " ";
        tuesday1 = dataSnapshot.child("Расписание").child("4").child("Б").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("4").child("Б").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("4").child("Б").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("4").child("Б").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("4").child("Б").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("4").child("Б").child("Вторник").child("6").getValue(String.class);
        tuesday7 = " ";
        tuesday8 = " ";
        wednesday1 = dataSnapshot.child("Расписание").child("4").child("Б").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("4").child("Б").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("4").child("Б").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("4").child("Б").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("4").child("Б").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("4").child("Б").child("Среда").child("6").getValue(String.class);
        wednesday7 = " ";
        wednesday8 = " ";
        thursday1 = dataSnapshot.child("Расписание").child("4").child("Б").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("4").child("Б").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("4").child("Б").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("4").child("Б").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("4").child("Б").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("4").child("Б").child("Четверг").child("6").getValue(String.class);
        thursday7 = " ";
        thursday8 = " ";
        friday1 = dataSnapshot.child("Расписание").child("4").child("Б").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("4").child("Б").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("4").child("Б").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("4").child("Б").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("4").child("Б").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("4").child("Б").child("Пятница").child("6").getValue(String.class);
        friday7 = " ";
        friday8 = " ";
        saturday1 = " ";
        saturday2 = " ";
        saturday3 = " ";
        saturday4 = " ";
        saturday5 = " ";
        saturday6 = " ";
        saturday7 = " ";
        saturday8 = " ";
    }
    private void setTimeTable4C(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("4").child("В").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("4").child("В").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("4").child("В").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("4").child("В").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("4").child("В").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("4").child("В").child("Понедельник").child("6").getValue(String.class);
        monday7 = " ";
        monday8 = " ";
        tuesday1 = dataSnapshot.child("Расписание").child("4").child("В").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("4").child("В").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("4").child("В").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("4").child("В").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("4").child("В").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("4").child("В").child("Вторник").child("6").getValue(String.class);
        tuesday7 = " ";
        tuesday8 = " ";
        wednesday1 = dataSnapshot.child("Расписание").child("4").child("В").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("4").child("В").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("4").child("В").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("4").child("В").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("4").child("В").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("4").child("В").child("Среда").child("6").getValue(String.class);
        wednesday7 = " ";
        wednesday8 = " ";
        thursday1 = dataSnapshot.child("Расписание").child("4").child("В").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("4").child("В").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("4").child("В").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("4").child("В").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("4").child("В").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("4").child("В").child("Четверг").child("6").getValue(String.class);
        thursday7 = " ";
        thursday8 = " ";
        friday1 = dataSnapshot.child("Расписание").child("4").child("В").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("4").child("В").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("4").child("В").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("4").child("В").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("4").child("В").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("4").child("В").child("Пятница").child("6").getValue(String.class);
        friday7 = " ";
        friday8 = " ";
        saturday1 = " ";
        saturday2 = " ";
        saturday3 = " ";
        saturday4 = " ";
        saturday5 = " ";
        saturday6 = " ";
        saturday7 = " ";
        saturday8 = " ";
    }
    private void setTimeTable5A(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("5").child("А").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("5").child("А").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("5").child("А").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("5").child("А").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("5").child("А").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("5").child("А").child("Понедельник").child("6").getValue(String.class);
        monday7 = " ";
        monday8 = " ";
        tuesday1 = dataSnapshot.child("Расписание").child("5").child("А").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("5").child("А").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("5").child("А").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("5").child("А").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("5").child("А").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("5").child("А").child("Вторник").child("6").getValue(String.class);
        tuesday7 = " ";
        tuesday8 = " ";
        wednesday1 = dataSnapshot.child("Расписание").child("5").child("А").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("5").child("А").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("5").child("А").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("5").child("А").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("5").child("А").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("5").child("А").child("Среда").child("6").getValue(String.class);
        wednesday7 = " ";
        wednesday8 = " ";
        thursday1 = dataSnapshot.child("Расписание").child("5").child("А").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("5").child("А").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("5").child("А").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("5").child("А").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("5").child("А").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("5").child("А").child("Четверг").child("6").getValue(String.class);
        thursday7 = " ";
        thursday8 = " ";
        friday1 = dataSnapshot.child("Расписание").child("5").child("А").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("5").child("А").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("5").child("А").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("5").child("А").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("5").child("А").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("5").child("А").child("Пятница").child("6").getValue(String.class);
        friday7 = " ";
        friday8 = " ";
        saturday1 = " ";
        saturday2 = " ";
        saturday3 = " ";
        saturday4 = " ";
        saturday5 = " ";
        saturday6 = " ";
        saturday7 = " ";
        saturday8 = " ";
    }
    private void setTimeTable5B(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("5").child("Б").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("5").child("Б").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("5").child("Б").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("5").child("Б").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("5").child("Б").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("5").child("Б").child("Понедельник").child("6").getValue(String.class);
        monday7 = " ";
        monday8 = " ";
        tuesday1 = dataSnapshot.child("Расписание").child("5").child("Б").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("5").child("Б").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("5").child("Б").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("5").child("Б").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("5").child("Б").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("5").child("Б").child("Вторник").child("6").getValue(String.class);
        tuesday7 = " ";
        tuesday8 = " ";
        wednesday1 = dataSnapshot.child("Расписание").child("5").child("Б").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("5").child("Б").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("5").child("Б").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("5").child("Б").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("5").child("Б").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("5").child("Б").child("Среда").child("6").getValue(String.class);
        wednesday7 = " ";
        wednesday8 = " ";
        thursday1 = dataSnapshot.child("Расписание").child("5").child("Б").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("5").child("Б").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("5").child("Б").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("5").child("Б").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("5").child("Б").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("5").child("Б").child("Четверг").child("6").getValue(String.class);
        thursday7 = " ";
        thursday8 = " ";
        friday1 = dataSnapshot.child("Расписание").child("5").child("Б").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("5").child("Б").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("5").child("Б").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("5").child("Б").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("5").child("Б").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("5").child("Б").child("Пятница").child("6").getValue(String.class);
        friday7 = " ";
        friday8 = " ";
        saturday1 = " ";
        saturday2 = " ";
        saturday3 = " ";
        saturday4 = " ";
        saturday5 = " ";
        saturday6 = " ";
        saturday7 = " ";
        saturday8 = " ";
    }
    private void setTimeTable5C(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("5").child("В").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("5").child("В").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("5").child("В").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("5").child("В").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("5").child("В").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("5").child("В").child("Понедельник").child("6").getValue(String.class);
        monday7 = " ";
        monday8 = " ";
        tuesday1 = dataSnapshot.child("Расписание").child("5").child("В").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("5").child("В").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("5").child("В").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("5").child("В").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("5").child("В").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("5").child("В").child("Вторник").child("6").getValue(String.class);
        tuesday7 = " ";
        tuesday8 = " ";
        wednesday1 = dataSnapshot.child("Расписание").child("5").child("В").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("5").child("В").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("5").child("В").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("5").child("В").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("5").child("В").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("5").child("В").child("Среда").child("6").getValue(String.class);
        wednesday7 = " ";
        wednesday8 = " ";
        thursday1 = dataSnapshot.child("Расписание").child("5").child("В").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("5").child("В").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("5").child("В").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("5").child("В").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("5").child("В").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("5").child("В").child("Четверг").child("6").getValue(String.class);
        thursday7 = " ";
        thursday8 = " ";
        friday1 = dataSnapshot.child("Расписание").child("5").child("В").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("5").child("В").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("5").child("В").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("5").child("В").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("5").child("В").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("5").child("В").child("Пятница").child("6").getValue(String.class);
        friday7 = " ";
        friday8 = " ";
        saturday1 = " ";
        saturday2 = " ";
        saturday3 = " ";
        saturday4 = " ";
        saturday5 = " ";
        saturday6 = " ";
        saturday7 = " ";
        saturday8 = " ";
    }
    private void setTimeTable6A(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("6").child("А").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("6").child("А").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("6").child("А").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("6").child("А").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("6").child("А").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("6").child("А").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("6").child("А").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("6").child("А").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("6").child("А").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("6").child("А").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("6").child("А").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("6").child("А").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("6").child("А").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("6").child("А").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("6").child("А").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("6").child("А").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("6").child("А").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("6").child("А").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("6").child("А").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("6").child("А").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("6").child("А").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("6").child("А").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("6").child("А").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("6").child("А").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("6").child("А").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("6").child("А").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("6").child("А").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("6").child("А").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("6").child("А").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("6").child("А").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("6").child("А").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("6").child("А").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("6").child("А").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("6").child("А").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("6").child("А").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("6").child("А").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("6").child("А").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("6").child("А").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("6").child("А").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("6").child("А").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("6").child("А").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("6").child("А").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("6").child("А").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("6").child("А").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("6").child("А").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("6").child("А").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("6").child("А").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("6").child("А").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable6B(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("6").child("Б").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("6").child("Б").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("6").child("Б").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("6").child("Б").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("6").child("Б").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("6").child("Б").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("6").child("Б").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("6").child("Б").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("6").child("Б").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("6").child("Б").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("6").child("Б").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("6").child("Б").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("6").child("Б").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("6").child("Б").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("6").child("Б").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("6").child("Б").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("6").child("Б").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("6").child("Б").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("6").child("Б").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("6").child("Б").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("6").child("Б").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("6").child("Б").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("6").child("Б").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("м").child("Б").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("6").child("Б").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("6").child("Б").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("6").child("Б").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("6").child("Б").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("6").child("Б").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("6").child("Б").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("6").child("Б").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("6").child("Б").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("6").child("Б").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("6").child("Б").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("6").child("Б").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("6").child("Б").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("6").child("Б").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("6").child("Б").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("6").child("Б").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("6").child("Б").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("6").child("Б").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("6").child("Б").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("6").child("Б").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("6").child("Б").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("6").child("Б").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("6").child("Б").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("6").child("Б").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("6").child("Б").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable6C(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("6").child("В").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("6").child("В").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("6").child("В").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("6").child("В").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("6").child("В").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("6").child("В").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("6").child("В").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("6").child("В").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("6").child("В").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("6").child("В").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("6").child("В").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("6").child("В").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("6").child("В").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("6").child("В").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("6").child("В").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("6").child("В").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("6").child("В").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("6").child("В").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("6").child("В").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("6").child("В").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("6").child("В").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("6").child("В").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("6").child("В").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("6").child("В").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("6").child("В").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("6").child("В").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("6").child("В").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("6").child("В").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("6").child("В").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("6").child("В").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("6").child("В").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("6").child("В").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("6").child("В").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("6").child("В").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("6").child("В").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("6").child("В").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("6").child("В").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("6").child("В").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("6").child("В").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("6").child("В").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("6").child("В").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("6").child("В").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("6").child("В").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("6").child("В").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("6").child("В").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("6").child("В").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("6").child("В").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("6").child("В").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable7A(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("7").child("А").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("7").child("А").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("7").child("А").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("7").child("А").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("7").child("А").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("7").child("А").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("7").child("А").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("7").child("А").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("7").child("А").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("7").child("А").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("7").child("А").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("7").child("А").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("7").child("А").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("7").child("А").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("7").child("А").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("7").child("А").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("7").child("А").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("7").child("А").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("7").child("А").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("7").child("А").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("7").child("А").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("7").child("А").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("7").child("А").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("7").child("А").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("7").child("А").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("7").child("А").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("7").child("А").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("7").child("А").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("7").child("А").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("7").child("А").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("7").child("А").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("7").child("А").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("7").child("А").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("7").child("А").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("7").child("А").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("7").child("А").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("7").child("А").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("7").child("А").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("7").child("А").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("7").child("А").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("7").child("А").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("7").child("А").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("7").child("А").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("7").child("А").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("7").child("А").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("7").child("А").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("7").child("А").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("7").child("А").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable7B(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("7").child("Б").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("7").child("Б").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("7").child("Б").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("7").child("Б").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("7").child("Б").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("7").child("Б").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("7").child("Б").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("7").child("Б").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("7").child("Б").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("7").child("Б").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("7").child("Б").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("7").child("Б").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("7").child("Б").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("7").child("Б").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("7").child("Б").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("7").child("Б").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("7").child("Б").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("7").child("Б").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("7").child("Б").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("7").child("Б").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("7").child("Б").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("7").child("Б").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("7").child("Б").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("7").child("Б").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("7").child("Б").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("7").child("Б").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("7").child("Б").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("7").child("Б").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("7").child("Б").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("7").child("Б").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("7").child("Б").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("7").child("Б").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("7").child("Б").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("7").child("Б").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("7").child("Б").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("7").child("Б").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("7").child("Б").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("7").child("Б").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("7").child("Б").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("7").child("Б").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("7").child("Б").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("7").child("Б").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("7").child("Б").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("7").child("Б").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("7").child("Б").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("7").child("Б").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("7").child("Б").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("7").child("Б").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable7C(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("7").child("В").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("7").child("В").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("7").child("В").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("7").child("В").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("7").child("В").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("7").child("В").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("7").child("В").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("7").child("В").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("7").child("В").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("7").child("В").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("7").child("В").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("7").child("В").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("7").child("В").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("7").child("В").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("7").child("В").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("7").child("В").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("7").child("В").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("7").child("В").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("7").child("В").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("7").child("В").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("7").child("В").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("7").child("В").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("7").child("В").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("7").child("В").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("7").child("В").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("7").child("В").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("7").child("В").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("7").child("В").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("7").child("В").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("7").child("В").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("7").child("В").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("7").child("В").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("7").child("В").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("7").child("В").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("7").child("В").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("7").child("В").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("7").child("В").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("7").child("В").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("7").child("В").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("7").child("В").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("7").child("В").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("7").child("В").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("7").child("В").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("7").child("В").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("7").child("В").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("7").child("В").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("7").child("В").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("7").child("В").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable8A(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("8").child("А").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("8").child("А").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("8").child("А").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("8").child("А").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("8").child("А").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("8").child("А").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("8").child("А").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("8").child("А").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("8").child("А").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("8").child("А").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("8").child("А").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("8").child("А").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("8").child("А").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("8").child("А").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("8").child("А").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("8").child("А").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("8").child("А").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("8").child("А").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("8").child("А").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("8").child("А").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("8").child("А").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("8").child("А").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("8").child("А").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("8").child("А").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("8").child("А").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("8").child("А").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("8").child("А").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("8").child("А").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("8").child("А").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("8").child("А").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("8").child("А").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("8").child("А").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("8").child("А").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("8").child("А").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("8").child("А").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("8").child("А").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("8").child("А").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("8").child("А").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("8").child("А").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("8").child("А").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("8").child("А").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("8").child("А").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("8").child("А").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("8").child("А").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("8").child("А").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("8").child("А").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("8").child("А").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("8").child("А").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable8B(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("8").child("Б").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("8").child("Б").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("8").child("Б").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("8").child("Б").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("8").child("Б").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("8").child("Б").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("8").child("Б").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("8").child("Б").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("8").child("Б").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("8").child("Б").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("8").child("Б").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("8").child("Б").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("8").child("Б").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("8").child("Б").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("8").child("Б").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("8").child("Б").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("8").child("Б").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("8").child("Б").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("8").child("Б").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("8").child("Б").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("8").child("Б").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("8").child("Б").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("8").child("Б").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("8").child("Б").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("8").child("Б").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("8").child("Б").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("8").child("Б").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("8").child("Б").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("8").child("Б").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("8").child("Б").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("8").child("Б").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("8").child("Б").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("8").child("Б").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("8").child("Б").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("8").child("Б").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("8").child("Б").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("8").child("Б").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("8").child("Б").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("8").child("Б").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("8").child("Б").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("8").child("Б").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("8").child("Б").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("8").child("Б").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("8").child("Б").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("8").child("Б").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("8").child("Б").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("8").child("Б").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("8").child("Б").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable8C(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("8").child("В").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("8").child("В").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("8").child("В").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("8").child("В").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("8").child("В").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("8").child("В").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("8").child("В").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("8").child("В").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("8").child("В").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("8").child("В").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("8").child("В").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("8").child("В").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("8").child("В").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("8").child("В").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("8").child("В").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("8").child("В").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("8").child("В").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("8").child("В").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("8").child("В").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("8").child("В").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("8").child("В").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("8").child("В").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("8").child("В").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("8").child("В").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("8").child("В").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("8").child("В").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("8").child("В").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("8").child("В").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("8").child("В").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("8").child("В").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("8").child("В").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("8").child("В").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("8").child("В").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("8").child("В").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("8").child("В").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("8").child("В").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("8").child("В").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("8").child("В").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("8").child("В").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("8").child("В").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("8").child("В").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("8").child("В").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("8").child("В").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("8").child("В").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("8").child("В").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("8").child("В").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("8").child("В").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("8").child("В").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable9A(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("9").child("А").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("9").child("А").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("9").child("А").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("9").child("А").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("9").child("А").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("9").child("А").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("9").child("А").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("9").child("А").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("9").child("А").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("9").child("А").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("9").child("А").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("9").child("А").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("9").child("А").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("9").child("А").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("9").child("А").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("9").child("А").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("9").child("А").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("9").child("А").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("9").child("А").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("9").child("А").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("9").child("А").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("9").child("А").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("9").child("А").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("9").child("А").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("9").child("А").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("9").child("А").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("9").child("А").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("9").child("А").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("9").child("А").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("9").child("А").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("9").child("А").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("9").child("А").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("9").child("А").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("9").child("А").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("9").child("А").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("9").child("А").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("9").child("А").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("9").child("А").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("9").child("А").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("9").child("А").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("9").child("А").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("9").child("А").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("9").child("А").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("9").child("А").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("9").child("А").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("9").child("А").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("9").child("А").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("9").child("А").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable9B(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("9").child("Б").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("9").child("Б").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("9").child("Б").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("9").child("Б").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("9").child("Б").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("9").child("Б").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("9").child("Б").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("9").child("Б").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("9").child("Б").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("9").child("Б").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("9").child("Б").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("9").child("Б").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("9").child("Б").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("9").child("Б").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("9").child("Б").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("9").child("Б").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("9").child("Б").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("9").child("Б").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("9").child("Б").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("9").child("Б").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("9").child("Б").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("9").child("Б").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("9").child("Б").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("9").child("Б").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("9").child("Б").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("9").child("Б").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("9").child("Б").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("9").child("Б").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("9").child("Б").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("9").child("Б").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("9").child("Б").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("9").child("Б").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("9").child("Б").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("9").child("Б").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("9").child("Б").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("9").child("Б").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("9").child("Б").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("9").child("Б").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("9").child("Б").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("9").child("Б").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("9").child("Б").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("9").child("Б").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("9").child("Б").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("9").child("Б").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("9").child("Б").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("9").child("Б").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("9").child("Б").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("9").child("Б").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable9C(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("9").child("В").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("9").child("В").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("9").child("В").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("9").child("В").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("9").child("В").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("9").child("В").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("9").child("В").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("9").child("В").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("9").child("В").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("9").child("В").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("9").child("В").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("9").child("В").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("9").child("В").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("9").child("В").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("9").child("В").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("9").child("В").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("9").child("В").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("9").child("В").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("9").child("В").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("9").child("В").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("9").child("В").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("9").child("В").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("9").child("В").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("9").child("В").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("9").child("В").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("9").child("В").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("9").child("В").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("9").child("В").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("9").child("В").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("9").child("В").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("9").child("В").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("9").child("В").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("9").child("В").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("9").child("В").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("9").child("В").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("9").child("В").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("9").child("В").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("9").child("В").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("9").child("В").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("9").child("В").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("9").child("В").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("9").child("В").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("9").child("В").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("9").child("В").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("9").child("В").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("9").child("В").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("9").child("В").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("9").child("В").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable10A(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("10").child("А").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("10").child("А").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("10").child("А").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("10").child("А").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("10").child("А").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("10").child("А").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("10").child("А").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("10").child("А").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("10").child("А").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("10").child("А").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("10").child("А").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("10").child("А").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("10").child("А").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("10").child("А").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("10").child("А").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("10").child("А").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("10").child("А").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("10").child("А").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("10").child("А").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("10").child("А").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("10").child("А").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("10").child("А").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("10").child("А").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("10").child("А").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("10").child("А").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("10").child("А").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("10").child("А").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("10").child("А").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("10").child("А").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("10").child("А").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("10").child("А").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("10").child("А").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("10").child("А").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("10").child("А").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("10").child("А").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("10").child("А").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("10").child("А").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("10").child("А").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("10").child("А").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("10").child("А").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("10").child("А").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("10").child("А").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("10").child("А").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("10").child("А").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("10").child("А").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("10").child("А").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("10").child("А").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("10").child("А").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable10B(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("10").child("Б").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("10").child("Б").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("10").child("Б").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("10").child("Б").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("10").child("Б").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("10").child("Б").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("10").child("Б").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("10").child("Б").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("10").child("Б").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("10").child("Б").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("10").child("Б").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("10").child("Б").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("10").child("Б").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("10").child("Б").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("10").child("Б").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("10").child("Б").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("10").child("Б").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("10").child("Б").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("10").child("Б").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("10").child("Б").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("10").child("Б").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("10").child("Б").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("10").child("Б").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("10").child("Б").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("10").child("Б").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("10").child("Б").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("10").child("Б").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("10").child("Б").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("10").child("Б").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("10").child("Б").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("10").child("Б").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("10").child("Б").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("10").child("Б").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("10").child("Б").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("10").child("Б").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("10").child("Б").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("10").child("Б").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("10").child("Б").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("10").child("Б").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("10").child("Б").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("10").child("Б").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("10").child("Б").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("10").child("Б").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("10").child("Б").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("10").child("Б").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("10").child("Б").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("10").child("Б").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("10").child("Б").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable10C(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("10").child("В").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("10").child("В").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("10").child("В").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("10").child("В").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("10").child("В").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("10").child("В").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("10").child("В").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("10").child("В").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("10").child("В").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("10").child("В").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("10").child("В").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("10").child("В").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("10").child("В").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("10").child("В").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("10").child("В").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("10").child("В").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("10").child("В").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("10").child("В").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("10").child("В").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("10").child("В").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("10").child("В").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("10").child("В").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("10").child("В").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("10").child("В").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("10").child("В").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("10").child("В").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("10").child("В").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("10").child("В").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("10").child("В").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("10").child("В").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("10").child("В").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("10").child("В").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("10").child("В").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("10").child("В").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("10").child("В").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("10").child("В").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("10").child("В").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("10").child("В").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("10").child("В").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("10").child("В").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("10").child("В").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("10").child("В").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("10").child("В").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("10").child("В").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("10").child("В").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("10").child("В").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("10").child("В").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("10").child("В").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable11A(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("11").child("А").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("11").child("А").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("11").child("А").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("11").child("А").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("11").child("А").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("11").child("А").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("11").child("А").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("11").child("А").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("11").child("А").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("11").child("А").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("11").child("А").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("11").child("А").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("11").child("А").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("11").child("А").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("11").child("А").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("11").child("А").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("11").child("А").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("11").child("А").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("11").child("А").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("11").child("А").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("11").child("А").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("11").child("А").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("11").child("А").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("11").child("А").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("11").child("А").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("11").child("А").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("11").child("А").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("11").child("А").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("11").child("А").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("11").child("А").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("11").child("А").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("11").child("А").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("11").child("А").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("11").child("А").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("11").child("А").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("11").child("А").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("11").child("А").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("11").child("А").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("11").child("А").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("11").child("А").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("11").child("А").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("11").child("А").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("11").child("А").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("11").child("А").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("11").child("А").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("11").child("А").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("11").child("А").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("11").child("А").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable11B(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("11").child("Б").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("11").child("Б").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("11").child("Б").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("11").child("Б").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("11").child("Б").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("11").child("Б").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("11").child("Б").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("11").child("Б").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("11").child("Б").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("11").child("Б").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("11").child("Б").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("11").child("Б").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("11").child("Б").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("11").child("Б").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("11").child("Б").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("11").child("Б").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("11").child("Б").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("11").child("Б").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("11").child("Б").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("11").child("Б").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("11").child("Б").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("11").child("Б").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("11").child("Б").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("11").child("Б").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("11").child("Б").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("11").child("Б").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("11").child("Б").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("11").child("Б").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("11").child("Б").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("11").child("Б").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("11").child("Б").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("11").child("Б").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("11").child("Б").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("11").child("Б").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("11").child("Б").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("11").child("Б").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("11").child("Б").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("11").child("Б").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("11").child("Б").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("11").child("Б").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("11").child("Б").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("11").child("Б").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("11").child("Б").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("11").child("Б").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("11").child("Б").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("11").child("Б").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("11").child("Б").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("11").child("Б").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTable11C(DataSnapshot dataSnapshot){
        monday1 = dataSnapshot.child("Расписание").child("11").child("В").child("Понедельник").child("1").getValue(String.class);
        monday2 = dataSnapshot.child("Расписание").child("11").child("В").child("Понедельник").child("2").getValue(String.class);
        monday3 = dataSnapshot.child("Расписание").child("11").child("В").child("Понедельник").child("3").getValue(String.class);
        monday4 = dataSnapshot.child("Расписание").child("11").child("В").child("Понедельник").child("4").getValue(String.class);
        monday5 = dataSnapshot.child("Расписание").child("11").child("В").child("Понедельник").child("5").getValue(String.class);
        monday6 = dataSnapshot.child("Расписание").child("11").child("В").child("Понедельник").child("6").getValue(String.class);
        monday7 = dataSnapshot.child("Расписание").child("11").child("В").child("Понедельник").child("7").getValue(String.class);
        monday8 = dataSnapshot.child("Расписание").child("11").child("В").child("Понедельник").child("8").getValue(String.class);
        tuesday1 = dataSnapshot.child("Расписание").child("11").child("В").child("Вторник").child("1").getValue(String.class);
        tuesday2 = dataSnapshot.child("Расписание").child("11").child("В").child("Вторник").child("2").getValue(String.class);
        tuesday3 = dataSnapshot.child("Расписание").child("11").child("В").child("Вторник").child("3").getValue(String.class);
        tuesday4 = dataSnapshot.child("Расписание").child("11").child("В").child("Вторник").child("4").getValue(String.class);
        tuesday5 = dataSnapshot.child("Расписание").child("11").child("В").child("Вторник").child("5").getValue(String.class);
        tuesday6 = dataSnapshot.child("Расписание").child("11").child("В").child("Вторник").child("6").getValue(String.class);
        tuesday7 = dataSnapshot.child("Расписание").child("11").child("В").child("Вторник").child("7").getValue(String.class);
        tuesday8 = dataSnapshot.child("Расписание").child("11").child("В").child("Вторник").child("8").getValue(String.class);
        wednesday1 = dataSnapshot.child("Расписание").child("11").child("В").child("Среда").child("1").getValue(String.class);
        wednesday2 = dataSnapshot.child("Расписание").child("11").child("В").child("Среда").child("2").getValue(String.class);
        wednesday3 = dataSnapshot.child("Расписание").child("11").child("В").child("Среда").child("3").getValue(String.class);
        wednesday4 = dataSnapshot.child("Расписание").child("11").child("В").child("Среда").child("4").getValue(String.class);
        wednesday5 = dataSnapshot.child("Расписание").child("11").child("В").child("Среда").child("5").getValue(String.class);
        wednesday6 = dataSnapshot.child("Расписание").child("11").child("В").child("Среда").child("6").getValue(String.class);
        wednesday7 = dataSnapshot.child("Расписание").child("11").child("В").child("Среда").child("7").getValue(String.class);
        wednesday8 = dataSnapshot.child("Расписание").child("11").child("В").child("Среда").child("8").getValue(String.class);
        thursday1 = dataSnapshot.child("Расписание").child("11").child("В").child("Четверг").child("1").getValue(String.class);
        thursday2 = dataSnapshot.child("Расписание").child("11").child("В").child("Четверг").child("2").getValue(String.class);
        thursday3 = dataSnapshot.child("Расписание").child("11").child("В").child("Четверг").child("3").getValue(String.class);
        thursday4 = dataSnapshot.child("Расписание").child("11").child("В").child("Четверг").child("4").getValue(String.class);
        thursday5 = dataSnapshot.child("Расписание").child("11").child("В").child("Четверг").child("5").getValue(String.class);
        thursday6 = dataSnapshot.child("Расписание").child("11").child("В").child("Четверг").child("6").getValue(String.class);
        thursday7 = dataSnapshot.child("Расписание").child("11").child("В").child("Четверг").child("7").getValue(String.class);
        thursday8 = dataSnapshot.child("Расписание").child("11").child("В").child("Четверг").child("8").getValue(String.class);
        friday1 = dataSnapshot.child("Расписание").child("11").child("В").child("Пятница").child("1").getValue(String.class);
        friday2 = dataSnapshot.child("Расписание").child("11").child("В").child("Пятница").child("2").getValue(String.class);
        friday3 = dataSnapshot.child("Расписание").child("11").child("В").child("Пятница").child("3").getValue(String.class);
        friday4 = dataSnapshot.child("Расписание").child("11").child("В").child("Пятница").child("4").getValue(String.class);
        friday5 = dataSnapshot.child("Расписание").child("11").child("В").child("Пятница").child("5").getValue(String.class);
        friday6 = dataSnapshot.child("Расписание").child("11").child("В").child("Пятница").child("6").getValue(String.class);
        friday7 = dataSnapshot.child("Расписание").child("11").child("В").child("Пятница").child("7").getValue(String.class);
        friday8 = dataSnapshot.child("Расписание").child("11").child("В").child("Пятница").child("8").getValue(String.class);
        saturday1 = dataSnapshot.child("Расписание").child("11").child("В").child("Суббота").child("1").getValue(String.class);
        saturday2 = dataSnapshot.child("Расписание").child("11").child("В").child("Суббота").child("2").getValue(String.class);
        saturday3 = dataSnapshot.child("Расписание").child("11").child("В").child("Суббота").child("3").getValue(String.class);
        saturday4 = dataSnapshot.child("Расписание").child("11").child("В").child("Суббота").child("4").getValue(String.class);
        saturday5 = dataSnapshot.child("Расписание").child("11").child("В").child("Суббота").child("5").getValue(String.class);
        saturday6 = dataSnapshot.child("Расписание").child("11").child("В").child("Суббота").child("6").getValue(String.class);
        saturday7 = dataSnapshot.child("Расписание").child("11").child("В").child("Суббота").child("7").getValue(String.class);
        saturday8 = dataSnapshot.child("Расписание").child("11").child("В").child("Суббота").child("8").getValue(String.class);
    }
    private void setTimeTableNotFound(DataSnapshot dataSnapshot)
    {
        monday1 ="Не найдено";
        monday2 = "Не найдено";
        monday3 = "Не найдено";
        monday4 = "Не найдено";
        monday5 = "Не найдено";
        monday6 = "Не найдено";
        monday7 = "Не найдено";
        monday8 = "Не найдено";
        tuesday1 = "Не найдено";
        tuesday2 ="Не найдено";
        tuesday3 = "Не найдено";
        tuesday4 = "Не найдено";
        tuesday5 = "Не найдено";
        tuesday6 = "Не найдено";
        tuesday7 = "Не найдено";
        tuesday8 = "Не найдено";
        wednesday1 = "Не найдено";
        wednesday2 = "Не найдено";
        wednesday3 = "Не найдено";
        wednesday4 = "Не найдено";
        wednesday5 = "Не найдено";
        wednesday6 = "Не найдено";
        wednesday7 = "Не найдено";
        wednesday8 = "Не найдено";
        thursday1 = "Не найдено";
        thursday2 ="Не найдено";
        thursday3 = "Не найдено";
        thursday4 = "Не найдено";
        thursday5 = "Не найдено";
        thursday6 = "Не найдено";
        thursday7 = "Не найдено";
        thursday8 = "Не найдено";
        friday1 = "Не найдено";
        friday2 = "Не найдено";
        friday3 = "Не найдено";
        friday4 = "Не найдено";
        friday5 = "Не найдено";
        friday6 = "Не найдено";
        friday7 = "Не найдено";
        friday8 = "Не найдено";
        saturday1 = "Не найдено";
        saturday2 ="Не найдено";
        saturday3 = "Не найдено";
        saturday4 = "Не найдено";
        saturday5 ="Не найдено";
        saturday6 ="Не найдено";
        saturday7 = "Не найдено";
        saturday8 = "Не найдено";
    }
}