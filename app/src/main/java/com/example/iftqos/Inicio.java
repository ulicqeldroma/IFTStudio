/*esta clase es la primera en cargar, aqui se revisa que el dispositivo tenga conectividad
 *asi mismo se revisa si ya se tiene una configuracion previa o es la primera vez que se
 *inicia esta aplicacion, y respectivamente carga la configuracion o carga el inicio de la
 *aplicacion
 */

package com.example.iftqos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Inicio extends Activity {
	
	TextView mbmes, mbapp;
	int opcion =0;
	File ruta_sd;
	File f;
	FileWriter fw;
	TelephonyManager telephonyManager;
	AndroidPhoneStateListener phoneStateListener;
	String estado_cel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carga1);
		ruta_sd = Environment.getExternalStorageDirectory();
		f = new File (ruta_sd.getAbsolutePath(), "configuracion.txt");
		telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);  
		phoneStateListener = new AndroidPhoneStateListener (this);
		telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SERVICE_STATE);
		estado_cel = phoneStateListener.estado();
				
		
		if (f.exists()){
			
			telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);  
			phoneStateListener = new AndroidPhoneStateListener (this);
			telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SERVICE_STATE);
			estado_cel = phoneStateListener.estado();
			if (estado_cel.equals("2")||estado_cel.equals("1")){
				try{
					Toast.makeText(this, "No tienes red", Toast.LENGTH_SHORT).show();
				}
				catch (Exception e){
					e.printStackTrace();
				}
			}
			else
				if(estado_cel.equals("3")){
					Toast.makeText(this, "Debes desactivar el modo avion", Toast.LENGTH_SHORT).show();
				}
				else
					if(estado_cel.equals("0")){
						Intent i = new Intent(this, MainActivity.class );					
				        startActivity(i);
					}				
		}
		
		else {

			telephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);  
			phoneStateListener = new AndroidPhoneStateListener (this);
			telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SERVICE_STATE);
			estado_cel = phoneStateListener.estado();
			if (estado_cel.equals("2")||estado_cel.equals("1")){
				try{
					Toast.makeText(this, "No tienes red", Toast.LENGTH_SHORT).show();
				}
				catch (Exception e){
					e.printStackTrace();
				}
			}
			else
				if(estado_cel.equals("3")){
					Toast.makeText(this, "Debes desactivar el modo avion", Toast.LENGTH_SHORT).show();
				}
				else
					if(estado_cel.equals("0")){
						Intent i = new Intent(this, Configuracion.class );					
						startActivity(i);
					}
		}
	}
}
