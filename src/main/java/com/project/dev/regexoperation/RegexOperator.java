/*
 * @fileoverview    {RegexOperator}
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
package com.project.dev.regexoperation;

import com.project.dev.regexitem.RegexItem;
import com.project.dev.thompson.construction.ThompsonConstruction;

/**
 * TODO: Description of {@code RegexOperator}.
 *
 * @author Dyson Parra
 * @since 11
 */
public class RegexOperator {

    /**
     * FIXME: Description of {@code getThompsonConstruction}. Obtiene la construccion de thompson de
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
        return root.getThompson();

    }
}
