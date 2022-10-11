/*
 * @fileoverview {AutomatPrinter} se encarga de realizar tareas especificas.
 *
 * @version             1.0
 *
 * @author              Dyson Arley Parra Tilano <dysontilano@gmail.com>
 * Copyright (C) Dyson Parra
 *
 * @History v1.0 --- La implementacion de {AutomatPrinter} fue realizada el 31/07/2022.
 * @Dev - La primera version de {AutomatPrinter} fue escrita por Dyson A. Parra T.
 */
package com.project.dev.automat.operation;

import com.project.dev.automat.Automat;
import com.project.dev.automat.Status;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * TODO: Definición de {@code AutomatPrinter}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
public class AutomatPrinter {

    /**
     * FIXME: Definición de {@code printInputSymbols}. Imprime los simbolos de entrada de un
     * automata finito
     *
     * @param inputSymbols son los simbolos de entrada del automata finito.
     */
    public static void printInputSymbols(Map<String, String> inputSymbols) {
        String symbol;
        System.out.println("Input symbols:");
        for (Map.Entry<String, String> entry : inputSymbols.entrySet()) {
            symbol = entry.getKey();
            System.out.printf("'%s' ", symbol);
        }
        System.out.println("");
    }

    /**
     * FIXME: Definición de {@code printStatus}. Imprime un estado de un automata finito.
     *
     * @param status es el estado.
     */
    public static void printStatus(Status status) {
        System.out.printf("'%s'\n\n", status.getCode());
        System.out.printf("'%b'\n\n", status.isEndStatus());

        String symbol;
        Status statusAux;

        if (!status.getNextStatuses().isEmpty())
            for (Map.Entry<String, Status> entry : status.getNextStatuses().entrySet()) {
                symbol = entry.getKey();
                statusAux = entry.getValue();
                System.out.printf("--[%s]--> '%s'\n", symbol, statusAux.getCode());
            }
        else
            System.out.printf("(none)\n");
    }

    /**
     * FIXME: Definición de {@code printStatuses}. Imprime los estados de un automata finito.
     *
     * @param allStatuses es el array con todos los estados del automata finito.
     */
    public static void printStatuses(ArrayList<Status> allStatuses) {
        Iterator statusIterator = allStatuses.iterator();
        Status statusAux;

        while (statusIterator.hasNext()) {
            statusAux = (Status) statusIterator.next();
            System.out.println("---");
            printStatus(statusAux);
            System.out.println("------\n");
        }
    }

    /**
     * FIXME: Definición de {@code printAutomat}. Imprime un automata finito.
     *
     * @param automat es el automata finito.
     */
    public static void printAutomat(Automat automat) {
        AutomatPrinter.printInputSymbols(automat.getInputSymbols());
        System.out.println("");
        AutomatPrinter.printStatuses(automat.getStatuses());
    }

    /**
     * FIXME: Definición de {@code printMatrix}. Imprime una matriz asociada a un automata finito.
     *
     * @param matrix es la matriz del automata finito.
     */
    public static void printMatrix(String[][] matrix) {
        for (String[] matrix1 : matrix) {
            for (int j = 0; j < matrix[0].length; j++)
                System.out.printf("(%2s) ", matrix1[j]);
            System.out.printf("\n");
        }
        System.out.println("");
    }
}
