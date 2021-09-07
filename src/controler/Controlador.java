package controler;

//import access.*;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.Toolkit;


public class Controlador implements ActionListener {
    
    // *||||||||||||||||||||||||||||||||| *\\
    // *------- CONNECTION VISTA -------- *\\
    // *||||||||||||||||||||||||||||||||| *\\

    private ViewOrden vistaOrden = new ViewOrden();
    private ViewClientes vistaClientes = new ViewClientes();
    private ViewMeseros vistaMeseros = new ViewMeseros();
    private ViewPlatos vistaPlatos = new ViewPlatos(); 
    private ViewTipoPlato vistaTipoPlato = new ViewTipoPlato();     
    private MainWindow mainVista = new MainWindow();

    public Controlador() {
    }

    public Controlador(MainWindow mainVista) {
        this.mainVista = mainVista;
        this.mainVista.setVisible(true);
        centreWindow(mainVista);
        this.mainVista.btnOrdenes.addActionListener(this);
        this.mainVista.btnClientes.addActionListener(this);
        this.mainVista.btnMeseros.addActionListener(this);
        this.mainVista.btnPlatos.addActionListener(this);
        this.mainVista.btnTipoPlato.addActionListener(this);
        this.mainVista.btnSalir.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.mainVista.btnOrdenes) {
            vistaOrden.setVisible(true);
            centreWindow(vistaOrden);
            new ControladorOrden(vistaOrden);

        }
        if (e.getSource() == this.mainVista.btnClientes) {
            vistaClientes.setVisible(true);
            centreWindow(vistaClientes);
            new ControladorClientes(vistaClientes);

        }
        if (e.getSource() == this.mainVista.btnMeseros) {
            vistaMeseros.setVisible(true);
            centreWindow(vistaMeseros);
            new ControladorMeseros(vistaMeseros);

        }
        if (e.getSource() == this.mainVista.btnPlatos) {
            vistaPlatos.setVisible(true);
            centreWindow(vistaPlatos);
            new ControladorPlatos(vistaPlatos);

        }
        if (e.getSource() == this.mainVista.btnTipoPlato) {
            vistaTipoPlato.setVisible(true);
            centreWindow(vistaTipoPlato);
            new ControladorTipoPlato(vistaTipoPlato);

        }
        if (e.getSource() == this.mainVista.btnSalir) {
            System.exit(0);
        }
    }
    public static void centreWindow(JFrame vista) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - vista.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - vista.getHeight()) / 2);
        vista.setLocation(x, y);
    }
}
