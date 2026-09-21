package com.example.menutictac.activities;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menutictac.R;

import android.content.Intent;

import android.util.Log;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.menutictac.shell.HomeFragment;
import com.example.menutictac.shell.ProfileFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import android.content.SharedPreferences;
import com.example.menutictac.activities.LoginActivity;
import com.example.menutictac.services.FBRef;

import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        drawerLayout = findViewById(R.id.drawer_layout);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                showFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.nav_local_game) {
                startActivity(new Intent(this, MainActivity.class));
            } else if (item.getItemId() == R.id.nav_rtdb_prep) {
                startActivity(new Intent(this, Main2Activity.class));
            } else if (item.getItemId() == R.id.nav_profile) {
                showFragment(new ProfileFragment());
            } else if (item.getItemId() == R.id.nav_logout) {
                logoutAndOpenLogin();
            }
        drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        if (savedInstanceState == null) {
            showFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    private void logoutAndOpenLogin() {
        // Turn off the automatic "stay connected" bypass.
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
        settings.edit().putBoolean("stayConnect", false).apply();

        // Firebase / Google sign-out.
        FBRef.refAuth.signOut();
        FBRef.signOutGoogle();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, fragment)
                .commit();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        String Actvity_Name = taskInfo.get(0).topActivity.getClassName(); // Be cautious with getRunningTasks, it's deprecated for third-party apps.
        int itemId = item.getItemId();
        if (itemId == R.id.activity1) {
            if (!Actvity_Name.equals("com.example.tasks.Activities.MainActivity")) {
                Log.i("MasterActivity", "Changing to MainActivity");
                Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
//        } else if (itemId == R.id.idTasksDone) {
//            if (!Actvity_Name.equals("com.example.tasks.Activities.DoneTasksActivity")) {
//                Log.i("MasterActivity", "Changing to DoneTasksActivity");
//                Intent intent = new Intent(this.getApplicationContext(), DoneTasksActivity.class);
//                startActivity(intent);
//            }
//        } else if (itemId == R.id.idYears) {
//            if (!Actvity_Name.equals("com.example.tasks.Activities.YearsActivity")) {
//                Log.i("MasterActivity", "Changing to YearsActivity");
//                Intent intent = new Intent(this.getApplicationContext(), YearsActivity.class);
//                startActivity(intent);
//            }

            // הוסיפו כאן את ה-if הנוספים עבור כל Activity שיצרתם:
            // לדוגמה:
            // } else if (itemId == R.id.idNew) {
            //     if (!Actvity_Name.equals("com.example.tasks.Activities.NewActivity")) {
            //         Log.i("MasterActivity", "Changing to NewActivity");
            //         startActivity(new Intent(this, NewActivity.class));
            //     }
            // במקום המילה New יופיע שם ה-Activity שלכם.

        } else if (itemId == R.id.disconnect) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Disconnect Account");
            adb.setMessage("Are you sure yo want to\n Disconnect account & Exit?"); // Typo: "you"
            adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // refAuth.signOut(); // Ensure refAuth is initialized and accessible
                    finishAffinity(); // Closes all activities in this task
                }
            });
            adb.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            adb.setCancelable(false);
            adb.create().show();
        }
//        } else if (itemId == R.id.idExit) {
//            AlertDialog.Builder adb = new AlertDialog.Builder(this);
//            adb.setTitle("Quit Application");
//            adb.setMessage("Are you sure?");
//            adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finishAffinity(); // Closes all activities in this task
//                }
//            });
//            adb.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//            adb.setCancelable(false);
//            adb.create().show();
//        }
        return super.onOptionsItemSelected(item);
    }


}
