package br.com.matheuscarino.fiapfintech.teste;

import br.com.matheuscarino.fiapfintech.dao.ClienteDao;
import br.com.matheuscarino.fiapfintech.factory.DaoFactory;
import br.com.matheuscarino.fiapfintech.model.Cliente;
import java.time.LocalDate;
import java.util.List;

import br.com.matheuscarino.fiapfintech.exception.DBException;

public class ClienteDaoTeste {

    public static void main(String[] args) {
        System.out.println("Iniciando teste de conexão com o banco de dados...");

        // Obter o DAO do cliente (isso já testa a conexão indiretamente)
        ClienteDao dao = DaoFactory.getClienteDao();
        System.out.println("DAO do cliente obtido com sucesso.");

        // Teste de listagem (melhor teste para verificar a conexão)
        System.out.println("Tentando listar todos os clientes...");
        try {
            List<Cliente> lista = dao.listar();
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            System.out.println("Total de clientes encontrados: " + lista.size());

            // Exibir alguns clientes se existirem
            if (!lista.isEmpty()) {
                System.out.println("Primeiros clientes na lista:");
                int count = 0;
                for (Cliente c : lista) {
                    System.out.println(c.getNome() + " - " + c.getCpf());
                    count++;
                    if (count >= 3) break; // Mostrar apenas os 3 primeiros
                }
            }
        } catch (DBException e) {
            System.out.println("ERRO ao conectar com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
