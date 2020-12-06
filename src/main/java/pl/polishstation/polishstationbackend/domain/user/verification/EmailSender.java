package pl.polishstation.polishstationbackend.domain.user.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.File;

@Component
public class EmailSender {
    @Autowired
    private JavaMailSender javaEmailSender;
    @Autowired
    ResourceLoader resourceLoader;

    public void sendMail(String to, String text) throws MessagingException {

        var mimeMessage = javaEmailSender.createMimeMessage();
        var mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setFrom("polishstationv2@wp.pl");
        mimeMessageHelper.setSubject("Verify registration at PolishStation");
        mimeMessageHelper.setText(text, true);
        var path = resourceLoader.getClassLoader().getResource("./static/logo.png");
        mimeMessageHelper.addInline("logoImage", new File(path.getPath()));
        javaEmailSender.send(mimeMessage);
    }

}
