package com.liontail.arfind.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.core.content.ContextCompat;

import com.liontail.arfind.R;


public class ModalWifi {
    private static PopupWindow popupWindow;
    public static void verificarYContinuar(Context context) {
        if (hayConexionInternet(context)) {


        } else {
            mostrarModalWifi(context);
        }
    }

    public static boolean hayConexionInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                return true;
            }
        }

        return false;
    }

    public static void mostrarModalWifi(final Context context) {
        // Creamos un BottomSheetDialog
        PopupWindow popupWindow = new PopupWindow(context);

        // Inflamos el diseño personalizado para el diálogo
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.layout_modal_wifi, null);

        // Configuramos el botón en el diálogo
        Button btnRestablecerConexion = dialogView.findViewById(R.id.btnRestablecerConexion);
        btnRestablecerConexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hayConexionInternet(context)) {
                    System.out.println("¡Conexión restablecida!");
                    cerrarModal(popupWindow);
                } else {
                    System.out.println("No se pudo restablecer la conexión. Intenta nuevamente.");
                }
            }
        });


        int colorResId = R.color.successArfind;
        int color = ContextCompat.getColor(context, colorResId);
        btnRestablecerConexion.setBackgroundColor(color);
        popupWindow.setContentView(dialogView);
        popupWindow.setElevation(0);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);



        popupWindow.showAtLocation(dialogView, Gravity.CENTER, 5, 5);
    }


    private static void cerrarModal(PopupWindow popupWindow) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

}

