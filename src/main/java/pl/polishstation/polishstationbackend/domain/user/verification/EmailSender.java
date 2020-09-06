package pl.polishstation.polishstationbackend.domain.user.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class EmailSender {
    @Autowired
    private JavaMailSender javaEmailSender;

    public void sendMail(String to, String text) throws MessagingException {
        var mimeMessage = javaEmailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setFrom("witach98@gmail.com");
        mimeMessageHelper.setSubject("Verify registration at PolishStation");
        mimeMessageHelper.setText(text, true);
        javaEmailSender.send(mimeMessage);
    }

}
