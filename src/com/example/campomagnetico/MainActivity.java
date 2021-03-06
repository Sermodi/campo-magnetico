package com.example.campomagnetico;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {	

	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Componentes
		final Button bGuion = (Button)findViewById(R.id.bGuion);
		final Button bMontaje = (Button)findViewById(R.id.bMontaje);
		final Button bComponentes = (Button)findViewById(R.id.bComponentes);
		final Button bSimulacion = (Button)findViewById(R.id.bSimulacion);
		final Button bAyuda = (Button)findViewById(R.id.bAyuda);
		
		//Botones con las llamadas a otras activities
		bGuion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				intent = new Intent("guion");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		bMontaje.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				intent = new Intent("montaje");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);	
			}
		});
		
		bComponentes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				intent = new Intent("componentes");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		bSimulacion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				intent = new Intent("simulacion");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
		
		bAyuda.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				intent = new Intent("AAyuda");
				startActivity(intent);
				
			}
		});
		//Fin de los botones
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch (item.getItemId()) {
	        case R.id.action_acercaDe:
	        	intent = new Intent("acerca_de");
	        	startActivity(intent);
	        	return true;
	        case R.id.action_ayuda:
	        	intent = new Intent("AAyuda");
	        	intent.putExtra("tab", 0);
	        	startActivity(intent);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
