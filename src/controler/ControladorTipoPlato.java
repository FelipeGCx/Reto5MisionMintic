package controler;

import view.ViewTipoPlato;
import access.TipoPlatoDAO;
import model.TipoPlato;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ControladorTipoPlato implements ActionListener {
    
    private ArrayList<TipoPlato> tipoPlatoList = null;
    private final TipoPlatoDAO tipoPlatoDAO = new TipoPlatoDAO();
    private ViewTipoPlato mainTipoPlato = new ViewTipoPlato();
    private int busqueda= 0;

    public ControladorTipoPlato(ViewTipoPlato mainTipoPlato) {
        this.mainTipoPlato = mainTipoPlato;
        this.mainTipoPlato.btnActualizar.addActionListener(this);
        this.mainTipoPlato.btnAgregar.addActionListener(this);
        this.mainTipoPlato.btnBuscar.addActionListener(this);
        this.mainTipoPlato.btnEliminar.addActionListener(this);
        this.mainTipoPlato.btnVolver.addActionListener(this);
        init();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.mainTipoPlato.btnActualizar) {
            functionBtnActualizar();
        }
        if (e.getSource() == this.mainTipoPlato.btnAgregar) {
            functionBtnAgregar();
        }
        if (e.getSource() == this.mainTipoPlato.btnBuscar) {
            functionBtnBuscar();
        }
        if (e.getSource() == this.mainTipoPlato.btnEliminar) {
            functionBtnEliminar();
        }
        if (e.getSource() == this.mainTipoPlato.btnVolver) {
            cleanFields();
            btnsDesactivate();
            this.mainTipoPlato.dispose();
        }
    }

    //* Metodo de inicio
    public void init() {
        btnsDesactivate();
        getForLoadTable();
        this.mainTipoPlato.txtId.setEditable(false);
        JTable table = this.mainTipoPlato.tblTipoPlato;
        tableSelected(table);
    }
    
    //* Desactiva botones que no son usables si se va a Agregar
    private void btnsDesactivate() {
        this.mainTipoPlato.btnEliminar.setEnabled(false);
        this.mainTipoPlato.btnActualizar.setEnabled(false);
    }
    
    //* Activa botones que son usables si se va a Eliminar o Actualizar
    private void btnsActivate() {
        this.mainTipoPlato.btnEliminar.setEnabled(true);
        this.mainTipoPlato.btnActualizar.setEnabled(true);
    }

    //* Obtiene los valores de la tabla y los envia para cargarlos en la vista
    public void getForLoadTable() {
        this.tipoPlatoList = tipoPlatoDAO.getAllTipoPlatos();
        loadTable(tipoPlatoList);
    }
 
    //* Carga los valores pasados por parametro a la tabla en vista
    public void loadTable(ArrayList<TipoPlato> tipoPlatoList) {
        String[] headers = { "ID","NOMBRE"};
        this.mainTipoPlato.tblTipoPlato.removeAll();
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(headers);
        for (int i = 0; i < tipoPlatoList.size(); i++) {
            tableModel.addRow(tipoPlatoList.get(i).toArray());
        }
        this.mainTipoPlato.tblTipoPlato.setModel(tableModel);
        this.mainTipoPlato.tblTipoPlato.setSelectionBackground(Color.lightGray);
        this.mainTipoPlato.tblTipoPlato.setRowHeight(20);
    }
    
    //* obtiene el valor seleccionado en la tabla de vista 
    public void tableSelected(JTable table) {
        ListSelectionModel cellSelectionModel = table.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                ArrayList<Object> values = new ArrayList<>();
                if (table.getSelectedRow() >= 0){
                int lead = table.getSelectedRow();
                int col = table.getColumnCount();
                for (int i = 0; i < col; i++) {
                    values.add(table.getValueAt(lead, i));
                }
                loadOnScreen(values);
            } 
        }
        });
    }
    
    //* Carga la seleccion de un registro de la tabla en los campos de vista para modificarlos
    public void loadOnScreen(ArrayList<Object> values) {
        this.mainTipoPlato.txtId.setText(String.valueOf(values.get(0)));
        this.mainTipoPlato.txtNombre.setText(String.valueOf(values.get(1)));
        modifyComponents();
    }
    //* Activa, Desactiva y modifica botones cuando se seleciona un registro
    public void modifyComponents() {
        this.mainTipoPlato.txtId.setEditable(false);
        this.mainTipoPlato.btnAgregar.setEnabled(false);
        this.mainTipoPlato.btnBuscar.setText("BUSQUEDA");
        btnsActivate();
        busqueda = 0;
    }

    //* Limpia los campos
    public void cleanFields() {
        this.mainTipoPlato.txtId.setText(null);
        this.mainTipoPlato.txtNombre.setText(null);
    }

    //* Obtiene los valores de los campos y a su vez los transforma en un objeto de tipo TipoPlato valido para poder hacer acciones DAO
    public TipoPlato getFields() {
        String nombre = this.mainTipoPlato.txtNombre.getText();
        String strId = this.mainTipoPlato.txtId.getText();
        if (!strId.equals("")){
            int id = Integer.parseInt(strId);
            TipoPlato objectTipoPlato = new TipoPlato(id,nombre);
            return objectTipoPlato;
        }else{
            TipoPlato objectTipoPlato = new TipoPlato(nombre);
            return objectTipoPlato;
        }
    }

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^-------- BUTTONS METHODS --------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    //* Función Agregar un registro (contiene control de error por si se dejan los campos vacios)
    private void functionBtnAgregar() {
        String nombre = this.mainTipoPlato.txtNombre.getText();
        if (!nombre.isEmpty()){
            TipoPlato params = getFields();
            tipoPlatoDAO.insertTipoPlato(params);
            cleanFields();
            getForLoadTable();
        } else {
            JOptionPane.showMessageDialog(null, "Debe Llenar Todos Los Campos");
        }
    }
    
    //* Función Eliminar, borra según la id del registro seleccionado en la tabla 
    private void functionBtnEliminar() {
        int id = Integer.parseInt(this.mainTipoPlato.txtId.getText());
        tipoPlatoDAO.deleteTipoPlato(id);
        cleanFields();
        getForLoadTable();
    }
    
    //* Funcion Actualizar, edita según la id del registro seleccionado en la tabla, modifica los campos que el usuario cambio 
    private void functionBtnActualizar() {
        TipoPlato params = getFields();
        tipoPlatoDAO.updateTipoPlato(params);
        cleanFields();
        getForLoadTable();
    }

    //* Funcion Buscar, usa el mismo boton para realizar dos acciones diferentes cada vez que se hace click
    public void functionBtnBuscar() {
        btnsDesactivate();
        if (busqueda == 0) {//* prepara la vista para poder hacer una busqueda,(limpia los campos, habilita campo id)
            busqueda = 1;
            this.mainTipoPlato.btnBuscar.setText("BUSCAR");
            this.mainTipoPlato.txtId.setEditable(true);
            this.mainTipoPlato.btnAgregar.setEnabled(false);
            cleanFields();
        } else {//* realiza la busqueda y resetea el boton al un estado inicial default
            busqueda = 0;
            String id = this.mainTipoPlato.txtId.getText();
            String nombre = this.mainTipoPlato.txtNombre.getText();
            if (!id.isEmpty() || !nombre.isEmpty()) {
                TipoPlato params = getFields();
                this.tipoPlatoList = tipoPlatoDAO.getSearchTipoPlato(params);
            }
            cleanFields();
            loadTable(tipoPlatoList);
            this.mainTipoPlato.btnBuscar.setText("BUSQUEDA");
            this.mainTipoPlato.txtId.setEditable(false);
            this.mainTipoPlato.btnAgregar.setEnabled(true);
        }
    }
}

