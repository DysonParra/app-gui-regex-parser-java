/*
 * @fileoverview    {Transition}
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
package com.project.dev.thompson.construction;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * TODO: Description of {@code Transition}.
 *
 * @author Dyson Parra
 * @since 11
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Transition {

    @NonNull
    private String code;                                        // Codigo de la trancision actual.
    @NonNull
    private String symbol;                                      // Simbolo usado para llegar de los estados de inicio a los de fin.
    @Builder.Default
    private Map<State, String> startStates = new HashMap<>();   // Estados de inicio de la trancision actual.
    @Builder.Default
    private Map<State, String> endStates = new HashMap<>();     // Estados de fin de la trancision actual.

    /**
     * TODO: Description of {@code addStartState}.
     *
     * @param state
     */
    public void addStartState(State state) {
        this.startStates.put(state, symbol);
    }

    /**
     * TODO: Description of {@code addStartState}.
     *
     * @param states
     */
    public void addStartState(Map<State, String> states) {
        states.entrySet().stream().forEach((entry) -> {
            addStartState(entry.getKey());
        });
    }

    /**
     * TODO: Description of {@code addEndState}.
     *
     * @param state
     */
    public void addEndState(State state) {
        this.endStates.put(state, symbol);
    }

    /**
     * TODO: Description of {@code addEndState}.
     *
     * @param states
     */
    public void addEndState(Map<State, String> states) {
        states.entrySet().stream().forEach((entry) -> {
            addEndState(entry.getKey());
        });
    }

    /**
     * TODO: Description of {@code getStartStatesString}.
     *
     * @return
     */
    public String getStartStatesString() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();                 // Crea objeto de tipo ByteArrayOutputStream.
        PrintStream printer = new PrintStream(os);                              // Crea objeto de tipo PrintStream.
        State state;

        if (!this.getStartStates().isEmpty())
            for (Map.Entry<State, String> entry : this.getStartStates().entrySet()) {
                state = entry.getKey();
                printer.printf("(%s) ", state.getCode());
            }
        else
            printer.printf("(none)");

        return os.toString().trim();
    }

    /**
     * TODO: Description of {@code getEndStatesString}.
     *
     * @return
     */
    public String getEndStatesString() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();                 // Crea objeto de tipo ByteArrayOutputStream.
        PrintStream printer = new PrintStream(os);                              // Crea objeto de tipo PrintStream.
        State state;

        if (!this.getStartStates().isEmpty())
            for (Map.Entry<State, String> entry : this.getEndStates().entrySet()) {
                state = entry.getKey();
                printer.printf("(%s) ", state.getCode());
            }
        else
            printer.printf("(none)");

        return os.toString().trim();
    }

    /**
     * Obtiene el valor en {String} del objeto actual.
     *
     * @return un {String} con la representación del objeto.
     */
    @Override
    public String toString() {
        return Integer.toHexString(hashCode());
    }
}
