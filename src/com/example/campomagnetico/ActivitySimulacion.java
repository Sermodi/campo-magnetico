package com.example.campomagnetico;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import Apartados.Apartado;
import Apartados.Datos;
import Apartados.GestoraInformacion;
import Apartados.Medida;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class ActivitySimulacion extends Activity {
	
	protected Intent intent;
	/** Un seekBar por apartado*/
	protected SeekBar seekbarA;
	protected SeekBar seekbarB;
	protected SeekBar seekbarC;

	/** Un Textview por seekbar */
	protected TextView seektextA;
	protected TextView seektextB;
	protected TextView seektextC;
	
	/** Un Textview por apartado*/
	protected TextView tesla1;
	protected TextView tesla2;
	protected TextView tesla3;
	
	/** Un boton por apartado */
	protected Button tomarMedA;
	protected Button tomarMedB;
	protected Button tomarMedC;
	
	/** Un Array de Medidas por apartado */
	protected Datos datosA = new Datos(1);
	protected Datos datosB = new Datos(2);
	protected Datos datosC = new Datos(3);
	
	protected TabHost tabs;
	
	// Adaptadores de las tablas
	protected Adapter_Tabla_Apar1 adaptador_apartado1;
	protected Adapter_Tabla adaptador_apartado2;
	protected Adapter_Tabla adaptador_apartado3;
	protected Adapter_Cabecera_Tabla_1 adaptador_cabecera1;
	protected Adapter_Cabecera_Tabla adaptador_cabecera2;
	protected Adapter_Cabecera_Tabla adaptador_cabecera3;
	
	//ListView's
	protected ListView lista_apartado1;
	protected ListView lista_apartado2;
	protected ListView lista_apartado3;
	protected ListView cabecera_apartado1;
	protected ListView cabecera_apartado2;
	protected ListView cabecera_apartado3;
	
	
	//ArrayLists Datos
	
	
	//Activit actual
	protected Activity activity;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simulacion);
		
		activity = this;
		//Inicializacion de los componentes de las listas
		lista_apartado1 = (ListView)findViewById(R.id.lvApartado1);
		lista_apartado2 = (ListView)findViewById(R.id.lvApartado2);
		lista_apartado3 = (ListView)findViewById(R.id.lvApartado3);
		
		cabecera_apartado1 = (ListView)findViewById(R.id.lvCabecera1);
		cabecera_apartado2 = (ListView)findViewById(R.id.lvCabecera2);
		cabecera_apartado3 = (ListView)findViewById(R.id.lvCabecera3);
		
		
		//Creacion de las cabeceras
		ArrayList<String> cabecera1 = new ArrayList<String>();
		cabecera1.add("Intensidad(mA)-Intensidad_C(A)-Campo(T)");
		
		adaptador_cabecera1 = new Adapter_Cabecera_Tabla_1(activity, cabecera1);
		cabecera_apartado1.setAdapter(adaptador_cabecera1);
		
		ArrayList<String> cabecera23 = new ArrayList<String>();
		cabecera23.add("Intensidad(mA):Campo(T)");
		
		adaptador_cabecera2 = new Adapter_Cabecera_Tabla(activity, cabecera23);
		cabecera_apartado2.setAdapter(adaptador_cabecera2);
		
		adaptador_cabecera3 = new Adapter_Cabecera_Tabla(activity, cabecera23);
		cabecera_apartado3.setAdapter(adaptador_cabecera3);
		
		
		
		//Si existen datos en los arrays de la gestora de informacion
		if (!GestoraInformacion.getDatosA().isEmpty()){
			datosA.setArrayDatos(GestoraInformacion.getDatosA());
			adaptador_apartado1 = new Adapter_Tabla_Apar1(activity, GestoraInformacion.getDatosA());
			lista_apartado1.setAdapter(adaptador_apartado1);
		}
		if (!GestoraInformacion.getDatosB().isEmpty()){
			datosB.setArrayDatos(GestoraInformacion.getDatosB());
			adaptador_apartado2 = new Adapter_Tabla(activity, GestoraInformacion.getDatosB());
			lista_apartado2.setAdapter(adaptador_apartado2);
		}
		if (!GestoraInformacion.getDatosC().isEmpty()){
			datosC.setArrayDatos(GestoraInformacion.getDatosC());
			adaptador_apartado3 = new Adapter_Tabla(activity, GestoraInformacion.getDatosC());
			lista_apartado3.setAdapter(adaptador_apartado3);
		}
		
		
		//Tabs
		Resources res = getResources();
		 
		tabs=(TabHost)findViewById(android.R.id.tabhost);
		tabs.setup();
		 
		TabHost.TabSpec spec=tabs.newTabSpec("tab1");
		spec.setContent(R.id.tabConductorA);
		spec.setIndicator("Conductor A", res.getDrawable(android.R.drawable.ic_dialog_map));
		tabs.addTab(spec);
		 
		spec=tabs.newTabSpec("tab2");
		spec.setContent(R.id.tabConductorB);
		spec.setIndicator("Conductor B", res.getDrawable(android.R.drawable.ic_dialog_map));
		tabs.addTab(spec);
		
		spec=tabs.newTabSpec("tab3");
		spec.setContent(R.id.tabConductorC);
		spec.setIndicator("Conductor C", res.getDrawable(android.R.drawable.ic_dialog_map));
		tabs.addTab(spec);
		 
		tabs.setCurrentTab(0);
		//Fin Tabs
		
		//Inicializacion de teslamometros
		final Apartado apartado1 = new Apartado(1);
		final Apartado apartado2 = new Apartado(2);
		final Apartado apartado3 = new Apartado(3);
		
		//Fin Teslamometros
		
		tomarMedA = (Button) findViewById(R.id.buttonA);
		tomarMedB = (Button) findViewById(R.id.buttonB);
		tomarMedC = (Button) findViewById(R.id.buttonC);
		
		//Botones
			tomarMedA.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					int progress = seekbarA.getProgress();
					Medida medA = new Medida(progress * 5, apartado1.getB(progress*5), progress * 5 * 2);
					datosA.add_dato(medA);
					
					GestoraInformacion.setDatosA(datosA.get_array());
					
					
					adaptador_apartado1 = new Adapter_Tabla_Apar1(activity, datosA.get_array());
					lista_apartado1.setAdapter(adaptador_apartado1);
					
				}
			});
				
			tomarMedB.setOnClickListener(new OnClickListener() {
					
				@Override
				public void onClick(View arg0) {
					int progress = seekbarB.getProgress();
					BigDecimal prog = new BigDecimal(-0.04 + 0.0025 * progress);
					prog = prog.setScale(5, RoundingMode.HALF_UP);
					double progDouble = prog.doubleValue();
					Medida medB = new Medida(progDouble, apartado2.getB(progDouble));
					datosB.add_dato(medB);
					
					GestoraInformacion.setDatosB(datosB.get_array());
					
					
					adaptador_apartado2 = new Adapter_Tabla(activity, datosB.get_array());
					lista_apartado2.setAdapter(adaptador_apartado2);
				}
			});

			tomarMedC.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					int progress = seekbarC.getProgress();
					BigDecimal prog = new BigDecimal(-0.04 + 0.0025 * progress);
					prog = prog.setScale(5, RoundingMode.HALF_UP);
					double progDouble = prog.doubleValue();
					Medida medC = new Medida(progDouble, apartado2.getB(progDouble));
					datosC.add_dato(medC);
					
					GestoraInformacion.setDatosC(datosC.get_array());
					adaptador_apartado3 = new Adapter_Tabla(activity, datosC.get_array());
					lista_apartado3.setAdapter(adaptador_apartado3);
				}
			});
		//Fin Botones
		
		
		//Seekbars y sus textview asociados

		seekbarA = (SeekBar) findViewById(R.id.seekBarA);
		seektextA = (TextView) findViewById(R.id.textSeekA);
		tesla1 = (TextView) findViewById(R.id.Tesla1);
		seekbarA.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			/**
			  * Evento encargado del control del estado del seekbar
			  */
			 public void onProgressChanged(SeekBar seekBar, int progress,
			    		boolean fromUser) {
					
			    	// TODO Añadir una variable global que tenga el contenido?
					seektextA.setText("" + (progress*5) + " mA");
					if(progress == 0){
						tesla1.setText("0 (T)");
					}else{
						tesla1.setText(""+ apartado1.getB(progress*5) + " (mT)");
					}
			    }

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// Nada que hacer
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// Nada que hacer
				
			}
			
		});
		
		seekbarB = (SeekBar) findViewById(R.id.seekBarB);
		seektextB = (TextView) findViewById(R.id.textSeekB);
		tesla2 = (TextView) findViewById(R.id.Tesla2);
		seekbarB.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			/**
			  * Evento encargado del control del estado del seekbar
			  */
			 public void onProgressChanged(SeekBar seekBar, int progress,
			    		boolean fromUser) {
					BigDecimal prog = new BigDecimal(-0.04 + 0.0025 * progress);
					prog = prog.setScale(5, RoundingMode.HALF_UP);
			    	// TODO Añadir una variable global que tenga el contenido?
					seektextB.setText("" + (prog.multiply(new BigDecimal(100)).doubleValue()) + " cm");
					tesla2.setText(""+ apartado2.getB(prog.doubleValue()) + " (mT)");
			    }

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// Nada que hacer
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// Nada que hacer
				
			}
			
		});
		
		seekbarC = (SeekBar) findViewById(R.id.seekBarC);
		seektextC = (TextView) findViewById(R.id.textSeekC);
		tesla3 = (TextView) findViewById(R.id.Tesla3);
		seekbarC.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			/**
			  * Evento encargado del control del estado del seekbar
			  */
			 public void onProgressChanged(SeekBar seekBar, int progress,
			    		boolean fromUser) {
					BigDecimal prog = new BigDecimal(-0.04 + 0.0025 * progress);
					prog = prog.setScale(5, RoundingMode.HALF_UP);
			    	// TODO Añadir una variable global que tenga el contenido?
					seektextC.setText("" + (prog.multiply(new BigDecimal(100)).doubleValue()) + " cm");
					tesla3.setText(""+ apartado3.getB(prog.doubleValue()) + " (mT)");
			    }

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// Nada que hacer
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// Nada que hacer
				
			}
			
		});
		
		//Fin SeekViews		
		
		
		//Logica borar una medicion
		this.registerForContextMenu(lista_apartado1);
		lista_apartado1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3) {
				
				final int pos = position;
				
		    	AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		    	builder
		    	.setTitle("Borrar medida")
		    	.setMessage("�Seguro que quieres borrar la medida seleccionada?")
		    	.setIcon(android.R.drawable.ic_dialog_alert)
		    	.setPositiveButton("Si", new DialogInterface.OnClickListener() {
		    	    public void onClick(DialogInterface dialog, int which) {
		    	    	
		    	    	datosA.get_array().remove(pos);
		    	    	GestoraInformacion.setDatosA(datosA.get_array());
		    	    	
						adaptador_apartado1 = new Adapter_Tabla_Apar1(activity, datosA.get_array());
						lista_apartado1.setAdapter(adaptador_apartado1);	
						
		    	    }
		    	})
		    	.setNegativeButton("No", null)
		    	.show();
		    	return false;
				
			}
		});
		
		this.registerForContextMenu(lista_apartado2);
		lista_apartado2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3) {
				
				final int pos = position;
				
		    	AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		    	builder
		    	.setTitle("Borrar medida")
		    	.setMessage("�Seguro que quieres borrar la medida seleccionada?")
		    	.setIcon(android.R.drawable.ic_dialog_alert)
		    	.setPositiveButton("Si", new DialogInterface.OnClickListener() {
		    	    public void onClick(DialogInterface dialog, int which) {			      	
		    	    	datosB.get_array().remove(pos);
		    	    	
		    	    	GestoraInformacion.setDatosB(datosB.get_array());
		    	    	
						adaptador_apartado2 = new Adapter_Tabla(activity, datosB.get_array());
						lista_apartado2.setAdapter(adaptador_apartado2);
		    	    }
		    	})
		    	.setNegativeButton("No", null)
		    	.show();
		    	return false;
				
			}
		});
		
		this.registerForContextMenu(lista_apartado3);
		lista_apartado3.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3) {
				
				final int pos = position;
				
		    	AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		    	builder
		    	.setTitle("Borrar medida")
		    	.setMessage("�Seguro que quieres borrar la medida seleccionada?")
		    	.setIcon(android.R.drawable.ic_dialog_alert)
		    	.setPositiveButton("Si", new DialogInterface.OnClickListener() {
		    	    public void onClick(DialogInterface dialog, int which) {			      	
		    	    	datosC.get_array().remove(pos);
		    	    	
		    	    	GestoraInformacion.setDatosC(datosC.get_array());
						adaptador_apartado3 = new Adapter_Tabla(activity, datosC.get_array());
						lista_apartado3.setAdapter(adaptador_apartado3);	
						
		    	    }
		    	})
		    	.setNegativeButton("No", null)
		    	.show();
		    	return false;
				
			}
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_simulacion, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    
	    	case R.id.action_csv:
	    		
	    		//Almaceno el resultado de la creacion del CSV
	    		int resultado = 100;
	    		
	    		if (tabs.getCurrentTabTag().equals("tab1")){
	    			if (datosA.get_array().isEmpty()){
	    				resultado = 5;
	    			}else{
	    				resultado = datosA.exportar_csv();
	    			}
	    			
	    		}else if (tabs.getCurrentTabTag().equals("tab2")){
	    			if (datosB.get_array().isEmpty()){
	    				resultado = 5;
	    			}else{
	    				resultado = datosB.exportar_csv();
	    			}
	    		}else if (tabs.getCurrentTabTag().equals("tab3")){
	    			if (datosC.get_array().isEmpty()){
	    				resultado = 5;
	    			}else{
	    				resultado = datosC.exportar_csv();
	    			}
	    		}
	    		
    			if (resultado == 1){
    				Toast.makeText(activity, "No se ha encontrado el\nalmacemiento externo (SD)", Toast.LENGTH_LONG).show();
    			}else if (resultado == 2){
    				Toast.makeText(activity, "Existe un archivo con el mismo nombre que la carpeta. "
    						+ "Por favor borre o renombre el archivo", Toast.LENGTH_LONG).show();
    			}else if (resultado == 3){
    				Toast.makeText(activity, "No se ha podido crear el archivo", Toast.LENGTH_LONG).show();
    			}else if (resultado == 4){
    				Toast.makeText(activity, "No se soporta el encoding de archivo", Toast.LENGTH_LONG).show();
    			}else if (resultado == 0){
    				Toast.makeText(activity, "Archivo guardado", Toast.LENGTH_LONG).show();
    			}else if (resultado == 5){
    				Toast.makeText(activity, "No has tomado ninguna medida en este apartado\nHaz alguna medicion antes de exportarlas", Toast.LENGTH_LONG).show();
    			}else {
    				Toast.makeText(activity, "La verdad, no se como has llegado a aqu�", Toast.LENGTH_LONG).show();
    			}
	    		
	    		return true;
	        case R.id.action_inicio:
	        	onBackPressed();
	        	
	            return true;
	        case R.id.action_acercaDe:
	        	intent = new Intent("acerca_de");
	        	startActivity(intent);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
