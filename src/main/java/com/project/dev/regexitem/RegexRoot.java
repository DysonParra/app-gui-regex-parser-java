/*
 * @fileoverview    {RegexRoot}
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
package com.project.dev.regexitem;

/**
 * TODO: Description of {@code RegexRoot}.
 *
 * @author Dyson Parra
 * @since 11
 */
public class RegexRoot extends RegexItem {

    /**
     * TODO: Description of {@code RegexRoot}.
     *
     */
    public RegexRoot() {
        this.setCode(0);                                                        // Asigna codigo al RegexItem actual.
        this.setType("Root");                                                   // Asigna tipo al RegexItem actual.
        this.setValue("Root");                                                  // Asigna valor al RegexItem actual.
        this.setKleeneType(0);                                                  // Asigna tipo de clausura de kleene al RegexItem actual.

        this.setFather(null);                                                   // Asigna padre al RegexItem.
        this.setFirstChild(null);                                               // Asigna primer hijo al RegexItem actual.
        this.setLastChild(null);                                                // Asigna ultimo hijo al RegexItem actual.

        this.setPrev(null);                                                     // Asigna anterior RegexItemal RegexItem actual.
        this.setNext(null);                                                     // Asigna siguienet RegexItem al RegexItem actual.
    }

    /**
     * FIXME: Description of {@code printDebugRegexItem}. Imprime el RegexItem con informacion
     * detallada.
     *
     * @param maxLevel es el maximo nivel dentro de la expresion regular a la que pertenece el
     *                 RegexItem actual.
     */
    @Override
    public void printDebugRegexItem(int maxLevel) {
        for (int i = 0; i < getLevel(); i++)
            System.out.print("           ");

        System.out.printf("(%8s) (%8s) (%8s) (%8s)", getFather(), this, getPrev(), getNext());

        for (int i = 0; i < maxLevel - getLevel(); i++)
            System.out.print("           ");

        System.out.printf("  '%6s' '%s'\n", getType(), getValue());

        RegexItem aux = getFirstChild();
        while (aux != null) {
            aux.printDebugRegexItem(maxLevel);
            aux = aux.getNext();
        }
    }

    /**
     * FIXME: Description of {@code printThompsonRegexItem}. Imprime el valor del RegexItem actual y
     * la contruccion de Thompson asociada.
     *
     * @param maxLevel    es el maximo nivel dentro de la expresion regular a la que pertenece el
     *                    RegexItem actual.
     * @param tabs        Es la cantidad de espacios que se escribiran antes del valor del
     *                    RegexItem.
     * @param endSpaces   Es la cantidad de espacios luego de imprimir el valor del RegexItem.
     * @param isOpen      indica si se escribira el valor de cerradura o de apertura del RegexItem
     *                    actual.
     * @param printChilds indica si tambien se imprimiran los hijos del RegexItem acual y sus
     *                    respectivas construcciones de Thompson.
     */
    @Override
    public void printThompsonRegexItem(int maxLevel, int tabs, int endSpaces, boolean isOpen, boolean printChilds) {
        String openValue = "";
        String toPrint;

        for (int i = 0; i < tabs; i++)
            openValue = openValue.concat(" ");

        openValue = openValue.concat(getType().concat(" "));

        toPrint = getValueToprint(openValue, maxLevel, tabs, endSpaces);
        System.out.print(toPrint);
        printThompsonConstruction();

        if (printChilds && getFirstChild() != null) {
            RegexItem aux = getFirstChild();
            while (aux != null) {
                aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, isOpen, printChilds);
                aux = aux.getNext();
            }

            toPrint = getValueToprint(openValue, maxLevel, tabs, endSpaces);
            System.out.print(toPrint);
            printThompsonConstruction();
        }
    }
}
