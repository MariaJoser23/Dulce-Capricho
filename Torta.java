public class Torta extends ProductoBase {
    private String sabor;

    public Torta(String sabor, double precio) {
        super("Torta " + sabor, precio);
        this.sabor = sabor;
    }

    public String getSabor() {
        return sabor;
    }
}

