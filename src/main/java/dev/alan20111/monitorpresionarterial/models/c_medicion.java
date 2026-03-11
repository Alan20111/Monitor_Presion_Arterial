package dev.alan20111.monitorpresionarterial.models;

import java.time.LocalDateTime;

public class c_medicion {
    private int id_medicion;
    private int id_paciente;
    private int presion_sistolica;
    private int presion_diastolica;
    private int pulsos;
    private LocalDateTime fecha_hora;

    public c_medicion() {
    }

    public c_medicion(int id_medicion, int id_paciente, int presion_sistolica, int presion_diastolica, int pulsos, LocalDateTime fecha_hora) {
        this.id_medicion = id_medicion;
        this.id_paciente = id_paciente;
        this.presion_sistolica = presion_sistolica;
        this.presion_diastolica = presion_diastolica;
        this.pulsos = pulsos;
        this.fecha_hora = fecha_hora;
    }

    public c_medicion(int id_paciente, int presion_sistolica, int presion_diastolica, int pulsos, LocalDateTime fecha_hora) {
        this.id_paciente = id_paciente;
        this.presion_sistolica = presion_sistolica;
        this.presion_diastolica = presion_diastolica;
        this.pulsos = pulsos;
        this.fecha_hora = fecha_hora;
    }

    public int getId_medicion() { return id_medicion; }
    public void setId_medicion(int id_medicion) { this.id_medicion = id_medicion; }

    public int getId_paciente() { return id_paciente; }
    public void setId_paciente(int id_paciente) { this.id_paciente = id_paciente; }

    public int getPresion_sistolica() { return presion_sistolica; }
    public void setPresion_sistolica(int presion_sistolica) { this.presion_sistolica = presion_sistolica; }

    public int getPresion_diastolica() { return presion_diastolica; }
    public void setPresion_diastolica(int presion_diastolica) { this.presion_diastolica = presion_diastolica; }

    public int getPulsos() { return pulsos; }
    public void setPulsos(int pulsos) { this.pulsos = pulsos; }

    public LocalDateTime getFecha_hora() { return fecha_hora; }
    public void setFecha_hora(LocalDateTime fecha_hora) { this.fecha_hora = fecha_hora; }
}