package com.enigdam.service;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.enigdam.daoimpl.PlayerDaoImpl;
import com.enigdam.entity.Player;
import com.enigdam.repository.IPlayerRepository;
import net.bytebuddy.utility.RandomString;

@Service
public class EmailService {
	
	
	@Autowired
	IPlayerRepository repo;
	@Autowired
	PlayerDaoImpl daoImpl;



	@Autowired
	private JavaMailSender mailSender;

	public void register(Player player) {
		try {
			
			String siteURL = "http://localhost:8080/game/";

			// Creates a random String of the given length.
			String randomCode = RandomString.make(64);
			player.setVerifyCode(randomCode);
			player.setVerified(false);

			daoImpl.addPlayer(player);

			sendVerificationEmail(player, siteURL);
		} catch (IOException | MessagingException e) {
			e.printStackTrace();
		}
	}

	private void sendVerificationEmail(Player player, String siteURL)
			throws MessagingException, IOException {

		String toAddress = player.getEmail();
		String fromAddress = "enigdam.class.info@gmail.com";
		String senderName = "EnigDam-Class";
		String subject = "Please verify your registration";
		String content = "<div><img alt='logo' width='120' height='150' src='/title.png' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: none; display: block;' title='EnigdamClass'/></div><br><br>"
				+ "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "EnigDam-Class.";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", player.getUsername());
		String verifyURL = siteURL+"verify?code=" + player.getVerifyCode();

		content = content.replace("[[URL]]", verifyURL);
		//content = content.replace("[[image]]", "file:///C:/Users/angel/OneDrive/Escritorio/DAM/CURSO/GitSpringBoot/EnigDam_Server/src/main/resources/title.png");

		helper.setText(content, true);

		mailSender.send(message);

		System.out.println("Email has been sent");
	}

	public boolean verify(String verificationCode) {

		Player player = repo.findByVerificationCode(verificationCode);
			
		if (player == null || player.isVerified()) {
			return false;
		} else {
			player.setVerifyCode(null);
			player.setVerified(true);
			daoImpl.addPlayer(player);
			return true;
		}

	}
}
