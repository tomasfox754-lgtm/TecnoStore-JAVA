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
}