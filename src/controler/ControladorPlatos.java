package controler;

import view.ViewPlatos;
import access.PlatoDAO;
import model.Plato;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


public class ControladorPlatos implements ActionListener{
    private ArrayList<Plato> platoList = null;    
    private ArrayList<String> tipoPlatoList = null;
    private final PlatoDAO platoDAO = new PlatoDAO();
    private ViewPlatos mainPlatos = new ViewPlatos();
    private int busqueda= 0;
    
    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^---------- CONSTRUCTOR ----------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    public ControladorPlatos(ViewPlatos mainPlatos) {
        this.mainPlatos = mainPlatos;
        this.mainPlatos.btnActualizar.addActionListener(this);
        this.mainPlatos.btnAgregar.addActionListener(this);
        this.mainPlatos.btnBuscar.addActionListener(this);
        this.mainPlatos.btnEliminar.addActionListener(this);
        this.mainPlatos.btnVolver.addActionListener(this);
        init();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.mainPlatos.btnActualizar) {
            functionBtnActualizar();
        }
        if (e.getSource() == this.mainPlatos.btnAgregar) {
            functionBtnAgregar();
        }
        if (e.getSource() == this.mainPlatos.btnBuscar) {
            functionBtnBuscar();
        }
        if (e.getSource() == this.mainPlatos.btnEliminar) {
            functionBtnEliminar();
        }
        if (e.getSource() == this.mainPlatos.btnVolver) {
            cleanFields();
            btnsDesactivate();
            this.mainPlatos.dispose();
        }
    }

     //* Metodo de inicio
     public void init() {
        btnsDesactivate();
        getForLoadTable();
        this.tipoPlatoList = platoDAO.getTipoPlato();
        this.mainPlatos.txtId.setEditable(false);
        loadCbxTipoPlato(tipoPlatoList);
        JTable table = this.mainPlatos.tblPlato;
        tableSelected(table);
    }
    
    //* Desactiva botones que no son usables si se va a Agregar
    private void btnsDesactivate() {
        this.mainPlatos.btnEliminar.setEnabled(false);
        this.mainPlatos.btnActualizar.setEnabled(false);
    }
    
    //* Activa botones que son usables si se va a Eliminar o Actualizar
    private void btnsActivate() {
        this.mainPlatos.btnEliminar.setEnabled(true);
        this.mainPlatos.btnActualizar.setEnabled(true);
    }

    //* Obtiene los valores de la tabla y los envia para cargarlos en la vista
    public void getForLoadTable() {
        this.platoList = platoDAO.getAllPlatos();
        loadTable(platoList);
    }
    
    //* Carga los valores pasados por parametro a la tabla en vista
    public void loadTable(ArrayList<Plato> platosList) {
        String[] headers = { "ID", "NOMBRE", "PRECIO", "TIPO DE PLATO"};
        this.mainPlatos.tblPlato.removeAll();
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(headers);
        for (int i = 0; i < platosList.size(); i++) {
            tableModel.addRow(platosList.get(i).toArray());
        }
        this.mainPlatos.tblPlato.setModel(tableModel);
        this.mainPlatos.tblPlato.setSelectionBackground(Color.lightGray);
        this.mainPlatos.tblPlato.setRowHeight(20);
    }

    //* Carga los valores al comboBox de meseros
    public void loadCbxTipoPlato(ArrayList<String> meserosList) {
        this.mainPlatos.cbxTipoPlato.removeAllItems();
        this.mainPlatos.cbxTipoPlato.addItem(" ");
        for (String item : meserosList) {
            this.mainPlatos.cbxTipoPlato.addItem(item);
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
        this.mainPlatos.txtId.setText(String.valueOf(values.get(0)));
        this.mainPlatos.txtNombre.setText(String.valueOf(values.get(1)));
        this.mainPlatos.txtPrecio.setText(String.valueOf(values.get(2)));
        this.mainPlatos.cbxTipoPlato.setSelectedItem(String.valueOf(values.get(3)));
        removeCbx();
        modifyComponents();
    }
    //* Activa, Desactiva y modifica botones cuando se seleciona un registro
    public void modifyComponents() {
        this.mainPlatos.txtId.setEditable(false);
        this.mainPlatos.btnAgregar.setEnabled(false);
        this.mainPlatos.btnBuscar.setText("BUSQUEDA");
        btnsActivate();
        busqueda = 0;
    }
    //* Limpia los campos
    public void cleanFields() {
        this.mainPlatos.txtId.setText(null);
        this.mainPlatos.txtNombre.setText(null);
        this.mainPlatos.txtPrecio.setText(null);
        String cbx = this.mainPlatos.cbxTipoPlato.getItemAt(0);
        if (cbx.equals(" ")){
            selectedCbx();
        }else{
            this.mainPlatos.cbxTipoPlato.insertItemAt(" ", 0);
            selectedCbx();
        }
    }
    //* Seleciona el campo nulo o vacio
    public void selectedCbx(){
        this.mainPlatos.cbxTipoPlato.setSelectedItem(" ");
    }
    //* Remueve el campo nulo o vacio para evitar errores
    public void removeCbx(){
        this.mainPlatos.cbxTipoPlato.removeItem(" ");
    }
    //* Obtiene los valores de los campos y a su vez los transforma en un objeto de tipo plato valido para poder hacer acciones DAO
    public Plato getFields() {
        String nombre = this.mainPlatos.txtNombre.getText();
        String strPrecio = this.mainPlatos.txtPrecio.getText();
        String tipoPlato = (String) this.mainPlatos.cbxTipoPlato.getSelectedItem();
        int tipoPlatoId = platoDAO.getIdTipoPlato(tipoPlato); 
        String strId = this.mainPlatos.txtId.getText();
        Double precio = (!strPrecio.equals("")) ? Double.parseDouble(strPrecio) : 0.0;
        if (!strId.equals("")){
            int id = Integer.parseInt(strId);
            Plato objectPlato = new Plato(id, nombre, precio, tipoPlatoId);
            return objectPlato;
        }else{
            Plato objectPlato = new Plato(nombre, precio, tipoPlatoId);
            return objectPlato;
        }
    }

    // ^|||||||||||||||||||||||||||||||||^ \\
    // ^-------- BUTTONS METHODS --------^ \\
    // ^|||||||||||||||||||||||||||||||||^ \\

    //* Función Agregar un registro (contiene control de error por si se dejan los campos vacios)
    private void functionBtnAgregar() {
        String nombre = this.mainPlatos.txtNombre.getText();
        String precio = this.mainPlatos.txtPrecio.getText();
        String tipoPlato = (String) this.mainPlatos.cbxTipoPlato.getSelectedItem();
        if (!nombre.isEmpty() && !precio.isEmpty() && !tipoPlato.equals(" ")) {
            Plato params = getFields();
            platoDAO.insertPlato(params);
            cleanFields();
            getForLoadTable();
        } else {
            JOptionPane.showMessageDialog(null, "Debe Llenar Todos Los Campos");
        }
    }
    
    //* Función Eliminar, borra según la id del registro seleccionado en la tabla 
    private void functionBtnEliminar() {
        int id = Integer.parseInt(this.mainPlatos.txtId.getText());
        platoDAO.deletePlato(id);
        cleanFields();
        getForLoadTable();
    }
    
    //* Funcion Actualizar, edita según la id del registro seleccionado en la tabla, modifica los campos que el usuario cambio 
    private void functionBtnActualizar() {
        Plato params = getFields();
        platoDAO.updatePlato(params);
        cleanFields();
        getForLoadTable();
    }

    //* Funcion Buscar, usa el mismo boton para realizar dos acciones diferentes cada vez que se hace click
    public void functionBtnBuscar() {

        btnsDesactivate();
        if (busqueda == 0) {//* prepara la vista para poder hacer una busqueda,(limpia los campos, habilita campo id)
            busqueda = 1;
            this.mainPlatos.btnBuscar.setText("BUSCAR");
            this.mainPlatos.txtId.setEditable(true);
            this.mainPlatos.btnAgregar.setEnabled(false);
            cleanFields();
        } else {//* realiza la busqueda y resetea el boton al un estado inicial default
            busqueda = 0;
            String id = this.mainPlatos.txtId.getText();            
            String nombre = this.mainPlatos.txtNombre.getText();
            String precio = this.mainPlatos.txtPrecio.getText();
            String tipoPlato = (String) this.mainPlatos.cbxTipoPlato.getSelectedItem();
            if (!id.isEmpty() || !nombre.isEmpty() || !precio.isEmpty() || !tipoPlato.equals(" ")) {
                Plato params = getFields();
                this.platoList = platoDAO.getSearchPlato(params);
                loadTable(platoList);
            }
            cleanFields();
            this.mainPlatos.btnBuscar.setText("BUSQUEDA");
            this.mainPlatos.txtId.setEditable(false);
            this.mainPlatos.btnAgregar.setEnabled(true);
        }
    }
}
