/*
 * @fileoverview {RegexParser} se encarga de realizar tareas especificas.
 *
 * @version             1.0
 *
 * @author              Dyson Arley Parra Tilano <dysontilano@gmail.com>
 * Copyright (C) Dyson Parra
 *
 * @History v1.0 --- La implementacion de {RegexParser} fue realizada el 31/07/2022.
 * @Dev - La primera version de {RegexParser} fue escrita por Dyson A. Parra T.
 */
package com.project.dev.regexoperation;

import com.project.dev.regexitem.RegexConcat;
import com.project.dev.regexitem.RegexConstant;
import com.project.dev.regexitem.RegexItem;
import com.project.dev.regexitem.RegexParenthesis;
import com.project.dev.regexitem.RegexRoot;
import com.project.dev.regexitem.RegexSymbol;
import com.project.dev.regexitem.RegexUnion;
import java.util.ArrayList;
import java.util.List;

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
        RegexItem root = new RegexRoot();                                                   // Crea la raiz de los RegexItem.
        root.setValue(regex);                                                               // Asigna valor a root.
        regexCodes[0] = 0;
        List<int[]> errors = new ArrayList<>();
        // Intenta convertir el String en RegexItem.
        new RegexParser().parseRegexItem(regex, root, 0, REGEX_STATUS_START, errors);

        //System.out.println("Final :" + result[0] + " " + result[1]);
        /*
         * -
         * root.printRegexItem(root.findMaxLevel(root.getLevel())); System.out.println("");
         * RegexPrinter.printRegex(root); System.out.println("");
         */
        if(!errors.isEmpty()) {
            // Devuelve los errores.
            return errors;
        } else {
            // Devuelve la raiz de la expresion.
            return root;
        }
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
     * @param errors es donde se almacenarán los errores que se encuentren en la regex.
     * @return el codigo de error o aceptacion y la posicion del ultimo caracter leido.
     */
    public int[] parseRegexItem(String regex, RegexItem father, int index, int status, List<int[]> errors) {
        RegexItem item = null;                                                  // Indica la posicion de los RegexItem que se vayan creando.
        int[] result;                                                           // Indica el resultado luego de cada ejecucion recursiva.
        int prevStatus;
        for (int i = index; i < regex.length(); i++) {
            prevStatus = status;
            switch (status) {
                case REGEX_STATUS_START:
                    status = updateStatus(
                            regex.charAt(i),
                            REGEX_STATUS_ERROR_FIRST_CHAR_INVALID,
                            REGEX_STATUS_ERROR_FIRST_CHAR_INVALID,
                            REGEX_STATUS_ERROR_FIRST_CHAR_INVALID,
                            REGEX_STATUS_ERROR_FIRST_CHAR_INVALID,
                            REGEX_STATUS_SYMBOL_READED,
                            REGEX_STATUS_OPEN_PARENTHESIS_READED,
                            REGEX_STATUS_ERROR_FIRST_CHAR_INVALID,
                            REGEX_STATUS_ERROR_FIRST_CHAR_INVALID,
                            REGEX_STATUS_EMPTY_SEQUENCE_READED,
                            REGEX_STATUS_NULL_SEQUENCE_READED
                    );
                    break;

                case REGEX_STATUS_NULL_SEQUENCE_READED:
                    status = updateStatus(
                            regex.charAt(i),
                            REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_EMPTY_SEQUENCE_NOT_END_SEQUENCE,
                            REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_EMPTY_SEQUENCE_NOT_END_SEQUENCE,
                            REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_EMPTY_SEQUENCE_NOT_END_SEQUENCE,
                            REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_EMPTY_SEQUENCE_NOT_END_SEQUENCE,
                            REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_EMPTY_SEQUENCE_NOT_END_SEQUENCE,
                            REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_EMPTY_SEQUENCE_NOT_END_SEQUENCE,
                            REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_EMPTY_SEQUENCE_NOT_END_SEQUENCE,
                            REGEX_STATUS_END_SEQUENCE_READED,
                            REGEX_STATUS_ERROR_NULL_SEQUENCE_IN_POS_DIFF_AS_FIRST,
                            REGEX_STATUS_ERROR_EMPTY_SEQUENCE_IN_POS_DIFF_AS_FIRST
                    );
                    break;

                case REGEX_STATUS_EMPTY_SEQUENCE_READED:
                    status = updateStatus(
                            regex.charAt(i),
                            REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_NULL_SEQUENCE_NOT_END_SEQUENCE,
                            REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_NULL_SEQUENCE_NOT_END_SEQUENCE,
                            REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_NULL_SEQUENCE_NOT_END_SEQUENCE,
                            REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_NULL_SEQUENCE_NOT_END_SEQUENCE,
                            REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_NULL_SEQUENCE_NOT_END_SEQUENCE,
                            REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_NULL_SEQUENCE_NOT_END_SEQUENCE,
                            REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_NULL_SEQUENCE_NOT_END_SEQUENCE,
                            REGEX_STATUS_END_SEQUENCE_READED,
                            REGEX_STATUS_ERROR_NULL_SEQUENCE_IN_POS_DIFF_AS_FIRST,
                            REGEX_STATUS_ERROR_EMPTY_SEQUENCE_IN_POS_DIFF_AS_FIRST
                    );
                    break;

                case REGEX_STATUS_END_SEQUENCE_READED:
                    status = updateStatus(
                            regex.charAt(i),
                            REGEX_STATUS_ERROR_CHAR_AFTER_END_SEQUENCE,
                            REGEX_STATUS_ERROR_CHAR_AFTER_END_SEQUENCE,
                            REGEX_STATUS_ERROR_CHAR_AFTER_END_SEQUENCE,
                            REGEX_STATUS_ERROR_CHAR_AFTER_END_SEQUENCE,
                            REGEX_STATUS_ERROR_CHAR_AFTER_END_SEQUENCE,
                            REGEX_STATUS_ERROR_CHAR_AFTER_END_SEQUENCE,
                            REGEX_STATUS_ERROR_CHAR_AFTER_END_SEQUENCE,
                            REGEX_STATUS_ERROR_CHAR_AFTER_END_SEQUENCE,
                            REGEX_STATUS_ERROR_NULL_SEQUENCE_IN_POS_DIFF_AS_FIRST,
                            REGEX_STATUS_ERROR_EMPTY_SEQUENCE_IN_POS_DIFF_AS_FIRST
                    );
                    break;

                case REGEX_STATUS_UNION_READED:
                    status = updateStatus(
                            regex.charAt(i),
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_UNION,
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_UNION,
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_UNION,
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_UNION,
                            REGEX_STATUS_SYMBOL_READED,
                            REGEX_STATUS_OPEN_PARENTHESIS_READED,
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_UNION,
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_UNION,
                            REGEX_STATUS_ERROR_NULL_SEQUENCE_IN_POS_DIFF_AS_FIRST,
                            REGEX_STATUS_ERROR_EMPTY_SEQUENCE_IN_POS_DIFF_AS_FIRST
                    );
                    break;

                case REGEX_STATUS_CONCAT_READED:
                    status = updateStatus(
                            regex.charAt(i),
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_CONCAT,
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_CONCAT,
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_CONCAT,
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_CONCAT,
                            REGEX_STATUS_SYMBOL_READED,
                            REGEX_STATUS_OPEN_PARENTHESIS_READED,
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_CONCAT,
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_CONCAT,
                            REGEX_STATUS_ERROR_NULL_SEQUENCE_IN_POS_DIFF_AS_FIRST,
                            REGEX_STATUS_ERROR_EMPTY_SEQUENCE_IN_POS_DIFF_AS_FIRST
                    );
                    break;

                case REGEX_STATUS_OPEN_PARENTHESIS_READED:
                    status = updateStatus(
                            regex.charAt(i),
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_OPEN_PARENTHESIS,
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_OPEN_PARENTHESIS,
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_OPEN_PARENTHESIS,
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_OPEN_PARENTHESIS,
                            REGEX_STATUS_SYMBOL_READED,
                            REGEX_STATUS_OPEN_PARENTHESIS_READED,
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_OPEN_PARENTHESIS,
                            REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_OPEN_PARENTHESIS,
                            REGEX_STATUS_ERROR_NULL_SEQUENCE_IN_POS_DIFF_AS_FIRST,
                            REGEX_STATUS_ERROR_EMPTY_SEQUENCE_IN_POS_DIFF_AS_FIRST
                    );
                    break;

                case REGEX_STATUS_CLOSE_PARENTHESIS_READED:
                    status = updateStatus(
                            regex.charAt(i),
                            REGEX_STATUS_UNION_READED,
                            REGEX_STATUS_CONCAT_READED,
                            REGEX_STATUS_KLEENE_READED,
                            REGEX_STATUS_KLEENE_PLUS_READED,
                            REGEX_STATUS_SYMBOL_READED,
                            REGEX_STATUS_OPEN_PARENTHESIS_READED,
                            REGEX_STATUS_CLOSE_PARENTHESIS_READED,
                            REGEX_STATUS_END_SEQUENCE_READED,
                            REGEX_STATUS_ERROR_NULL_SEQUENCE_IN_POS_DIFF_AS_FIRST,
                            REGEX_STATUS_ERROR_EMPTY_SEQUENCE_IN_POS_DIFF_AS_FIRST
                    );
                    break;

                case REGEX_STATUS_SYMBOL_READED:
                    status = updateStatus(
                            regex.charAt(i),
                            REGEX_STATUS_UNION_READED,
                            REGEX_STATUS_CONCAT_READED,
                            REGEX_STATUS_KLEENE_READED,
                            REGEX_STATUS_KLEENE_PLUS_READED,
                            REGEX_STATUS_SYMBOL_READED,
                            REGEX_STATUS_OPEN_PARENTHESIS_READED,
                            REGEX_STATUS_CLOSE_PARENTHESIS_READED,
                            REGEX_STATUS_END_SEQUENCE_READED,
                            REGEX_STATUS_ERROR_NULL_SEQUENCE_IN_POS_DIFF_AS_FIRST,
                            REGEX_STATUS_ERROR_EMPTY_SEQUENCE_IN_POS_DIFF_AS_FIRST
                    );
                    break;

                case REGEX_STATUS_KLEENE_READED:
                    status = updateStatus(
                            regex.charAt(i),
                            REGEX_STATUS_UNION_READED,
                            REGEX_STATUS_CONCAT_READED,
                            REGEX_STATUS_ERROR_KLEENE_CHAR_AFTER_OTHER_KLEENE_CHAR,
                            REGEX_STATUS_ERROR_KLEENE_CHAR_AFTER_OTHER_KLEENE_CHAR,
                            REGEX_STATUS_SYMBOL_READED,
                            REGEX_STATUS_OPEN_PARENTHESIS_READED,
                            REGEX_STATUS_CLOSE_PARENTHESIS_READED,
                            REGEX_STATUS_END_SEQUENCE_READED,
                            REGEX_STATUS_ERROR_NULL_SEQUENCE_IN_POS_DIFF_AS_FIRST,
                            REGEX_STATUS_ERROR_EMPTY_SEQUENCE_IN_POS_DIFF_AS_FIRST
                    );
                    break;

                case REGEX_STATUS_KLEENE_PLUS_READED:
                    status = updateStatus(
                            regex.charAt(i),
                            REGEX_STATUS_UNION_READED,
                            REGEX_STATUS_CONCAT_READED,
                            REGEX_STATUS_ERROR_KLEENE_CHAR_AFTER_OTHER_KLEENE_CHAR,
                            REGEX_STATUS_ERROR_KLEENE_CHAR_AFTER_OTHER_KLEENE_CHAR,
                            REGEX_STATUS_SYMBOL_READED,
                            REGEX_STATUS_OPEN_PARENTHESIS_READED,
                            REGEX_STATUS_CLOSE_PARENTHESIS_READED,
                            REGEX_STATUS_END_SEQUENCE_READED,
                            REGEX_STATUS_ERROR_NULL_SEQUENCE_IN_POS_DIFF_AS_FIRST,
                            REGEX_STATUS_ERROR_EMPTY_SEQUENCE_IN_POS_DIFF_AS_FIRST
                    );
                    break;

                default:
                    return new int[]{status, i - 1};
            }

            //System.out.println(i + " " + regex.charAt(i) + " " + status);
            switch (status) {
                case REGEX_STATUS_START:
                    return new int[]{status, i};

                case REGEX_STATUS_NULL_SEQUENCE_READED:
                    break;

                case REGEX_STATUS_EMPTY_SEQUENCE_READED:
                    break;

                case REGEX_STATUS_END_SEQUENCE_READED:
                    break;

                case REGEX_STATUS_UNION_READED:
                    item = new RegexUnion(++regexCodes[0], father);
                    break;

                case REGEX_STATUS_CONCAT_READED:
                    item = new RegexConcat(++regexCodes[0], father);
                    break;

                case REGEX_STATUS_OPEN_PARENTHESIS_READED:
                    item = new RegexParenthesis(++regexCodes[0], father);
                    result = parseRegexItem(regex, item, i + 1, status, errors);
                    status = result[0];
                    i = result[1];
                    break;

                case REGEX_STATUS_CLOSE_PARENTHESIS_READED:
                    if (!"Root".equals(father.getType()))
                        return new int[]{status, i};
                    else {
                        errors.add(new int[]{REGEX_STATUS_ERROR_MORE_CLOSE_THAT_OPEN_PARENTHESIS, i}); 
                        status = prevStatus;
                        break;
                    }

                case REGEX_STATUS_SYMBOL_READED:
                    item = new RegexSymbol(++regexCodes[0], father);
                    item.setValue(regex.substring(i, i + 1));
                    break;

                case REGEX_STATUS_KLEENE_READED:
                    if (item != null)
                        item.setKleeneType(2);
                    item = null;
                    break;

                case REGEX_STATUS_KLEENE_PLUS_READED:
                    if (item != null)
                        item.setKleeneType(1);
                    item = null;
                    break;

                default:
                    errors.add(new int[]{status, i}); 
                    status = prevStatus;
                    break;
            }
        }
        int[] lastResult;

        if ("Root".equals(father.getType())) {
            if(regex.length() == 2)
                switch (regex.charAt(regex.length() - 2)) {
                    case '@':
                    case '$':
                        item = new RegexSymbol(++regexCodes[0], father);
                        item.setValue(String.valueOf(regex.charAt(regex.length() - 2)));
                        break;
                }
            lastResult = new int[]{status, regex.length() - 1};
        } else {
            errors.add(new int[]{REGEX_STATUS_ERROR_MORE_OPEN_THAT_CLOSE_PARENTHESIS, regex.length() - 1});
            if (regex.length() != 1 && regex.charAt(regex.length() - 1) != '\\')
                errors.add(new int[]{REGEX_STATUS_ERROR_LAST_CHAR_IS_NOT_END_SEQUENCE, regex.length() - 1});
            lastResult = new int[]{REGEX_STATUS_ERROR_MORE_OPEN_THAT_CLOSE_PARENTHESIS, 0};
        }

        if ("Root".equals(father.getType()) && regex.charAt(regex.length() - 1) != '\\') {
            errors.add(new int[]{REGEX_STATUS_ERROR_LAST_CHAR_IS_NOT_END_SEQUENCE, regex.length() - 1});
            lastResult = new int[]{REGEX_STATUS_ERROR_LAST_CHAR_IS_NOT_END_SEQUENCE, regex.length() - 1};
        }
        return lastResult;
    }
}
