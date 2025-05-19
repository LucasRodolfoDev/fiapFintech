package br.com.matheuscarino.fiapfintech.factory;

import br.com.matheuscarino.fiapfintech.dao.ClienteDao;
import br.com.matheuscarino.fiapfintech.dao.ContaDao;
import br.com.matheuscarino.fiapfintech.dao.UsuarioDao;
import br.com.matheuscarino.fiapfintech.dao.impl.OracleClienteDao;
import br.com.matheuscarino.fiapfintech.dao.impl.OracleContaDao;
import br.com.matheuscarino.fiapfintech.dao.impl.OracleUsuarioDao;

public class DaoFactory {

    public static ClienteDao getClienteDao() {
        return new OracleClienteDao();
    }

    public static ContaDao getContaDao() {
        return new OracleContaDao();
    }

    public static UsuarioDao getUsuarioDao() {
        return new OracleUsuarioDao();
    }
}
