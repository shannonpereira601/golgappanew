package com.frostox.calculo.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.frostox.calculo.Entities.Mcq;
import com.frostox.calculo.Entities.McqItem;
import com.frostox.calculo.Entities.Note;
import com.frostox.calculo.Entities.Standard;
import com.frostox.calculo.Entities.Subject;
import com.frostox.calculo.Entities.Topic;
import com.frostox.calculo.dao.DaoMaster;
import com.frostox.calculo.dao.DaoSession;
import com.frostox.calculo.dao.McqDao;
import com.frostox.calculo.dao.NoteDao;
import com.frostox.calculo.dao.StandardDao;
import com.frostox.calculo.dao.SubjectDao;
import com.frostox.calculo.dao.TopicDao;
import com.frostox.calculo.enums.Entities;
import com.frostox.calculo.fragments.EntityFragment;
import com.frostox.calculo.fragments.EntityFragment1;

import java.util.List;

import calculo.frostox.com.calculo.R;
import de.greenrobot.dao.query.Query;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, EntityFragment1.OnFragmentInteractionListener {

    CoordinatorLayout coordinatorLayout;

    Firebase ref;

    private DrawerLayout drawer;

    private EntityFragment1 standardFragment;

    private EntityFragment1 subjectFragment;

    private EntityFragment1 topicFragment;

    private EntityFragment1 mcqFragment;

    private EntityFragment1 noteFragment;

    private boolean mcqMode = true;

    private String current = "Standard";

    private Entities currentList = Entities.STANDARD;

    private TextView courses, subjects, topics, mcqnotes;

    private HorizontalScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Firebase.setAndroidContext(this);
        ref = new Firebase("https://extraclass.firebaseio.com/");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.colayout);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        scrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        standardFragment = new EntityFragment1();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, standardFragment)
                .commit();

        courses = (TextView) findViewById(R.id.courses);
        subjects = (TextView) findViewById(R.id.subjects);
        topics = (TextView) findViewById(R.id.topics);
        mcqnotes = (TextView) findViewById(R.id.mcqnotes);

        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!current.equals("Standard")) {
            Log.d("Check", "onnBckClld");
            navPrev();
        } else {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mcq) {
            mcqMode = true;
            if (current.equals("MCQ"))
                navPrev();

            //TODO Fragment operations
        } else if (id == R.id.nav_notes) {
            mcqMode = false;
            if (current.equals("Note"))
                navPrev();
            //TODO Fragement operations
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(Home.this, Login.class);
            ref.unauth();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Snackbar.make(coordinatorLayout, "Not Implemented Yet", Snackbar.LENGTH_LONG).show();
        } else if (id == R.id.nav_send) {
            Snackbar.make(coordinatorLayout, "Not Implemented Yet", Snackbar.LENGTH_LONG).show();
        } else if (id == R.id.nav_about_us) {
            Snackbar.make(coordinatorLayout, "Not Implemented Yet", Snackbar.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onFragmentInteraction(Uri uri) {
        Snackbar.make(coordinatorLayout, "fragment interaction", Snackbar.LENGTH_LONG).show();
    }


    public void navNext(String key, String name) {
        name = name.toUpperCase();
        switch (current) {
            case "Standard":
                if (name.equals("9TH GRADE")) {
                    name = "9th GRADE";
                } else if (name.equals("10TH GRADE")) {
                    name = "10th GRADE";
                }
                current = "Subject";
                subjectFragment = new EntityFragment1();

                Bundle bundle = new Bundle();
                bundle.putString("current", current);
                bundle.putString("id", key);
                // set Fragmentclass Arguments
                subjectFragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_out2, R.anim.slide_in2)
                        .replace(R.id.content_frame, subjectFragment).addToBackStack("Standard")
                        .commit();

                courses.setText(name + " >");
                subjects.setVisibility(View.VISIBLE);
                subjects.setText("SUBJECTS >");
                topics.setVisibility(View.GONE);
                mcqnotes.setVisibility(View.GONE);

                break;
            case "Subject":
                current = "Topic";
                topicFragment = new EntityFragment1();
                bundle = new Bundle();
                bundle.putString("current", current);
                bundle.putString("id", key);
                // set Fragmentclass Arguments
                topicFragment.setArguments(bundle);
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_out2, R.anim.slide_in2)
                        .replace(R.id.content_frame, topicFragment).addToBackStack("Subject")
                        .commit();

                subjects.setText(name + " >");
                topics.setVisibility(View.VISIBLE);
                mcqnotes.setVisibility(View.GONE);

                break;
            case "Topic":
                if (mcqMode) {
                    current = "MCQ";
                    mcqFragment = new EntityFragment1();
                    bundle = new Bundle();
                    bundle.putString("current", current);
                    bundle.putString("id", key);
                    mcqFragment.setArguments(bundle);
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_out2, R.anim.slide_in2)
                            .replace(R.id.content_frame, mcqFragment).addToBackStack("Topic")
                            .commit();

                    topics.setText(name + " >");
                    mcqnotes.setVisibility(View.VISIBLE);
                    mcqnotes.setText("MCQs >");
                } else {
                    current = "Note";
                    noteFragment = new EntityFragment1();
                    bundle = new Bundle();
                    bundle.putString("current", current);
                    bundle.putString("id", key);
                    noteFragment.setArguments(bundle);
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_out2, R.anim.slide_in2)
                            .replace(R.id.content_frame, noteFragment).addToBackStack("Topic")
                            .commit();

                    topics.setText(name + " >");
                    mcqnotes.setVisibility(View.VISIBLE);
                    mcqnotes.setText("NOTES >");
                }


                break;
            case "MCQ":

                //Goto mcq activity
                Intent intent = new Intent(this, McqActivity.class);
                intent.putExtra("name", name);
                // intent.putExtra("id", id);

                startActivity(intent);
                break;
            case "Note":
                //Goto note activity
                intent = new Intent(this, ScreenSlideActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
        }


        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        });
    }

    public void navPrev() {


        switch (current) {

            case "Subject":
                current = "Standard";
                subjects.setVisibility(View.GONE);
                courses.setText("COURSES >");
                break;

            case "Topic":
                current = "Subject";
                topics.setVisibility(View.GONE);
                subjects.setText("SUBJECTS >");
                break;

            case "MCQ":
                current = "Topic";
                mcqnotes.setVisibility(View.GONE);
                topics.setText("TOPICS >");
                break;

            case "Note":
                current = "Topic";
                mcqnotes.setVisibility(View.GONE);
                topics.setText("TOPICS >");

        }

        getSupportFragmentManager().popBackStack();

        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        });
    }

    public void navRoot() {

    }
}
