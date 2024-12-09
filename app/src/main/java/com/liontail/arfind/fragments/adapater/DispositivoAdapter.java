package com.liontail.arfind.fragments.adapater;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.liontail.arfind.R;
import com.liontail.arfind.api.ApiService;
import com.liontail.arfind.dispositivos.CambiarPlanDto;
import com.liontail.arfind.dispositivos.CambiarPlanResonse;
import com.liontail.arfind.dispositivos.ChangeApodoDto;
import com.liontail.arfind.dispositivos.ChangeApodoResponse;
import com.liontail.arfind.dispositivos.DarseBajaDto;
import com.liontail.arfind.dispositivos.DarseBajaResponse;
import com.liontail.arfind.dispositivos.DispositivoDto;
import com.liontail.arfind.dispositivos.EliminarInvitadoDto;
import com.liontail.arfind.dispositivos.EliminarInvitadoResponse;
import com.liontail.arfind.dispositivos.GeneratedCodigoDto;
import com.liontail.arfind.dispositivos.GeneratedCodigoResponse;
import com.liontail.arfind.firebase.presenter.SharedReferencesPresenter;
import com.liontail.arfind.firebase.singleton.PlanListSingleton;
import com.liontail.arfind.planes.PlanesDto;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DispositivoAdapter extends RecyclerView.Adapter<DispositivoAdapter.ViewHolder> {

    private Context context;
    private List<DispositivoDto> dispositivoList;
    private ApiService apiService;



    // Agregado

    public DispositivoAdapter(Context context, List<DispositivoDto> dispositivoList, ApiService apiService) {
        this.context = context;
        this.dispositivoList = dispositivoList;
        this.apiService = apiService;
    }

    @NonNull
    @Override
    public DispositivoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_dispositivo, parent, false);
        return new DispositivoAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DispositivoAdapter.ViewHolder holder, int position) {
        DispositivoDto dispositivo = dispositivoList.get(position);

        //Log.i("DISPOSITIVOS", String.valueOf(dispositivo.getUbicacion().getLatitude()));
        //Log.i("DISPOSITIVOS", String.valueOf(dispositivo.getUbicacion().getLongitude()));

        holder.txtTitulo.setText(dispositivo.getApodo());

        if(dispositivo.getPlanId() == null){
            holder.txtEstadoPlan.setText("No cuenta con un plan");
            holder.imgConfigurarDispositivo.setVisibility(View.INVISIBLE);
            holder.imgCambiarApodo.setVisibility(View.INVISIBLE);
            holder.imgCodigoCompartir.setVisibility(View.INVISIBLE);
        }

        // Configurar el click para cambiar el apodo
        holder.imgCambiarApodo.setOnClickListener(v -> {
            showCambiarApodo(dispositivo.getId(), dispositivo.getApodo(), position);
        });

        holder.imgCodigoCompartir.setOnClickListener(v -> {
            Log.i("DISPOSITIVOS", dispositivo.getId());
            generateCodigoInvitado(dispositivo.getId());
        });

        holder.imgConfigurarDispositivo.setOnClickListener(v -> {
            Log.i("DISPOSITIVOS", dispositivo.getId());
            showBottomSheetConfigurationCodigo(dispositivo.getId(), dispositivo.getApodo(), position, dispositivo);
        });

    }


    @Override
    public int getItemCount() {
        return dispositivoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtSubtitulo, txtEstadoPlan;
        ImageView imgItem, imgConfigurarDispositivo, imgCambiarApodo, imgCodigoCompartir;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.dispositivo_titulo);
            txtSubtitulo = itemView.findViewById(R.id.dispositivo_descripcion);
            txtEstadoPlan = itemView.findViewById(R.id.txt_estado_plan);


            imgItem = itemView.findViewById(R.id.img_item_plan);
            imgCambiarApodo = itemView.findViewById(R.id.img_cambiar_apodo);
            imgCodigoCompartir = itemView.findViewById(R.id.img_codigo_para_compartir);
            imgConfigurarDispositivo = itemView.findViewById(R.id.img_configuracion_dispositivo);

        }
    }

    // Método para cambiar el apodo del dispositivo
    private void showCambiarApodo(String deviceId, String currentApodo, int position) {
        // Aquí creamos un diálogo donde el usuario podrá escribir el nuevo apodo
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_change_apodo); // Layout para cambiar apodo

        // Vista donde el usuario ingresa el nuevo apodo
        TextView inputNuevoApodo = dialog.findViewById(R.id.apodoACambiar);
        Button btnCambiarApodo = dialog.findViewById(R.id.btnCambiarApodo);





        inputNuevoApodo.setText(currentApodo);


        btnCambiarApodo.setOnClickListener(v -> {
            String nuevoApodo = inputNuevoApodo.getText().toString();
            if (!nuevoApodo.isEmpty()) {
                changeApodo(deviceId, nuevoApodo, position);
                dialog.dismiss();
            } else {
                Toast.makeText(context, "Por favor ingresa un apodo válido.", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    // Llamada a la API para cambiar el apodo del dispositivo
    private void changeApodo(String deviceId, String nuevoApodo, int position) {
        ChangeApodoDto changeApodoDto = new ChangeApodoDto(deviceId, nuevoApodo);

        apiService.updateApodoDispositivo(changeApodoDto).enqueue(new Callback<ChangeApodoResponse>() {
            @Override
            public void onResponse(Call<ChangeApodoResponse> call, Response<ChangeApodoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ChangeApodoResponse changeApodoResponse = response.body();
                    String message = changeApodoResponse.getMessage();
                    //Toast.makeText(context, message, Toast.LENGTH_SHORT).show(); // Mostrar mensaje de éxito

                    // Actualizar el apodo en la lista y notificar al adaptador
                    dispositivoList.get(position).setApodo(nuevoApodo);
                    notifyItemChanged(position);  // Esto actualizará la vista de la tarjeta

                    // Mostrar el bottom sheet de éxito
                    showBottomSheetResponseApodo(true);
                } else {
                    Toast.makeText(context, "Error al cambiar el apodo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangeApodoResponse> call, Throwable t) {
                Toast.makeText(context, "Falló la conexión al servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para mostrar el código generado (ya existe)
    private void generateCodigoInvitado(String deviceId) {
        GeneratedCodigoDto generatedCodigoDto = new GeneratedCodigoDto(deviceId);

        apiService.generateCodigoInvitado(generatedCodigoDto).enqueue(new Callback<GeneratedCodigoResponse>() {
            @Override
            public void onResponse(Call<GeneratedCodigoResponse> call, Response<GeneratedCodigoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GeneratedCodigoResponse generatedCodigoResponse = response.body();
                    String codigoInvitado = generatedCodigoResponse.getCodigo_invitado();
                    showDialogCodigoCompartido(codigoInvitado); // Mostrar el código en un diálogo
                } else {
                    Toast.makeText(context, "Error generating code", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeneratedCodigoResponse> call, Throwable t) {
                Toast.makeText(context, "Failed to connect to the server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para mostrar el código generado (ya existe)
    private void showDialogCodigoCompartido(String codigoInvitado) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_copy_code_dispositivo);
        Button btnCopiarCodigo = dialog.findViewById(R.id.btnCopiarCodigo);

        TextView codigo = dialog.findViewById(R.id.codigo);
        codigo.setText(codigoInvitado);

        btnCopiarCodigo.setOnClickListener(v -> {
            showBottomSheetResponseCodigo(true);
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    // Mostrar el bottom sheet de respuesta para el cambio de apodo
    private void showBottomSheetResponseApodo(boolean success) {
        int bottomSheetLayout = success ? R.layout.bottomsheet_response_ok_apodo : R.layout.bottomsheet_response_error;
        View bottomSheetView = LayoutInflater.from(context).inflate(bottomSheetLayout, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        // Cerrar el bottom sheet después de 2 segundos
        new Handler().postDelayed(bottomSheetDialog::dismiss, 2000);
    }

    // Mostrar el bottom sheet de respuesta para el código generado
    private void showBottomSheetResponseCodigo(boolean success) {
        int bottomSheetLayout = success ? R.layout.bottomsheet_response_ok_codigo : R.layout.bottomsheet_response_error;
        View bottomSheetView = LayoutInflater.from(context).inflate(bottomSheetLayout, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        new Handler().postDelayed(bottomSheetDialog::dismiss, 2000);
    }

    private void showBottomSheetConfigurationCodigo(String id, String apodo, int position, DispositivoDto dispositivo) {
        int bottomSheetLayout = R.layout.bottomsheet_configuration;
        View bottomSheetView = LayoutInflater.from(context).inflate(bottomSheetLayout, null);
        LinearLayout cambiarApodo = bottomSheetView.findViewById(R.id.ly_cambiar_apodo);
        LinearLayout compartirDispositivos = bottomSheetView.findViewById(R.id.ly_compartir_dispositivos);
        LinearLayout cambiarPlan = bottomSheetView.findViewById(R.id.ly_cambiar_plan);
        LinearLayout cancelarPlan = bottomSheetView.findViewById(R.id.ly_cancelar_plan);
        LinearLayout eliminarCompartido = bottomSheetView.findViewById(R.id.ly_eliminar_compartido);

        cambiarApodo.setOnClickListener(v -> {
            showCambiarApodo(id, apodo, position);
        });

        compartirDispositivos.setOnClickListener(v -> {
            generateCodigoInvitado(id);
        });

        eliminarCompartido.setOnClickListener(v -> {
            showDialogEliminarInvitado(id, dispositivo);
        });

        cambiarPlan.setOnClickListener(v -> {
            showDialogCambiarPlan(id, dispositivo);
        });

        cancelarPlan.setOnClickListener(v -> {
            showDialogCancelarPlan(id, dispositivo);
        });

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }


    private void showDialogCambiarPlan(String idDevice, DispositivoDto dispositivo) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_cambiar_plan);
        Button btnCambiarPlan = dialog.findViewById(R.id.btnCambiarPlan);
        final String[] planId = {""};

        List<PlanesDto> planes = PlanListSingleton.Companion.obtenerPlanes();

        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroupOptions);

        for (PlanesDto opcion : planes) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(opcion.getNombre());
            radioButton.setTag(opcion.getId());
            radioGroup.addView(radioButton);
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // Obtener el RadioButton seleccionado
            RadioButton selectedRadioButton = dialog.findViewById(checkedId);
            if (selectedRadioButton != null) {
                // Establecer el planId con el ID del plan seleccionado
                planId[0] = (String) selectedRadioButton.getTag();

                // Cambiar los colores del RadioButton
                ColorStateList colorStateList = new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_checked},
                                new int[]{-android.R.attr.state_checked}
                        },
                        new int[]{
                                ContextCompat.getColor(context, R.color.blueArfind),
                                ContextCompat.getColor(context, R.color.grayArfind)
                        }
                );
                selectedRadioButton.setButtonTintList(colorStateList);
            }
        });

        btnCambiarPlan.setOnClickListener(v -> {
            if (dispositivo.getPlanId().equalsIgnoreCase(planId[0])) {
                showBottomSheetResponseCambioPlan(false);
            } else {
                cambiarPlan(idDevice, planId[0]);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


    private void cambiarPlan(String deviceId, String planId) {
        CambiarPlanDto cambiarPlanDto = new CambiarPlanDto(deviceId, planId);
        apiService.cambiarPlan(cambiarPlanDto).enqueue(new Callback<CambiarPlanResonse>() {
            @Override
            public void onResponse(Call<CambiarPlanResonse> call, Response<CambiarPlanResonse> response) {
                if (response.isSuccessful()) {
                    showBottomSheetResponseCambioPlan(true);
                } else {
                    Toast.makeText(context, "Error al cambiar el plan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CambiarPlanResonse> call, Throwable t) {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBottomSheetResponseCambioPlan(boolean success) {
        int bottomSheetLayout = success ? R.layout.bottomsheet_response_ok_cambio_plan : R.layout.bottomsheet_response_error_cambio_plan;
        View bottomSheetView = LayoutInflater.from(context).inflate(bottomSheetLayout, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        new Handler().postDelayed(bottomSheetDialog::dismiss, 2000);
    }

    private void showDialogEliminarInvitado(String idDevice, DispositivoDto dispositivo) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_eliminar_invitado);
        Button btnCambiarPlan = dialog.findViewById(R.id.btnCambiarPlan);
        final String[] invitadoId = {""};

        List<DispositivoDto.DetalleUsuario> invitados = dispositivo.getDetallesUsuariosInvitados();




        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroupOptions);

        for (DispositivoDto.DetalleUsuario opcion : invitados) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(opcion.getNombre());
            radioButton.setTag(opcion.getId());
            radioGroup.addView(radioButton);
        }

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // Obtener el RadioButton seleccionado
            RadioButton selectedRadioButton = dialog.findViewById(checkedId);
            if (selectedRadioButton != null) {
                // Establecer el planId con el ID del plan seleccionado
                invitadoId[0] = (String) selectedRadioButton.getTag();

                // Cambiar los colores del RadioButton
                ColorStateList colorStateList = new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_checked},
                                new int[]{-android.R.attr.state_checked}
                        },
                        new int[]{
                                ContextCompat.getColor(context, R.color.blueArfind),
                                ContextCompat.getColor(context, R.color.grayArfind)
                        }
                );
                selectedRadioButton.setButtonTintList(colorStateList);
            }
        });
        btnCambiarPlan.setOnClickListener(v -> {
            Log.i("ELIMINAR INVITADO", idDevice);
            Log.i("ELIMINAR INVITADO", invitadoId[0]);
            eliminarInvitados(idDevice, invitadoId[0]);
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void eliminarInvitados(String deviceId, String invitedUsers) {
        EliminarInvitadoDto eliminarInvitadoDto = new EliminarInvitadoDto(deviceId, invitedUsers);
        apiService.eliminarInvitados(eliminarInvitadoDto).enqueue(new Callback<EliminarInvitadoResponse>() {
            @Override
            public void onResponse(Call<EliminarInvitadoResponse> call, Response<EliminarInvitadoResponse> response) {
                showBottomSheetResponseEliminarInvitado(response.isSuccessful());
            }
            @Override
            public void onFailure(Call<EliminarInvitadoResponse> call, Throwable t) {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBottomSheetResponseEliminarInvitado(boolean success) {
        int bottomSheetLayout = success ? R.layout.bottomsheet_response_ok_eliminar_invitado : R.layout.bottomsheet_response_error_eliminar_invitado;
        View bottomSheetView = LayoutInflater.from(context).inflate(bottomSheetLayout, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        new Handler().postDelayed(bottomSheetDialog::dismiss, 2000);
    }

    @SuppressLint("SetTextI18n")
    private void showDialogCancelarPlan(String idDevice, DispositivoDto dispositivo) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_cancelar_plan);
        TextView txtCancelarPlan = dialog.findViewById(R.id.txt_info_cancelar_plan);
        Button btnCambiarPlan = dialog.findViewById(R.id.btnCancelarPlan);

        txtCancelarPlan.setText("Cancelar el plan del dispositivo : " + dispositivo.getApodo());

        btnCambiarPlan.setOnClickListener(view -> {
            if(idDevice != null){
                cancelarPlan(idDevice);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(dialog.getWindow().getAttributes());
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void cancelarPlan(String deviceId) {

        DarseBajaDto darseBajaDto = new DarseBajaDto(deviceId);
        apiService.darseDeBaja(darseBajaDto).enqueue(new Callback<DarseBajaResponse>() {
            @Override
            public void onResponse(Call<DarseBajaResponse> call, Response<DarseBajaResponse> response) {
                showBottomSheetResponseCancelarPlan(response.isSuccessful());

                SharedReferencesPresenter.INSTANCE.cargarSharedReferences(success -> {
                    if (success) {

                        System.out.println("Carga completada con éxito");
                    } else {
                        System.out.println("Error en la carga");
                    }
                    return null;
                });
            }
            @Override
            public void onFailure(Call<DarseBajaResponse> call, Throwable t) {
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBottomSheetResponseCancelarPlan(boolean success) {
        int bottomSheetLayout = success ? R.layout.bottomsheet_response_ok_cancelar_plan : R.layout.bottomsheet_response_error_cancelar_plan;
        View bottomSheetView = LayoutInflater.from(context).inflate(bottomSheetLayout, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        new Handler().postDelayed(bottomSheetDialog::dismiss, 2000);
    }
}
