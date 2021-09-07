package controler;

import view.ViewClientes;
import access.ClienteDAO;
import model.Cliente;

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

public class ControladorClientes implements ActionListener {
    
    private ArrayList<Cliente> clienteList = null;
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private ViewClientes mainClientes = new ViewClientes();
    private int busqueda= 0;

    public ControladorClientes(ViewClientes mainClientes) {
        this.mainClientes = mainClientes;
        this.mainClientes.btnActualizar.addActionListener(this);
        this.mainClientes.btnAgregar.addActionListener(this);
        this.mainClientes.btnBuscar.addActionListener(this);
        this.mainClientes.btnEliminar.addActionListener(this);
        this.mainClientes.btnVolver.addActionListener(this);
        init();
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.mainClientes.btnActualizar) {
            functionBtnActualizar();
        }
        if (e.getSource() == this.mainClientes.btnAgregar) {
            functionBtnAgregar();
        }
        if (e.getSource() == this.mainClientes.btnBuscar) {
            functionBtnBuscar();
        }
        if (e.getSource() == this.mainClientes.btnEliminar) {
            functionBtnEliminar();
        }
        if (e.getSource() == this.mainClientes.btnVolver) {
            cleanFields();
            btnsDesactivate();
            this.mainClientes.dispose();
        }
    }

    //* Metodo de inicio
    public void init() {
        btnsDesactivate();
        getForLoadTable();
        this.mainClientes.txtId.setEditable(false);
        JTable table = this.mainClientes.tblClientes;
        tableSelected(table);
    }
    
    //* Desactiva botones que no son usables si se va a Agregar
    private void btnsDesactivate() {
        this.mainClientes.btnEliminar.setEnabled(false);
        this.mainClientes.btnActualizar.setEnabled(false);
    }
    
    //* Activa botones que son usables si se va a Eliminar o Actualizar
    private void btnsActivate() {
        this.mainClientes.btnEliminar.setEnabled(true);
        this.mainClientes.btnActualizar.setEnabled(true);
    }

    //* Obtiene los valores de la tabla y los envia para cargarlos en la vista
    public void getForLoadTable() {
        this.clienteList = clienteDAO.getAllClientes();
        loadTable(clienteList);
    }
 
    //* Carga los valores pasados por parametro a la tabla en vista
    public void loadTable(ArrayList<Cliente> clienteList) {
        String[] headers = { "ID", "NOMBRE", "APELLIDO", "DOCUMENTO", "TELEFONO"};
        this.mainClientes.tblClientes.removeAll();
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(headers);
        for (int i = 0; i < clienteList.size(); i++) {
            tableModel.addRow(clienteList.get(i).toArray());
        }
        this.mainClientes.tblClientes.setModel(tableModel);
        this.mainClientes.tblClientes.setSelectionBackground(Color.lightGray);
        this.mainClientes.tblClientes.setRowHeight(20);
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
        this.mainClientes.txtId.setText(String.valueOf(values.get(0)));
        this.mainClientes.txtNombre.setText(String.valueOf(values.get(1)));
        this.mainClientes.txtApellido.setText(String.valueOf(values.get(2)));
        this.mainClientes.txtDocumento.setText(String.valueOf(values.get(3)));
        this.mainClientes.txtTelefono.setText(String.valueOf(values.get(4)));
        modifyComponents();
    }
    //* Activa, Desactiva y modifica botones cuando se seleciona un registro
    public void modifyComponents() {
        this.mainClientes.txtId.setEditable(false);
        this.mainClientes.btnAgregar.setEnabled(false);
        this.mainClientes.btnBuscar.setText("BUSQUEDA");
        btnsActivate();
        busqueda = 0;
    }
    //* Limpia los campos
    public void cleanFields() {
        this.mainClientes.txtId.setText(null);
        this.mainClientes.txtNombre.setText(null);
        this.mainClientes.txtApellido.setText(null);
        this.mainClientes.txtDocumento.setText(null);
        this.mainClientes.txtTelefono.setText(null);
    }
   
    //* Obtiene los valores de los campos y a su vez los transforma en un objeto de tipo Cliente valido para poder hacer acciones DAO
    public Cliente getFields() {
        String nombre=(String) this.mainClientes.txtNombre.getText();
        String apellido=(String) this.mainClientes.txtApellido.getText();
        String documento=(String) this.mainClientes.txtDocumento.getText();
        String telefono=(String) this.mainClientes.txtTelefono.getText();
        String strId = this.mainClientes.txtId.getText();
        if (!strId.equals("")){
            int id = Integer.parseInt(strId);
            Cliente objectCliente = new Cliente(id, nombre, apellido, documento, telefono);
            return objectCliente;
        }else{
            Cliente objectCliente = new Cliente(nombre, apellido, documento,telefono);
            return objectCliente;
        }
    }

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^-------- BUTTONS METHODS --------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    //* Función Agregar un registro (contiene control de error por si se dejan los campos vacios)
    private void functionBtnAgregar() {
        String nombre=(String) this.mainClientes.txtNombre.getText();
        String apellido=(String) this.mainClientes.txtApellido.getText();
        String documento=(String) this.mainClientes.txtDocumento.getText();
        String telefono=(String) this.mainClientes.txtTelefono.getText();
        if (!nombre.isEmpty() && !apellido.equals(" ") && !documento.equals(" ") && !telefono.equals(" ")) {
            Cliente params = getFields();
            clienteDAO.insertCliente(params);
            cleanFields();
            getForLoadTable();
        } else {
            JOptionPane.showMessageDialog(null, "Debe Llenar Todos Los Campos");
        }
    }
    
    //* Función Eliminar, borra según la id del registro seleccionado en la tabla 
    private void functionBtnEliminar() {
        int id = Integer.parseInt(this.mainClientes.txtId.getText());
        clienteDAO.deleteCliente(id);
        cleanFields();
        getForLoadTable();
    }
    
    //* Funcion Actualizar, edita según la id del registro seleccionado en la tabla, modifica los campos que el usuario cambio 
    private void functionBtnActualizar() {
        Cliente params = getFields();
        clienteDAO.updateCliente(params);
        cleanFields();
        getForLoadTable();
    }

    //* Funcion Buscar, usa el mismo boton para realizar dos acciones diferentes cada vez que se hace click
    public void functionBtnBuscar() {
        btnsDesactivate();
        if (busqueda == 0) {//* prepara la vista para poder hacer una busqueda,(limpia los campos, habilita campo id)
            busqueda = 1;
            this.mainClientes.btnBuscar.setText("BUSCAR");
            this.mainClientes.txtId.setEditable(true);
            this.mainClientes.btnAgregar.setEnabled(false);
            cleanFields();
        } else {//* realiza la busqueda y resetea el boton al un estado inicial default
            busqueda = 0;
            String id = this.mainClientes.txtId.getText();            
            String nombre=(String) this.mainClientes.txtNombre.getText();
            String apellido=(String) this.mainClientes.txtApellido.getText();
            String documento=(String) this.mainClientes.txtDocumento.getText();
            String telefono=(String) this.mainClientes.txtTelefono.getText();
            if (!id.isEmpty() || !nombre.isEmpty() || !apellido.equals(" ") || !documento.equals(" ")
                    || !telefono.equals(" ")) {
                Cliente params = getFields();
                this.clienteList = clienteDAO.getSearchCliente(params);
                loadTable(clienteList);
            }
            cleanFields();
            this.mainClientes.btnBuscar.setText("BUSQUEDA");
            this.mainClientes.txtId.setEditable(false);
            this.mainClientes.btnAgregar.setEnabled(true);
        }
    }
}
