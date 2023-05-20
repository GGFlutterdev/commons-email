package com.napoli.servingwebcontent;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public ResponseEntity<String> greeting(@RequestParam(name="mittente", required=true) String mittente, @RequestParam(name="destinatario", required=true) String destinatario, @RequestParam(name="oggetto", required=true) String oggetto, @RequestParam(name="message", required=true) String message, Model model) {
		System.out.println(mittente+" "+destinatario+" "+oggetto+" "+message);
		if (isValidEmail(mittente)){
			if (isValidEmail(destinatario)){
				SimpleEmail mail = new SimpleEmail();
				try {
					mail.setHostName("smtp.gmail.com");
					mail.setAuthenticator(new DefaultAuthenticator("socialnotes2021@gmail.com", "fxyffsvvabkrvqrj"));
					mail.setStartTLSEnabled(true);
					mail.setSmtpPort(587);
					mail.setFrom(mittente);
					mail.addTo(destinatario);
					mail.setSubject(oggetto);
					mail.setMsg(message);
					String idMessage = mail.send();
					if (idMessage!=null) {
						return ResponseEntity.ok("Messaggio inviato!");
					}
					else
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Messaggio non inviato!");
				} catch (EmailException e) {
					throw new RuntimeException(e);
				}
			}
			else{
				//System.out.println("Mail destinatario non valida!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mail destinatario non valida!");
			}
		}
		else {
			//System.out.println("Mail mittente non valida!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mail mittente non valida!");
		}
	}

}