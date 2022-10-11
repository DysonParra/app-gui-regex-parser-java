/*
 * @fileoverview {Automat} se encarga de realizar tareas especificas.
 *
 * @version             1.0
 *
 * @author              Dyson Arley Parra Tilano <dysontilano@gmail.com>
 * Copyright (C) Dyson Parra
 *
 * @History v1.0 --- La implementacion de {Automat} fue realizada el 31/07/2022.
 * @Dev - La primera version de {Automat} fue escrita por Dyson A. Parra T.
 */
package com.project.dev.automat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO: Definición de {@code Automat}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Automat {

    /* Codigo del automata */
    private String code;
    /* Estados del automata */
    @Builder.Default
    private ArrayList<Status> statuses = new ArrayList<>();
    /* Simbolos de entrada */
    @Builder.Default
    private Map<String, String> inputSymbols = new HashMap<>();
    /* Caracter de fin de secuencia */
    private String endSequence;
    /* Caracter de secuencia nula */
    private String nullSequence;

    /**
     * TODO: Definición de {@code getEndSequenceChar}.
     *
     * @return
     */
    public char getEndSequenceChar() {
        return endSequence.charAt(0);
    }

    /**
     * TODO: Definición de {@code getNullSequenceChar}.
     *
     * @return
     */
    public char getNullSequenceChar() {
        return nullSequence.charAt(0);
    }

}
