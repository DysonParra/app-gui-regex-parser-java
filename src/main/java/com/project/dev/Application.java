/*
 * @fileoverview {Application} se encarga de realizar tareas especificas.
 *
 * @version             1.0
 *
 * @author              Dyson Arley Parra Tilano <dysontilano@gmail.com>
 * Copyright (C) Dyson Parra
 *
 * @History v1.0 --- La implementacion de {Application} fue realizada el 31/07/2022.
 * @Dev - La primera version de {Application} fue escrita por Dyson A. Parra T.
 */
package com.project.dev;

import com.project.dev.frame.MainFrame;

/**
 * TODO: Definición de {@code Application}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
public class Application {

    /**
     * Entrada principal del sistema.
     *
     * @param args argumentos de la linea de comandos.
     */
    public static void main(String[] args) {
        MainFrame mainFrame;                                    // Menú principal.
        mainFrame = new MainFrame();                            // Inicializa instancia de MainFrame.
        mainFrame.setVisible(true);                             // Pone visible el MainFrame.

        //RegexItem regex = (RegexItem) RegexParser.parseString("((1|0.1)*|1)+\\");
        //RegexItem regex = (RegexItem) RegexParser.parseString("(1*0.1*)+\\");
        //RegexItem regex = (RegexItem) RegexParser.parseString("(SAL*ES|AZU.CAR|TOOL|ON)*.IF(TOO)\\");
        //RegexItem regex = (RegexItem) RegexParser.parseString("IF(TOO)*\\");
        //RegexItem regex = (RegexItem) RegexParser.parseString("(SA|AZ)*OF\\");
        //RegexItem regex = (RegexItem) RegexParser.parseString("(SA).IF(TO)\\");
        //RegexItem regex = (RegexItem) RegexParser.parseString("(0|10*1)*0*\\");
        //RegexItem regex = (RegexItem) RegexParser.parseString("ab\\");
        //RegexItem regex = (RegexItem) RegexParser.parseString("$\\");
    }
}
