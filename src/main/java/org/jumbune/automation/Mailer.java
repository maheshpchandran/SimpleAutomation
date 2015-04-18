/*
 * 
 */
package org.jumbune.automation;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class Mailer.
 */
public class Mailer {
	
	private static Logger mailerLogger = Logger.getLogger(Mailer.class);

	/**
	 * Mailme.
	 * 
	 * @param mailto
	 *            the mailto
	 * @param sDocument
	 *            the s document
	 */
	public static void mailme(String[] mailto, String sDocument) {

		final String username = "<FILL>@gmail.com";
		final String password = "impetus123";
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		for (String sAddress : mailto) {
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});

			try {
				Multipart multiPart = new MimeMultipart("alternative");

				Message message = new MimeMessage(session);
				MimeBodyPart htmlPart = new MimeBodyPart();
				MimeBodyPart textPart = new MimeBodyPart();
				message.setFrom(new InternetAddress("sandstormqa@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(sAddress));
				message.setSubject("SandStorm Automation status ");
				htmlPart.setContent(sDocument, "text/html");

				
				
				multiPart.addBodyPart(htmlPart);
				message.setContent(multiPart);
				Transport.send(message);
				System.out.println("Done:" + sAddress);

			} catch (MessagingException e) {
				mailerLogger.error(e);
			}
		}
	}

}
