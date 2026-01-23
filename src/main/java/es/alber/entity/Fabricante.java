package es.alber.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "fabricante")
public class Fabricante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod")
    private Integer codigo;

    @Column(name = "nombre")
    private String nombre;

    // Relación: Un Fabricante tiene MUCHOS productos.
    // 'mappedBy' dice que la clave la tiene la variable "fabricante" en la otra clase.
    @OneToMany(mappedBy = "fabricante", cascade = CascadeType.ALL)
    private List<Producto> listaProductos;

    public Fabricante() {}

    public Fabricante(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public Integer getCodigo() { return codigo; }
    public void setCodigo(Integer codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Producto> getListaProductos() { return listaProductos; }
    public void setListaProductos(List<Producto> listaProductos) { this.listaProductos = listaProductos; }

    @Override
    public String toString() {
        return "Fabricante: " + nombre + " (ID: " + codigo + ")";
    }
}