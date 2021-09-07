package controler;

import view.MainWindow;

public class App {
    public static void main(String[] args) throws Exception {
        MainWindow mainVista = new MainWindow();
        new Controlador(mainVista);
    }
}