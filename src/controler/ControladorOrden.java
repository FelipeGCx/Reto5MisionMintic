package controler;

import view.ViewOrden;
import access.OrdenDAO;
import model.Orden;

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

public class ControladorOrden implements ActionListener {
    private ArrayList<Orden> ordenList = null;
    private ArrayList<String> meserosList = null;
    private ArrayList<String> platosList = null;
    private ArrayList<String> clientesList = null;   
    private final OrdenDAO ordenDAO = new OrdenDAO();
    private ViewOrden mainOrden = new ViewOrden();
    private int busqueda= 0;
           
    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^---------- CONSTRUCTOR ----------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    public ControladorOrden(ViewOrden mainOrden) {
        this.mainOrden = mainOrden;
        this.mainOrden.btnActualizar.addActionListener(this);
        this.mainOrden.btnAgregar.addActionListener(this);
        this.mainOrden.btnBuscar.addActionListener(this);
        this.mainOrden.btnEliminar.addActionListener(this);
        this.mainOrden.btnVolver.addActionListener(this);
        init();
    }

    //* Escucha las acciones hechas en vista en la ventana orden *\\
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.mainOrden.btnActualizar) {
            functionBtnActualizar();
        }
        if (e.getSource() == this.mainOrden.btnAgregar) {
            functionBtnAgregar();
        }
        if (e.getSource() == this.mainOrden.btnBuscar) {
            functionBtnBuscar();
        }
        if (e.getSource() == this.mainOrden.btnEliminar) {
            functionBtnEliminar();
        }
        if (e.getSource() == this.mainOrden.btnVolver) {
            cleanFields();
            btnsDesactivate();
            this.mainOrden.dispose();
        }
    }

    //* Metodo de inicio
    public void init() {
        btnsDesactivate();
        getForLoadTable();
        this.mainOrden.txtId.setEditable(false);
        this.meserosList = ordenDAO.getMeseros();
        loadCbxMeseros(meserosList);
        this.clientesList = ordenDAO.getClientes();
        loadCbxClientes(clientesList);
        this.platosList = ordenDAO.getPlatos();
        loadCbxPlatos(platosList);
        JTable table = this.mainOrden.tblOrden;
        tableSelected(table);
    }
    
    //* Desactiva botones que no son usables si se va a Agregar
    private void btnsDesactivate() {
        this.mainOrden.btnEliminar.setEnabled(false);
        this.mainOrden.btnActualizar.setEnabled(false);
    }
    
    //* Activa botones que son usables si se va a Eliminar o Actualizar
    private void btnsActivate() {
        this.mainOrden.btnEliminar.setEnabled(true);
        this.mainOrden.btnActualizar.setEnabled(true);
    }

    //* Obtiene los valores de la tabla y los envia para cargarlos en la vista
    public void getForLoadTable() {
        this.ordenList = ordenDAO.getAllOrdens();
        loadTable(ordenList);
    }
 
    //* Carga los valores pasados por parametro a la tabla en vista
    public void loadTable(ArrayList<Orden> ordensList) {
        String[] headers = { "ID", "FECHA", "CLIENTE", "PLATO", "MESERO" };
        this.mainOrden.tblOrden.removeAll();
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(headers);
        for (int i = 0; i < ordensList.size(); i++) {
            tableModel.addRow(ordensList.get(i).toArray());
        }
        this.mainOrden.tblOrden.setModel(tableModel);
        this.mainOrden.tblOrden.setSelectionBackground(Color.lightGray);
        this.mainOrden.tblOrden.setRowHeight(20);
    }

    //* Carga los valores al comboBox de meseros
    public void loadCbxMeseros(ArrayList<String> meserosList) {
        this.mainOrden.cbxMesero.removeAllItems();
        this.mainOrden.cbxMesero.addItem(" ");
        for (String item : meserosList) {
            this.mainOrden.cbxMesero.addItem(item);
        }
    }

    //* Carga los valores al comboBox de clientes
    public void loadCbxClientes(ArrayList<String> clientesList) {
        this.mainOrden.cbxCliente.removeAllItems();
        this.mainOrden.cbxCliente.addItem(" ");
        for (String item : clientesList) {
            this.mainOrden.cbxCliente.addItem(item);
        }
    }

    //* Carga los valores al comboBox de platos
    public void loadCbxPlatos(ArrayList<String> platosList) {
        this.mainOrden.cbxPlato.removeAllItems();
        this.mainOrden.cbxPlato.addItem(" ");
        for (String item : platosList) {
            this.mainOrden.cbxPlato.addItem(item);
        }
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
        this.mainOrden.txtId.setText(String.valueOf(values.get(0)));
        this.mainOrden.txtFechaServicio.setText(String.valueOf(values.get(1)));
        this.mainOrden.cbxCliente.setSelectedItem(String.valueOf(values.get(2)));
        this.mainOrden.cbxPlato.setSelectedItem(String.valueOf(values.get(3)));
        this.mainOrden.cbxMesero.setSelectedItem(String.valueOf(values.get(4)));
        removeCbx();
        modifyComponents();
    }
    //* Activa, Desactiva y modifica botones cuando se seleciona un registro
    public void modifyComponents() {
        this.mainOrden.txtId.setEditable(false);
        this.mainOrden.btnAgregar.setEnabled(false);
        this.mainOrden.btnBuscar.setText("BUSQUEDA");
        btnsActivate();
        busqueda = 0;
    }
    //* Limpia los campos
    public void cleanFields() {
        this.mainOrden.txtId.setText(null);
        this.mainOrden.txtFechaServicio.setText(null);
        String cbx = this.mainOrden.cbxCliente.getItemAt(0);
        if (cbx.equals(" ")){
            selectedCbx();
        }else{
            this.mainOrden.cbxCliente.insertItemAt(" ", 0);
            this.mainOrden.cbxPlato.insertItemAt(" ", 0);
            this.mainOrden.cbxMesero.insertItemAt(" ", 0);
            selectedCbx();
        }
    }
    //* Seleciona el campo nulo o vacio
    public void selectedCbx(){
        this.mainOrden.cbxCliente.setSelectedItem(" ");
        this.mainOrden.cbxPlato.setSelectedItem(" ");
        this.mainOrden.cbxMesero.setSelectedItem(" ");
    }
    //* Remueve el campo nulo o vacio para evitar errores
    public void removeCbx(){
        this.mainOrden.cbxCliente.removeItem(" ");
        this.mainOrden.cbxPlato.removeItem(" ");
        this.mainOrden.cbxMesero.removeItem(" ");
    }
    //* Obtiene los valores de los campos y a su vez los transforma en un objeto de tipo Orden valido para poder hacer acciones DAO
    public Orden getFields() {
        String fecha = this.mainOrden.txtFechaServicio.getText();
        String cliente = (String) this.mainOrden.cbxCliente.getSelectedItem();
        String plato = (String) this.mainOrden.cbxPlato.getSelectedItem();
        String mesero = (String) this.mainOrden.cbxMesero.getSelectedItem();
        int clienteId = ordenDAO.getIdCliente(cliente); 
        int meseroId = ordenDAO.getIdMesero(mesero); 
        int platoId = ordenDAO.getIdPlato(plato);
        String strId = this.mainOrden.txtId.getText();
        if (!strId.equals("")){
            int id = Integer.parseInt(strId);
            Orden objectOrden = new Orden(id, fecha, clienteId, platoId, meseroId);
            return objectOrden;
        }else{
            Orden objectOrden = new Orden(fecha, clienteId, platoId, meseroId);
            return objectOrden;
        }
    }

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^-------- BUTTONS METHODS --------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    //* Función Agregar un registro (contiene control de error por si se dejan los campos vacios)
    private void functionBtnAgregar() {
        String fecha = this.mainOrden.txtFechaServicio.getText();
        String cliente = (String) this.mainOrden.cbxCliente.getSelectedItem();
        String plato = (String) this.mainOrden.cbxPlato.getSelectedItem();
        String mesero = (String) this.mainOrden.cbxMesero.getSelectedItem();
        if (!fecha.isEmpty() && !cliente.equals(" ") && !plato.equals(" ") && !mesero.equals(" ")) {
            Orden params = getFields();
            ordenDAO.insertOrden(params);
            cleanFields();
            getForLoadTable();
        } else {
            JOptionPane.showMessageDialog(null, "Debe Llenar Todos Los Campos");
        }
    }
    
    //* Función Eliminar, borra según la id del registro seleccionado en la tabla 
    private void functionBtnEliminar() {
        int id = Integer.parseInt(this.mainOrden.txtId.getText());
        ordenDAO.deleteOrden(id);
        cleanFields();
        getForLoadTable();
    }
    
    //* Funcion Actualizar, edita según la id del registro seleccionado en la tabla, modifica los campos que el usuario cambio 
    private void functionBtnActualizar() {
        Orden params = getFields();
        ordenDAO.updateOrden(params);
        cleanFields();
        getForLoadTable();
    }

    //* Funcion Buscar, usa el mismo boton para realizar dos acciones diferentes cada vez que se hace click
    public void functionBtnBuscar() {
        btnsDesactivate();
        if (busqueda == 0) {//* prepara la vista para poder hacer una busqueda,(limpia los campos, habilita campo id)
            busqueda = 1;
            this.mainOrden.btnBuscar.setText("BUSCAR");
            this.mainOrden.txtId.setEditable(true);
            this.mainOrden.btnAgregar.setEnabled(false);
            cleanFields();
        } else {//* realiza la busqueda y resetea el boton al un estado inicial default
            busqueda = 0;
            String id = this.mainOrden.txtId.getText();            
            String fecha = this.mainOrden.txtFechaServicio.getText();
            String cliente = (String) this.mainOrden.cbxCliente.getSelectedItem();
            String plato = (String) this.mainOrden.cbxPlato.getSelectedItem();
            String mesero = (String) this.mainOrden.cbxMesero.getSelectedItem();
            if (!id.isEmpty() || !fecha.isEmpty() || !cliente.equals(" ") || !plato.equals(" ") || !mesero.equals(" ")) {
                Orden params = getFields();
                ArrayList<Object> filters = filters();
                String filter = (String) filters.get(0);
                //! se desconoce si el casteo de ArrayList de tipo Object a Integer de esta forma es buena practica ▼
                ArrayList<Integer> pos = (ArrayList<Integer>) filters.get(1);
                this.ordenList = ordenDAO.getSearchOrden(params,filter,pos);
                loadTable(ordenList);
            }
            cleanFields();
            this.mainOrden.btnBuscar.setText("BUSQUEDA");
            this.mainOrden.txtId.setEditable(false);
            this.mainOrden.btnAgregar.setEnabled(true);
        }
    }

    public ArrayList<Object> filters() {
        ArrayList<Object> array = new ArrayList<>(); 
        ArrayList<Integer> pos = new ArrayList<>(); 
        String filter = null;
        String cliente = (String) this.mainOrden.cbxCliente.getSelectedItem();
        String plato = (String) this.mainOrden.cbxPlato.getSelectedItem();
        String mesero = (String) this.mainOrden.cbxMesero.getSelectedItem();

        if (!cliente.equals(" ") && !plato.equals(" ") && !mesero.equals(" ")) {
            filter = "Or_IdCliente = ?  and Or_IdPlato = ? and Or_IdMesero = ? or Or_Id = ? or Or_FechaServicio <= ?;";
            pos.add(4);pos.add(5);pos.add(1);pos.add(2);pos.add(3);
        }else if (!cliente.equals(" ") && !plato.equals(" ")) {
            filter = "Or_IdCliente = ?  and Or_IdPlato = ? or Or_Id = ? or Or_FechaServicio <= ? or Or_IdMesero = ?;";
            pos.add(3);pos.add(4);pos.add(1);pos.add(2);pos.add(5);
        }
        else if (!plato.equals(" ") && !mesero.equals(" ")) {
            filter = "Or_IdPlato = ?  and Or_IdMesero = ? or Or_Id = ? or Or_FechaServicio <= ? or Or_IdCliente = ? ";
            pos.add(3);pos.add(4);pos.add(5);pos.add(1);pos.add(2);
        }
        else if (!mesero.equals(" ") && !cliente.equals(" ")) {
            filter = "Or_IdMesero = ?  and Or_IdCliente = ? or Or_Id = ? or Or_FechaServicio <= ? or Or_IdPlato = ?;";
            pos.add(3);pos.add(4);pos.add(2);pos.add(5);pos.add(1);
        }else {
            filter = "Or_Id = ? or Or_FechaServicio <= ? Or Or_IdCliente = ?  or Or_IdPlato = ? or Or_IdMesero = ?;";
            pos.add(1);pos.add(2);pos.add(3);pos.add(4);pos.add(5);
        }
        array.add(filter);
        array.add(pos);
        return array;
    }
}
