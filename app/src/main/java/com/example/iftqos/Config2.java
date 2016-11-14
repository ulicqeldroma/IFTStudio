/*
 * En esta clase se puede actualizar la configuracion de la cantidad de datos que utilizara
 * la aplicacion
 */
package com.example.iftqos;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


import android.support.v7.app.ActionBarActivity;
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

public class Config2 extends ActionBarActivity implements OnClickListener{
	String [][] valores;
	String	strLinea ;
	int y = 0;
	TextView mbmes1, mbapp1;
	int mbmes_ac, mbapp_ac;
	Button actualizar;
	File f;
	FileWriter fw;
	File ruta_sd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config2);
		valores = new String [2][2];
		mbmes1 = (TextView) findViewById(R.id.mbmes1);
		mbapp1 = (TextView) findViewById(R.id.mbapp1);
		actualizar = (Button) findViewById(R.id.actualizar_config);
		try
		{
		    File ruta_sd = Environment.getExternalStorageDirectory();
		    FileInputStream fstream = new FileInputStream(ruta_sd.getAbsolutePath()+ "/configuracion.txt");
            DataInputStream entrada = new DataInputStream(fstream);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            
            while ((strLinea = buffer.readLine()) != null)   {            	            	             	 
            	valores[y]= strLinea.split(",");  
            	y++;
            }
		}
		catch (Exception e){ //Catch de excepciones
            System.err.println("Ocurrio un error: " + e.getMessage());
        }
		mbmes1.setText(valores[0][1]);
		mbmes_ac = Integer.parseInt(valores[0][1]);
		mbapp1.setText(valores[1][1]);
		mbapp_ac= Integer.parseInt(valores[1][1]);
		
		actualizar.setOnClickListener(this);
		
	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.actualizar_config){
			int mbmes_act2, mbapp_act2;
			mbmes_act2 = Integer.parseInt(mbmes1.getText().toString());
			mbapp_act2 = Integer.parseInt(mbapp1.getText().toString());
			ruta_sd = Environment.getExternalStorageDirectory();
			f = new File (ruta_sd.getAbsolutePath(), "configuracion.txt");
			if( mbapp_ac < 30) {
				Toast.makeText(this, "Los MB asignados para la aplicación deben ser mayores a 30.", Toast.LENGTH_LONG).show();
			}
			else{
				if(mbapp_ac>mbmes_ac){
					Toast.makeText(this, "Los MB asignados para la aplicación deben ser menores que el total del mes", Toast.LENGTH_LONG).show();
				}
				else{
				try{
					fw = new FileWriter(f);
					fw.write("mbmes," + mbmes_act2 +"\n");
					fw.flush();
					fw.write("mbapp," + mbapp_act2 +"\n");
					fw.flush();
					fw.close();
					Toast.makeText(this, "Datos actualizados",Toast.LENGTH_LONG).show();
				} catch (IOException e){
					e.printStackTrace();
				}
				//Toast.makeText(this,  "yy", Toast.LENGTH_LONG).show();
				Intent i = new Intent(this, MainActivity.class );					
		        startActivity(i);
				}	
			}
		}
	}
}
	
