package main.optimization.platform.utils;

import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Class responsible for mailing feature
 * @author Hugo Alexandre 
 *
 */
public class EmailSender{

		
	private String from;
	private String pass;
	private ArrayList<String> to;
	private String subject;
	private String body;
	
	/**
	 * Instantiates an Email Sender
	 * @param from String that indicates sender
	 * @param pass String that indicates sender password
	 * @param to List of Strings that indicates the receivers
	 * @param subject String indicates the subject of the email
	 * @param body String indicates the body of the email
	 */
	public EmailSender(String from, String pass, ArrayList<String> to, String subject, String body) {
		
		setFrom(from);
		setPass(pass);
		setBody(body);
		setSubject(subject);
		this.to = to;
	}

 /**
  * Sends an email
  */
	public void sendFromGMail() {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.size()];

            // To get the array of addresses
            for( int i = 0; i < to.size(); i++ ) {
                toAddress[i] = new InternetAddress(to.get(i));
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
            
            System.out.println("sended");
            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
	
	/** Gets the sender큦 email.
	 * @return  String representing the sender큦 email.
	*/
    public String getFrom() {
		return from;
	}

    /** Sets the sender큦 email.
	 * @param from String containing the sender큦 email.
	*/
	public void setFrom(String from) {
		this.from = from;
	}

	/** Gets the sender큦 password.
	 * @return  String representing the sender큦 password.
	*/
	public String getPass() {
		return pass;
	}

	/** Sets the sender큦 password.
	 * @param pass String containing the sender큦 password.
	*/
	public void setPass(String pass) {
		this.pass = pass;
	}

	/** Gets message큦 subject.
	 * @return  String representing the message큦 subject.
	*/
	public String getSubject() {
		return subject;
	}

	/** Sets the message큦 subject.
	 * @param subject String containing the message큦 subject.
	*/
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/** Gets message큦 body.
	 * @return  String representing the message큦 body.
	*/
	public String getBody() {
		return body;
	}

	/** Sets the message큦 body.
	 * @param body String containing the message큦 body.
	*/
	public void setBody(String body) {
		this.body = body;
	}
}