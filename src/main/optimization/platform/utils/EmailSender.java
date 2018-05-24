package main.optimization.platform.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Class responsible for mailing feature
 * @author Hugo Alexandre 
 *
 */
public class EmailSender{
	// Admin e-mail: es2.2018.eic2.02@gmail.com
	private String from;
	private String pass;
	private List<String> to;
	private List<String> cc;
 	private String subject;
	private String body;
	private File attachment;
	
	/**
	 * Instantiates an Email Sender
	 * @param from String that indicates sender
	 * @param pass String that indicates sender password
	 * @param to List of Strings that indicates the receivers
	 * @param subject String indicates the subject of the email
	 * @param body String indicates the body of the email
	 */
	public EmailSender(String from, String pass, List<String> to, String subject, String body) {
		this.to = to;
		this.cc = new ArrayList<>();
		setFrom(from);
		setPass(pass);
		setBody(body);
		setSubject(subject);
	}

 /**
  * Sends an email
 * @throws MessagingException 
 * @throws AddressException 
  */
	public void sendFromGMail() throws AddressException, MessagingException {
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

        message.setFrom(new InternetAddress(from));
        InternetAddress[] toAddress = new InternetAddress[to.size()];
        InternetAddress[] ccAddress = new InternetAddress[cc.size()];

        
        // To get the array of addresses
        for( int i = 0; i < to.size(); i++ ) {
            toAddress[i] = new InternetAddress(to.get(i));
        }

        for( int i = 0; i < toAddress.length; i++) {
            message.addRecipient(Message.RecipientType.TO, toAddress[i]);
        }
        
        
        // To get the array of CCs
        for( int i = 0; i < cc.size(); i++ ) {
            ccAddress[i] = new InternetAddress(cc.get(i));
        }
        
        for( int i = 0; i < ccAddress.length; i++) {
            message.addRecipient(Message.RecipientType.CC, ccAddress[i]);
        }
        
        message.setSubject(subject);

        // Adds the body to the message
        Multipart multipart = new MimeMultipart();
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText(body);
        multipart.addBodyPart(textBodyPart);
        
        // Adds the attachment to the message
        if(attachment != null) {
        	MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			try {
				attachmentBodyPart.setDataHandler(new DataHandler(attachment.toURI().toURL()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
            
			attachmentBodyPart.setFileName(attachment.getName());
            multipart.addBodyPart(attachmentBodyPart);	
        }
        message.setContent(multipart);
        
        Transport transport = session.getTransport("smtp");
        transport.connect(host, from, pass);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
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
	
	public void addToCC(String email) {
		cc.add(email);
	}

	public void addAttachment(File attachment) {
		this.attachment = attachment;
	}
}