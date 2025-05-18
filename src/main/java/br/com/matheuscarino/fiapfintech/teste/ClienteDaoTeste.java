package br.com.matheuscarino.fiapfintech.teste;

import br.com.matheuscarino.fiapfintech.dao.ClienteDao;
import br.com.matheuscarino.fiapfintech.factory.DaoFactory;
import br.com.matheuscarino.fiapfintech.model.Cliente;
import java.time.LocalDate;
import java.util.List;

import br.com.matheuscarino.fiapfintech.exception.DBException;

public class ClienteDaoTeste {

    public static void main(String[] args) {
        // Cadastrar cliente
        ClienteDao dao = DaoFactory.getClienteDao();
        Cliente cliente = new Cliente(
            "João da Silva",
            "12345678900",
            "joao@gmail.com",
            "11999999999",
            "Rua das Flores, 123",
            LocalDate.of(1990, 1, 1),
            true
        );
        // try {
        //     dao.cadastrar(cliente);
        // } catch (DBException e) {
        //     throw new RuntimeException(e);
        // }

        // Buscar cliente por ID e atualizá-lo (utilize o mesmo cliente cadastrado acima)
//        try {
//            cliente = dao.buscar(1);
//        } catch (DBException e) {
//            throw new RuntimeException(e);
//        }
//        cliente.setNome("Alan Donavan");
//        try {
//            dao.atualizar(cliente);
//        } catch (DBException e) {
//            throw new RuntimeException(e);
//        }

        // Listar todos os clientes

//        try {
//            List <Cliente> lista = dao.listar();
//            for (Cliente c : lista) {
//                System.out.println(c.getNome() + " - " + c.getCpf());
//            }
//        } catch (DBException e) {
//            throw new RuntimeException(e);
//        }

        // Remover cliente

        try {
            dao.remover(21);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
    }
}
