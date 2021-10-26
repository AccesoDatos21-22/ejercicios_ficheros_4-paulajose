package dao;

import java.io.*;
import java.util.List;
import java.util.Scanner;

import modelo.Medicamento;

public class MedicamentoAleatorio implements MedicamentoDAO {
	
	public final static int TAM_NOMBRE = 30;
	public final static int TAM_REGISTRO = 88;
	
	@Override
	public boolean guardar(Medicamento medicamento) {

		String nombre;
		RandomAccessFile raf = null;
		int pos;
		StringBuilder sb;

		try {
			nombre = "medicamentos.dat";
			raf = new RandomAccessFile(nombre, "rw");

			pos = (medicamento.getCod() -1) * TAM_REGISTRO;
			raf.seek(pos);

			sb = new StringBuilder(medicamento.getNombre());
			sb.setLength(TAM_NOMBRE);
			raf.writeChars(sb.toString());
			raf.writeInt(medicamento.getCod());
			raf.writeDouble(medicamento.getPrecio());
			raf.writeInt(medicamento.getStock());
			raf.writeInt(medicamento.getStockMaximo());
			raf.writeInt(medicamento.getStockMinimo());
			raf.writeInt(medicamento.getCodProveedor());
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {
				if (raf != null) {
					raf.close();
				}
			}
			catch (NullPointerException e){
				System.out.println(e.getMessage());
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public Medicamento buscar(int codigo) {

		Medicamento med = null;
		RandomAccessFile raf = null;
		int pos;
		StringBuilder nom = new StringBuilder();
		double pre;
		int cod, stock, stockMax, stockMin, codProv;

		try {
			raf = new RandomAccessFile("medicamentos.dat", "r");
			pos = (codigo - 1) * TAM_REGISTRO;
			raf.seek(pos);

			for (int i = 0; i < TAM_NOMBRE; i++){
				nom.append(raf.readChar());
			}
			cod = raf.readInt();
			if (codigo == cod) {
				pre = raf.readDouble();
				stock = raf.readInt();
				stockMax = raf.readInt();
				stockMin = raf.readInt();
				codProv = raf.readInt();

				med = new Medicamento(nom.toString(), (pre/1.04), codigo, stock, stockMax, stockMin, codProv);
			}
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		finally {
			try {
				if (raf != null) {
					raf.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (NullPointerException e){
				System.out.println(e.getMessage());
			}
		}
		return med;
	}

	@Override
	public boolean actualizar(Medicamento medicamento) {
		RandomAccessFile raf = null;
		int codigo, pos, stock, stockMax, stockMin, codProv;
		String nom;
		double pre;
		Scanner sc;
		StringBuilder sb;

		try {
			sc = new Scanner(System.in);
			raf = new RandomAccessFile("medicamentos.dat", "rw");
			codigo = medicamento.getCod();

			pos = ((codigo - 1) * TAM_REGISTRO);
			raf.seek(pos);

			System.out.println("Introduzca el nombre del medicamento:");
			nom = sc.nextLine();
			sb = new StringBuilder(nom);
			sb.setLength(TAM_NOMBRE);
			raf.writeChars(sb.toString());
			raf.writeInt(codigo);
			System.out.println("Introduzca el precio del medicamento:");
			pre = Double.parseDouble(sc.nextLine());
			pre += (pre*0.04f);
			raf.writeDouble(pre);
			System.out.println("Introduzca el stock del medicamento:");
			stock = Integer.parseInt(sc.nextLine());
			raf.writeInt(stock);
			System.out.println("Introduzca el stock máximo del medicamento:");
			stockMax = Integer.parseInt(sc.nextLine());
			raf.writeInt(stockMax);
			System.out.println("Introduzca el stock mínimo del medicamento:");
			stockMin = Integer.parseInt(sc.nextLine());
			raf.writeInt(stockMin);
			System.out.println("Introduzca el código de proveedor del medicamento:");
			codProv = Integer.parseInt(sc.nextLine());
			raf.writeInt(codProv);
			System.out.println("--------------------------------------------------");
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}
		finally {
			try {
				if (raf != null) {
					raf.close();
				}
			}
			catch (NullPointerException e){
				System.out.println(e.getMessage());
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public boolean borrar(Medicamento medicamento) {
		return false;
	}

	@Override
	public List<Medicamento> leerTodos() {
		return null;
	}

}
