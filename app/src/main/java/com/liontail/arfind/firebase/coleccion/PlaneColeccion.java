package com.liontail.arfind.firebase.coleccion;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.liontail.arfind.firebase.dto.DispositivoDto;
import com.liontail.arfind.firebase.dto.PlanDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PlaneColeccion {
    private static final String COLLECTION_PLAN = "planes";
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static CompletableFuture<List<PlanDto>> obtenerPlanesAsync() {
        CompletableFuture<List<PlanDto>> future = new CompletableFuture<>();

        db.collection(COLLECTION_PLAN)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<PlanDto> planes = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            PlanDto plan = document.toObject(PlanDto.class);
                            planes.add(plan);
                        }
                        future.complete(planes);
                    } else {
                        future.completeExceptionally(task.getException());
                    }
                });

        return future;
    }
}
