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
import com.project.dev.thompson.construction.State;
import com.project.dev.thompson.construction.ThompsonConstruction;

/**
 * TODO: Definición de {@code ThompsonKleenePlus}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
public class ThompsonKleenePlus extends ThompsonConstruction {

    /**
     * TODO: Definición de {@code ThompsonKleenePlus}.
     *
     * @param codes       indica el codigo actual de cada estado y de cada simbolo a crear.
     * @param regexSymbol indica el regexSymbol asociado al simbolo de la construccion de Thompson.
     */
    public ThompsonKleenePlus(int[] codes, RegexItem regexSymbol) {
        setType("KleenePlus");                                                  // Indica el tipo de construccion de Thompson que es.
        setRegexItem(regexSymbol);                                              // Indica el regxItem asociado a la construccion de Thompson.
        setStateQuantity(4);                                                    // Indica la cantidad de estados de la construccion de Thompson.

        ThompsonConstruction first = new ThompsonNullSymbol(codes);             // Crea simbolo nulo de Thompson.
        codes[0]--;                                                             // Reduce el numero de codigo del proximo estado.
        ThompsonConstruction second = new ThompsonSymbol(codes, regexSymbol);   // Crea simbolo de Thompson.
        codes[0]--;                                                             // Reduce el numero de codigo del proximo estado.
        ThompsonConstruction third = new ThompsonNullSymbol(codes);             // Crea simbolo de Thompson.
        codes[0] -= 2;                                                          // Reduce el numero de codigo del proximo estado.
        ThompsonConstruction fourth = new ThompsonNullSymbol(codes);            // Crea simbolo nulo de Thompson.
        fourth.getFirstState().getNextSymbol(0).setBackSymbol(true);            // Indica que el simbolo en la contruccion es para devolverse.

        State two = first.getLastState();                                       // Obtiene el ultimo estado de first.
        State three;                                                            // Crea un estado.
        State four = fourth.getLastState();                                     // Obtiene el ultimo estado de fourth.

        first.concat(second);                                                   // Concatena first y second.
        three = first.getLastState();                                           // Obtiene el ultimo estado de first.
        first.concat(third);                                                    // Concatena first y third.

        three.merge(fourth.getFirstState());                                    // Combina three con el ultimo estado de fourth.
        two.merge(four);                                                        // Combina two y four.

        setFirstState(first.getFirstState());                                   // Indica cual es el primer estado de la construccion de Thompson.
        setLastState(first.getLastState());                                     // Indica cual es el ultimo estado de la construccion de Thompson.
    }
}