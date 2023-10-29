/*
 * @fileoverview    {State}
 *
 * @version         2.0
 *
 * @author          Dyson Arley Parra Tilano <dysontilano@gmail.com>
 *
 * @copyright       Dyson Parra
 * @see             github.com/DysonParra
 *
 * History
 * @version 1.0     Implementation done.
 * @version 2.0     Documentation added.
 */
package com.project.dev.thompson.construction;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * TODO: Description of {@code State}.
 *
 * @author Dyson Parra
 * @since 11
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"prevSymbols", "nextSymbols"})
public class State {

    @NonNull
    private Integer code;                                                   // Codigo del estado actual.
    @Builder.Default
    private Map<Integer, InputSymbol> prevSymbols = new HashMap<>();    // Desde cuales estados se puede llegar a este.
    @Builder.Default
    private Map<Integer, InputSymbol> nextSymbols = new HashMap<>();    // Hacia cuales estados se puede llegar desde este.

    /**
     * TODO: Description of {@code addPrevSymbol}.
     *
     * @param code
     * @param symbol
     */
    public void addPrevSymbol(int code, InputSymbol symbol) {
        this.prevSymbols.put(code, symbol);
    }

    /**
     * TODO: Description of {@code addNextSymbol}.
     *
     * @param code
     * @param symbol
     */
    public void addNextSymbol(int code, InputSymbol symbol) {
        this.nextSymbols.put(code, symbol);
    }

    /**
     * TODO: Description of {@code getPrevSymbol}.
     *
     * @param number
     * @return
     */
    public InputSymbol getPrevSymbol(int number) {
        return prevSymbols.get(prevSymbols.keySet().toArray()[number]);
    }

    /**
     * TODO: Description of {@code getNextSymbol}.
     *
     * @param number
     * @return
     */
    public InputSymbol getNextSymbol(int number) {
        return nextSymbols.get(nextSymbols.keySet().toArray()[number]);
    }

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
     * FIXME: Description of {@code merge}. Combinar el estado actual con un estado parametro.
     *
     * @param state es el estado que se combinara con el actual.
     */
    public void merge(State state) {
        InputSymbol symbol;

        Map<Integer, InputSymbol> prevState = state.getPrevSymbols();
        Map<Integer, InputSymbol> nextState = state.getNextSymbols();

        if (!prevState.isEmpty()) {
            for (Map.Entry<Integer, InputSymbol> entry : prevState.entrySet()) {
                symbol = entry.getValue();
                addPrevSymbol(symbol.getCode(), symbol);
                entry.getValue().setNextState(this);
            }
            prevState.clear();
        }

        if (!nextState.isEmpty()) {
            for (Map.Entry<Integer, InputSymbol> entry : nextState.entrySet()) {
                symbol = entry.getValue();
                addNextSymbol(symbol.getCode(), symbol);
                entry.getValue().setPrevState(this);
            }

            nextState.clear();
        }
    }

    /**
     * FIXME: Description of {@code printStateDebug}. Imprime el estado cada uno de los simbolos a
     * los que lleva con informacion detallada.
     *
     * @param backSymbol indica si se llego al estado actual devolviendose.
     */
    public void printStateDebug(boolean backSymbol) {
        InputSymbol symbol;

        if (nextSymbols.isEmpty() || backSymbol)
            System.out.printf("([%s][%8s])\n", code, this);
        else
            for (Map.Entry<Integer, InputSymbol> entry : nextSymbols.entrySet()) {
                System.out.printf("([%s][%8s]) -", code, this);

                for (int i = 3 - String.valueOf(code).length(); i > 0; i--)
                    System.out.printf("-", code, this);

                symbol = entry.getValue();
                symbol.printSymbolDebug();
            }
    }

    /**
     * FIXME: Description of {@code printState}. Imprime el estado cada uno de los simbolos a los que
     * lleva.
     *
     * @param backSymbol indica si se llego al estado actual devolviendose.
     */
    public void printState(boolean backSymbol) {
        InputSymbol symbol;

        if (nextSymbols.isEmpty() || backSymbol)
            System.out.printf("(%s)\n", code);
        else
            for (Map.Entry<Integer, InputSymbol> entry : nextSymbols.entrySet()) {
                System.out.printf("(%s) -", code);

                for (int i = 3 - String.valueOf(code).length(); i > 0; i--)
                    System.out.printf("-", code);

                symbol = entry.getValue();
                symbol.printSymbol();
            }
    }
}
