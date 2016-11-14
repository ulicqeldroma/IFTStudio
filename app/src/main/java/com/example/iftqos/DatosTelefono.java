/*
 * En esta clase se obtienen los datos del telefono, asi mismo se genera un json el cual sera enviado
 * a la base de datos la cual almacena los registros del estado del celular.
 * 
 * Tambien se considera una variable llamada opcion_prueba la cual tomara el valor de 0 si lla prueba
 * es solicitada a traves del servicio, y tendra el valor de 1 si la prueba es solicitada por el
 * usuario.
 */
package com.example.iftqos;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;

public class DatosTelefono {
	public Context context;
	int TIMEOUT_MILLISEC = 10000;
	int bandera = 0;
	int botoncito=0;
	private String operador_red ="", imei_red ="", so_red = "", tipo_red = "", hora_red = "", fecha_red = "", MAC_red = "", potencia_red ="";
	private String lat="", lon="";
	private String valor_subida="", unidad_subida="", valor_bajada="", unidad_bajada="", bajada ="0", subida="0", latencia="0", perdida_paquetes="0";
	private int anio = 0, mes=0, dia=0, hora=0, minuto=0, id_proveedor=0, mcc=0,mnc=0,idradiobase=0, potencia_wifi=0, release=0;;
	private String [] aux = new String[2];
	private int opcion_prueba;
	private int tp = 0;
	private LocationManager locManager;
	private LocationListener locListener;
	private String cadena_final="";
	public String version_so ="", modelo ="";
	String estado_cel;
	
	TelephonyManager telephonyManager;
	AndroidPhoneStateListener phoneStateListener;
	public String tipo_conexion="";
	public Date horaActual=new Date();
	
	File ruta_sd;
	File f;
	FileWriter fw;
	List<String> lista = new ArrayList<String>();
	String strLinea;
	int y=0;
	String [][]cadenas;
	
	DatosTelefono(){
		
	}
	
	DatosTelefono(Context ctx){
		context = ctx;
		telephonyManager = (TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);  
		phoneStateListener = new AndroidPhoneStateListener (context);
		telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		potencia_red = phoneStateListener.valor_potencia();				
	}
	
	public void datos_red(){
		telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		potencia_red = phoneStateListener.valor_potencia();	
		//Toast.makeText(context, potencia_red, Toast.LENGTH_LONG).show();
		
		TipoRed tipofinal = new TipoRed();
		Wifi wifi = new Wifi(context);
		
		
		operador_red = telephonyManager.getNetworkOperatorName();
		//Toast.makeText(context, operador, Toast.LENGTH_SHORT).show();

		imei_red = telephonyManager.getDeviceId();
		//Toast.makeText(context, imei, Toast.LENGTH_SHORT).show();
		
		so_red = telephonyManager.getDeviceSoftwareVersion();
		//Toast.makeText(context, so, Toast.LENGTH_SHORT).show();
		
		tp = telephonyManager.getNetworkType();
		tipo_red = tipofinal.tipo_red(tp);
		//Toast.makeText(context, tipo, Toast.LENGTH_SHORT).show();
		
		anio=0;
		mes =0;
		dia=0;
		horaActual = new Date();

		if((horaActual.getMonth()+1)<=9){
			System.out.println("holis1");
			anio = (horaActual.getYear()+1900);			
			String x1 = "0" + (horaActual.getMonth()+1);  
			mes = Integer.parseInt(x1);
			//mes = x1;
			dia = horaActual.getDate();
			fecha_red = anio + "/" + mes + "/" + dia;
			}
		else{
			anio =horaActual.getYear()+1900;
			mes = horaActual.getMonth()+1;
			dia = horaActual.getDate();
			fecha_red = anio + "/" + mes + "/" + dia;
		}
		hora = horaActual.getHours();
		//Toast.makeText(context, hora + "", Toast.LENGTH_SHORT).show();
		minuto = horaActual.getMinutes();
		//Toast.makeText(context, minuto + "", Toast.LENGTH_SHORT).show();
		
		//Toast.makeText(context, fecha, Toast.LENGTH_SHORT).show();
		//Toast.makeText(context, hora, Toast.LENGTH_SHORT).show();
		 
		MAC_red = wifi.estado_wifi();
		//Toast.makeText(context,MAC_red, Toast.LENGTH_SHORT).show();
		version_so = Build.VERSION.RELEASE;
		modelo = Build.MANUFACTURER + " " + Build.MODEL;
		
		
		//Toast.makeText(context, "CODENAME: " + Build.VERSION.CODENAME, Toast.LENGTH_SHORT).show();

		ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		try{
			int tipo = mNetworkInfo.getType();			
			if(tipo == ConnectivityManager.TYPE_WIFI){
				
				tipo_conexion = "WiFi";		
				
				WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
				potencia_wifi = wifiManager.getConnectionInfo().getRssi();															
			}
			else if (tipo == ConnectivityManager.TYPE_MOBILE){
				tipo_conexion = "Mobile";					
			}
		}
		catch (Exception e) {
			tipo_conexion=null;
			e.printStackTrace();
			tipo_conexion = "Desc";
		}
		
		telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);  
		phoneStateListener = new AndroidPhoneStateListener (context);
		telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SERVICE_STATE);
		estado_cel = phoneStateListener.estado();
		if (estado_cel.equals("2")||estado_cel.equals("1")){
			try{
				Toast.makeText(context, "No tienes red", Toast.LENGTH_SHORT).show();
				mcc=334;
				mnc=0;
				idradiobase = 0;
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		else
			if(estado_cel.equals("3")){
				Toast.makeText(context, "Debes desactivar el modo avi�n", Toast.LENGTH_SHORT).show();
				mcc=334;
				mnc=0;
				idradiobase = 0;
			}
			else
				if(estado_cel.equals("0")){
					GsmCellLocation cellLocation = (GsmCellLocation)telephonyManager.getCellLocation();			       
					String networkOperator = telephonyManager.getNetworkOperator();
					mcc = Integer.parseInt(networkOperator.substring(0, 3));
					id_proveedor = mcc;
					mnc = Integer.parseInt(networkOperator.substring(3));
					idradiobase = cellLocation.getCid();
					int lac = cellLocation.getLac();
				}
		
		
		cadena_final = fecha_red + "," + hora_red + "," + bajada + "," + subida + "," + potencia_red + "," + opcion_prueba + "," + tipo_red;
		

		try
		{
		    File ruta_sd = Environment.getExternalStorageDirectory();
		    //File x = new File();
		    
		    
		    File f = new File(ruta_sd.getAbsolutePath(), "registro_datos.txt");	
		    
		    if(f.exists()){
		    	FileWriter fw = new FileWriter(f,true);
		    	fw.write(cadena_final + "\n");
		    	fw.flush();
		    	fw.close();
		    }
		    else{
		    	FileWriter fw = new FileWriter(f);
		    	fw.write(cadena_final + "\n");
		    	fw.flush();
		    	fw.close();
		    }			    		 
		}
		catch (Exception ex)
		{
		    Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
		    System.out.println("valio");
		}		
		
	}

	
	private void mostrarPosicion(Location loc) {
		String cadenota;
		telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		potencia_red = phoneStateListener.valor_potencia();
		System.out.println("cuatro: " + potencia_red);
		if(loc != null)
		{
			System.out.println("IMEI: " + imei_red);
			System.out.println("MAC: " + MAC_red);
			System.out.println("OPERADOR: " + operador_red);
			System.out.println("FECHA: " + fecha_red);
			System.out.println("HORA: " + hora_red);
			System.out.println("TIPO DE RED: " + tipo_red);
			System.out.println("POTENCIA: " + potencia_red);
			
			System.out.println("LATITUD: " + String.valueOf(loc.getLatitude()));
			lat = String.valueOf(loc.getLatitude());
			//lblPrecision.setText("Precision: " + String.valueOf(loc.getAccuracy()));
			System.out.println("LONGITUD: " + String.valueOf(loc.getLongitude()));
			lon = String.valueOf(loc.getLongitude());
			
			ConnectivityManager conManager = (ConnectivityManager) context.getSystemService (context.CONNECTIVITY_SERVICE);
			NetworkInfo mwifi = conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			NetworkInfo movil = conManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			ruta_sd = Environment.getExternalStorageDirectory();
			f = new File (ruta_sd.getAbsolutePath(), "datos_no_reg.txt");
			
			if (mwifi.isConnected()|| movil.isConnected()){
				carga_base_sin_archivo(); //si hay conexion va a cargar directos los datos				
				//revisa si el archivo existe
				if(f.exists()){ //si existe debe extraer los datos, separarlos y enviarlos a la base
										
					try{
					    FileInputStream fstream = new FileInputStream(ruta_sd.getAbsolutePath()+ "/datos_no_reg.txt");
			            DataInputStream entrada = new DataInputStream(fstream);
			            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
			            
			            while ((strLinea = buffer.readLine()) != null)   {
			            	Toast.makeText(context, buffer.readLine(), Toast.LENGTH_SHORT).show();
			            	lista.add(strLinea);
			            	/*Toast.makeText(context, y, Toast.LENGTH_SHORT).show();
			            	lista.add(strLinea);			            	
			            	y++; //cuantos datos hay*/
			            }
			            for(String cadena:lista){
			            	carga_base_con_archivo(cadena);
			            	Toast.makeText(context, "hola", Toast.LENGTH_SHORT).show();
			            }
			            f.delete();
					}
					catch (Exception e){
						
					}
				}
				else{
					//si no existe el archivo quiere decir que no hay registros que almacenar
					
				}
			}
			else{
				//Toast.makeText(context, "Creando_Archivo", Toast.LENGTH_LONG).show();
				
				String cadena_datos = lat +  "," + lon +  "," + anio +  "," + mes +  "," + dia +  "," + hora +  "," + minuto +  "," + imei_red +  "," + MAC_red +  "," + operador_red +  "," + id_proveedor +  "," + tipo_red +  "," + potencia_wifi +  "," + potencia_red +  "," + version_so +  "," + modelo +  "," + mcc +  "," + mnc +  "," + idradiobase +  "," + tipo_conexion +  "," + bajada +  "," + subida +  "," + latencia +  "," + perdida_paquetes; 
				
				try{
					if(f.exists()){
						
						FileWriter fw = new FileWriter(f,true);
						fw.write(cadena_datos + "\n");
						fw.flush();
						fw.close();
					}
					else{
						FileWriter fw = new FileWriter(f);
						fw.write(cadena_datos + "\n");
						fw.flush();
						fw.close();
					}
				}
				catch (Exception e){}

				//almacena datos en archivo
			}
			//conexion.insertar(MAC_red, imei_red, operador_red, fecha_red, hora_red, String.valueOf(loc.getLatitude()), String.valueOf(loc.getLongitude()), "0", tipo_red, "0");
			
			Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
		}
		else
		{
			System.out.println("IMEI: " + imei_red);
			System.out.println("MAC: " + MAC_red);
			System.out.println("OPERADOR: " + operador_red);
			System.out.println("FECHA: " + fecha_red);
			System.out.println("HORA: " + hora_red);
			System.out.println("TIPO DE RED: " + tipo_red);
			System.out.println("POTENCIA: " + potencia_red);
			
			lat = "0";
			lon = "0";
			System.out.println("LATITUD: (sin_datos)");
			System.out.println("LONGITUD: (sin_datos)");
			carga_base_sin_archivo();
			//carga_base();
			//lblPrecision.setText("Precision: (sin_datos)");
		}
	}
	
	public class Data {
		// private List<User> users;
		public List<User> users;

		// +getters/setters
	}

	static class User {
		String idusers;
		String UserName;
		String FullName;

		public String getUserName() {
			return UserName;
		}

		public String getidusers() {
			return idusers;
		}

		public String getFullName() {
			return FullName;
		}

		public void setUserName(String value) {
			UserName = value;
		}

		public void setidusers(String value) {
			idusers = value;
		}

		public void setFullName(String value) {
			FullName = value;
		}
	}

	
	public void carga_base_sin_archivo(){
		
			try {
				JSONObject json = new JSONObject();
			
				json.put("latitud",lat);
				json.put("longitud",lon);
				json.put("anio",anio);
				json.put("mes",mes); 
				json.put("dia",dia); 
				json.put("hora",hora);
				json.put("minuto",minuto);
				json.put("imei",imei_red);
				json.put("dir_mac",MAC_red);
				json.put("proveedor",operador_red);
				json.put("id_proveedor",id_proveedor);
				json.put("tipo_red",tipo_red);
				json.put("intensidad_senial_wifi",potencia_wifi);
				json.put("intensidad_senial_movil",potencia_red);  
				json.put("os_version",version_so);
				json.put("modelo",modelo);
				json.put("release",null);
				json.put("mcc",mcc);
				json.put("mnc",mnc); 
				json.put("id_radiobase",idradiobase); 
				json.put("tipo_conexion",tipo_conexion);
				json.put("bajada",bajada);
				json.put("subida",subida);
				json.put("latencia",latencia); 
				json.put("perdida_paquetes",perdida_paquetes); 
				json.put("jitter",null);
			
				HttpParams httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
				HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
				HttpClient client = new DefaultHttpClient(httpParams);
			//
			//String url = "http://10.0.2.2:8080/sample1/webservice2.php?json={\"UserName\":1,\"FullName\":2}";
				String url = "http://54.191.193.31:80/ws.php";

				HttpPost request = new HttpPost(url);
				request.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
				request.setHeader("json", json.toString());
				HttpResponse response = client.execute(request);
				HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
				if (entity != null) {
					InputStream instream = entity.getContent();

					String result = RestClient.convertStreamToString(instream);
					Log.i("Read from server", result);
					Toast.makeText(context,  result, Toast.LENGTH_LONG).show();
				}


			} catch (Throwable t) {
				Toast.makeText(context, "Request failed: " + t.toString(),Toast.LENGTH_LONG).show();
			}
		

	}
	
	
	public void carga_base_con_archivo(String  datos){
		String [] datos_sep = new String[25];
		datos_sep = datos.split(",");
		
		
		try {
			JSONObject json = new JSONObject();
		
			json.put("latitud",datos_sep[0]);
			json.put("longitud",datos_sep[1]);
			json.put("anio",datos_sep[2]);
			json.put("mes",datos_sep[3]); 
			json.put("dia",datos_sep[4]); 
			json.put("hora",datos_sep[5]);
			json.put("minuto",datos_sep[6]);
			json.put("imei",datos_sep[7]);
			json.put("dir_mac",datos_sep[8]);
			json.put("proveedor",datos_sep[9]);
			json.put("id_proveedor",datos_sep[10]);
			json.put("tipo_red",datos_sep[11]);
			json.put("intensidad_senial_wifi",datos_sep[12]);
			json.put("intensidad_senial_movil",datos_sep[13]);  
			json.put("os_version",datos_sep[14]);
			json.put("modelo",datos_sep[15]);
			json.put("release",null);
			json.put("mcc",datos_sep[16]);
			json.put("mnc",datos_sep[17]); 
			json.put("id_radiobase",datos_sep[18]); 
			json.put("tipo_conexion",datos_sep[19]);
			json.put("bajada",datos_sep[20]);
			json.put("subida",datos_sep[21]);
			json.put("latencia",datos_sep[22]); 
			json.put("perdida_paquetes",datos_sep[23]); 
			json.put("jitter",null);
		
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpClient client = new DefaultHttpClient(httpParams);
		//
		//String url = "http://10.0.2.2:8080/sample1/webservice2.php?json={\"UserName\":1,\"FullName\":2}";
			String url = "http://54.191.193.31:80/ws.php";

			HttpPost request = new HttpPost(url);
			request.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
			request.setHeader("json", json.toString());
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
		// If the response does not enclose an entity, there is no need
			if (entity != null) {
				InputStream instream = entity.getContent();

				String result = RestClient.convertStreamToString(instream);
				Log.i("Read from server", result);
				Toast.makeText(context,  result, Toast.LENGTH_LONG).show();
			}


		} catch (Throwable t) {
			Toast.makeText(context, "Request failed: " + t.toString(),Toast.LENGTH_LONG).show();
		}
	

}
	

	public void comenzarLocalizacion(int opcion)
	{
		opcion_prueba = opcion;
		telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		potencia_red = phoneStateListener.valor_potencia();
		System.out.println("dos: " + potencia_red);
		//Obtenemos una referencia al LocationManager
		locManager = 
			(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		
		//Obtenemos la ultima posicion conocida
		Location loc = 
			locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		//Mostramos la ultima posicion conocida
		datos_red();
		mostrarPosicion(loc);
		//cargar senal
		
		//Nos registramos para recibir actualizaciones de la posicion
		locListener = new LocationListener() {
	    	public void onLocationChanged(Location location) {
	    		datos_red();
	    		mostrarPosicion(location);
	    	}
	    	public void onProviderDisabled(String provider){
	    		
	    		//lblEstado.setText("Provider OFF");
	    	}
	    	public void onProviderEnabled(String provider){
	    		//lblEstado.setText("Provider ON ");
	    	}
	    	public void onStatusChanged(String provider, int status, Bundle extras){
	    		Log.i("", "Provider Status: " + status);
	    		//lblEstado.setText("Provider Status: " + status);
	    	}
		};
		
		locManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER, 300000, 0, locListener);
	}

}
