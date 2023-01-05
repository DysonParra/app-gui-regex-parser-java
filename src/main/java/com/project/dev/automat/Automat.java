/*
 * @fileoverview    {Automat} se encarga de realizar tareas específicas.
 *
 * @version         2.0
 *
 * @author          Dyson Arley Parra Tilano <dysontilano@gmail.com>
 *
 * @copyright       Dyson Parra
 * @see             github.com/DysonParra
 *
 * History
 * @version 1.0     Implementación realizada.
 * @version 2.0     Documentación agregada.
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
