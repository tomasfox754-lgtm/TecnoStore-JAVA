package com.tecnostore.servicio;

import com.tecnostore.modelo.Cliente;
import com.tecnostore.persistencia.ClienteDAO;
import java.sql.SQLException;
import java.util.List;

public class GestorClientes {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    public void registrar(Cliente cliente) throws SQLException {
        try {
            clienteDAO.registrar(cliente);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new IllegalArgumentException(
                        "Ya existe un cliente con esa identificacion.",
                        e
                );
            }

            throw e;
        }
    }
    public List<Cliente> listar() throws SQLException {
    return clienteDAO.listar();
}
}