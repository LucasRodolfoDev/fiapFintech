package br.com.matheuscarino.fiapfintech.teste;

import br.com.matheuscarino.fiapfintech.bo.EmailBo;
import br.com.matheuscarino.fiapfintech.exception.EmailException;

public class EmailTeste {
    // cria um teste de envio de emails utilizando o EmailBo
    // o teste deve ser executado com o servidor de email configurado
    public static void main(String[] args) {
        EmailBo emailBo = new EmailBo();
        try {
            emailBo.enviarEmail("lrodolfo30@gmail.com", "Teste", "Teste");
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }

}
