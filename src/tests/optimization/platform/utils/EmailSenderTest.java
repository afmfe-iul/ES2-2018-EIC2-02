package tests.optimization.platform.utils;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.junit.Test;
import main.optimization.platform.utils.EmailSender;

public class EmailSenderTest {

	EmailSender instance;

	String from = "from";
	String pass = "pass";
	ArrayList<String> to = new ArrayList<String>();
	String subject = "subject";
	String body = "body";

	
	@Test
	public final void testEmailSender() {
		to.add("test@test.com");
		instance = new EmailSender(from, pass, to, subject, body);
	}

	@Test
	public final void testGetterAndSetterFrom() {
		instance = new EmailSender(from, pass, to, subject, body);
		from = "test";
		instance.setFrom(from);
		assertEquals("The getter for the field 'from' is not returning the same value that was setted.", instance.getFrom(), from);
	}

	@Test
	public final void testSendFromGMail() {
		instance = new EmailSender(from, pass, to, subject, body);
		instance.setFrom("es2.2018.eic2.02@gmail.com");
		instance.setPass("123PASSWORD");
		to.add("tmrfo1@gmail.com");
		instance.setSubject("subject");
		instance.setBody("testJUnit");
		try {
			instance.sendFromGMail();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public final void testGetterAndSetterPass() {
		instance = new EmailSender(from, pass, to, subject, body);
		pass = "test";
		instance.setPass(pass);
		assertEquals("The getter for the field 'pass' is not returning the same value that was setted.", instance.getPass(), pass);
	}

	@Test
	public final void testGetterAndSetterSubject() {
		instance = new EmailSender(from, pass, to, subject, body);
		subject = "test";
		instance.setSubject(subject);
		assertEquals("The getter for the field 'subject' is not returning the same value that was setted.", instance.getSubject(), subject);
	}

	@Test
	public final void testGetterAndSetterBody() {
		instance = new EmailSender(from, pass, to, subject, body);
		body = "test";
		instance.setBody(body);
		assertEquals("The getter for the field 'body' is not returning the same value that was setted.", instance.getBody(), body);
		
	}

}
