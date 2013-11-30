package com.example.campomagnetico;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

public class ImagenComponenteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagen_componente);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.imagen_componente, menu);
		String nombre = getIntent().getStringExtra("nombre");
		String desc = getIntent().getStringExtra("descripcion");
		String img = getIntent().getStringExtra("foto");
		int iimg = Integer.parseInt(img);
		Toast toast1 = Toast.makeText(getApplicationContext(),iimg, Toast.LENGTH_SHORT);
		toast1.show();
		ImageView imagenMostrada = (ImageView) findViewById(R.id.imagenComponente);
		imagenMostrada.setImageDrawable(getResources().getDrawable(iimg));
		return true;
	}

}
