package com.liontail.arfind.mercadopago;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.liontail.arfind.api.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MPCheckout {
    private Context context;
    private ApiService apiService;
    public MPCheckout(Context context, ApiService apiService) {
        this.context = context;
        this.apiService = apiService;
    }

    public void enviarSolicitud(String idProducto, String idPlan) {


        MPCart request = new MPCart(idProducto, idPlan);
        apiService.createOrdenMercadoPagoAndroid(request).enqueue(new Callback<MPResponse>() {
            @Override
            public void onResponse(Call<MPResponse> call, Response<MPResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String urlPago = response.body().getUrl();
                    if (urlPago != null && !urlPago.isEmpty()) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlPago));
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Error: URL de pago no válida", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    Log.e("MPCheckout", "Error: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<MPResponse> call, Throwable t) {
                Toast.makeText(context, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                Log.e("MPCheckout", "Error en la solicitud: ", t);
            }
        });
    }
}
