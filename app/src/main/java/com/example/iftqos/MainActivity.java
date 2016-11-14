/*
 * En esta clase se crean los tres tabs de la aplicacion, asqui se les asigna el icono y la clase 
 * que van a cargar, ademas se inicia la alarma, esta es la encargada de ejecutar las mediciones 
 * cada determinado tiempo.
 */
package com.example.iftqos;

import java.util.Calendar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TabActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, 10);
		Intent intent = new Intent(this, TestService.class);
		PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
		AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		int i;
		i=180; //30*60*1000
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),i*1000, pintent);
		startService(new Intent(getBaseContext(), TestService.class));
		
		Resources res = getResources(); // Objeto base para poner todos los objetos de tipo Drawable
	    TabHost tabHost = getTabHost();  // Menu TabHost
	    TabHost.TabSpec spec;  // Cada tab del menu
	    //Intent intent;  // se utiliza para agregar cada Activity Class para cada tab del menu
	    
	    intent = new Intent().setClass(this, TestQoS.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	    
	    spec = tabHost.newTabSpec("TEST").setIndicator("",res.getDrawable(R.drawable.iniciar)).setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, HistoricoIFT.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    spec = tabHost.newTabSpec("HISTORICO").setIndicator("", res.getDrawable(R.drawable.historico)).setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, AcercaIFT.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    spec = tabHost.newTabSpec("ACERCA DE").setIndicator("", res.getDrawable(R.drawable.about)).setContent(intent);
	    tabHost.addTab(spec);

	}
	
	@Override
	public void onBackPressed() {
		// Write your code here
		//Toast.makeText(this, "hola2", Toast.LENGTH_SHORT).show();
		//super.onBackPressed();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.config) {
			Intent i = new Intent().setClass(this, Config2.class);
			startActivity(i);
			
			return true;
		}
		if(id == R.id.config){
			
		}
		return super.onOptionsItemSelected(item);
	}
	
}
