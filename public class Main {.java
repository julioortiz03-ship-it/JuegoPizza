import java.util.*;

class Pizza {
    enum Tamano { PEQUEÑA, MEDIANA, GRANDE }
    enum Ingrediente { PEPPERONI, JAMON, PINA, QUESO }

    Tamano tamano;
    List<Ingrediente> ingredientes;
    boolean horneada = false;

    public Pizza(Tamano tamano) {
        this.tamano = tamano;
        this.ingredientes = new ArrayList<>();
    }

    void agregar(Ingrediente ing) {
        ingredientes.add(ing);
        System.out.println("Agregaste: " + ing);
    }

    void hornear() {
        horneada = true;
        System.out.println("Pizza horneada!");
    }

    @Override
    public String toString() {
        return tamano + " con " + ingredientes;
    }
}

class Orden {
    String cliente;
    Pizza pizzaPedida;

    public Orden(String cliente, Pizza pizzaPedida) {
        this.cliente = cliente;
        this.pizzaPedida = pizzaPedida;
    }

    static Orden crearAleatoria() {
        String[] nombres = {"Julio", "Maria", "Carlos"};
        Random r = new Random();

        Pizza.Tamano t = Pizza.Tamano.values()[r.nextInt(Pizza.Tamano.values().length)];
        Pizza p = new Pizza(t);
        p.agregar(Pizza.Ingrediente.values()[r.nextInt(Pizza.Ingrediente.values().length)]);
        p.agregar(Pizza.Ingrediente.QUESO);

        return new Orden(nombres[r.nextInt(nombres.length)], p);
    }
}

class Cocina {

    private Orden[] ordenes = new Orden[5];


    boolean entregar(Orden orden, Pizza miPizza) {
        if (orden.pizzaPedida.tamano == miPizza.tamano &&
            orden.pizzaPedida.ingredientes.equals(miPizza.ingredientes)) {

            System.out.println("Orden correcta para " + orden.cliente);
            return true;
        } else {
            System.out.println("Te equivocaste. El cliente queria: " + orden.pizzaPedida);
            System.out.println("Tu hiciste: " + miPizza);
            return false;
        }
    }
}

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Orden orden = Orden.crearAleatoria();
        System.out.println("--- NUEVA ORDEN ---");
        System.out.println(orden.cliente + " quiere: " + orden.pizzaPedida);

        System.out.println("\n--- TU TURNO ---");
        System.out.println("Elige tamano: 0=PEQUENA, 1=MEDIANA, 2=GRANDE");
        int opTam = sc.nextInt();
        Pizza miPizza = new Pizza(Pizza.Tamano.values()[opTam]);

        System.out.println("Elige ingredientes: 0=PEPPERONI, 1=JAMON, 2=PINA, 3=QUESO");
        sc.nextLine();
        String[] ops = sc.nextLine().split(" ");
        for (String s : ops) {
            int i = Integer.parseInt(s);
            miPizza.agregar(Pizza.Ingrediente.values()[i]);
        }

        miPizza.hornear();

        Cocina cocina = new Cocina();
        cocina.entregar(orden, miPizza);

        sc.close();
    }
}
