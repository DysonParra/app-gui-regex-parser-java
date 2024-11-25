/*
 * @fileoverview    {ThompsonOperator}
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
package com.project.dev.thompson.operation;

import com.project.dev.automat.Automat;
import com.project.dev.automat.Partition;
import com.project.dev.automat.Status;
import com.project.dev.thompson.construction.InputSymbol;
import com.project.dev.thompson.construction.State;
import com.project.dev.thompson.construction.ThompsonConstruction;
import com.project.dev.thompson.construction.Transition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * TODO: Description of {@code ThompsonOperator}.
 *
 * @author Dyson Parra
 * @since 11
 */
public class ThompsonOperator {

    /**
     * FIXME: Description of {@code makeStatesArray}. Crea un array con cada uno de los estados de la
     * construccion de thompson.
     *
     * @param thompson es la construccion de thompson a la que se le actualizaran los estados.
     * @return un array con todos los estados de la construccion de thompson.
     */
    private static State[] makeStatesArray(ThompsonConstruction thompson) {
        State[] states = new State[thompson.getStateQuantity()];                // Estado encontrados.
        int[] newStatesPos = {-1};                                              // Indica en que posicion de array se agrega el proximo estado.

        State firsState = thompson.getFirstState();                             // Obtiene el primer estado.
        ThompsonOperator.addStatesToArray(firsState, states, newStatesPos);     // Agrega el primer estado y los siguientes al array de estados.

        return states;                                                          // Devuelve el array con los estados.
    }

    /**
     * FIXME: Description of {@code addStatesToArray}. Agrega un estado y todos los que le siguen a
     * un array.
     *
     * @param firstState   indica el primer estado.
     * @param states       es el array con lo estados que se iranagregando.
     * @param newStatesPos indica la posicion en el array donde se va a agregar el proximo estado.
     */
    private static void addStatesToArray(State firstState, State[] states, int[] newStatesPos) {
        boolean addedState = false;                                             // Si el estado actual ya se agrego al array.
        newStatesPos[0]++;

        for (State state : states)                                              // Recorre el array con los estados.
            if (state == firstState) {
                addedState = true;
                newStatesPos[0]--;
                break;
            }

        InputSymbol symbol;

        if (!addedState) {
            states[newStatesPos[0]] = firstState;

            for (Map.Entry<Integer, InputSymbol> entry : firstState.getNextSymbols().entrySet()) {
                symbol = entry.getValue();
                addStatesToArray(symbol.getNextState(), states, newStatesPos);
            }
        }
    }

    /**
     * FIXME: Description of {@code updateStatesCodes}. Actualiza los nombres de los estados de una
     * construccion de thompson
     *
     * @param states es el array con los estados de una construccion de thompson.
     */
    private static void updateStatesCodes(State[] states) {
        for (int i = 0; i < states.length; i++)
            states[i].setCode(i + 1);
    }

    /**
     * FIXME: Description of {@code makeStateTransitions}. Obtiene los estados a los que se puede
     * lleger desde un estado inicial usando trancisiones con un simbolo indicado.
     *
     * @param state     indica el estado inicial.
     * @param symbol    indica el simbolo para pasar del estado inicial a otro.
     * @param endStates indica donde se pondran los estados de fin.
     * @param recursive indica si se va a ejecutar recursivamente comprobando las trancisiones de
     *                  cada estado que se agregue.
     */
    private static void makeStateTransitions(State state, String symbol, Map<State, String> endStates, boolean recursive) {
        State nextState;
        InputSymbol nextSymbol;
        String nextSymbolValue;
        boolean print = false;

        for (Map.Entry<Integer, InputSymbol> entry : state.getNextSymbols().entrySet()) {
            nextSymbol = entry.getValue();
            nextSymbolValue = nextSymbol.getRegexItem().getValue();
            nextState = nextSymbol.getNextState();

            if (print) {
                System.out.printf("(%2s)--[%2s][%s]--> ", state.getCode(), nextSymbol.getCode(), nextSymbolValue);
                System.out.printf("(%2s)", nextState.getCode());
            }

            if (endStates.get(nextState) == null) {
                if (print)
                    System.out.print(" ... ");
                if (nextSymbolValue.equals(symbol)) {
                    if (print)
                        System.out.printf("ADDED (%2s)\n", nextState.getCode());
                    endStates.put(nextState, nextSymbolValue);

                    if (recursive)
                        makeStateTransitions(nextState, symbol, endStates, recursive);
                } else if (print)
                    System.out.println("");

            } else
                System.out.print("\n");
        }
    }

    /**
     * FIXME: Description of {@code makeClosingLambda}. Obtiene el ciere lambda de cada estado.
     *
     * @param states son los estados de una construccion de thompson.
     * @return un array de trancisiones con los cierres lambda.
     */
    private static Transition[] makeClosingLambda(State[] states) {
        boolean print = false;
        Transition[] closingLambdas = new Transition[states.length];
        for (int i = 0; i < states.length; i++) {
            //System.out.printf("(%s)\n", states[i].getCode());
            closingLambdas[i] = Transition.builder().code(states[i].getCode() + "").symbol("λ").build();
            closingLambdas[i].addStartState(states[i]);
            if (print)
                System.out.printf("Making transitions for (%2s) with [%s]\n", states[i].getCode(), "λ");
            makeStateTransitions(states[i], "λ", closingLambdas[i].getEndStates(), true);
            if (print)
                System.out.println("");
            closingLambdas[i].addEndState(states[i]);
        }

        return closingLambdas;
    }

    /**
     * FIXME: Description of {@code getInputSymbols}. Obtiene los simbolos de entrada de una
     * construccion de thompson.
     *
     * @param states son los estados de una construccion de thompson.
     * @return los simbolos de entrada de la construccion de thompson.
     */
    private static Map<String, String> getInputSymbols(State[] states) {
        boolean print = false;
        if (print)
            System.out.println("Getting input symbols...");
        Map<String, String> inputSymbols = new HashMap<>();
        InputSymbol nextSymbol;
        String nextSymbolValue;

        for (State state : states) {
            for (Map.Entry<Integer, InputSymbol> entry : state.getNextSymbols().entrySet()) {
                nextSymbol = entry.getValue();
                nextSymbolValue = nextSymbol.getRegexItem().getValue();

                if (inputSymbols.get(nextSymbolValue) == null && !nextSymbolValue.equals("λ")) {
                    inputSymbols.put(nextSymbolValue, nextSymbolValue);
                    if (print)
                        System.out.printf("Found symbol [%s]\n", nextSymbolValue);
                }
            }
        }
        if (print)
            System.out.println("");

        return inputSymbols;
    }

    /**
     * FIXME: Description of {@code compareTransitionStates}. Compara si dos maps de estados tienen
     * los mismos estados.
     *
     * @param states1 es el primer map de estados.
     * @param states2 es el segundo map de stados.
     * @return si ambos maps tienen los mismos estados.
     */
    private static boolean compareTransitionStates(Map<State, String> states1, Map<State, String> states2) {
        if (states1.size() != states2.size())
            return false;
        else if (!states1.entrySet().stream().noneMatch((entry) -> (states2.get(entry.getKey()) == null)))
            return false;

        return true;
    }

    /**
     * FIXME: Description of {@code existTransition}. Valida si ya existe una transicion en el
     * arrayList de trancisiones con estados iniciales igual a los estados parametro.
     *
     * @param allTransitions contiene todas las trancisiones que hay hasta ahora.
     * @param startStates    son los estados que se compararran si ya existen en una transicion.
     * @return si los estados ya estan en una trancision existente.
     */
    private static boolean existTransition(ArrayList<Transition[]> allTransitions, Map<State, String> startStates) {
        boolean existTransition = false;
        Transition transAux;

        Iterator aux = allTransitions.iterator();

        while (aux.hasNext()) {
            transAux = ((Transition[]) aux.next())[0];
            //System.out.println(transAux.getCode() + " " + transAux.getSymbol());
            if (compareTransitionStates(startStates, transAux.getStartStates())) {
                existTransition = true;
                break;
            }
        }

        return existTransition;
    }

    /**
     * FIXME: Description of {@code getFirstTransitions}. Obtiene las transiciones del primer estado
     * del automata finito a crear.
     *
     * @param closingLambdas son los cierres lambda de la construccion de thompson.
     * @param inputSymbols   son los simbolos de entrada de la construccion de thompson.
     * @return las transiciones del primer estado del automata finito a crear.
     */
    private static Transition[] getFirstTransitions(Transition[] closingLambdas, Map<String, String> inputSymbols) {
        boolean print = false;
        Transition[] firstTransitions = new Transition[inputSymbols.size()];
        String symbol;

        for (int i = 0; i < firstTransitions.length; i++) {
            symbol = inputSymbols.get(inputSymbols.keySet().toArray()[i]);
            firstTransitions[i] = Transition.builder().code(i + "").symbol(symbol).build();
        }

        State state;

        for (Map.Entry<State, String> entry : closingLambdas[0].getEndStates().entrySet()) {
            state = entry.getKey();
            for (Transition firstTransition : firstTransitions) {
                firstTransition.addStartState(state);
                if (print)
                    System.out.printf("Making transitions for (%2s) with [%s]\n", state.getCode(), firstTransition.getSymbol());
                makeStateTransitions(state, firstTransition.getSymbol(), firstTransition.getEndStates(), false);
                if (print)
                    System.out.println("");
            }
            //System.out.println("---");
            //ThompsonPrinter.printTransitions(inputSymbolsTrasitions);
            //System.out.println("---\n");
        }

        for (int j = 0; j < firstTransitions.length; j++) {
            Transition transitionAux = Transition.builder()
                    .code(j + "")
                    .symbol(firstTransitions[j].getSymbol())
                    .build();
            transitionAux.addEndState(firstTransitions[j].getEndStates());

            for (Map.Entry<State, String> entry : transitionAux.getEndStates().entrySet())
                firstTransitions[j].addEndState(closingLambdas[entry.getKey().getCode() - 1].getEndStates());

            //ThompsonPrinter.printTransition(transitionAux);
            //System.out.println("");
        }

        //ThompsonPrinter.printTransitions(firstTransitions);
        //System.out.println("---\n");
        return firstTransitions;
    }

    /**
     * FIXME: Description of {@code getTransitions}. Obtiene las transiciones de los estados del
     * automata finito a crear.
     *
     * @param allTransitions contiene todas las trancisiones que se han agregado hasta ahora.
     * @param closingLambdas son los cierres lambda de la construccion de thompson.
     * @param startStates    son los estados de inicio de la trancision.
     * @param inputSymbols   son los simbolos de entrada de la construccion de thompson.
     */
    private static void getTransitions(ArrayList<Transition[]> allTransitions, Transition[] closingLambdas, Map<State, String> startStates, Map<String, String> inputSymbols) {
        boolean print = false;
        Transition[] transitions = new Transition[inputSymbols.size()];
        String symbol;

        for (int i = 0; i < transitions.length; i++) {
            symbol = inputSymbols.get(inputSymbols.keySet().toArray()[i]);
            transitions[i] = Transition.builder().code(i + "").symbol(symbol).build();
            transitions[i].addStartState(startStates);
        }

        //System.out.println("---");
        //ThompsonPrinter.printTransitions(transitions);
        //System.out.println("---\n");
        State state;

        for (Transition transition : transitions) {
            for (Map.Entry<State, String> entry : transition.getStartStates().entrySet()) {
                state = entry.getKey();
                if (print)
                    System.out.printf("Making transitions for (%2s) with [%s]\n", state.getCode(), transition.getSymbol());
                makeStateTransitions(state, transition.getSymbol(), transition.getEndStates(), false);
                if (print)
                    System.out.println("");
            }
        }

        //System.out.println("-----");
        //ThompsonPrinter.printTransitions(transitions);
        //System.out.println("----------\n");
        for (int j = 0; j < transitions.length; j++) {
            Transition transitionAux = Transition.builder().code(j + "").symbol(transitions[j].getSymbol()).build();
            transitionAux.addEndState(transitions[j].getEndStates());

            for (Map.Entry<State, String> entry : transitionAux.getEndStates().entrySet())
                transitions[j].addEndState(closingLambdas[entry.getKey().getCode() - 1].getEndStates());
        }

        //System.out.println("-----");
        //ThompsonPrinter.printTransitions(transitions);
        //System.out.println("----------\n");
        if (!existTransition(allTransitions, transitions[0].getStartStates()) && !transitions[0].getStartStates().isEmpty()) {
            allTransitions.add(transitions);

            for (int j = 0; j < transitions.length; j++) {
                Transition transitionAux = Transition.builder().code(j + "").symbol(transitions[j].getSymbol()).build();
                transitionAux.addEndState(transitions[j].getEndStates());

                for (Map.Entry<State, String> entry : transitionAux.getEndStates().entrySet())
                    transitions[j].addEndState(closingLambdas[entry.getKey().getCode() - 1].getEndStates());

                //System.out.println("---");
                //ThompsonPrinter.printTransition(transitions[j]);
                if (existTransition(allTransitions, transitions[j].getEndStates())) {
                    //System.out.println("\nExist");
                    //System.out.println("--------\n");
                } else if (compareTransitionStates(transitions[j].getStartStates(), transitions[j].getEndStates())) {
                    //System.out.println("\nReverse");
                    //System.out.println("--------\n");
                } else if (!existTransition(allTransitions, transitions[j].getEndStates()) && !transitions[j].getEndStates().isEmpty()) {
                    //System.out.println("\nNO Exist");
                    //System.out.println("--------\n");

                    getTransitions(allTransitions, closingLambdas, transitions[j].getEndStates(), inputSymbols);
                }
            }
        } else {
            //System.out.println("\nRepeat");
            //System.out.println("--------\n");
        }

    }

    /**
     * FIXME: Description of {@code makeAutomatStatusesArray}. Obtiene un array con los estados de un
     * automata finito.
     *
     * @param closingLambdas son los cierres lambda una construccion de thompson.
     * @param inputSymbols   son los simbolos de entrada de una construccion de thompson.
     * @param lastState      indica el ultimo estado de la construccion de thompson (aceptacion).
     * @return los estados del automata finito.
     */
    private static ArrayList<Status> makeAutomatStatusesArray(Transition[] closingLambdas, Map<String, String> inputSymbols, State lastState) {
        boolean print = false;
        ArrayList<Transition[]> allTransitions = new ArrayList<>();
        Iterator transIterator;
        Iterator statusIterator1;
        Iterator statusIterator2;

        Transition[] transitions = getFirstTransitions(closingLambdas, inputSymbols);
        allTransitions.add(transitions);

        if (print) {
            System.out.println("---");
            ThompsonPrinter.printTransitions(transitions);
            System.out.println("------\n");
        }

        // Agrega tranciciones en base a cierres lambda.
        for (Transition transition : transitions)
            getTransitions(allTransitions, closingLambdas, transition.getEndStates(), inputSymbols);

        // Agrega cantidad de estados igual a la cantidad de trancisiones.
        ArrayList<Status> allStatuses = new ArrayList<>();

        Transition[] transAux1;
        Status statusAux1;
        Status statusAux2;

        transIterator = allTransitions.iterator();

        while (transIterator.hasNext()) {
            transAux1 = (Transition[]) transIterator.next();

            if (transAux1[0].getStartStates().get(lastState) == null) {
                statusAux1 = Status.builder()
                        .code(transAux1[0].getStartStatesString())
                        .build();
                //statusAux1 = new Status(transAux1[0].getStartStatesString());
            } else {
                statusAux1 = Status.builder()
                        .code(transAux1[0].getStartStatesString())
                        .endStatus(true)
                        .build();
                //statusAux1 = new Status(transAux1[0].getStartStatesString(), true);
            }

            allStatuses.add(statusAux1);

            //System.out.println("---");
            //ThompsonPrinter.printTransitions(transAux1);
            //System.out.println("------\n");
        }

        // Agrega siguientes estados a cada estado.
        statusIterator1 = allStatuses.iterator();
        transIterator = allTransitions.iterator();

        while (statusIterator1.hasNext()) {
            statusAux1 = (Status) statusIterator1.next();
            transAux1 = (Transition[]) transIterator.next();

            for (Transition transAux11 : transAux1) {
                statusIterator2 = allStatuses.iterator();
                while (statusIterator2.hasNext()) {
                    statusAux2 = (Status) statusIterator2.next();
                    if (statusAux2.getCode().equals(transAux11.getEndStatesString())) {
                        statusAux1.addNextStatus(transAux11.getSymbol(), statusAux2);
                        break;
                    }
                }
            }
        }

        // Modifica los nombres de cada estado.
        statusIterator1 = allStatuses.iterator();

        for (int i = 1; i < 1000; i++) {
            if (!statusIterator1.hasNext())
                break;

            statusAux1 = (Status) statusIterator1.next();
            statusAux1.setCode("S" + String.valueOf(i));
        }

        return allStatuses;
    }

    /**
     * FIXME: Description of {@code findPartition}. Obtiene en que particion está un estado.
     *
     * @param partitions indica las particiones.
     * @param state      indica el estado que se va abuscar.
     * @return la particion donde se encuentra el estado.
     */
    public static Partition findPartition(ArrayList<Partition> partitions, Status state) {
        Partition partition = null;

        for (Partition aux : partitions) {
            if (aux.contains(state)) {
                partition = aux;
                break;
            }
        }
        return partition;
    }

    /**
     * FIXME: Description of {@code simplifyAutomatStatuses}. Simplifica los estados que va a tener
     * un automata finito.
     *
     * @param statuses     indica los estados del automata.
     * @param inputSymbols indica los simbolos de entrada del automata.
     * @return los estados simplificados.
     */
    public static ArrayList<Status> simplifyAutomatStatuses(ArrayList<Status> statuses, Map<String, String> inputSymbols) {

        ArrayList<Status> simplified = (ArrayList<Status>) statuses.clone();
        ArrayList<Partition> partitions = new ArrayList<>();

        Partition part_0 = Partition.builder().name("P0:").build();                     // Crea la partición 0.
        Partition part_1 = Partition.builder().name("P1:").build();                     // Crea la partición 1.
        partitions.add(part_0);                                                         // Agrega la partición 0 a la lista de particiones.
        partitions.add(part_1);                                                         // Agrega la partición 1 a la lista de particiones.

        // Reparte todos los esados en la partición 0 y 1.
        simplified.forEach(state -> {
            if (state.isEndStatus())
                part_1.add(state);
            else
                part_0.add(state);
        });

        boolean print = false;                                                          // Si va a imprimir información de debug durante el proceso.
        boolean foundPart;                                                              // Si ya se encontró la partición común.
        Partition commonPart;                                                           // Partición a la que va el primer elemento con el simbolo indicado en cada recorrido.
        Partition partAux;                                                              // En que partición se encuentra el estado al que hace transición el actual con algún simbolo.
        boolean divided;                                                                // Si se hizo alguna división de partición.
        Partition newDivision;                                                          // Referencia a la nuevas particiones que se vayan creando.
        do {                                                                            // Recorre mientras se hagan divisiones.
            if (print) {                                                                // Si se indicó imprimir.
                System.out.print("\nStarting while: {");                                // Muestra que va a empezar el recorrido.
                for (Partition part : partitions) {                                     // Recorre todas las particiones.
                    System.out.println("\n    " + part.getName());                      // Muestra el nombre de la partición.
                    for (Status state : part.getStatuses()) {                           // Recorre los estados de la partición.
                        System.out.print("    ");                                       // Imprime espacios al inicio de la línea.
                        state.simplePrint();                                            // Muestra la partición.
                    }
                }
                System.out.println("}");                                                // Imprrime cierre.
            }

            divided = false;                                                            // Indica que no se han encontrado divisiones.
            for (Map.Entry<String, String> entry : inputSymbols.entrySet()) {           // Recorre los simbolos de entrada.
                if (divided)                                                            // Si se hizo una división en la partición.
                    break;                                                              // termina de recorrer los simbolos.
                String symbol = entry.getKey();                                         // Obtiene el simbolo.
                if (print)                                                              // Si se indicó imprimir.
                    System.out.println("\n" + symbol);                                  // Muestra el simbolo.
                newDivision = null;                                                     // Reinicia las las divisiones.
                for (Partition part : partitions) {                                     // Recorre todas las particiones para el simbolo actual.
                    foundPart = false;                                                  // Indica que no se ha encontrado la partición común aún.
                    commonPart = null;                                                  // Reinicia la partición a donde deben ir los simbolos.
                    if (print)                                                          // Si se indicó imprimir.
                        System.out.println("\n    " + part.getName());                  // Muestra el nombre de la partición.
                    if (part.size() == 1) {                                             // Si el tamaño de la partición es uno.
                        Status state = part.getStatuses().get(0);                       // Obtiene el único estado de la partición.
                        if (print) {                                                     // Si se indicó imprimir.
                            System.out.print("    ");                                   // Imprime espacios al inicio de la línea.
                            state.simplePrint();                                        // Muestra la partición.
                        }
                        continue;                                                       // Pasa a la siguiente partición.
                    }
                    for (Status state : part.getStatuses()) {                           // Recorre los estados de la partición.
                        String name = "P" + partitions.size() + ":";                    // Obtiene el nombre que tendrá la nueva partición en caso que se vaya a crear.
                        partAux = findPartition(partitions, state.getNextStatuses().get(symbol));  // Obtiene la partición en donde se encuentra el estado a adonde va el actual con el simbolo actual.
                        if (!foundPart) {                                                // Si no se ha encontrado la partición común.
                            foundPart = true;                                           // Indica que se encontró la partición común.
                            commonPart = partAux;                                       // Almacena la partición común.
                            if (print) {                                                 // Si se indicó imprimir.
                                System.out.print("    Common is ");                     // Muestra mensaje que indica que a continuación se muestra la partición común.
                                System.out.println(commonPart != null ? commonPart.getName() : "null");// Muestra la partición común.
                            }
                        }
                        if (print) {                                                     // Si se indicó imprimir.
                            System.out.print("    ");                                   // Imprime espacios al inicio de la línea.
                            state.simplePrint();                                        // Muestra el estado.
                            System.out.print("       " + partAux);                      // Muestra a que partición va el estado actual si se usa el simbolo actual.
                        }
                        if (partAux != commonPart) {                                     // Si el estado actual no va a la partción común usando el simbolo actual.
                            divided = true;                                             // Indica que se hizo una división.
                            if (print)                                                   // Si se indicó imprimir.
                                System.out.print(" ---> Different");                    // Muestra que mensaje que indica que el estado actual va a una partición diferente a la común.
                            if (newDivision == null) {                                   // Si es el primer estado que va a una partición diferente en el recorrido actual.
                                if (print)                                               // Si se indicó imprimir.
                                    System.out.print(" ----> Created '" + name + "'");  // Muestra mensaje indicando que se acaba de crear una nueva particón y que se agregó el estado allí.
                                newDivision = Partition.builder().name(name).build();   // Crea una nueva partición.
                                newDivision.add(state);                                 // Agrega el estado actual a la nueva división.
                            } else {
                                if (print)                                               // Si se indicó imprimir.
                                    System.out.print(" ---> Added to '" + name + "'");  // Muestra mensaje indicando que se agregó el estado en la nueva partición.
                                newDivision.add(state);                                 // Agrega el estado actual a la nueva división.
                            }
                            if (print)                                                   // Si se indicó imprimir.
                                System.out.println("");                                 // Muestra salto de línea.
                        } else if (print)                                                  // Si se indicó imprimir.
                            System.out.println("");                                     // Muestra salto de línea.
                    }
                    if (divided) {                                                       // Si se hizo una división.
                        if (print) {                                                     // Si se indicó imprimir.
                            System.out.println("    newDivision:");                     // Muestra mensaje que indica que a continuación se muestra la nueva partición.
                            System.out.println("    " + newDivision);                   // Muestra la nueva partición.
                        }
                        for (Status state : newDivision.getStatuses()) {                // Recorre los estados de la nueva división.
                            part.remove(state);                                         // Borra los estados de la nueva partción de la actual.
                        }
                        partitions.add(newDivision);                                    // Aggrega la nueva división a la lista de particiones.
                        break;
                    }
                }
            }
        } while (divided);

        boolean print2 = false;
        if (print2)
            System.out.print("\nStatuses: {");
        Map<String, Status> nextStatuses;
        Map<String, Status> nextStatusesAux;
        Partition found;
        for (Status state : simplified) {
            if (print2) {
                System.out.print("\n   ");
                state.simplePrint();
            }
            nextStatuses = state.getNextStatuses();
            nextStatusesAux = new HashMap<>();
            for (Map.Entry<String, Status> entry : nextStatuses.entrySet()) {
                found = findPartition(partitions, entry.getValue());
                if (print2) {
                    System.out.print("      " + entry.getKey());
                    System.out.print(" --> [" + entry.getValue());
                    System.out.print(" --> " + found.getName());
                    System.out.print(" --> " + found.getStatuses().get(0));
                    System.out.println("]");
                }
                nextStatusesAux.put(entry.getKey(), found.getStatuses().get(0));
            }
            state.setNextStatuses(nextStatusesAux);
        }
        if (print2)
            System.out.println("}");

        ArrayList<Status> toDelete = new ArrayList<>();
        partitions.forEach(part -> {
            for (int i = 0; i < part.getStatuses().size(); i++)
                if (i != 0)
                    toDelete.add(part.getStatuses().get(i));
        });

        toDelete.forEach(state -> simplified.remove(state));

        if (print2) {
            System.out.println("\nStatuses: {\n");
            simplified.forEach(state -> state.print(true, 3));
            System.out.println("}");
        }
        return simplified;
    }

    /**
     * FIXME: Description of {@code makeAutomat}. Obtiene un automata finito en base a una
     * construccion de thompson.
     *
     * @param thompson     es la construccion de thompson.
     * @param endSequence  indica el caracter de fin de secuencia del automata finito.
     * @param nullSequence indica cual es la secuencia nula.
     * @return el automata finito.
     */
    public static Automat makeAutomat(ThompsonConstruction thompson, String endSequence, String nullSequence) {
        boolean print = false;
        State[] states = ThompsonOperator.makeStatesArray(thompson);
        ThompsonOperator.updateStatesCodes(states);

        if (print) {
            thompson.print(true);
            System.out.println("\n");
        }

        //ThompsonPrinter.printNextStates(states);
        //System.out.println("\n");
        Transition[] closingLambdas = ThompsonOperator.makeClosingLambda(states);
        if (print)
            System.out.println("\n");
        //ThompsonPrinter.printTransitions(closingLambdas);
        //System.out.println("\n");

        Map<String, String> inputSymbols = ThompsonOperator.getInputSymbols(states);
        //System.out.println("\n");
        //ThompsonPrinter.printInputSymbols(inputSymbols);
        //System.out.println("\n");

        ArrayList<Status> allStatuses = ThompsonOperator.makeAutomatStatusesArray(closingLambdas, inputSymbols, thompson.getLastState());

        if (inputSymbols.get(nullSequence) != null) {                            // Si el automata solo reconoce la secuencia nula.
            inputSymbols.remove(nullSequence);                                  // Borra la secuencia nula de los simbolos de entrada.
            allStatuses.remove(allStatuses.get(1));                             // Borra el segundo estado.
            allStatuses.get(0).setNextStatuses(new HashMap<>());                // Indica que del estado 1 no es posible llegar a ninguno.
            allStatuses.get(0).setEndStatus(true);                              // Indica que el primer estado es de aceptacion.
        }

        ArrayList<Status> simplifiedStatuses = simplifyAutomatStatuses(allStatuses, inputSymbols);
        return Automat.builder()
                .code("")
                //.statuses(allStatuses)
                .statuses(simplifiedStatuses)
                .inputSymbols(inputSymbols)
                .endSequence(endSequence)
                .nullSequence(nullSequence)
                .build();
    }
}
