package org.lasseufpa.circular;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity
        extends
        AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener,
        CircularListFragment.OnCircularListFragmentInteractionListener,
        Switch.OnCheckedChangeListener
{


    NavigationView nav;
    ViewPager viewPager;
    Toolbar toolbar;
    String[] titles = {"Mapa","Frota Ativa","Paradas"};
    private MainPagerAdapter mainPagerAdapter;

    //repositorio de paradas
    public static final RepositorioParadas repositorioParadas = new RepositorioParadas();
    //repositório de circulares
    public static final RepositorioCircular repositorioCirculares = new RepositorioCircular();
    //repositorio rotas
    public static final RepositorioRotas repositorioRotas = new RepositorioRotas();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });
        fab.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        nav = (NavigationView) findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        nav.setCheckedItem(R.id.nav_map);

        MenuItem switchItem = nav.getMenu().findItem(R.id.nav_switch);
        Switch switchView1 = (Switch) MenuItemCompat.getActionView(switchItem);
        switchView1.setChecked(true);
        startCircularService();
        switchView1.setOnCheckedChangeListener(this);

        MenuItem paradaSwitch = nav.getMenu().findItem(R.id.nav_switch_stop);
        Switch switchViewparada = (Switch) MenuItemCompat.getActionView(paradaSwitch);
        switchViewparada.setChecked(true);
        switchViewparada.setOnCheckedChangeListener(this);

        MenuItem rota1Switch = nav.getMenu().findItem(R.id.nav_switch_rote1);
        Switch switchViewrota1 = (Switch) MenuItemCompat.getActionView(rota1Switch);
        switchViewrota1.setChecked(true);
        switchViewrota1.setOnCheckedChangeListener(this);

        MenuItem rota2switch = nav.getMenu().findItem(R.id.nav_switch_rote2);
        Switch switchViewrota2 = (Switch) MenuItemCompat.getActionView(rota2switch);
        switchViewrota2.setChecked(true);
        switchViewrota2.setOnCheckedChangeListener(this);



        viewPager = (ViewPager) findViewById(R.id.pager);
        mainPagerAdapter = new MainPagerAdapter(this.getSupportFragmentManager(),this);
        viewPager.setAdapter(mainPagerAdapter);
        viewPager.addOnPageChangeListener(this);






    }

    public void startCircularService () {
        Intent in = new Intent("CIRCULAR_LOCATION");
        in.setPackage(this.getPackageName());
        startService(in);
    }

    public void stopCircularService() {
        Intent in = new Intent(MainActivity.this,CircularUpdateService.class);
        in.setPackage(this.getPackageName());
        stopService(in);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopCircularService();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_map) {

            viewPager.setCurrentItem(0);

        }else if (id == R.id.nav_frota) {

            viewPager.setCurrentItem(1);
        } else if (id == R.id.nav_stops) {
            viewPager.setCurrentItem(2);
        } else if (id == R.id.nav_config) {
            viewPager.setCurrentItem(3);
        } else if (id == R.id.nav_feedback) {
            viewPager.setCurrentItem(4);
        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        toolbar.setTitle(titles[position]);
        if (position==1) {
            toolbar.setElevation(0);
        } else {
            toolbar.setElevation(5);
        }
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                nav.setCheckedItem(R.id.nav_map);
                break;
            case 1:
                nav.setCheckedItem(R.id.nav_frota);
                break;
            case 2:
                nav.setCheckedItem(R.id.nav_stops);
                break;
            case 3:
                nav.setCheckedItem(R.id.nav_config);
                break;
            case 4:
                nav.setCheckedItem(R.id.nav_feedback);
                break;
        }

    }

    void CircularObjectInFocus(String CircularName) {
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onListFragmentInteraction(String CircularName) {
        CircularObjectInFocus(CircularName);
    }

    @Override
    public void onCheckedChanged(CompoundButton sSwitch, boolean b) {

       if (sSwitch.getId()==R.id.nav_switch) {
            if (b) {
                startCircularService();
                Log.i("UpdateCircularService","Servico inicial");
            } else {
                stopCircularService();
                Log.i("UpdateCircularService","Servico parado");
            }
        } else if (sSwitch.getId()==R.id.nav_switch_stop) {
           repositorioParadas.setParadasActive(b);
           Log.i("UpdateCircularService","switch paradas mudado");
       } else if (sSwitch.getId()==R.id.nav_switch_rote1) {
           repositorioRotas.setRota1Ativa(b);
           Log.i("UpdateCircularService","switch rota1 mudado");
       } else if (sSwitch.getId()==R.id.nav_switch_rote2) {
           repositorioRotas.setRota2Ativa(b);
           Log.i("UpdateCircularService","switch rota mudado");
       }
        }

}
