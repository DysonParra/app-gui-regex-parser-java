/*
 * @fileoverview {RegexOperator} se encarga de realizar tareas especificas.
 *
 * @version             1.0
 *
 * @author              Dyson Arley Parra Tilano <dysontilano@gmail.com>
 * Copyright (C) Dyson Parra
 *
 * @History v1.0 --- La implementacion de {RegexOperator} fue realizada el 31/07/2022.
 * @Dev - La primera version de {RegexOperator} fue escrita por Dyson A. Parra T.
 */
package com.project.dev.regexoperation;

import com.project.dev.regexitem.RegexItem;
import com.project.dev.thompson.construction.ThompsonConstruction;

/**
 * TODO: Definición de {@code RegexOperator}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
public class RegexOperator {

    /**
     * FIXME: Definición de {@code getThompsonConstruction}. Obtiene la construccion de thompson de
     * una expresion regular.
     *
     * @param root es la raiz de la expresion regular.
     * @return la construccion de thompson de la expresion regular.
     */
    public static ThompsonConstruction getThompsonConstruction(RegexItem root) {
        /*
         * -
         * RegexPrinter.printRegex(root, false); System.out.println("");
         * RegexPrinter.printRegex(root, true); System.out.println("");
         * RegexPrinter.printRegexDebug(root); System.out.println("\n");
         */

        RegexAdder.addRegexUnionGroups(root);
        /*
         * -
         * RegexPrinter.printRegex(root, false); System.out.println("");
         * RegexPrinter.printRegex(root, true); System.out.println("");
         * RegexPrinter.printRegexDebug(root); System.out.println("\n");
         */

        RegexAdder.addRegexConcatGroups(root);

        /*
         * -
         * RegexPrinter.printRegex(root, false); System.out.println("");
         * RegexPrinter.printRegex(root, true); System.out.println("");
         * RegexPrinter.printRegexDebug(root); System.out.println("\n");
         */
        RegexAdder.addThompsonConstructions(root, root.findMaxLevel(root.getLevel()));
        //System.out.println("\n");

        //System.out.println("Uniting...");
        RegexAdder.uniteThompsonConstructions(root, root.findMaxLevel(root.getLevel()));
        //System.out.println("\n");

        ThompsonConstruction thompson = root.getThompson();
        return thompson;

    }
}
