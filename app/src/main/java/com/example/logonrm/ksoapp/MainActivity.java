package com.example.logonrm.ksoapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    String METHOD_NAME = "Soma";
    String SOAP_ACTION = "";

    String NAMESPACE = "http://heiderlopes.com.br/";
    String SOAP_URL = "http://10.3.1.42:8080/CalculadoraWSService/CalculadoraWS";

    SoapObject request;
    SoapPrimitive calcular;
    ProgressDialog pdialog;


    private EditText etValor1;
    private EditText etValor2;
    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etValor1 = (EditText) findViewById(R.id.numero1);
        etValor2 = (EditText) findViewById(R.id.numero2);
        tvResultado = (TextView) findViewById(R.id.tvResultado);

        CalcularAsync calcularAsync = new CalcularAsync();
        calcularAsync.execute();
    }

    private class CalcularAsync extends AsyncTask<Void, Void, Void>{
        //int v1 = Integer.parseInt(etValor1.getText().toString());
        //int v2 = Integer.parseInt(etValor2.getText().toString());

        @Override
        protected Void doInBackground(Void... params) {

            request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("num1", v1);
            request.addProperty("num2", v2);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_URL);
            try {
                httpTransport.call(SOAP_ACTION, envelope);
                calcular = (SoapPrimitive) envelope.getResponse();
            } catch (Exception e) {
                e.getMessage();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pdialog.dismiss();
            Toast.makeText(getApplicationContext(), "Resultado: " + calcular.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(MainActivity.this);
            pdialog.setMessage("Converting...");
            pdialog.show();
        }
    }
}
