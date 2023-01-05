/*
 * @fileoverview    {StatePrinter} se encarga de realizar tareas específicas.
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
package com.project.dev.stateoperation;

import com.project.dev.thompson.construction.InputSymbol;
import com.project.dev.thompson.construction.State;
import java.util.Map;

/**
 * TODO: Definición de {@code StatePrinter}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
public class StatePrinter {

    /**
     * FIXME: Definición de {@code printStates}. Imprime un estado y los estados a los que se puede
     * llegar desde este con informacion detallada.
     *
     * @param firstState indica el primer estado.
     * @param backSymbol indica si al estado se llego desde un estado posterior (devolviendose).
     */
    public static void printStates(State firstState, boolean backSymbol) {
        InputSymbol symbol;

        if (firstState.getNextSymbols().isEmpty() || backSymbol)
            System.out.printf("([%s][%8s])\n", firstState.getCode(), firstState);
        else
            for (Map.Entry<Integer, InputSymbol> entry : firstState.getNextSymbols().entrySet()) {
                System.out.printf("([%s][%8s]) -", firstState.getCode(), firstState);

                for (int i = 3 - String.valueOf(firstState.getCode()).length(); i > 0; i--)
                    System.out.printf("-", firstState.getCode(), firstState);

                symbol = entry.getValue();
                System.out.printf("[%2s][%8s][%8s]---> ", symbol.getCode(), symbol, symbol.getRegexItem());
                printStates(symbol.getNextState(), symbol.isBackSymbol());
            }
    }

    /**
     * FIXME: Definición de {@code printStatesValues}. Imprime un estado y los estados a los que se
     * puede llegar desde este.
     *
     * @param firstState indica el primer estado.
     * @param backSymbol indica si al estado se llego desde un estado posterior (devolviendose).
     */
    public static void printStatesValues(State firstState, boolean backSymbol) {
        InputSymbol symbol;

        if (firstState.getNextSymbols().isEmpty() || backSymbol)
            System.out.printf("(%s)\n", firstState.getCode());
        else
            for (Map.Entry<Integer, InputSymbol> entry : firstState.getNextSymbols().entrySet()) {
                System.out.printf("(%s) -", firstState.getCode());

                for (int i = 3 - String.valueOf(firstState.getCode()).length(); i > 0; i--)
                    System.out.printf("-", firstState.getCode());

                symbol = entry.getValue();

                System.out.printf("[%2s][%s]---> ", symbol.getCode(), symbol.getRegexItem().getValue());

                printStatesValues(symbol.getNextState(), symbol.isBackSymbol());
            }
    }

    /**
     * FIXME: Definición de {@code printClosingLambda}. Imprime todos los cierres lambda de un
     * estado y los estados que le siguen.
     *
     * @param closingLambdas son todos los cierres lambda.
     */
    public static void printClosingLambda(Map<State, Map<State, String>> closingLambdas) {
        State from;
        String symbol;
        State to;
        Map<State, String> closingLambda;

        for (Map.Entry<State, Map<State, String>> entry : closingLambdas.entrySet()) {
            from = entry.getKey();
            closingLambda = entry.getValue();
            System.out.printf("(%2s)\n", from.getCode());

            for (Map.Entry<State, String> entry2 : closingLambda.entrySet()) {
                symbol = entry2.getValue();
                to = entry2.getKey();
                System.out.printf("(%2s) --[%s]--> ", from.getCode(), symbol);
                System.out.printf("(%2s)\n", to.getCode());
            }
            System.out.println("");
        }
    }
}
