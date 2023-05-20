package com.napoli.servingwebcontent;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class GreetingController {

	private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	public static boolean isValidEmail(String email) {
		Matcher matcher = EMAIL_PATTERN.matcher(email);
		return matcher.matches();
	}

	@PostMapping("/sendEmail")
	public String greeting(@RequestParam(name="mittente", required=true) String mittente, @RequestParam(name="destinatario", required=true) String destinatario, @RequestParam(name="oggetto", required=true) String oggetto, @RequestParam(name="message", required=true) String message, Model model) {
		System.out.println(mittente+" "+destinatario+" "+oggetto+" "+message);
		if (isValidEmail(mittente)){
			if (isValidEmail(destinatario)){
				HtmlEmail mail = new HtmlEmail();
				try {
					mail.setFrom(mittente);
					ArrayList<InternetAddress> destinatari = new ArrayList<>();
					destinatari.add(new InternetAddress(destinatario));
					mail.setTo(destinatari);
					mail.setSubject(oggetto);
					mail.setMsg(message);
					String idMessage = mail.send();
					if (idMessage!=null) {
						System.out.println("Messaggio inviato!");
						return "Messaggio inviato";
					}
					else
						return "Messaggio non inviato";
				} catch (EmailException e) {
					throw new RuntimeException(e);
				} catch (AddressException e) {
					throw new RuntimeException(e);
				}
			}
			else{
				System.out.println("Mail destinatario non valida!");
				return "Mail destinatario non valida!";
			}
		}
		else {
			System.out.println("Mail mittente non valida!");
			return "Mail mittente non valida!";
		}
	}

}