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
package com.project.dev.thompson.construction.type;

import com.project.dev.regexitem.RegexItem;
import com.project.dev.thompson.construction.ThompsonConstruction;

/**
 * TODO: Definición de {@code ThompsonConcat}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
public class ThompsonConcat extends ThompsonConstruction {

    /**
     * TODO: Definición de {@code ThompsonConcat}.
     *
     * @param codes  indica el codigo actual de cada estado y de cada simbolo a crear.
     * @param groupC es un RegexItem de tipo concatenacion.
     */
    public ThompsonConcat(int[] codes, RegexItem groupC) {
        setType("Concat");                                                      // Indica el tipo de construccion de Thompson que es.
        setRegexItem(groupC);                                                   // Indica el regxItem asociado a la construccion de Thompson.

        RegexItem aux = groupC.getFirstChild();                                 // Obtiene el primer grupo del regexItem de tipo concatenacion.

        ThompsonConstruction first = new ThompsonSymbol(codes, aux);            // Crea simbolo de Thompson.
        setFirstState(first.getFirstState());                                   // Indica cual es el primer estado de la construccion de Thompson.

        aux = aux.getNext();                                                    // Obtiene el siguiente grupo.

        while (aux != null) {                                                   // Recorre los grupos.
            codes[0]--;                                                         // Reduce el numero de codigo del proximo estado.
            ThompsonConstruction nullSymbol = new ThompsonNullSymbol(codes);    // Crea simbolo nulo de Thompson.
            codes[0]--;                                                         // Reduce el numero de codigo del proximo estado.
            ThompsonConstruction group = new ThompsonSymbol(codes, aux);        // Crea simbolo de Thompson.

            nullSymbol.concat(group);                                           // Concatena nullSymbol con group.
            first.concat(nullSymbol);                                           // Concatena first con nullSymbol.

            setLastState(first.getLastState());                                 // Actualiza el ultimo estado de la contruccion de Thompson.
            aux = aux.getNext();                                                // Obtiene el siguiente grupo.
        }

        setStateQuantity(first.getStateQuantity());                             // Indica la cantidad de estados de la construccion de Thompson.
    }
}
