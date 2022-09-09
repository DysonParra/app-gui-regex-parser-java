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
package com.project.dev.frame;

import com.project.dev.automat.Automat;
import com.project.dev.automat.operation.AutomatOperator;
import com.project.dev.regexitem.RegexConstant;
import com.project.dev.regexitem.RegexItem;
import com.project.dev.regexoperation.RegexOperator;
import com.project.dev.regexoperation.RegexParser;
import com.project.dev.thompson.construction.ThompsonConstruction;
import com.project.dev.thompson.operation.ThompsonOperator;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * TODO: Definición de {@code MainFrame}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
public class MainFrame extends javax.swing.JFrame implements RegexConstant {

    private String regexText;
    private RegexItem regex;

    /**
     * TODO: Definición de {@code MainFrame}.
     *
     */
    public MainFrame() {
        this.setTitle("Main Frame");
        initComponents();
        //jTextRegex.setText("(0|10*1)*0*\\");
        //jTextRegex.setText("A(BC)\\");
        //jTextRegex.setText("(0|1|0|1)(A|B|A|B)C\\");
        jTextRegex.setText("(0|1)(A|B)C(0|1)\\");
        //jTextRegex.setText("abcabc\\");
    }

    /**
     * TODO: Definición de {@code printLog}.
     *
     * @param logCode
     * @param pos
     */
    private void printLog(int logCode, int pos) {

        if (logCode > 0) {
            jTextRegexResult.setText(jTextRegexResult.getText()
                    + "La expresion regular indicada es correcta.\n"
                    + "Genere el automata finito en el menu archivo.\n\n");
        } else {
            jTextRegexResult.setText(jTextRegexResult.getText()
                    + "Error en caracter #" + (pos + 1)
                    + ":  " + jTextRegex.getText().charAt(pos) + "\n");

            switch (logCode) {
                case REGEX_STATUS_ERROR_FIRST_CHAR_INVALID:
                    jTextRegexResult.setText(jTextRegexResult.getText()
                            + "El primer caracter debe ser diferente a:   |.*+)\\\n\n");
                    break;

                case REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_EMPTY_SEQUENCE_NOT_END_SEQUENCE:
                    jTextRegexResult.setText(jTextRegexResult.getText()
                            + "No puede escribir algun caracter diferente a fin de secuencia luego de indicar que es una secuencia vacia.\n\n");
                    break;

                case REGEX_STATUS_ERROR_NEXT_CHAR_AFTER_NULL_SEQUENCE_NOT_END_SEQUENCE:
                    jTextRegexResult.setText(jTextRegexResult.getText()
                            + "No puede escribir algun caracter diferente a fin de secuencia luego de indicar que es la secuencia nula.\n\n");
                    break;

                case REGEX_STATUS_ERROR_CHAR_AFTER_END_SEQUENCE:
                    jTextRegexResult.setText(jTextRegexResult.getText()
                            + "No puede escribir caracteres luego del caracter de fin de secuencia.\n\n");
                    break;

                case REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_UNION:
                    jTextRegexResult.setText(jTextRegexResult.getText()
                            + "Luego del caracter de union solo puede escribir un caracter diferente a:   |.*+)\\$@\n\n");
                    break;

                case REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_CONCAT:
                    jTextRegexResult.setText(jTextRegexResult.getText()
                            + "Luego del caracter de concatenacion solo puede escribir un caracter diferente a:   |.*+)\\$@\n\n");
                    break;

                case REGEX_STATUS_ERROR_INVALID_CHAR_AFTER_OPEN_PARENTHESIS:
                    jTextRegexResult.setText(jTextRegexResult.getText()
                            + "Luego de abrir un parentesis solo puede escribir un caracter diferente a:   |.*+)\\$@\n\n");
                    break;

                case REGEX_STATUS_ERROR_KLEENE_CHAR_AFTER_OTHER_KLEENE_CHAR:
                    jTextRegexResult.setText(jTextRegexResult.getText()
                            + "No puede escribir dos caracteres de kleene *+ juntos.\n\n");
                    break;

                case REGEX_STATUS_ERROR_NULL_SEQUENCE_IN_POS_DIFF_AS_FIRST:
                    jTextRegexResult.setText(jTextRegexResult.getText()
                            + "No puede escribir el caracter de secuencia nula luego de escribir algun otro caracter.\n\n");
                    break;

                case REGEX_STATUS_ERROR_EMPTY_SEQUENCE_IN_POS_DIFF_AS_FIRST:
                    jTextRegexResult.setText(jTextRegexResult.getText()
                            + "No puede escribir el caracter de secuencia vacia luego de escribir algun otro caracter.\n\n");
                    break;

                case REGEX_STATUS_ERROR_MORE_OPEN_THAT_CLOSE_PARENTHESIS:
                    jTextRegexResult.setText(jTextRegexResult.getText()
                            + "Hay mas parentesis de apertura que de cierre.\n\n");
                    break;

                case REGEX_STATUS_ERROR_MORE_CLOSE_THAT_OPEN_PARENTHESIS:
                    jTextRegexResult.setText(jTextRegexResult.getText()
                            + "Hay mas parentesis de cierre que de apertura.\n\n");
                    break;

                case REGEX_STATUS_ERROR_LAST_CHAR_IS_NOT_END_SEQUENCE:
                    jTextRegexResult.setText(jTextRegexResult.getText()
                            + "El ultimo caracter debe ser fin de secuencia:   \\.\n\n");
                    break;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelRegex = new javax.swing.JLabel();
        jTextRegex = new javax.swing.JTextField();
        jButtonValidateRegex = new javax.swing.JButton();
        jScrollPaneRegexResult = new javax.swing.JScrollPane();
        jTextRegexResult = new javax.swing.JTextArea();
        jMenuMain = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemGenerateAutomat = new javax.swing.JMenuItem();
        jMenuItemCleanLog = new javax.swing.JMenuItem();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabelRegex.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelRegex.setText("Escriba una expresión regular");

        jTextRegex.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextRegex.addActionListener(this::jTextRegexActionPerformed);

        jButtonValidateRegex.setText("Validar");
        jButtonValidateRegex.addActionListener(this::jButtonValidateRegexActionPerformed);

        jTextRegexResult.setColumns(20);
        jTextRegexResult.setForeground(new java.awt.Color(0, 0, 0));
        jTextRegexResult.setRows(5);
        jTextRegexResult.setCaretColor(new java.awt.Color(0, 0, 0));
        jTextRegexResult.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextRegexResult.setDoubleBuffered(true);
        jTextRegexResult.setEnabled(false);
        jTextRegexResult.setSelectionColor(new java.awt.Color(0, 0, 0));
        jScrollPaneRegexResult.setViewportView(jTextRegexResult);

        jMenuFile.setText("Archivo");

        jMenuItemGenerateAutomat.setText("Generar autómata finito");
        jMenuItemGenerateAutomat.setEnabled(false);
        jMenuItemGenerateAutomat.addActionListener(this::jMenuItemGenerateAutomatActionPerformed);
        jMenuFile.add(jMenuItemGenerateAutomat);

        jMenuItemCleanLog.setText("limpiar log");
        jMenuItemCleanLog.addActionListener(this::jMenuItemCleanLogActionPerformed);
        jMenuFile.add(jMenuItemCleanLog);

        jMenuItemExit.setText("Salir");
        jMenuItemExit.addActionListener(this::jMenuItemExitActionPerformed);
        jMenuFile.add(jMenuItemExit);

        jMenuMain.add(jMenuFile);

        jMenuHelp.setText("Ayuda");

        jMenuItem1.setText("Caracteres reservados");
        jMenuItem1.addActionListener(this::jMenuItem1ActionPerformed);
        jMenuHelp.add(jMenuItem1);

        jMenuMain.add(jMenuHelp);

        setJMenuBar(jMenuMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelRegex, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(260, 260, 260)
                                .addComponent(jButtonValidateRegex)
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(28, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jTextRegex, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(100, 100, 100))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jScrollPaneRegexResult, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(26, 26, 26))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabelRegex)
                                .addGap(20, 20, 20)
                                .addComponent(jTextRegex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonValidateRegex)
                                .addGap(32, 32, 32)
                                .addComponent(jScrollPaneRegexResult, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * TODO: Definición de {@code jMenuItemExitActionPerformed}.
     *
     * @param evt
     */
    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    /**
     * TODO: Definición de {@code jButtonValidateRegexActionPerformed}.
     *
     * @param evt
     */
    private void jButtonValidateRegexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonValidateRegexActionPerformed

        regexText = jTextRegex.getText();

        if (!"".equals(regexText)) {
            Object result = RegexParser.parseString(regexText);
            List<int[]> errors;

            try {
                regex = (RegexItem) result;
                printLog(1, 1);
                jMenuItemGenerateAutomat.setEnabled(true);
                //RegexPrinter.printRegexDebug(regex);
            } catch (Exception e) {
                errors = (List<int[]>) result;
                errors.forEach(error -> printLog(error[0], error[1]));
                jMenuItemGenerateAutomat.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jButtonValidateRegexActionPerformed

    /**
     * TODO: Definición de {@code jMenuItemGenerateAutomatActionPerformed}.
     *
     * @param evt
     */
    private void jMenuItemGenerateAutomatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGenerateAutomatActionPerformed

        AutomatFrame automatFrame = new AutomatFrame();
        automatFrame.setTitle(regexText);

        ThompsonConstruction thompson = RegexOperator.getThompsonConstruction(regex);
        //ThompsonPrinter.printThompsonConstruction(thompson, true);

        Automat automat = ThompsonOperator.makeAutomat(thompson, "\\", "$");
        //AutomatPrinter.printAutomat(automat);
        //System.out.println("\n");
        String[][] matrix = AutomatOperator.getAutomatMatrix(automat);
        //AutomatPrinter.printMatrix(matrix);

        AutomatOperator.addMatrixToJFrame(automatFrame, matrix);
        automatFrame.setVisible(true);
        automatFrame.setAutomat(automat);

    }//GEN-LAST:event_jMenuItemGenerateAutomatActionPerformed

    /**
     * TODO: Definición de {@code jMenuItem1ActionPerformed}.
     *
     * @param evt
     */
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JOptionPane.showMessageDialog(this,
                "@ Indica secuencia vacia.\n"
                + "$   Indica secuencia nula.\n"
                + "\\    Indica fin de secuencia.\n"
                + "|    Indica union.\n"
                + ".    Indica concatenacion.\n"
                + "*   Indica clausura de kleene.\n"
                + "+   Indica clausura de kleene plus.\n"
        );
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * TODO: Definición de {@code jMenuItemCleanLogActionPerformed}.
     *
     * @param evt
     */
    private void jMenuItemCleanLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCleanLogActionPerformed
        jTextRegexResult.setText("");
    }//GEN-LAST:event_jMenuItemCleanLogActionPerformed
    
    /**
     * TODO: Definición de {@code jTextRegexActionPerformed}.
     *
     * @param evt
     */
    private void jTextRegexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:jTextRegexActionPerformed
    }//GEN-LAST:jTextRegexActionPerformed

    /**
     * Entrada principal del sistema.
     *
     * @param args argumentos de la linea de comandos.
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonValidateRegex;
    private javax.swing.JLabel jLabelRegex;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItemCleanLog;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemGenerateAutomat;
    private javax.swing.JMenuBar jMenuMain;
    private javax.swing.JScrollPane jScrollPaneRegexResult;
    private javax.swing.JTextField jTextRegex;
    private javax.swing.JTextArea jTextRegexResult;
    // End of variables declaration//GEN-END:variables
}
