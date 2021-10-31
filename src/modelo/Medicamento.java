package modelo;

import java.util.Scanner;

public class Medicamento {

	static Scanner sc = new Scanner(System.in);

	public final static float IVA = 0.04f;

	private String nombre; // tama�o 30, 60 bytes
	private double precio; // 8 bytes
	private int cod; // 4 bytes
	private int stock; // 4 bytes
	private int stockMaximo; // 4 bytes
	private int stockMinimo; // 4 bytes
	private int codProveedor; // 4 bytes
	private static int cod_Siguiente;

	public Medicamento() {
		super();
	}

	public Medicamento(String nombre, double precio, int stock, int stockMaximo, int stockMinimo, int codProveedor) {

		Scanner sc = new Scanner(System.in);

		this.nombre = nombre;
		this.precio = precio + (precio * IVA);

		if (stock < stockMaximo && stock > stockMinimo)
			this.stock = stock;
		else
			while (stock > stockMaximo || stock < stockMinimo) {
				System.err.println("\nEl stock introducido no es válido. Introduzca otro: ");
				stock = sc.nextInt();

				this.stock = stock;
			}


		this.stockMaximo = stockMaximo;
		this.stockMinimo = stockMinimo;
		this.codProveedor = codProveedor;

		cod_Siguiente++;
		cod = cod_Siguiente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getStockMaximo() {
		return stockMaximo;
	}

	public void setStockMaximo(int stockMaximo) {
		this.stockMaximo = stockMaximo;
	}

	public int getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public int getCodProveedor() {
		return codProveedor;
	}

	public void setCodProveedor(int codProveedor) {
		this.codProveedor = codProveedor;
	}

	public static float getIva() {
		return IVA;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Medicamento) {

			Medicamento medicamento = (Medicamento) obj;

			if (this.getCod() == medicamento.getCod())
				return true;
			else
				return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "\nCódigo: " + cod + "\nNombre: " + nombre + "\nPrecio: " + precio + "\nStock: " + stock
				+ "\nProveedor: " + codProveedor;
	}

}