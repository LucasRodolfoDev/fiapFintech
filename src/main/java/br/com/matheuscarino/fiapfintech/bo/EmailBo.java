package br.com.matheuscarino.fiapfintech.bo;

import br.com.matheuscarino.fiapfintech.exception.EmailException;
import jakarta.mail.Authenticator;
import jakarta.mail.Session;
import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Transport;
import io.github.cdimascio.dotenv.Dotenv;

public class EmailBo {

    private static final Dotenv dotenv = Dotenv.configure()
            .directory("/Users/carino/IdeaProjects/fiapFintech")
            .load();

    private static final String EMAIL_FROM = dotenv.get("EMAIL_FROM");
    private static final String APP_PASSWORD = dotenv.get("APP_PASSWORD");

    public void enviarEmail(String destinatario, String assunto, String mensagem) throws EmailException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(assunto);
            message.setText(mensagem);

            Transport.send(message);
        } catch (Exception e) {
            throw new EmailException("Erro ao enviar email", e);
        }
    }
}
