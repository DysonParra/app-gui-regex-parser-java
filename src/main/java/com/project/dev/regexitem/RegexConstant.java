/*
 * @fileoverview {RegexConstant} se encarga de realizar tareas especificas.
 *
 * @version             1.0
 *
 * @author              Dyson Arley Parra Tilano <dysontilano@gmail.com>
 * Copyright (C) Dyson Parra
 *
 * @History v1.0 --- La implementacion de {RegexConstant} fue realizada el 31/07/2022.
 * @Dev - La primera version de {RegexConstant} fue escrita por Dyson A. Parra T.
 */
package com.project.dev.regexitem;

/**
 * TODO: Definición de {@code RegexItem}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
public interface RegexConstant {

    public static final int REGEX_STATUS_START = 0;
    public static final int REGEX_STATUS_NULL_SEQUENCE_READED = 1;
    public static final int REGEX_STATUS_EMPTY_SEQUENCE_READED = 2;
    public static final int REGEX_STATUS_END_SEQUENCE_READED = 3;
    public static final int REGEX_STATUS_UNION_READED = 4;
    public static final int REGEX_STATUS_CONCAT_READED = 5;
    public static final int REGEX_STATUS_OPEN_PARENTHESIS_READED = 6;
    public static final int REGEX_STATUS_CLOSE_PARENTHESIS_READED = 7;
    public static final int REGEX_STATUS_SYMBOL_READED = 8;
    public static final int REGEX_STATUS_KLEENE_READED = 9;
    public static final int REGEX_STATUS_KLEENE_PLUS_READED = 10;

    public static final int REGEX_STATUS_ERROR_FIRST_CHAR_INVALID = -1;
    public static final int REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_EMPTY_SEQUENCE_NOT_END_SEQUENCE = -2;
    public static final int REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_NULL_SEQUENCE_NOT_END_SEQUENCE = -3;
    public static final int REGEX_STATUS_ERROR_CHAR_AFTER_END_SEQUENCE = -4;
    public static final int REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_UNION = -5;
    public static final int REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_CONCAT = -6;
    public static final int REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_OPEN_PARENTHESIS = -7;
    public static final int REGEX_STATUS_ERROR_KLEENE_CHAR_AFTER_OTHER_KLEENE_CHAR = -8;
    public static final int REGEX_STATUS_ERROR_NULL_SEQUENCE_IN_POS_DIFF_AS_FIRST = -9;
    public static final int REGEX_STATUS_ERROR_EMPTY_SEQUENCE_IN_POS_DIFF_AS_FIRST = -10;
    public static final int REGEX_STATUS_ERROR_MORE_OPEN_THAT_CLOSE_PARENTHESIS = -11;
    public static final int REGEX_STATUS_ERROR_MORE_CLOSE_THAT_OPEN_PARENTHESIS = -12;
    public static final int REGEX_STATUS_ERROR_LAST_CHAR_IS_NOT_END_SEQUENCE = -13;

}
