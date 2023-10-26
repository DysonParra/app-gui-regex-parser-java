/*
 * @fileoverview    {AutomatOperator}
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
package com.project.dev.automat.operation;

import com.project.dev.automat.Automat;
import com.project.dev.automat.Status;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import static javax.swing.JTable.AUTO_RESIZE_OFF;

/**
 * TODO: Definición de {@code AutomatOperator}.
 *
 * @author Dyson Parra
 * @since 1.8
 */
public class AutomatOperator {

    /**
     * FIXME: Definición de {@code getAutomatMatrix}. Obtiene la matriz del automata finito.
     *
     * @param automat es el automata finito.
     * @return una matriz con los estados ysimbolos de entrada del automata finito.
     */
    public static String[][] getAutomatMatrix(Automat automat) {
        ArrayList<Status> statuses = automat.getStatuses();
        Map<String, String> inputSymbols = automat.getInputSymbols();
        String[][] matrix = new String[statuses.size() + 1][inputSymbols.size() + 2];

        Iterator statusIterator;
        Status statusAux1;
        Status statusAux2;
        String aux;
        int index;

        for (String[] matrix1 : matrix)
            for (int j = 0; j < matrix[0].length; j++)
                matrix1[j] = "";

        //matrix[0][0] = "/";
        matrix[0][matrix[0].length - 1] = "Accept";

        index = 1;
        for (Map.Entry<String, String> entry : inputSymbols.entrySet()) {
            aux = entry.getValue();
            matrix[0][index] = aux;
            index++;
        }

        index = 1;
        statusIterator = statuses.iterator();
        while (statusIterator.hasNext()) {
            statusAux1 = (Status) statusIterator.next();

            if (statusAux1.isEndStatus())
                matrix[index][matrix[0].length - 1] = "1";
            else
                matrix[index][matrix[0].length - 1] = "0";

            matrix[index][0] = statusAux1.getCode();

            for (Map.Entry<String, Status> entry : statusAux1.getNextStatuses().entrySet()) {
                aux = entry.getKey();
                statusAux2 = entry.getValue();

                for (int j = 1; j < matrix[0].length; j++)
                    if (aux.equals(matrix[0][j]))
                        matrix[index][j] = statusAux2.getCode();
            }

            index++;
        }

        return matrix;
    }

    /**
     * FIXME: Definición de {@code matrixToJTable}. Lleva un matrix parametro a un JTable, y el
     * JTable a un JScrollPane. Pone las posiciones de la matrix con valor diferente a "" en el
     * Jtable como no editable. Pone las posiciones de la matrix con valor = "" con fondo rojo
     * (estados de error). Además pone las demas posiciones con fondo color Cian. Deja las casillas
     * del JTable con valor = null con fondo blanco.
     *
     * @param matrix      es la matrix que se almacenara en un JTable.
     * @param jScrollPane es donde se almacenara el JTable.
     * @param Table       JTable en que se almacenara la matrix
     * @return el JSCrollPane con el JTable almacenado.
     */
    public static JScrollPane matrixToJTable(String[][] matrix, JScrollPane jScrollPane, JTable Table) {
        int m = matrix.length;                                                  // m es la cantidad de filas de la matrix.
        int n = matrix[0].length;                                               // n es la cantidad de columnas de la matrix.
        boolean[][] editable = new boolean[m][n];                               // Matrix que indica si es posible editar la celda.
        String[] id = new String[n];                                            // Matrix titulos de las columnas.
        String[][] matrixStr = new String[m][n];                                // Matrix de Strings.

        // Llena la matrix que indica si la posicion i,j es editable.
        for (int i = 0; i < m; i++) {                                           // Recorre la matrix por filas.
            for (int j = 0; j < n; j++) {                                       // Recorre la matrix por columnas.
                if (matrix[i][j].isEmpty()) {                                  // Evalua si la matrix parametro en la posicion i,j es un cero.
                    editable[i][j] = false;                                     // Si es un cero pone la posicion como editable.
                }
            }
        }
        ////////////////////////////////////////////////////////////////////////////

        // Llena la matrix de titulos con "." .
        ////////////////////////////////////////////////////////////////////////////
        for (int i = 0; i < n; i++) {                                           // Recorre la matrix.
            id[i] = ".";                                                        //Pone un punto en cada posicion de la matrix.
        }
        ////////////////////////////////////////////////////////////////////////////

        // Asigna formato al JTable.
        ///////////////////////////////////////////////////////////////////////////
        jScrollPane.setBounds(10, 10, 0, 0);                                    // A jScrollPane1 le asigna dimensiones de 0,0 y que deje una margen de 10 px en la ventana.
        //Table = new JTable();                                                 // Inicializa el JTable en caso de que no lo este.
        Table.setModel(new javax.swing.table.DefaultTableModel(m, n));          // A Table le asigna la cantidad de filas y de columnas de la matrix parametro.
        Table.setModel(new javax.swing.table.DefaultTableModel(matrixStr, id) { // Inicia Table con "" en todas sus celdas, y con "." en cada uno de los titulos de las columnas.
            Class[] types = new Class[]{java.lang.String.class};                // Crea un vector de clases con una unica posicion (String.class).

            @Override
            public Class getColumnClass(int columnIndex) {                      // Asigna el tipo de Datos que permite cada celda.
                return types[0];                                                // A cada celda le asigna como tipo de dato permitido types[0], es decir String.
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {      // Asigna a cada celda si es o no editable.
                return editable[rowIndex][columnIndex];                         // A cada celda le asigna el valor almacenado en el vector editable[i][j], si la matrix parametro en dicha posicion tiene un valor diferente a "", se pone la celda como no editable.
            }
        });
        ////////////////////////////////////////////////////////////////////////////

        // Escribe lo valores de la matrix parameto en el JTable.
        ////////////////////////////////////////////////////////////////////////////
        for (int i = 0; i < m; i++) {                                           // Recorre matrix parametro por filas.
            for (int j = 0; j < n; j++) {                                       // Recorre matrix parametro por columnas.
                if (matrix[i][j].isEmpty()) {                                  // Si la matrix parametro en i,j tiene un cero.
                    Table.setValueAt("", i, j);                                 // Se inicializa como null. Sin este if las posiciones vacias iniciarian en cero.
                } else {                                                        // Si no es "" se escrbe el mismo valor de la matrix en el JTable.
                    Table.setValueAt(matrix[i][j], i, j);                       // Almacena el valor de la matrix en la posicion del JTable.
                }
            }
        }
        ////////////////////////////////////////////////////////////////////////////

        Table.setColumnSelectionAllowed(true);                                  // Indica que al dar click en una celda solo se seleccone esta y no toda la fila.
        Table.setRowHeight(50);                                                 // Asigna a todas las filas un tamaño de 50px.
        jScrollPane.setBounds(10, 10,
                (Table.getColumnCount() * (Table.getRowHeight() + 10)) + 3,
                (Table.getRowCount() * Table.getRowHeight()) + 23);             // Hace que el JscrollPane tenga las mismas dimensiones del JTable mas unos cuantos pixeles de margen.
        jScrollPane.setViewportView(Table);                                     // Agrega el JTable al jScrollPane.

        // Asigna dimensiones de 50px a cada columna.
        for (int i = 0; i < n; i++) {
            Table.getColumnModel().getColumn(i).setMinWidth(60);                // Pone 50px como ancho minimo.
            Table.getColumnModel().getColumn(i).setPreferredWidth(60);          // Pone 50px como ancho preferido.
            Table.getColumnModel().getColumn(i).setMaxWidth(60);                // Pone 50px como ancho maximo.
        }

        Table.setDefaultRenderer(String.class, new CellModel());                // Colorea cada celda segun como esta especificado en colorCelda.
        return jScrollPane;                                                     // Devuelve el jScrollPane parametro con el JTable y todas sus modificaciones dentro de este.
    }

    /**
     * FIXME: Definición de {@code addMatrixToJFrame}. Agrega una matrix a un JTable y el JTable a
     * un JFrame.
     *
     * @param frame  el el JFrame al que se le agregara la matriz.
     * @param matrix es la matriz que se agregara al JFrame.
     */
    public static void addMatrixToJFrame(JFrame frame, String[][] matrix) {
        JScrollPane jScrollPane = new JScrollPane();                            // Inicializa jScrollPane1.
        JTable table = new JTable();                                            // Inicializa Table.

        jScrollPane = AutomatOperator.matrixToJTable(matrix, jScrollPane, table);
        frame.add(jScrollPane);

        // Hace que la tabla no se redimensione automaticamente.
        table.setAutoResizeMode(AUTO_RESIZE_OFF);

        // Evalua el tamaño del jSCrollPane.
        // Si sus dimensiones son menores a 180x720.
        //System.out.println(jScrollPane.getWidth());
        //System.out.println(jScrollPane.getHeight());
        if (jScrollPane.getWidth() < 1280 && jScrollPane.getHeight() < 720) {
            // Redimensiona frame con las dimensiones de jScrollPane mas algunos pixeles de margen.
            frame.resize(jScrollPane.getWidth() + 35, jScrollPane.getHeight() + 80);
        } // En caso de que el alto sea mayor o igual a 720px y el ancho menor a 1280px, se redimensiona de tal modo que aparezca un jscrollbar vertical.
        else if (jScrollPane.getWidth() < 1280 && jScrollPane.getHeight() >= 720) {
            // Redimensiona frame para que tenga un alto de 720px, y un ancho de unos 35px mas, en los cuales quedara el jscrollbar vertical.
            frame.resize(jScrollPane.getWidth() + 55, 720);

            // Se redimensiona el jscrollbar de tal modo que conserve un espacio entre este y el frame.
            jScrollPane.resize(jScrollPane.getWidth() + 20, 640);
        } // En caso de que el alto sea menor a 720px y el ancho mayor o igual a 1280px, se redimensiona de tal modo que aparezca un jscrollbar Horizontal.
        else if (jScrollPane.getWidth() >= 1280 && jScrollPane.getHeight() < 720) {
            // Redimensiona Main_Frame para que tenga un alto de 1280px, y un ancho de unos 100px mas, en los cuales quedara el jscrollbar vertical.
            frame.resize(1280, jScrollPane.getHeight() + 100);

            // Se redimensiona el jscrollbar de tal modo que conserve un espacio entre este y el Main_Frame.
            jScrollPane.resize(1260, jScrollPane.getHeight() + 20);

        } // En caso de que el alto sea mayor o igual a 720px y el ancho mayor o igual a 1280px, se redimensiona de tal modo que aparezca un jscrollbar vertical y uno horizontal.
        else if (jScrollPane.getWidth() >= 1280 && jScrollPane.getHeight() >= 720) {
            // Redimensiona Main_Frame para que tenga un alto de 720px, y un ancho de 1280px.
            frame.resize(1280, 720);

            // Se redimensiona el jscrollbar de tal modo que conserve un espacio entre este y el Main_Frame.
            jScrollPane.resize(1245, 640);
        }

        // Hace que frame no se pueda redimensionar.
        frame.setResizable(false);
    }

    /**
     * FIXME: Definición de {@code valiadteString}. Valida si una cadena puede ser reconocida por un
     * automata finito.
     *
     * @param automat es el automata finito.
     * @param word    es la cadena que se verificara si puede ser reconocida por el automata
     * @return una matriz con los estados ysimbolos de entrada del automata finito.
     */
    public static int valiadteString(Automat automat, String word) {
        int result = 0;                                                         // Indica el último caracter leido de la cadena.
        Status status = automat.getStatuses().get(0);                           // Obtiene el primer estado.
        Map<String, Status> nextStatuses;                                       // Siguientes estados del estado actual.

        if (word.length() < 2)                                                  // Si la cadena tiene menos de dos caracteres.
            return -1;                                                          // Devuelve -1 indicando que la cadena no tiene el ancho minimo.
        if (word.charAt(word.length() - 1) != automat.getEndSequenceChar())     // Si la cadena no termina en fin de secuencia.
            return -2;                                                          // Devuelve -1 inidcando que la cadena no es valida.
        else if (word.length() == 2
                && // Si la cadena tiene dos caracacteres.
                status.isEndStatus()
                && // Si el primer estado es de aceptacion.
                word.charAt(0) == automat.getNullSequenceChar())                // Si el primer caracter es la secuencia nula.
            return 2;                                                           // Devuelve dos indicando que la secuencia se acepta.

        for (result = 0; result < word.length() - 1; result++) {                // Recorre la cadena.
            nextStatuses = status.getNextStatuses();                            // Obtiene los siguentes estados del estado actual.
            status = nextStatuses.get(String.valueOf(word.charAt(result)));     // Obtiene el siguiente estado al que se llega con el simbolo actual.

            if (status == null)                                                 // Si llega a un estado no valido.
                break;                                                          // Sale del ciclo.
        }

        if (status != null && status.isEndStatus())
            return result + 1;
        else
            return result;
    }

}
