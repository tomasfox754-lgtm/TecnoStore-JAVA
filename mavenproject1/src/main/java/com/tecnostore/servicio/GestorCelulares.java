package com.tecnostore.servicio;

import com.tecnostore.modelo.Celular;
import com.tecnostore.persistencia.CelularDAO;
import java.sql.SQLException;
import java.util.List;

public class GestorCelulares {

    private final CelularDAO celularDAO = new CelularDAO();

    public void registrar(Celular celular) throws SQLException {
        celularDAO.registrar(celular);
    }

    public List<Celular> listar() throws SQLException {
        return celularDAO.listar();
    }
    public boolean actualizar(Celular celular) throws SQLException {
    if (celular.getId() <= 0) {
        throw new IllegalArgumentException(
                "El ID del celular debe ser mayor que cero."
        );
    }

    return celularDAO.actualizar(celular);
}
        public boolean eliminar(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El ID del celular debe ser mayor que cero."
            );
        }

        return celularDAO.eliminar(id);
    }
}