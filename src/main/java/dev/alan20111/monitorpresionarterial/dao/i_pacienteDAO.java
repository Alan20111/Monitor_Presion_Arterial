package dev.alan20111.monitorpresionarterial.dao;

import dev.alan20111.monitorpresionarterial.models.c_paciente;
import java.util.List;
import java.util.Optional;

public interface i_pacienteDAO {
    boolean insert(c_paciente paciente);
    boolean update(c_paciente paciente);
    boolean delete(int id_paciente);

    Optional<c_paciente> findById(int id_paciente);
    List<c_paciente> findAll();
}