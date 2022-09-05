/*
 * @fileoverview {FileName} se encarga de realizar tareas especificas.
 *
 * @version             1.0
 *
 * @author              Dyson Arley Parra Tilano <dysontilano@gmail.com>
 * Copyright (C) Dyson Parra
 *
 * @History v1.0 --- La implementacion de {FileName} fue realizada el 31/07/2022.
 * @Dev - La primera version de {FileName} fue escrita por Dyson A. Parra T.
 */
package com.project.dev.automat;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * TODO: Definición de {@code Partition}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Partition {

    /*
     * Nombre de la partición.
     */
    @Builder.Default
    private String name = "";
    /*
     * Estados que tiene la partición.
     */
    @Builder.Default
    @NonNull
    private ArrayList<Status> statuses = new ArrayList<>();

    /**
     * TODO: Definición de {@code add}.
     *
     * @param state
     * @return
     */
    public boolean add(Status state) {
        return statuses.add(state);
    }

    /**
     * TODO: Definición de {@code contains}.
     *
     * @param state
     * @return
     */
    public boolean contains(Status state) {
        return statuses.contains(state);
    }

    /**
     * TODO: Definición de {@code remove}.
     *
     * @param state
     * @return
     */
    public boolean remove(Status state) {
        return statuses.remove(state);
    }

    /**
     * TODO: Definición de {@code size}.
     *
     * @return
     */
    public int size() {
        return statuses.size();
    }

    /**
     * TODO: Definición de {@code print}.
     *
     * @param spaces
     */
    public void print(int spaces) {
        for (int i = 0; i < spaces; i++)
            System.out.print(" ");
        System.out.println(name);
        statuses.forEach(state -> state.print(true, spaces));
    }
}
