/*
 * @fileoverview    {RegexPrinter} se encarga de realizar tareas específicas.
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
package com.project.dev.regexoperation;

import com.project.dev.regexitem.RegexItem;

/**
 * TODO: Definición de {@code RegexPrinter}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
public class RegexPrinter {

    /**
     * FIXME: Definición de {@code printRegex}. Imprime los RegexItem de una expresion regular.
     *
     * @param root        es la raiz de la expresion regular.
     * @param printGroups indica s i se imprimiran los grupos.
     */
    public static void printRegex(RegexItem root, boolean printGroups) {
        RegexItem aux = root;
        if (aux != null)
            while (aux != null) {
                switch (aux.getType()) {
                    case "Root":
                        if (aux.getFirstChild() != null)
                            printRegex(aux.getFirstChild(), printGroups);
                        break;

                    case "Parent":
                        System.out.print("(");
                        if (aux.getFirstChild() != null)
                            printRegex(aux.getFirstChild(), printGroups);
                        System.out.print(")");

                        switch (aux.getKleeneType()) {
                            case 1:
                                System.out.print("+");
                                break;

                            case 2:
                                System.out.print("*");
                                break;
                        }
                        break;

                    case "GroupU":
                        if (printGroups)
                            System.out.print("[");
                        if (aux.getFirstChild() != null)
                            printRegex(aux.getFirstChild(), printGroups);
                        if (printGroups)
                            System.out.print("]");
                        break;

                    case "GroupC":
                        if (printGroups)
                            System.out.print("{");
                        if (aux.getFirstChild() != null)
                            printRegex(aux.getFirstChild(), printGroups);
                        if (printGroups)
                            System.out.print("}");
                        break;

                    case "Group":
                        if (printGroups)
                            System.out.print("<");
                        if (aux.getFirstChild() != null)
                            printRegex(aux.getFirstChild(), printGroups);
                        if (printGroups)
                            System.out.print(">");
                        else if (aux.getNext() != null) {
                            switch (aux.getFather().getType()) {
                                case "GroupC":
                                    System.out.print(".");
                                    break;

                                case "GroupU":
                                    System.out.print("|");
                                    break;
                            }
                        }

                        break;

                    case "Symbol":
                        System.out.print(aux.getValue());
                        switch (aux.getKleeneType()) {
                            case 1:
                                System.out.print("+");
                                break;

                            case 2:
                                System.out.print("*");
                                break;
                        }
                        break;

                    case "Concat":
                        System.out.print(".");
                        break;

                    case "Union":
                        System.out.print("|");
                        break;
                }

                aux = aux.getNext();
            }
        else
            System.out.println("Void");
    }

    /**
     * FIXME: Definición de {@code printRegexDebug}. Imprime los RegexItem de una expresion regular
     * con informacion detallada.
     *
     * @param root es la raiz de la expresion regular.
     */
    public static void printRegexDebug(RegexItem root) {
        if (root != null)                                                       // Si la raiz no esta vacia.
            root.printDebugRegexItem(root.findMaxLevel(root.getLevel()));       // Imprime la expresion regular con informacion detallada.
        else                                                                    // Si la raiz esta vacia.
            System.out.println("Void");                                         // Imprime que esta vacia.
    }

    /**
     * FIXME: Definición de {@code printRegexThompson}. Imprime el valor de los RegexItem de una
     * expresion regular y su contruccion de Thompson asociada.
     *
     * @param root        es la raiz de la expresion regular.
     * @param tabs        Es la cantidad de espacios que se escribiran antes del valor del
     *                    RegexItem.
     * @param endSpaces   Es la cantidad de espacios luego de imprimir el valor del RegexItem.
     * @param isOpen      indica si se escribira el valor de cerradura o de apertura del RegexItem
     *                    actual.
     * @param printChilds indica si tambien se imprimiran los hijos del RegexItem acual y sus
     *                    respectivas construcciones de Thompson.
     */
    public static void printRegexThompson(RegexItem root, int tabs, int endSpaces, boolean isOpen, boolean printChilds) {
        if (root != null)                                                        // Si la raiz no esta vacia.
            root.printThompsonRegexItem(root.findMaxLevel(root.getLevel()), tabs, endSpaces, isOpen, printChilds);
        else                                                                    // Si la raiz esta vacia.
            System.out.println("Void");                                         // Imprime que esta vacia.
    }
}
