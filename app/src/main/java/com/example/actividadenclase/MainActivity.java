package com.example.actividadenclase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText matricula,nombres,libro,dia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matricula=(EditText)findViewById(R.id.matricula);
        nombres=(EditText)findViewById(R.id.nombres);
        libro=(EditText)findViewById(R.id.libro);
        dia=(EditText)findViewById(R.id.dia);
    }
    public void registro(View view){
        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this, "administracion",null,1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String matriculaText= matricula.getText().toString();
        String nombresText= nombres.getText().toString();
        String libroText= libro.getText().toString();
        String diaText= dia.getText().toString();

        if(!matriculaText.isEmpty()||!nombresText.isEmpty() ||!libroText.isEmpty() ||!diaText.isEmpty() ){
            bd.execSQL("insert into estudiantes(id_estudiante,nombres,libro,dia)"+"values ("+matriculaText+",'"+nombresText+"','"+libroText+"','"+diaText+"')");
            bd.close();
            matricula.setText("");
            nombres.setText("");
            dia.setText("");
            libro.setText("");
            Toast.makeText(this,"Se cargaron los datos del estudiante",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,"Por favor, llenar todos los campos",Toast.LENGTH_SHORT).show();
        }
        }
    public void consultarMatricula(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();

        String matriculaText = matricula.getText().toString();

        if(!matriculaText.isEmpty()){
            Cursor fila = bd.rawQuery(
                    "select nombres,libro,dia from estudiantes where " + "id_estudiante= "+ matriculaText,null);
            if(fila.moveToFirst()){
                nombres.setText(fila.getString(0));
                libro.setText(fila.getString(1));
                dia.setText(fila.getString(2));
            }else{
                Toast.makeText(this,"No existe un estudiante con dicha matrícula",Toast.LENGTH_SHORT).show();
            }
            bd.close();
        }else{
            Toast.makeText(this,"Ingrese un número de matrícula",Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarEstudiante(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String matriculaText = matricula.getText().toString();
        if(!matriculaText.isEmpty()){
            bd.execSQL("delete from estudiantes where id_estudiante=" + matriculaText);
            bd.close();
            matricula.setText("");
            nombres.setText("");
            libro.setText("");
            dia.setText("");
            Toast.makeText(this, "Estudiante eliminado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Ingrese numero de matricula", Toast.LENGTH_SHORT).show();
        }
    }
    public void modificarInformacion(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String matriculaText = matricula.getText().toString();
        String nombresText = nombres.getText().toString();
        String libroText = libro.getText().toString();
        String diaText = dia.getText().toString();
        if(!matriculaText.isEmpty()){
            if(nombresText.isEmpty()||libroText.isEmpty()||diaText.isEmpty()){
                Toast.makeText(this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
            }
            try {
                bd.execSQL("update estudiantes set id_estudiante="+ matriculaText + ",nombres='" + nombresText + "',libro='" + libroText + "',dia='" + diaText + "' where id_estudiante=" + matriculaText);
                matricula.setText("");
                nombres.setText("");
                libro.setText("");
                dia.setText("");
                bd.close();
                Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show();
            }
            catch(Exception e){

            }
        }else{
            Toast.makeText(this, "Ingrese una matricula", Toast.LENGTH_SHORT).show();
        }
    }
}
