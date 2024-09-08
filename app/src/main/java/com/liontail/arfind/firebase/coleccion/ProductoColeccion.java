package com.liontail.arfind.firebase.coleccion;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import com.liontail.arfind.firebase.dto.ProductoDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ProductoColeccion {
    private static final String COLLECTION_PRODUCTO = "productos";
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static CompletableFuture<List<ProductoDto>> obtenerProductosAsync() {
        CompletableFuture<List<ProductoDto>> future = new CompletableFuture<>();

        db.collection(COLLECTION_PRODUCTO)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<ProductoDto> productos = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ProductoDto producto = document.toObject(ProductoDto.class);
                            productos.add(producto);
                        }
                        future.complete(productos);
                    } else {
                        future.completeExceptionally(task.getException());
                    }
                });

        return future;
    }
}
