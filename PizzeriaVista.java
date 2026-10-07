import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PizzeriaVista {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pizzeria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        Orden[] orden = { Orden.crearAleatoria() };
        JLabel lblOrden = new JLabel(orden[0].toString(), SwingConstants.CENTER);
        frame.add(lblOrden, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridLayout(2, 2, 5, 5));
        campos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JComboBox<Pizza.Tamano> comboTamano = new JComboBox<>(Pizza.Tamano.values());
        JCheckBox chk1 = new JCheckBox("PEPPERONI");
        JCheckBox chk2 = new JCheckBox("JAMON");
        JCheckBox chk3 = new JCheckBox("CHAMPINONES");
        JCheckBox chk4 = new JCheckBox("JAMON CERRANO");
        JCheckBox chk5 = new JCheckBox("QUESO");

        JPanel pIng = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pIng.add(chk1); pIng.add(chk2); pIng.add(chk3); pIng.add(chk4); pIng.add(chk5);

        campos.add(new JLabel("Tamano:")); campos.add(comboTamano);
        campos.add(new JLabel("Ingredientes:")); campos.add(pIng);

        JLabel resultado = new JLabel("Prepara la pizza", SwingConstants.CENTER);
        JButton btnEntregar = new JButton("Entregar");
        JButton btnNueva = new JButton("Nueva Orden");
        JPanel botones = new JPanel(new FlowLayout());
        botones.add(btnEntregar); botones.add(btnNueva);

        JPanel sur = new JPanel(new BorderLayout());
        sur.add(resultado, BorderLayout.NORTH);
        sur.add(botones, BorderLayout.SOUTH);

        btnEntregar.addActionListener(e -> {
            Pizza p = new Pizza((Pizza.Tamano) comboTamano.getSelectedItem());
            if (chk1.isSelected()) p.agregar(Pizza.Ingrediente.PEPPERONI);
            if (chk2.isSelected()) p.agregar(Pizza.Ingrediente.JAMON);
            if (chk3.isSelected()) p.agregar(Pizza.Ingrediente.CHAMPINONES);
            if (chk4.isSelected()) p.agregar(Pizza.Ingrediente.JAMON_CERRANO);
            if (chk5.isSelected()) p.agregar(Pizza.Ingrediente.QUESO);
            if (p.esIgual(orden[0].pizzaPedida)) {
                resultado.setText("Orden correcta!");
            } else {
                resultado.setText("Mal. Queria: " + orden[0].pizzaPedida);
            }
        });

        btnNueva.addActionListener(e -> {
            orden[0] = Orden.crearAleatoria();
            lblOrden.setText(orden[0].toString());
            resultado.setText("Nueva orden");
            chk1.setSelected(false); chk2.setSelected(false);
            chk3.setSelected(false); chk4.setSelected(false); chk5.setSelected(false);
        });

        frame.add(campos, BorderLayout.CENTER);
        frame.add(sur, BorderLayout.SOUTH);
        frame.setSize(500, 250);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class Pizza {
    enum Tamano { PEQUEÑA, MEDIANA, GRANDE }
    enum Ingrediente { PEPPERONI, JAMON, CHAMPINONES, JAMON_CERRANO , QUESO }
    Tamano tamano;
    java.util.List<Ingrediente> ingredientes = new ArrayList<>();
    Pizza(Tamano t) { tamano = t; }
    void agregar(Ingrediente i) { if (!ingredientes.contains(i)) ingredientes.add(i); }
    boolean esIgual(Pizza o) { return tamano == o.tamano && ingredientes.containsAll(o.ingredientes) && o.ingredientes.containsAll(ingredientes); }
    public String toString() { return tamano + " con " + ingredientes; }
}

class Orden {
    String cliente;
    Pizza pizzaPedida;
    Orden(String c, Pizza p) { cliente = c; pizzaPedida = p; }
    static Orden crearAleatoria() {
        String[] n = {"Julio", "Maria", "Carlos", "Tiago"};
        Random r = new Random();
        Pizza p = new Pizza(Pizza.Tamano.values()[r.nextInt(3)]);
        p.agregar(Pizza.Ingrediente.values()[r.nextInt(5)]);
        p.agregar(Pizza.Ingrediente.QUESO);
        return new Orden(n[r.nextInt(4)], p);
    }
    public String toString() { return cliente + " quiere: " + pizzaPedida; }
}