/*En esta clase se le pide al usuario que introduzca la capacidad de su plan de datos
 * asi como el total de megas que se van a destinar para la aplicacion, el minimo de megas permitido
 * son 30, una vez validada la cantidad, carga la clase MainActivity
 */
package com.example.iftqos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
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

public class Configuracion extends Activity implements OnClickListener {
	Button aceptar;
	File ruta_sd;
	File f;
	FileWriter fw;
	int opcion =0;
	TextView mbmes, mbapp;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_extra);
		ruta_sd = Environment.getExternalStorageDirectory();
		f = new File (ruta_sd.getAbsolutePath(), "configuracion.txt");
		if(f.exists()){
			opcion=0;
		}
		else
			opcion=1;
		aceptar = (Button) findViewById(R.id.actualizar_config);			
		aceptar.setOnClickListener(this);
        
	}
	
	@Override
	public void onBackPressed() {
		// Write your code here
		Toast.makeText(this, "hola2", Toast.LENGTH_SHORT).show();
		//super.onBackPressed();
	}
	
	public void onClick(View v){
		if(v.getId()==R.id.actualizar_config){
			if (opcion==1){ //no existe configuracion
				mbmes = (TextView) findViewById(R.id.mbmes1);
				int mbmesfinal =  Integer.parseInt(mbmes.getText().toString()); //revisar el cast
				mbapp = (TextView) findViewById(R.id.mbapp1);
				int mbappfinal = Integer.parseInt((String) mbapp.getText().toString());				
				if( mbappfinal < 30) {
					Toast.makeText(this, "Los MB asignados para la aplicacion deben ser mayores a 30.", Toast.LENGTH_LONG).show();
				}
				else{
					if(mbappfinal>mbmesfinal){
						Toast.makeText(this, "Los MB asignados para la aplicacion deben ser menores que el total del mes", Toast.LENGTH_LONG).show();
					}
					else{
					try{
						fw = new FileWriter(f);
						fw.write("mbmes," + mbmesfinal +"\n");
						fw.flush();
						fw.write("mbapp," + mbappfinal +"\n");
						fw.flush();
						fw.close();
					} catch (IOException e){
						e.printStackTrace();
					}
					//Toast.makeText(this,  "yy", Toast.LENGTH_LONG).show();
					Intent i = new Intent(this, MainActivity.class );					
			        startActivity(i);
					}	
				}
			}
			
			if(opcion==0){ // ya existe configuracion
				//Toast.makeText(this,  "xx", Toast.LENGTH_LONG).show();
				Intent i = new Intent(this, MainActivity.class );				
		        startActivity(i);
			}

		}
	}


}
