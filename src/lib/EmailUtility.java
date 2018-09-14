package lib;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtility {

	public static void SendEmail(String sub, String msg) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("abc@gmail.com", "xyz"));
		email.setSSLOnConnect(true);
		email.setFrom("abc@gmail.com");
		email.setSubject(sub);
		email.setMsg(msg);
		email.addTo("abc@gmail.com");
		email.send();
		System.out.println("Email successfully sent...!!!");
	}
}
