package br.com.matheuscarino.fiapfintech.factory;

import br.com.matheuscarino.fiapfintech.dao.ClienteDao;
import br.com.matheuscarino.fiapfintech.dao.impl.OracleClienteDao;

public class DaoFactory {

    public static ClienteDao getClienteDao() {
        return new OracleClienteDao();
    }
}
