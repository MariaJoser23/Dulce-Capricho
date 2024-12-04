import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class DulceCaprichoMenu extends JFrame {

    private JComboBox<String> categoriaComboBox, tamañoComboBox, productoComboBox, complementoComboBox, saborTortaComboBox;
    private JCheckBox quiereTortaCheckBox;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JLabel totalLabel;
    private JTextField precioTextField, precioTortaTextField;
    private double totalVentas = 0.0;

    private DecimalFormat decimalFormat = new DecimalFormat("#0.000");

    public DulceCaprichoMenu() {
        setTitle("Dulce Capricho");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JPanel panelSeleccion = new JPanel();
        panelSeleccion.setLayout(new GridLayout(8, 2, 10, 10));
        panelSeleccion.add(new JLabel("Categoría:"));
        categoriaComboBox = new JComboBox<>(new String[]{"Cafés", "Bebidas Frías", "Tortas"});
        panelSeleccion.add(categoriaComboBox);

        panelSeleccion.add(new JLabel("Tamaño:"));
        tamañoComboBox = new JComboBox<>(new String[]{"Pequeño", "Mediano", "Grande"});
        panelSeleccion.add(tamañoComboBox);

        panelSeleccion.add(new JLabel("Producto:"));
        productoComboBox = new JComboBox<>(new String[]{"Espresso", "Latte", "Cappuccino"});
        panelSeleccion.add(productoComboBox);

        panelSeleccion.add(new JLabel("Complementos:"));
        complementoComboBox = new JComboBox<>(new String[]{"Sin complemento", "Jarabe de vainilla", "Jarabe de caramelo", "Jarabe de avellana", "Crema batida"});
        panelSeleccion.add(complementoComboBox);

        panelSeleccion.add(new JLabel("Precio del Producto (COP):"));
        precioTextField = new JTextField();
        panelSeleccion.add(precioTextField);

        quiereTortaCheckBox = new JCheckBox("¿Quiere torta?");
        panelSeleccion.add(quiereTortaCheckBox);

        panelSeleccion.add(new JLabel("Sabor de Torta:"));
        saborTortaComboBox = new JComboBox<>(new String[]{"Tres Leches", "Red Velvet", "Cheesecake", "Zanahoria", "Mora", "Fresa", "Banano", "Chocolate"});
        panelSeleccion.add(saborTortaComboBox);

        panelSeleccion.add(new JLabel("Precio de la Torta (COP):"));
        precioTortaTextField = new JTextField();
        panelSeleccion.add(precioTortaTextField);

        JButton agregarButton = new JButton("Agregar al Carrito");
        JButton calcularTotalButton = new JButton("Calcular Total");
        panelSeleccion.add(agregarButton);
        panelSeleccion.add(calcularTotalButton);

        modeloTabla = new DefaultTableModel(new String[]{"Producto", "Detalles", "Precio (COP)"}, 0);
        tablaProductos = new JTable(modeloTabla);

        JPanel panelTotal = new JPanel(new BorderLayout());
        totalLabel = new JLabel("Total de Ventas: $0.000");
        panelTotal.add(totalLabel, BorderLayout.WEST);

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        calcularTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularTotal();
            }
        });

        categoriaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProductos();
            }
        });

        add(panelSeleccion, BorderLayout.NORTH);
        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
        add(panelTotal, BorderLayout.SOUTH);

        quiereTortaCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProductos(); 
            }
        });
    }

    private void agregarProducto() {
        try {
            String categoria = (String) categoriaComboBox.getSelectedItem();
            String tamaño = (String) tamañoComboBox.getSelectedItem();
            String producto = (String) productoComboBox.getSelectedItem();
            String complemento = (String) complementoComboBox.getSelectedItem();
            String saborTorta = (String) saborTortaComboBox.getSelectedItem();

            String precioTexto = precioTextField.getText();
            
            if (precioTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar el precio del producto", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double precio = Double.parseDouble(precioTexto);
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (quiereTortaCheckBox.isSelected() && categoria.equals("Tortas")) {
                String precioTortaTexto = precioTortaTextField.getText();
                
                if (precioTortaTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar el precio de la torta", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double precioTorta = Double.parseDouble(precioTortaTexto);
                if (precioTorta <= 0) {
                    JOptionPane.showMessageDialog(this, "El precio de la torta debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                modeloTabla.addRow(new Object[]{"Torta", saborTorta, precioTorta});
                totalVentas += precioTorta;
                totalLabel.setText("Total de Ventas: $" + decimalFormat.format(totalVentas));
                return; 
            }

            String detalles = tamaño + " " + producto + ", " + complemento;
            modeloTabla.addRow(new Object[]{categoria, detalles, precio});
            totalVentas += precio;

            if (quiereTortaCheckBox.isSelected()) {

                String precioTortaTexto = precioTortaTextField.getText();
                
                if (precioTortaTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar el precio de la torta", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double precioTorta = Double.parseDouble(precioTortaTexto);
                if (precioTorta <= 0) {
                    JOptionPane.showMessageDialog(this, "El precio de la torta debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                modeloTabla.addRow(new Object[]{"Torta", saborTorta, precioTorta});
                totalVentas += precioTorta;
            }

            precioTextField.setText("");
            precioTortaTextField.setText("");

            totalLabel.setText("Total de Ventas: $" + decimalFormat.format(totalVentas));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularTotal() {
        JOptionPane.showMessageDialog(this, "El total de ventas es: $" + decimalFormat.format(totalVentas), "Total de Ventas", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarProductos() {
        String categoria = (String) categoriaComboBox.getSelectedItem();

        if (quiereTortaCheckBox.isSelected()) {
            productoComboBox.setEnabled(false);
            complementoComboBox.setEnabled(false);
        } else {
            productoComboBox.setEnabled(true);
            complementoComboBox.setEnabled(true);
        }

        if (categoria.equals("Tortas")) {
            productoComboBox.setEnabled(false); 
            complementoComboBox.setEnabled(false);  
        } else {
            productoComboBox.setEnabled(true);  
            complementoComboBox.setEnabled(true);  
        }
    }

    public static void main(String[] args) {
        DulceCaprichoMenu menu = new DulceCaprichoMenu();
        menu.setVisible(true);
    }
}


