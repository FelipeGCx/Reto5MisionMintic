package controler;

import view.ViewMeseros;
import access.MeseroDAO;
import model.Mesero;

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

public class ControladorMeseros implements ActionListener{
    
    private ArrayList<Mesero> meseroList = null;
    private final MeseroDAO meseroDAO = new MeseroDAO();
    private ViewMeseros mainMeseros = new ViewMeseros();
    private int busqueda= 0;
    
    public ControladorMeseros(ViewMeseros mainMeseros) {
        this.mainMeseros = mainMeseros;
        this.mainMeseros.btnActualizar.addActionListener(this);
        this.mainMeseros.btnAgregar.addActionListener(this);
        this.mainMeseros.btnBuscar.addActionListener(this);
        this.mainMeseros.btnEliminar.addActionListener(this);
        this.mainMeseros.btnVolver.addActionListener(this);
        init();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.mainMeseros.btnActualizar) {
            functionBtnActualizar();
        }
        if (e.getSource() == this.mainMeseros.btnAgregar) {
            functionBtnAgregar();
        }
        if (e.getSource() == this.mainMeseros.btnBuscar) {
            functionBtnBuscar();
        }
        if (e.getSource() == this.mainMeseros.btnEliminar) {
            functionBtnEliminar();
        }
        if (e.getSource() == this.mainMeseros.btnVolver) {
            cleanFields();
            btnsDesactivate();
            this.mainMeseros.dispose();
        }
    }

    //* Metodo de inicio
    public void init() {
        btnsDesactivate();
        getForLoadTable();
        this.mainMeseros.txtId.setEditable(false);
        JTable table = this.mainMeseros.tblMeseros;
        tableSelected(table);
    }
    
    //* Desactiva botones que no son usables si se va a Agregar
    private void btnsDesactivate() {
        this.mainMeseros.btnEliminar.setEnabled(false);
        this.mainMeseros.btnActualizar.setEnabled(false);
    }
    
    //* Activa botones que son usables si se va a Eliminar o Actualizar
    private void btnsActivate() {
        this.mainMeseros.btnEliminar.setEnabled(true);
        this.mainMeseros.btnActualizar.setEnabled(true);
    }

    //* Obtiene los valores de la tabla y los envia para cargarlos en la vista
    public void getForLoadTable() {
        this.meseroList = meseroDAO.getAllMeseros();
        loadTable(meseroList);
    }
 
    //* Carga los valores pasados por parametro a la tabla en vista
    public void loadTable(ArrayList<Mesero> meseroList) {
        String[] headers = { "ID", "NOMBRE", "APELLIDO", "DOCUMENTO","CORREO", "TELEFONO"};
        this.mainMeseros.tblMeseros.removeAll();
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(headers);
        for (int i = 0; i < meseroList.size(); i++) {
            tableModel.addRow(meseroList.get(i).toArray());
        }
        this.mainMeseros.tblMeseros.setModel(tableModel);
        this.mainMeseros.tblMeseros.setSelectionBackground(Color.lightGray);
        this.mainMeseros.tblMeseros.setRowHeight(20);
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
        this.mainMeseros.txtId.setText(String.valueOf(values.get(0)));
        this.mainMeseros.txtNombre.setText(String.valueOf(values.get(1)));
        this.mainMeseros.txtApellidos.setText(String.valueOf(values.get(2)));
        this.mainMeseros.txtDocumento.setText(String.valueOf(values.get(3)));
        this.mainMeseros.txtCorreo.setText(String.valueOf(values.get(4)));
        this.mainMeseros.txtTelefono.setText(String.valueOf(values.get(5)));
        modifyComponents();
    }
    //* Activa, Desactiva y modifica botones cuando se seleciona un registro
    public void modifyComponents() {
        this.mainMeseros.txtId.setEditable(false);
        this.mainMeseros.btnAgregar.setEnabled(false);
        this.mainMeseros.btnBuscar.setText("BUSQUEDA");
        btnsActivate();
        busqueda = 0;
    }
    //* Limpia los campos
    public void cleanFields() {
        this.mainMeseros.txtId.setText(null);
        this.mainMeseros.txtNombre.setText(null);
        this.mainMeseros.txtApellidos.setText(null);
        this.mainMeseros.txtDocumento.setText(null);
        this.mainMeseros.txtCorreo.setText(null);
        this.mainMeseros.txtTelefono.setText(null);
    }
   
    //* Obtiene los valores de los campos y a su vez los transforma en un objeto de tipo Mesero valido para poder hacer acciones DAO
    public Mesero getFields() {
        String nombre=(String) this.mainMeseros.txtNombre.getText();
        String apellido=(String) this.mainMeseros.txtApellidos.getText();
        String documento=(String) this.mainMeseros.txtDocumento.getText();
        String correo=(String) this.mainMeseros.txtCorreo.getText();
        String telefono=(String) this.mainMeseros.txtTelefono.getText();
        String strId = this.mainMeseros.txtId.getText();
        if (!strId.equals("")){
            int id = Integer.parseInt(strId);
            Mesero objectMesero = new Mesero(id, nombre, apellido, documento, correo, telefono);
            return objectMesero;
        }else{
            Mesero objectMesero = new Mesero(nombre, apellido, documento, correo, telefono);
            return objectMesero;
        }
    }

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^-------- BUTTONS METHODS --------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    //* Función Agregar un registro (contiene control de error por si se dejan los campos vacios)
    private void functionBtnAgregar() {
        String nombre=(String) this.mainMeseros.txtNombre.getText();
        String apellido=(String) this.mainMeseros.txtApellidos.getText();
        String documento=(String) this.mainMeseros.txtDocumento.getText();
        String correo=(String) this.mainMeseros.txtCorreo.getText();
        String telefono=(String) this.mainMeseros.txtTelefono.getText();
        if (!nombre.isEmpty() && !apellido.equals(" ") && !documento.equals(" ") && !telefono.equals(" ") && !correo.equals(" ")) {
            Mesero params = getFields();
            meseroDAO.insertMesero(params);
            cleanFields();
            getForLoadTable();
        } else {
            JOptionPane.showMessageDialog(null, "Debe Llenar Todos Los Campos");
        }
    }
    
    //* Función Eliminar, borra según la id del registro seleccionado en la tabla 
    private void functionBtnEliminar() {
        int id = Integer.parseInt(this.mainMeseros.txtId.getText());
        meseroDAO.deleteMesero(id);
        cleanFields();
        getForLoadTable();
    }
    
    //* Funcion Actualizar, edita según la id del registro seleccionado en la tabla, modifica los campos que el usuario cambio 
    private void functionBtnActualizar() {
        Mesero params = getFields();
        meseroDAO.updateMesero(params);
        cleanFields();
        getForLoadTable();
    }

    //* Funcion Buscar, usa el mismo boton para realizar dos acciones diferentes cada vez que se hace click
    public void functionBtnBuscar() {
        btnsDesactivate();
        if (busqueda == 0) {//* prepara la vista para poder hacer una busqueda,(limpia los campos, habilita campo id)
            busqueda = 1;
            this.mainMeseros.btnBuscar.setText("BUSCAR");
            this.mainMeseros.txtId.setEditable(true);
            this.mainMeseros.btnAgregar.setEnabled(false);
            cleanFields();
        } else {//* realiza la busqueda y resetea el boton al un estado inicial default
            busqueda = 0;
            String id = this.mainMeseros.txtId.getText();            
            String nombre=(String) this.mainMeseros.txtNombre.getText();
            String apellido=(String) this.mainMeseros.txtApellidos.getText();
            String documento=(String) this.mainMeseros.txtDocumento.getText();
            String correo=(String) this.mainMeseros.txtCorreo.getText();
            String telefono=(String) this.mainMeseros.txtTelefono.getText();
            if (!id.isEmpty() || !nombre.isEmpty() || !apellido.equals(" ") || !documento.equals(" ") || !telefono.equals(" ")
                    || !correo.equals(" ")) {
                Mesero params = getFields();
                this.meseroList = meseroDAO.getSearchMesero(params);
                loadTable(meseroList);
            }
            cleanFields();
            this.mainMeseros.btnBuscar.setText("BUSQUEDA");
            this.mainMeseros.txtId.setEditable(false);
            this.mainMeseros.btnAgregar.setEnabled(true);
        }
    }
}