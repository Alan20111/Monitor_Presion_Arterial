package dev.alan20111.monitorpresionarterial.dao;

import dev.alan20111.monitorpresionarterial.models.c_medicion;
import java.util.List;

public interface i_medicionDAO {
    boolean insert(c_medicion medicion);
    boolean update(c_medicion medicion);
    boolean delete(int id_medicion);

    List<c_medicion> findByPacienteId(int id_paciente);
    List<c_medicion> findAll();
}