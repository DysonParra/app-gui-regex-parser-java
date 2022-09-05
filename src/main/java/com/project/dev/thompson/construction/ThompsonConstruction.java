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
package com.project.dev.thompson.construction;

import com.project.dev.regexitem.RegexItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TODO: Definición de {@code ThompsonConstruction}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
//@AllArgsConstructor
//@Builder
@Data
//@NoArgsConstructor
@EqualsAndHashCode(exclude = {"regexItem", "firstState", "lastState"})
public abstract class ThompsonConstruction {

    private String type;                                                        // Tipo de construccion de Thompson.
    private RegexItem regexItem;                                                // RegexItem asociado a la construccion de Thompson actual.
    private int stateQuantity;                                                  // Ccantidad de estados de la construccion de Thompson.
    private State firstState;                                                   // Primer estado de la construccion de Thompson.
    private State lastState;                                                    // Último estado de la construccion de Thompson.

    /**
     * Obtiene el valor en {String} del objeto actual.
     *
     * @return un {String} con la representación del objeto.
     */
    @Override
    public String toString() {
        return Integer.toHexString(hashCode());
    }

    /**
     * FIXME: Definición de {@code concat}. Une la construccion de Thompson actual con una
     * parametro.
     *
     * @param construction es la construccion que se agregara a la actual.
     */
    public void concat(ThompsonConstruction construction) {
        State last = getLastState();                                            // Obtiene el ultimo estado de la contruccion de Thompson actual.
        setLastState(construction.getLastState());                              // Asigna como ultimo estado el ultimo estado de la contruccion de Thompson parametro.
        last.merge(construction.getFirstState());                               // Combina el ultimo estado de la contruccion de Thompson actual con la indicada como parametro.
        setStateQuantity(getStateQuantity() + construction.getStateQuantity() - 1); // Actualiza la cantidad de estados de la construccion de Thompson.
    }

    /**
     * FIXME: Definición de {@code print}. Imprime la construccion de Thompson.
     *
     * @param printStateQuantity indica si imprime la cantidad de estados.
     */
    public void print(boolean printStateQuantity) {
        if (printStateQuantity)                                                 // Si se indico imprimir la cantidad de estados.
            System.out.printf("{%d}\n", getStateQuantity());                    // Imprime la cantidad de estados.
        getFirstState().printState(false);                                      // Imprime la construccion de Thompson acctual.
    }
}
