package com.example.cristian.restaurante;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sarku_japan_layout);

        home fragmentHome = new home();
        platos fragmentPlatos = new platos();
        bebidas fragmentBebidas = new bebidas();
        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

        fragmentList.add(fragmentHome);
        fragmentList.add(fragmentPlatos);
        fragmentList.add(fragmentBebidas);

        MyPagerAdapter mSectionsPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),fragmentList);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.idLocation) {

            Intent i=new Intent(this,gestionMapa.class);
            //startActivityForResult(i,777); mas tarde usare este cuando tenga que comunicar los datos
            startActivity(i);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.idViewMap) {
            Intent e =new Intent(this,MapsActivity.class);
            startActivity(e);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
