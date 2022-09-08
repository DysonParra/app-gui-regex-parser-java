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
package com.project.dev.regexoperation;

import com.project.dev.regexitem.RegexConcat;
import com.project.dev.regexitem.RegexConstant;
import com.project.dev.regexitem.RegexItem;
import com.project.dev.regexitem.RegexParenthesis;
import com.project.dev.regexitem.RegexRoot;
import com.project.dev.regexitem.RegexSymbol;
import com.project.dev.regexitem.RegexUnion;

/**
 * TODO: Definición de {@code RegexParser}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
public class RegexParser implements RegexConstant {

    // usado para agregar un identificador a cada RegexItem que se vaya creando.
    private static int[] regexCodes = {0};

    /**
     * FIXME: Definición de {@code parseString}. Convierte un String en RegexItem.
     *
     * @param regex es el String a convertir en RegexItem.
     * @return un objeto con la raiz de los RegexItem o un array con el codigo de error y la
     *         posicion del caracter que lo genero.
     */
    public static Object parseString(String regex) {
        RegexItem root = new RegexRoot();                                       // Crea la raiz de los RegexItem.
        root.setValue(regex);                                                   // Asigna valor a root.
        regexCodes[0] = 0;
        int[] result = new RegexParser().parseRegexItem(regex, root, 0, 0);     // Intenta convertir el String en RegexItem.

        //System.out.println("Final :" + result[0] + " " + result[1]);
        /*
         * -
         * root.printRegexItem(root.findMaxLevel(root.getLevel())); System.out.println("");
         * RegexPrinter.printRegex(root); System.out.println("");
         */
        if (result[0] < 0)                                                      // Si la expresion regular no es valida.
            return result;                                                      // Devuelve result indicando el codigo de eeror el numero de caracter que lo genero.
        else                                                                    // Si la expresion es valida.
            return root;                                                        // Devuelve la raiz de la expresion.
    }

    /**
     * FIXME: Definición de {@code updateStatus}. Obtiene el siguiente estado en base al actual.
     *
     * @param input        es el caracter de entrada.
     * @param union        es el estado que se devuelve si input es una union.
     * @param concat       es el estado que se devuelve si input es una concatenacion.
     * @param kleene       es el estado que se devuelve si input es una clausura de kleene.
     * @param kleenePlus   es el estado que se devuelve si input es una klausura de kleene plus.
     * @param symbol       es el estado que se devuelve si input es cualquier caracter no reservado.
     * @param openParenth  es el estado que se devuelve si input es una apertura de parentesis.
     * @param closeParenth es el estado que se devuelve si input es un cierre de parentesis.
     * @param endSeq       es el estado que se devuelve si input es fin de secuencia.
     * @param emptySeq     es el estado que se devuelve si input es la secuencia vacia.
     * @param nullSeq      es el estado que se devuelve si input es la secuencia nula.
     * @return el nuevo estado.
     */
    private int updateStatus(char input, int union, int concat, int kleene,
            int kleenePlus, int symbol, int openParenth, int closeParenth,
            int endSeq, int emptySeq, int nullSeq) {
        int newStatus;

        switch (input) {
            case '|':
                newStatus = union;
                break;

            case '.':
                newStatus = concat;
                break;

            case '*':
                newStatus = kleene;
                break;

            case '+':
                newStatus = kleenePlus;
                break;

            case '(':
                newStatus = openParenth;
                break;

            case ')':
                newStatus = closeParenth;
                break;

            case '\\':
                newStatus = endSeq;
                break;

            case '$':
                newStatus = emptySeq;
                break;

            case '@':
                newStatus = nullSeq;
                break;

            default:
                newStatus = symbol;
                break;
        }

        return newStatus;
    }

    /**
     * FIXME: Definición de {@code parseRegexItem}. Convierte un String en RegexItem.
     *
     * @param regex  es el String a convertir en RegexItem.
     * @param father es el RegexItem al que se le agregaran nuevos RegexItem como hijos.
     * @param index  es la posicion actual en el String regex.
     * @param status es el estado actual del array.
     * @return el codigo de error o aceptacion y la posicion del ultimo caracter leido.
     */
    public int[] parseRegexItem(String regex, RegexItem father, int index, int status) {
        RegexItem item = null;                                                  // Indica la posicion de los RegexItem que se vayan creando.
        int[] result;                                                           // Indica el resultado luego de cada ejecucion recursiva.

        for (int i = index; i < regex.length(); i++) {
            switch (status) {
                case 0:
                    status = updateStatus(
                            regex.charAt(i),
                            -1,
                            -1,
                            -1,
                            -1,
                            8,
                            6,
                            -1,
                            -1,
                            2,
                            1
                    );
                    break;

                case 1:
                    status = updateStatus(
                            regex.charAt(i),
                            -2,
                            -2,
                            -2,
                            -2,
                            -2,
                            -2,
                            -2,
                            3,
                            -9,
                            -10
                    );
                    break;

                case 2:
                    status = updateStatus(
                            regex.charAt(i),
                            -3,
                            -3,
                            -3,
                            -3,
                            -3,
                            -3,
                            -3,
                            3,
                            -9,
                            -10
                    );
                    break;

                case 3:
                    status = updateStatus(
                            regex.charAt(i),
                            -4,
                            -4,
                            -4,
                            -4,
                            -4,
                            -4,
                            -4,
                            -4,
                            -9,
                            -10
                    );
                    break;

                case 4:
                    status = updateStatus(
                            regex.charAt(i),
                            -5,
                            -5,
                            -5,
                            -5,
                            8,
                            6,
                            -5,
                            -5,
                            -9,
                            -10
                    );
                    break;

                case 5:
                    status = updateStatus(
                            regex.charAt(i),
                            -6,
                            -6,
                            -6,
                            -6,
                            8,
                            6,
                            -6,
                            -6,
                            -9,
                            -10
                    );
                    break;

                case 6:
                    status = updateStatus(
                            regex.charAt(i),
                            -7,
                            -7,
                            -7,
                            -7,
                            8,
                            6,
                            -7,
                            -7,
                            -9,
                            -10
                    );
                    break;

                case 7:
                    status = updateStatus(
                            regex.charAt(i),
                            4,
                            5,
                            9,
                            10,
                            8,
                            6,
                            7,
                            3,
                            -9,
                            -10
                    );
                    break;

                case 8:
                    status = updateStatus(
                            regex.charAt(i),
                            4,
                            5,
                            9,
                            10,
                            8,
                            6,
                            7,
                            3,
                            -9,
                            -10
                    );
                    break;

                case 9:
                    status = updateStatus(
                            regex.charAt(i),
                            4,
                            5,
                            -8,
                            -8,
                            8,
                            6,
                            7,
                            3,
                            -9,
                            -10
                    );
                    break;

                case 10:
                    status = updateStatus(
                            regex.charAt(i),
                            4,
                            5,
                            -8,
                            -8,
                            8,
                            6,
                            7,
                            3,
                            -9,
                            -10
                    );
                    break;

                default:
                    return new int[]{status, i - 1};
            }

            //System.out.println(i + " " + regex.charAt(i) + " " + status);
            switch (status) {
                case 0:
                    return new int[]{status, i};

                case 1:
                    break;

                case 2:
                    break;

                case 3:
                    break;

                case 4:
                    item = new RegexUnion(++regexCodes[0], father);
                    break;

                case 5:
                    item = new RegexConcat(++regexCodes[0], father);
                    break;

                case 6:
                    item = new RegexParenthesis(++regexCodes[0], father);
                    result = parseRegexItem(regex, item, i + 1, status);
                    status = result[0];
                    i = result[1];
                    break;

                case 7:
                    if (!"Root".equals(father.getType()))
                        return new int[]{status, i};
                    else
                        return new int[]{-12, i};

                case 8:
                    item = new RegexSymbol(++regexCodes[0], father);
                    item.setValue(regex.substring(i, i + 1));
                    break;

                case 9:
                    if (item != null)
                        item.setKleeneType(2);
                    item = null;
                    break;

                case 10:
                    if (item != null)
                        item.setKleeneType(1);
                    item = null;
                    break;

                default:
                    return new int[]{status, i};
            }
        }

        if ("Root".equals(father.getType())) {
            if (regex.charAt(regex.length() - 1) == '\\') {
                switch (regex.charAt(regex.length() - 2)) {
                    case '@':
                    case '$':
                        item = new RegexSymbol(++regexCodes[0], father);
                        item.setValue(String.valueOf(regex.charAt(regex.length() - 2)));
                        break;
                }
                return new int[]{status, regex.length() - 1};
            } else
                return new int[]{-13, regex.length() - 1};
        } else
            return new int[]{-11, 0};
    }
}
