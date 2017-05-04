package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "puesto")
public class Puesto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1170393877208884376L;
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "propietario")
	private String propietario;
	@Column(name = "productos")
	private String productos;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "telefono")
	private String telefono;
	@Column(name = "num_puesto")
	private Integer num_puesto;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mercado_id")
	private Mercado mercado;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	@OneToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	public Puesto(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public Puesto() {
		super();
	}

	public Puesto(String nombre) {
		super();
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPropietario() {
		return propietario;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}

	public String getProductos() {
		return productos;
	}

	public void setProductos(String productos) {
		this.productos = productos;
	}

	public Integer getNum_puesto() {
		return num_puesto;
	}

	public void setNum_puesto(Integer num_puesto) {
		this.num_puesto = num_puesto;
	}

	public Mercado getMercado() {
		return mercado;
	}

	public void setMercado(Mercado mercado) {
		this.mercado = mercado;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	
}
