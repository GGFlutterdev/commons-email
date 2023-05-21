package com.napoli.servingwebcontent.controller;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class EmailController {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private static boolean isValidEmail(String email) {
		Matcher matcher = EMAIL_PATTERN.matcher(email);
		return matcher.matches();
	}

	@PostMapping("/sendemail") // Definisci il percorso di richiesta per il metodo che invierà l'email
    public ResponseEntity<Map<String, String>> sendEmailButtonPressed(@RequestParam(name="destinatario", required=true) String destinatario, @RequestParam(name="oggetto", required=true) String oggetto, @RequestParam(name="message", required=true) String message, Model model) {
        ResponseEntity<Map<String, String>> result = sendEmail(destinatario, oggetto, message, model); // Invoca il metodo sendEmail() qui
		return result;
    }

    private ResponseEntity<Map<String, String>> sendEmail(String destinatario, String oggetto, String message, Model model) {
		Map<String, String> response = new HashMap<>();
		if (isValidEmail(destinatario)){
			System.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
			SimpleEmail mail = new SimpleEmail();
			try {
				mail.setHostName("smtp.gmail.com");
				mail.setAuthenticator(new DefaultAuthenticator("socialnotes2021@gmail.com", "fxyffsvvabkrvqrj"));
				mail.setStartTLSEnabled(true);
				mail.setSmtpPort(587);
				mail.setFrom("socialnotes2021@gmail.com");
				mail.addTo(destinatario);
				mail.setSubject(oggetto);
				mail.setMsg(message);
				String idMessage = mail.send();
				if (idMessage!=null) {
					response.put("message", "Il tuo messaggio è stato inviato.");
					return ResponseEntity.ok(response);
				}
				else
					response.put("error", "Il tuo messaggio non è stato inviato.");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			} catch (EmailException e) {
				throw new RuntimeException(e);
			}
		}
		else{
			response.put("error", "Mail del destinatario non valida");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
    
}
