/*
 * Esta clase es la encargada de generar el servicio en el cual se ejecuta la obtencion
 * de datos del dispositivo movil, asi como la ejecucion de la prueba de velocidad.
 */

package com.example.iftqos;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class TestService extends Service {
	
	public DatosTelefono datos;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), "Service Created", 1).show();
		datos = new DatosTelefono(this);
		//datos.comenzarLocalizacion();
		//datos.datos_red();
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), "Service Destroy", 1).show();
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), "Service Running ", 1).show();
		datos.comenzarLocalizacion(0);
		datos.datos_red();
		return super.onStartCommand(intent, flags, startId);
	}

}
