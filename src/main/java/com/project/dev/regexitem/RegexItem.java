/*
 * @fileoverview    {RegexItem}
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

import com.project.dev.thompson.construction.InputSymbol;
import com.project.dev.thompson.construction.ThompsonConstruction;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TODO: Definición de {@code RegexItem}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
//@AllArgsConstructor
//@Builder
@Data
//@NoArgsConstructor
@EqualsAndHashCode(exclude = {"father", "firstChild", "lastChild", "prev", "next", "thompson", "symbol"})
public abstract class RegexItem {

    private int code;                                                           // Codigo del RegexItem.
    private int level;                                                          // Nivel de profundidad del regexItem.
    private String type;                                                        // Tipo de regexItem que es.
    private String value;                                                       // Valor del regexItem actual.
    private int kleeneType;                                                     // Si contiene clausura de kleene (0 = no, 1 = +, 2 = *)

    private RegexItem father;                                                   // Padre del RegexItem actual.
    private RegexItem firstChild;                                               // Primer hijo del RegexItem actual.
    private RegexItem lastChild;                                                // Último hijo del RegexItem actual.

    private RegexItem prev;                                                     // Anterior RegexItem.
    private RegexItem next;                                                     // Siguiente RegexItem.

    private ThompsonConstruction thompson;                                      // Contruccion de thompson equivalente al RegexItem actual.
    private InputSymbol symbol;                                                 // Simbolo dentro de la contruccion de thompson que apunta a este RegexItem.

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
     * FIXME: Definición de {@code getRoot}. Obtiene la raiz desde el RegexItem actual.
     *
     * @return la raiz del regexItem actual.
     */
    public RegexItem getRoot() {
        RegexItem aux = this.getFather();                                       // Obtiene el padre del RegexItem actual.
        while (aux.getFather() != null)                                         // Mientras el padre del padre del RegexItem actual no sea null.
            aux = aux.getFather();                                              // Obtiene el padre del padre del RegexItem actual.
        return aux;                                                             // Devuelve la raiz del RegexItem.
    }

    /**
     * FIXME: Definición de {@code findMaxLevel}. Obtiene la mayor profundidad de un RegexItem.
     *
     * @param level Es el nivel del regexItem actual.
     * @return El maximo nivel dentro de una expresion regular.
     */
    public int findMaxLevel(int level) {
        RegexItem aux;                                                          // Crea apuntador a un RegexItem para recorrer los hermanos del RegexItem.
        aux = this;                                                             // A aux le lleva una copia del RegexItem invocador.

        if (aux != null)                                                        // Si el RegexItem actuual no esta vacio.
            while (aux != null) {                                               // Recorre los hermanos de aux.
                if (aux.getLevel() > level)                                     // Si el nivel de aux es mayor que level.
                    level = aux.getLevel();                                     // A level le lleva el nivel de aux.

                if (aux.getFirstChild() != null)                                // Si aux tiene hijos.
                    level = aux.getFirstChild().findMaxLevel(level);            // Busca el nivel de cada uno de los hijos de aux.

                aux = aux.getNext();                                            // Pasa al siguiente hermano de aux.
            }

        return level;                                                           // Devuelve el mayor nivel encontrado.
    }

    /**
     * FIXME: Definición de {@code printThompsonConstruction}. Imprime la construccion de Thompson
     * equivalente al RegexItem actual.
     */
    public void printThompsonConstruction() {
        if (getThompson() != null)                                              // Si no tiene una construccion de Thompson asignada.
            getThompson().print(true);                                          // Imprime la construccion de Thompson asociada.
        else if (getSymbol() != null)                                           // Si no tiene un simbolo dentro de una construccion de Thompson asignado.
            getSymbol().printSymbol();                                          // Imprime el simbolo asociado.
        else                                                                    // Si no tiene construccion de Thompson asociada ni simbolo dentro de una.
            System.out.println("|");                                            // Imprime el caracter indicado.

        System.out.println("");
    }

    /**
     * FIXME: Definición de {@code getValueToprint}. Almacena un string precedido y antecedido de
     * una cantidad de espacios dependiendo del nivel del RegexItem.
     *
     * @param value     es el eString a imprimir.
     * @param maxLevel  es el maximo nivel dentro de la expresion regular.
     * @param tabs      Es la cantidad de espacios que se escribiran antes del String..
     * @param endSpaces Es la cantidad de espacios luego de imprimir el String.
     * @return el valor en String de lo que se acaba de imprimir.
     */
    public String getValueToprint(String value, int maxLevel, int tabs, int endSpaces) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();                     // Crea objeto de tipo ByteArrayOutputStream.
        PrintStream printer = new PrintStream(os);                                  // Crea objeto de tipo PrintStream.

        int levelAux = maxLevel - getLevel() + 1;                                   // Obtiene la diferencia entre el nivel de RegexItem y el maximo nivel.
        int startSpaces = getLevel() * tabs;                                        // Obtiene la cantidad de espacios que se imprimiran al principio dependiendo del nivel.

        for (int i = 0; i < startSpaces; i++)                                       // Recorre la cantidad de espacios antes del String.
            printer.print(" ");                                                     // Imprime un espacio.

        printer.print(value);                                                       // Imprime el String.

        for (int i = 0; i < ((levelAux * tabs) - value.length()) + endSpaces; i++)  // Recorre la cantidad de espacios al final menos el tamaño del String.
            printer.print(" ");                                                     // Imprime un espacio.

        return os.toString();                                                       // Devuelve lo que se acaba de imprimir.
    }

    /**
     * TODO: Definición de {@code printDebugRegexItem}.
     *
     * @param maxLevel
     */
    public abstract void printDebugRegexItem(int maxLevel);

    /**
     * TODO: Definición de {@code printThompsonRegexItem}.
     *
     * @param maxLevel
     * @param isOpen
     * @param tabs
     * @param printChilds
     * @param spaces
     */
    public abstract void printThompsonRegexItem(int maxLevel, int tabs, int spaces, boolean printChilds, boolean isOpen);

}
