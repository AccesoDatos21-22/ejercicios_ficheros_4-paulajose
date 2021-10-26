package modelo;

import java.util.List;
import java.util.Scanner;

import dao.MedicamentoDAO;

public class Farmacia implements MedicamentoDAO {

	private List<Medicamento> medicamentos;

	public Farmacia() {

	}

	public Farmacia(List<Medicamento> lista) {
		this.medicamentos = lista;
	}

	@Override
	public boolean guardar(Medicamento medicamento) {
		this.medicamentos.add(medicamento);
		return true;
	}

	@Override
	public boolean borrar(Medicamento medicamento) {
		this.medicamentos.remove(medicamento);
		return true;
	}

	@Override
	public List<Medicamento> leerTodos() {
		return this.medicamentos;
	}

	@Override
	public Medicamento buscar(int codigo) {
		Medicamento m = null;
		try {
			for (Medicamento med : medicamentos) {
				if (med.getCod()==codigo){
					m = med;
				}
			}
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		return m;
	}

	@Override
	public boolean actualizar(Medicamento medicamento, List<Medicamento> l) {

		String nom;
		Double pre;
		int cod, stock, stockMaximo, stockMinimo, codProveedor;
		Scanner sc = null;
		Medicamento m;

		try {
			sc = new Scanner(System.in);
			for (Medicamento med : medicamentos) {
				if (medicamento.toString().equals(med.toString())){
					medicamentos.remove(med);
					System.out.println("Introduzca el nombre:");
					nom = sc.nextLine();
					System.out.println("Introduzca el precio:");
					pre = Double.parseDouble(sc.nextLine());
				//	cod = l.size();
					System.out.println("Introduzca el stock:");
					stock = Integer.parseInt(sc.nextLine());
					System.out.println("Introduzca el stock máximo:");
					stockMaximo = Integer.parseInt(sc.nextLine());
					System.out.println("Introduzca el stock mínimo:");
					stockMinimo = Integer.parseInt(sc.nextLine());
					System.out.println("Introduzca el código de proveedor:");
					codProveedor = Integer.parseInt(sc.nextLine());

					m = new Medicamento(nom, pre, cod, stock, stockMaximo, stockMinimo, codProveedor);
				}
			}
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		finally {
			sc.close();
		}
		return false;
	}
}
