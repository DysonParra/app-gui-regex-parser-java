/*
 * @fileoverview    {RegexGroup} se encarga de realizar tareas específicas.
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
package com.project.dev.regexitem;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * TODO: Definición de {@code RegexGroup}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
public class RegexGroup extends RegexItem {

    /**
     * TODO: Definición de {@code RegexGroup}.
     *
     * @param code   indica el codigo del RegexItem.
     * @param father indica el padre del RegexItem.
     * @param type   indica el tipo de grupo que sera.
     */
    public RegexGroup(int code, RegexItem father, String type) {
        this.setCode(code);                                                     // Asigna codigo al RegexItem actual.
        this.setType("Group".concat(type));                                     // Asigna tipo al RegexItem actual.

        ByteArrayOutputStream os = new ByteArrayOutputStream();                 // Crea objeto de tipo ByteArrayOutputStream.
        PrintStream printer = new PrintStream(os);                              // Crea objeto de tipo PrintStream.
        printer.printf("%03d", getCode());                                      // Imprime el codigo.

        switch (type) {                                                         // Evalua el tipo de grupo.

            case "U":                                                           // Si es un grupo de union.
                this.setValue("[" + os.toString() + "]");                       // Asigna valor al RegexItem actual.
                break;                                                          // Termina de valuar el tipo de grupo.

            case "C":                                                           // Si es un grupo de concatenacion.
                this.setValue("{" + os.toString() + "}");                       // Asigna valor al RegexItem actual.
                break;                                                          // Termina de valuar el tipo de grupo.

            default:                                                            // Si es un grupo generico.
                this.setValue("<" + os.toString() + ">");                       // Asigna valor al RegexItem actual.
                break;                                                          // Termina de valuar el tipo de grupo.
        }

        this.setKleeneType(0);                                                  // Asigna tipo de clausura de kleene al RegexItem actual.

        this.setFather(father);                                                 // Asigna padre al RegexItem.

        if (father != null) {                                                   // Si si especifica un padre para el RegexItem.
            this.setLevel(father.getLevel() + 1);                               // Asigna al RegexItem el nivel del padre mas uno.
            this.setPrev(father.getLastChild());                                // Asigna como anterior hermano el ultimo hijo del padre.

            if (father.getFirstChild() == null)                                 // Si el padre no tiene hijos.
                father.setFirstChild(this);                                     // Indica que el RegexItem actual es el primer hijo.
            else                                                                // Si el padre si tiene hijos.
                father.getLastChild().setNext(this);                            // Indica que el RegexItem actual es ahora el siguiente hermano del ultimo hijo.

            father.setLastChild(this);                                          // Indica que el RegexItem actual es ahora el ultimo hijo.
        }
    }

    /**
     * FIXME: Definición de {@code printDebugRegexItem}. Imprime el RegexItem con informacion
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
     * FIXME: Definición de {@code printThompsonRegexItem}. Imprime el valor del RegexItem actual y
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
        String closeValue = "";

        for (int i = 0; i < tabs; i++) {
            openValue = openValue.concat(" ");
            closeValue = closeValue.concat(" ");
        }

        openValue = openValue.concat(getValue());
        switch (getType()) {
            case "GroupU":
                closeValue = closeValue.concat("[...]");
                break;

            case "GroupC":
                closeValue = closeValue.concat("{...}");
                break;

            case "Group":
                closeValue = closeValue.concat("<...>");
                break;

            default:
                closeValue = closeValue.concat("<   >");
                break;
        }

        String toPrint;
        if (printChilds && getFirstChild() != null) {
            toPrint = getValueToprint(openValue, maxLevel, tabs, endSpaces);
            System.out.print(toPrint);
            printThompsonConstruction();

            RegexItem aux = getFirstChild();
            while (aux != null) {
                aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, isOpen, printChilds);
                aux = aux.getNext();
            }

            toPrint = getValueToprint(closeValue, maxLevel, tabs, endSpaces);
            System.out.print(toPrint);
            printThompsonConstruction();
        } else if (isOpen) {
            toPrint = getValueToprint(openValue, maxLevel, tabs, endSpaces);
            System.out.print(toPrint);
            printThompsonConstruction();
        } else {
            toPrint = getValueToprint(closeValue, maxLevel, tabs, endSpaces);
            System.out.print(toPrint);
            printThompsonConstruction();
        }
    }
}
