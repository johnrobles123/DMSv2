package com.silverlake.dms.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.silverlake.dms.model.DeviceListBean;
import com.silverlake.dms.model.ReservationBean;
import com.silverlake.dms.model.User;
import com.silverlake.dms.service.UserService;

public class MailSSL {
	
	public void sendMail(ReservationBean rb, User user, DeviceListBean dl) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("jj","xxx");
						//return new PasswordAuthentication("dms","xxx");
					}
				});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("dms@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(user.getEmail()));
			message.setSubject("Device Monitoring System");
			message.setText("Dear " + rb.getUserName() + "," +
					"\n\n This email is to confirm your reservation using: " +
					"\n\n Device: " + dl.getDeviceName() +
					"\n\n on Date: " + rb.getReserveDate().toString() +
					"\n\n From: " + rb.getTimeFrom().toString() + " To: " + rb.getTimeTo().toString() +
					"\n\n Location: " + rb.getLocation() +
					"\n\n Additional Information: " + rb.getAddInfo() + 
					"\n\n PLEASE DO NOT REPLY TO THIS EMAIL");
	
			Transport.send(message);
	
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
