package mx.unam.computoMovil.battleship;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyActivity extends ActionBarActivity {

    private String[] opcionesMenu = null;
    private DrawerLayout drawerLayout = null;
    private ListView drawerList = null;
    private ActionBarDrawerToggle drawerToggle = null;
    private Fragment fragment=null;
    private FragmentManager fragmentManager=null;
    private int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        fragmentManager = getSupportFragmentManager();

        opcionesMenu = new String[] { "BattleShip", "Multiplayer Battle", "Highscores" };
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setBackgroundColor(Color.CYAN);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setOnItemClickListener(opcionesOnItemClickListener);

        drawerList.setAdapter(new ArrayAdapter<String>(getSupportActionBar()
                .getThemedContext(), android.R.layout.simple_list_item_1,
                opcionesMenu));

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_drawer, R.string.app_name,R.string.app_name) {

            public void onDrawerClosed(View view) {
                ActivityCompat.invalidateOptionsMenu(MyActivity.this);
            }

            public void onDrawerOpened(View drawerView) {
                ActivityCompat.invalidateOptionsMenu(MyActivity.this);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        String titulo=null;


        fragment = new Game();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment).commit();


        posicion=0;

        getSupportActionBar().setTitle("Principal");

        drawerLayout.closeDrawer(drawerList);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        boolean menuAbierto = drawerLayout.isDrawerOpen(drawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private OnItemClickListener opcionesOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view,
                                int position, long id) {
            // TODO Auto-generated method stub
            String titulo=null;
            switch (position) {
                case 0:
                    titulo = "BattleShip";
                    if(posicion!=0){

                        fragment = new Game();


                    }

                    break;
                case 1:
                    titulo = "Multijugador";
                    if(posicion!=1){

                        fragment = new MultiplayerGame();


                    }

                    break;
                case 2:
                    titulo = "Scores";
                    if(posicion!=2){

                        fragment = new Scores();


                    }

                    break;

            }

            if(position!=posicion){

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment).commit();

                drawerList.setItemChecked(position, true);
                Log.d("si cambio fragment","si lo cambio");

            }
            posicion=position;
            getSupportActionBar().setTitle(titulo);
            drawerLayout.closeDrawer(drawerList);
        }
    };
}
