package com.liontail.arfind.firebase.coleccion;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.liontail.arfind.firebase.dto.UsuarioDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UsuarioColeccion {
    private static final String COLLECTION_USUARIOS = "usuarios";
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static CompletableFuture<List<UsuarioDto>> obtenerUsuariosAsync() {
        CompletableFuture<List<UsuarioDto>> future = new CompletableFuture<>();

        db.collection(COLLECTION_USUARIOS)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<UsuarioDto> usuarios = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            UsuarioDto usuario = document.toObject(UsuarioDto.class);
                            usuarios.add(usuario);
                        }
                        future.complete(usuarios);
                    } else {
                        future.completeExceptionally(task.getException());
                    }
                });

        return future;
    }
}
