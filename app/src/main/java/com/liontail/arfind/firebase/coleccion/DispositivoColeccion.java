package com.liontail.arfind.firebase.coleccion;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.liontail.arfind.firebase.dto.DispositivoDto;
import com.liontail.arfind.firebase.dto.UsuarioDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DispositivoColeccion {
    private static final String COLLECTION_DISPOSITIVOS = "dispositivos";
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static CompletableFuture<List<DispositivoDto>> obtenerDispositivosAsync() {
        CompletableFuture<List<DispositivoDto>> future = new CompletableFuture<>();

        db.collection(COLLECTION_DISPOSITIVOS)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<DispositivoDto> dispositivos = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            DispositivoDto dispositivo = document.toObject(DispositivoDto.class);
                            dispositivos.add(dispositivo);
                        }
                        future.complete(dispositivos);
                    } else {
                        future.completeExceptionally(task.getException());
                    }
                });

        return future;
    }
}
