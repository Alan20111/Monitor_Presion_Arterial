package dev.alan20111.monitorpresionarterial.models;

import java.time.LocalDate;

public class c_paciente {
    private int id_paciente;
    private String nombres;
    private String apellidos;
    private LocalDate fecha_nacimiento;
    private String genero;
    private double peso;
    private double altura;

    public c_paciente(int id_paciente, String nombres, String apellidos, LocalDate fecha_nacimiento, String genero, double peso, double altura) {
        this.id_paciente = id_paciente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fecha_nacimiento = fecha_nacimiento;
        this.genero = genero;
        this.peso = peso;
        this.altura = altura;
    }

    public c_paciente(String nombres, String apellidos, LocalDate fecha_nacimiento, String genero, double peso, double altura) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fecha_nacimiento = fecha_nacimiento;
        this.genero = genero;
        this.peso = peso;
        this.altura = altura;
    }

    public int getId_paciente() { return id_paciente; }
    public void setId_paciente(int id_paciente) { this.id_paciente = id_paciente; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public LocalDate getFecha_nacimiento() { return fecha_nacimiento; }
    public void setFecha_nacimiento(LocalDate fecha_nacimiento) { this.fecha_nacimiento = fecha_nacimiento; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }

    @Override
    public String toString() {
        return nombres + " " + apellidos;
    }
    public String getDiagnosticoPeso() {
        if (this.altura <= 0 || this.peso <= 0) {
            return "Sin datos";
        }

        double imc = this.peso / (this.altura * this.altura);

        // Clasificación de la OMS
        if (imc < 18.5) {
            return "Bajo peso";
        } else if (imc >= 18.5 && imc <= 24.9) {
            return "Normal";
        } else if (imc >= 25.0 && imc <= 29.9) {
            return "Sobrepeso";
        } else {
            return "Obesidad";
        }
    }
}