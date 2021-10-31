package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import modelo.Medicamento;

public class MedicamentoAleatorio implements MedicamentoDAO {

    public final static int TAM_NOMBRE = 30;
    public final static int TAM_REGISTRO = 88;

    @Override
    public boolean guardar(Medicamento medicamento) {

        try (RandomAccessFile raf = new RandomAccessFile(new File("Medicamentos.bin"), "rw")) {

            // Escribir
            int posicion = (medicamento.getCod() - 1) * TAM_REGISTRO;
            raf.seek(posicion);

            StringBuilder sb = new StringBuilder(medicamento.getNombre());
            sb.setLength(TAM_NOMBRE);
            raf.writeChars(sb.toString());

            raf.writeInt(medicamento.getCod());
            raf.writeDouble(medicamento.getPrecio());
            raf.writeInt(medicamento.getStockMaximo());
            raf.writeInt(medicamento.getStockMinimo());
            raf.writeInt(medicamento.getStock());
            raf.writeInt(medicamento.getCodProveedor());

            raf.seek(posicion);

            while (raf.getFilePointer() < raf.length()) {

                String nombre = "";
                for (int i = 0; i < TAM_NOMBRE; i++) {
                    nombre += raf.readChar();
                }

                int codigo = raf.readInt();
                Double precio = raf.readDouble();
                int stockMax = raf.readInt();
                int stockMin = raf.readInt();
                int stock = raf.readInt();
                int codProveedor = raf.readInt();

                System.out.println("\nCódigo: " + codigo + "\nNombre: " + nombre + "\nPrecio: " + precio + "\nStock: " + stock + "\nCódigo de proveedor: " + codProveedor);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<Medicamento> buscar(String nombre) {

        List<Medicamento> medBuscados = new ArrayList<Medicamento>();

        int posicion = 0;

        try (RandomAccessFile raf = new RandomAccessFile("Medicamentos.bin", "r")) {

            while (raf.getFilePointer() < raf.length()) {

                raf.seek(posicion * TAM_REGISTRO);
                posicion++;

                //Prueba para ver las veces que se recorre el while
                // System.out.println("Posición: " + (posicion - 1));

                String nom = "";
                for (int i = 0; i < TAM_NOMBRE; i++) {
                    nom += raf.readChar();
                }

                StringBuilder sb = new StringBuilder(nom);
                sb.setLength(nombre.length());

                nom = sb.toString();

                //Comprobación de que el nombre corta bien al nombre leido
                //System.out.println("Pueba Nombre: " + sb.toString());

                if (nombre.equalsIgnoreCase(nom)) {

                    int codigo = raf.readInt();
                    double precio = raf.readDouble();
                    int stockMax = raf.readInt();
                    int stockMin = raf.readInt();
                    int stock = raf.readInt();
                    int codProveedor = raf.readInt();

                    precio = precio / 1.04;

                    Medicamento med = new Medicamento(nombre, precio, stock, stockMax, stockMin, codProveedor);
                    med.setCod(codigo);

                    medBuscados.add(med);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return medBuscados;
    }

    @Override
    public boolean actualizar(Medicamento medicamento) {


        return false;
    }

    @Override
    public boolean borrar(Medicamento medicamento) {

        int posicion = (medicamento.getCod() - 1) * TAM_REGISTRO;
        RandomAccessFile raf = null;

        try {

            raf = new RandomAccessFile("Medicamentos.bin", "rw");

            raf.seek(posicion);

            medicamento.setNombre("");
            StringBuilder sb = new StringBuilder(medicamento.getNombre());
            sb.setLength(TAM_NOMBRE);
            raf.writeChars(sb.toString());

            raf.writeInt(0);
            raf.writeDouble(0.0);
            raf.writeInt(0);
            raf.writeInt(0);
            raf.writeInt(0);
            raf.writeInt(0);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public List<Medicamento> leerTodos() {

      /*  List<Medicamento> medicamentos = new ArrayList<Medicamento>();
        Medicamento medicamento = new Medicamento();
        int cont = 0;

        try (RandomAccessFile raf = new RandomAccessFile("Medicamentos.bin", "r")) {

            while (raf.getFilePointer() < raf.length()) {

                int codigo = raf.readInt();
                medicamento.setCod(codigo);

                String nombre = "";
                for (int i = 0; i < TAM_NOMBRE; i++) {
                    nombre += raf.readChar();
                }
                medicamento.setNombre(nombre);

                Double precio = raf.readDouble();
                medicamento.setPrecio(precio);

                int stockMax = raf.readInt();
                medicamento.setStockMaximo(stockMax);

                int stockMin = raf.readInt();
                medicamento.setStockMinimo(stockMin);

                int stock = raf.readInt();
                medicamento.setStock(stock);

                int codProveedor = raf.readInt();
                medicamento.setCodProveedor(codProveedor);

                medicamentos.add(medicamento);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }*/
        return null;
    }
}