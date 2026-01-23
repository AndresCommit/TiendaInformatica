package es.alber.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "precio")
    private Double precio;

    // Relación: Muchos Productos pertenecen a UN Fabricante.
    // @JoinColumn es la columna real de la base de datos (la Foreign Key).
    @ManyToOne
    @JoinColumn(name = "codigo_fabricante")
    private Fabricante fabricante;

    public Producto() {}

    public Producto(String nombre, Double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    // Getters y Setters
    public Integer getCodigo() { return codigo; }
    public void setCodigo(Integer codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Fabricante getFabricante() { return fabricante; }
    public void setFabricante(Fabricante fabricante) { this.fabricante = fabricante; }

    @Override
    public String toString() {
        return "Producto: " + nombre + " | Precio: " + precio + "€";
    }
}