/*
 * En esta clase se carga el objetivo de la prueba, asi como el boton de inicio de la prueba
 * al presionar el boton de inicio de la prueba se obtienen los datos del estado del telefono
 * a traves de la clase "DatosTelefono"
 */
package com.example.iftqos;

import java.util.Calendar;


import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TestQoS extends Activity {
	Button prueba;
	public DatosTelefono dtsTel;
	Context x;
	TelephonyManager telephonyManager;
	AndroidPhoneStateListener phoneStateListener;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_qos);
		x = this;
		prueba = (Button) findViewById (R.id.btnRunTest);
		dtsTel = new DatosTelefono(x);
		 prueba.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {					
					dtsTel.comenzarLocalizacion(1);
					dtsTel.datos_red();
					//Toast.makeText(x, "hola mundo", Toast.LENGTH_LONG).show();
				
					
				}
			});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_qo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onBackPressed() {
		// Write your code here
		
		//super.onBackPressed();
	}
}
