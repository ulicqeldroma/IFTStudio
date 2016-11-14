/*
 * Para poder obtener la potencia de senal y el estado del servicio del telefono es necesario estar
 * revisando el cambio en el estado del celular, esta clase se encarga de detectar los cambios de
 * senal y de conectividad.
 */
package com.example.iftqos;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.widget.Toast;

public class AndroidPhoneStateListener extends PhoneStateListener {
	
	 public static int signalStrengthValue;
	 public static int valor;
	 Context context;
	 
	 AndroidPhoneStateListener(Context context){
		 this.context = context;
	 }

     @Override
     public void onSignalStrengthsChanged(SignalStrength signalStrength) {
         super.onSignalStrengthsChanged(signalStrength);
         if (signalStrength.isGsm()) {
             if (signalStrength.getGsmSignalStrength() != 99){
                 signalStrengthValue = signalStrength.getGsmSignalStrength() * 2 - 113;
                 //Toast.makeText(context, "GSM: " + signalStrengthValue, Toast.LENGTH_SHORT).show();
                 //return signalStrength+"";
             }
             else{
                 signalStrengthValue = signalStrength.getGsmSignalStrength();
                 //Toast.makeText(context, "GSM: " + signalStrengthValue, Toast.LENGTH_SHORT).show();
                 //return signalStrength+"";
             }
         } else {
        	 //Toast.makeText(context, "Cdma: " + signalStrengthValue, Toast.LENGTH_SHORT).show();
             signalStrengthValue = signalStrength.getCdmaDbm();

         }
     }
     
     public void onServiceStateChanged(ServiceState serviceState) {
			// Log.d(CellFinderMapActivity.CELLFINDER,
			// "MyPhoneStateListener.onServiceStateChanged()");
			super.onServiceStateChanged(serviceState);

			int state = serviceState.getState();
			System.out.println("Estado: " + state);
			switch (state) {
			case ServiceState.STATE_IN_SERVICE:
				valor = state;
				//Toast.makeText(context, "holaaa1: " + valor ,Toast.LENGTH_SHORT).show();
				break;
			case ServiceState.STATE_EMERGENCY_ONLY:
				valor = state;
				//Toast.makeText(context, "holaaa2: " + valor, Toast.LENGTH_SHORT).show();
				break;
			case ServiceState.STATE_POWER_OFF:
				valor = state;
				//Toast.makeText(context, "holaaa3: " + valor, Toast.LENGTH_SHORT).show();
				break;
			case ServiceState.STATE_OUT_OF_SERVICE:
				valor = state;
				//Toast.makeText(context, "holaaa4: " + valor, Toast.LENGTH_SHORT).show();
				break;
			}
		}
  
     
     public String estado(){
    	 System.out.println("hola valor : " + valor);
    	 return valor+"";
     }

     
     public String valor_potencia(){
    	 return signalStrengthValue+"";
     }

}
