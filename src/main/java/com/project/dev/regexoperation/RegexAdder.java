/*
 * @fileoverview    {RegexAdder}
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

import com.project.dev.regexitem.RegexGroup;
import com.project.dev.regexitem.RegexItem;
import com.project.dev.thompson.construction.InputSymbol;
import com.project.dev.thompson.construction.State;
import com.project.dev.thompson.construction.ThompsonConstruction;
import com.project.dev.thompson.construction.type.ThompsonConcat;
import com.project.dev.thompson.construction.type.ThompsonKleene;
import com.project.dev.thompson.construction.type.ThompsonKleenePlus;
import com.project.dev.thompson.construction.type.ThompsonSymbol;
import com.project.dev.thompson.construction.type.ThompsonUnion;

/**
 * TODO: Definición de {@code RegexAdder}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
public class RegexAdder {

    private static int[] regexCodes = {100};                                    // Array que servira para agregar un identificador a cada RegexItem que se vaya creando.
    private static int[] codes = {0, 0};                                         // 0 = state, 1 = symbol.

    /**
     * FIXME: Definición de {@code addRegexUnionGroups}. Agrega grupos a las uniones de una
     * expresion regular.
     *
     * @param root es la raiz de la expresion regular.
     */
    public static void addRegexUnionGroups(RegexItem root) {
        RegexItem aux = root;
        int unionQuantity = 0;
        if (aux != null)
            while (aux != null) {
                switch (aux.getType()) {
                    case "Root":
                    case "Parent":
                    case "Group":
                    case "GroupC":
                    case "GroupU":
                        if (aux.getFirstChild() != null)
                            addRegexUnionGroups(aux.getFirstChild());
                        break;

                    case "Symbol":
                    case "Concat":
                        break;

                    case "Union":
                        unionQuantity++;
                        break;
                }

                aux = aux.getNext();
            }

        if (unionQuantity != 0) {
            RegexItem grandFather = root.getFather();
            RegexItem father = new RegexGroup(++regexCodes[0], root.getFather(), "U");
            RegexItem start = root;
            RegexItem end;

            for (int i = 0; i <= unionQuantity; i++) {
                RegexItem group = new RegexGroup(++regexCodes[0], father, "");
                group.setFirstChild(start);
                end = start;
                end.setPrev(null);

                if (i == 0)
                    father.setFirstChild(group);
                else if (i == unionQuantity)
                    father.setLastChild(group);

                while (end != null) {
                    if ("Union".equals(end.getType())) {
                        group.setLastChild(end.getPrev());
                        group.getLastChild().setNext(null);
                        start = end.getNext();
                        //RegexItem union = new RegexUnion(father);
                        break;
                    } else if ("GroupU".equals(end.getType())) {
                        group.setLastChild(end.getPrev());
                        group.getLastChild().setNext(null);
                        end.setPrev(null);
                        end = null;
                        break;
                    }

                    end.setFather(group);
                    end = end.getNext();
                }

                updateLevels(group.getFirstChild(), 2);
            }

            grandFather.setFirstChild(father);
        }
    }

    /**
     * FIXME: Definición de {@code addRegexConcatGroups}. Agrega grupos a las concatenaciones de una
     * expresion regular.
     *
     * @param root es la raiz de la expresion regular.
     */
    public static void addRegexConcatGroups(RegexItem root) {
        RegexItem aux = root;
        int concatQuantity = 0;
        if (aux != null)
            while (aux != null) {
                switch (aux.getType()) {
                    case "Root":
                    case "Parent":
                    case "Group":
                    case "GroupC":
                    case "GroupU":
                        if (aux.getFirstChild() != null)
                            addRegexConcatGroups(aux.getFirstChild());
                        break;

                    case "Symbol":
                    case "Union":
                        break;

                    case "Concat":
                        concatQuantity++;
                        break;
                }

                aux = aux.getNext();
            }

        if (concatQuantity != 0) {
            RegexItem grandFather = root.getFather();
            RegexItem father = new RegexGroup(++regexCodes[0], root.getFather(), "C");
            RegexItem start = root;
            RegexItem end;

            for (int i = 0; i <= concatQuantity; i++) {
                RegexItem group = new RegexGroup(++regexCodes[0], father, "");
                group.setFirstChild(start);
                end = start;
                end.setPrev(null);

                if (i == 0)
                    father.setFirstChild(group);
                else if (i == concatQuantity)
                    father.setLastChild(group);

                while (end != null) {
                    if ("Concat".equals(end.getType())) {
                        group.setLastChild(end.getPrev());
                        group.getLastChild().setNext(null);
                        start = end.getNext();
                        //RegexItem concat = new RegexConcat(father);
                        break;
                    } else if ("GroupC".equals(end.getType())) {
                        group.setLastChild(end.getPrev());
                        group.getLastChild().setNext(null);
                        end.setPrev(null);
                        end = null;
                        break;
                    }

                    end.setFather(group);
                    end = end.getNext();
                }
                updateLevels(group.getFirstChild(), 2);
            }

            grandFather.setFirstChild(father);
        }
    }

    /**
     * FIXME: Definición de {@code updateLevels}. Actualiza los niveles de los RegexItem de una
     * expresion regular.
     *
     * @param firstChild es el primer hijo de la raiz de la expresion regular.
     * @param quantity   es la cantidad de niveles que se aumentaran a los RegexItem.
     */
    private static void updateLevels(RegexItem firstChild, int quantity) {
        RegexItem aux = firstChild;                                             // Obtiene el primer hijo.
        if (aux != null)                                                         // Si el primer hijo no esta vacio.
            while (aux != null) {                                                // Recorre firstChild y sus hermanos.
                aux.setLevel(aux.getLevel() + quantity);                          // Aumenta a aux a cantidad de niveles indicados por quantity.
                if (aux.getFirstChild() != null)                                 // Si aux tiene hijos.
                    updateLevels(aux.getFirstChild(), quantity);                // Aumenta el nivel de sus hijos.

                aux = aux.getNext();                                            // Pasa el hermano de aux.
            }
    }

    /**
     * FIXME: Definición de {@code addThompsonConstructions}. Agrega construcciones de Thompson a
     * los RegexItem de una expresion regular.
     *
     * @param root     es la raiz de la expresion regular.
     * @param maxLevel es el maximo nivel dentro de la expresion regular.
     */
    public static void addThompsonConstructions(RegexItem root, int maxLevel) {
        int tabs = 4;
        int endSpaces = 10;
        boolean print = false;

        RegexItem aux = root;
        if (aux != null)
            while (aux != null) {
                switch (aux.getType()) {
                    case "Root":
                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, true, false);

                        if (aux.getFirstChild() != null)
                            addThompsonConstructions(aux.getFirstChild(), maxLevel);

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, true, false);
                        break;

                    case "Symbol":

                        switch (aux.getKleeneType()) {
                            case 0:
                                aux.setThompson(new ThompsonSymbol(codes, aux));
                                break;

                            case 1:
                                aux.setThompson(new ThompsonKleenePlus(codes, aux));
                                break;

                            case 2:
                                aux.setThompson(new ThompsonKleene(codes, aux));
                                break;
                        }

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, true, false);

                        break;

                    case "Parent":

                        switch (aux.getKleeneType()) {
                            case 0:
                                aux.setThompson(new ThompsonSymbol(codes, aux));
                                break;

                            case 1:
                                aux.setThompson(new ThompsonKleenePlus(codes, aux));
                                break;

                            case 2:
                                aux.setThompson(new ThompsonKleene(codes, aux));
                                break;
                        }

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, true, false);

                        addThompsonConstructions(aux.getFirstChild(), maxLevel);

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, false, false);

                        break;

                    case "GroupU":
                        aux.setThompson(new ThompsonUnion(codes, aux));

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, true, false);

                        addThompsonConstructions(aux.getFirstChild(), maxLevel);

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, false, false);
                        break;

                    case "GroupC":
                        aux.setThompson(new ThompsonConcat(codes, aux));

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, true, false);

                        addThompsonConstructions(aux.getFirstChild(), maxLevel);

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, false, false);

                        break;

                    case "Group":

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, true, false);

                        addThompsonConstructions(aux.getFirstChild(), maxLevel);

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, false, false);

                        break;
                }

                aux = aux.getNext();
            }
    }

    /**
     * FIXME: Definición de {@code uniteThompsonConstructions}. Une las construcciones de Thompson
     * de los RegexItem de una expresion regular.
     *
     * @param root     es la raiz de la expresion regular.
     * @param maxLevel es el maximo nivel dentro de la expresion regular.
     */
    public static void uniteThompsonConstructions(RegexItem root, int maxLevel) {
        int tabs = 4;
        int endSpaces = 10;
        boolean print = false;

        RegexItem aux = root;
        InputSymbol symbol;
        State prev;
        State next;
        ThompsonConstruction thompson;

        if (aux != null)
            while (aux != null) {
                switch (aux.getType()) {
                    case "Root":

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, true, false);

                        if (aux.getFirstChild() != null)
                            uniteThompsonConstructions(aux.getFirstChild(), maxLevel);

                        aux.setThompson(aux.getFirstChild().getThompson());
                        aux.setFirstChild(null);
                        aux.setLastChild(null);
                        aux.setType("Thompson");

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, true, false);

                        break;

                    case "Symbol":

                        while (aux.getNext() != null && "Symbol".equals(aux.getNext().getType())) {
                            aux.getThompson().concat(aux.getNext().getThompson());
                            aux.setNext(aux.getNext().getNext());
                        }

                        if (aux.getNext() != null)
                            aux.getNext().setPrev(aux);

                        if (aux.getFather() != null) {
                            if (aux.getNext() == null)
                                aux.getFather().setLastChild(aux);
                        }

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, false, false);

                        break;

                    case "Parent":

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, true, false);

                        uniteThompsonConstructions(aux.getFirstChild(), maxLevel);

                        if (aux.getFirstChild().getNext() == null) {
                            symbol = aux.getSymbol();
                            prev = symbol.getPrevState();
                            next = symbol.getNextState();

                            prev.getNextSymbols().remove(symbol.getCode());
                            next.getPrevSymbols().remove(symbol.getCode());

                            prev.merge(aux.getFirstChild().getThompson().getFirstState());
                            next.merge(aux.getFirstChild().getThompson().getLastState());

                            thompson = aux.getThompson();
                            thompson.setStateQuantity(thompson.getStateQuantity() + aux.getFirstChild().getThompson().getStateQuantity() - 2);

                            aux.setFirstChild(null);
                            aux.setLastChild(null);
                            aux.setType("Symbol");

                            if (print)
                                aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, false, false);

                            if (aux.getPrev() != null)
                                aux = aux.getPrev();

                            if (aux.getNext() != null)
                                continue;
                        } else if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, false, false);

                        break;

                    case "GroupU":

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, true, false);

                        uniteThompsonConstructions(aux.getFirstChild(), maxLevel);
                        aux.setFirstChild(null);
                        aux.setType("Symbol");

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, false, false);

                        break;

                    case "GroupC":

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, true, false);

                        uniteThompsonConstructions(aux.getFirstChild(), maxLevel);
                        aux.setFirstChild(null);
                        aux.setType("Symbol");

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, false, false);

                        break;

                    case "Group":

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, true, false);

                        uniteThompsonConstructions(aux.getFirstChild(), maxLevel);

                        if (aux.getFirstChild().getNext() == null) {
                            symbol = aux.getSymbol();
                            prev = symbol.getPrevState();
                            next = symbol.getNextState();

                            prev.getNextSymbols().remove(symbol.getCode());
                            next.getPrevSymbols().remove(symbol.getCode());

                            prev.merge(aux.getFirstChild().getThompson().getFirstState());
                            next.merge(aux.getFirstChild().getThompson().getLastState());

                            aux.setThompson(aux.getFirstChild().getThompson());
                            aux.getThompson().setFirstState(prev);
                            aux.getThompson().setLastState(next);

                            aux.setFirstChild(null);
                            aux.setType("Symbol");

                            thompson = aux.getFather().getThompson();
                            thompson.setStateQuantity(thompson.getStateQuantity() + aux.getThompson().getStateQuantity() - 2);

                        }

                        if (print)
                            aux.printThompsonRegexItem(maxLevel, tabs, endSpaces, false, false);

                        break;
                }

                aux = aux.getNext();
            }
    }
}
