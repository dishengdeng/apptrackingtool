package portal.service.Impl;

import java.util.Date;

import javax.mail.Message;

import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import portal.service.EmailService;
@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private Session mailSession;



	@Override
	public void sendEmail(String to, String subject, String text,String from) throws Exception {



			Message message = new MimeMessage(mailSession);
			

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			message.setFrom(new InternetAddress(from));

			message.setSubject(subject);

			message.setSentDate(new Date());
			
			message.setText(text);
			
			Transport.send(message);



	}

}
