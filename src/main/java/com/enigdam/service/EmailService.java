package com.enigdam.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.enigdam.entity.Player;
import com.enigdam.repository.IPlayerRepository;
import com.enigdam.settings.Settings;

import net.bytebuddy.utility.RandomString;

@Service
public class EmailService {
	//TODO CREAR EL RECUPERAR PASSWORD AL USUARIO

	@Autowired
	IPlayerRepository repo;
	@Autowired
	private JavaMailSender mailSender;


	public boolean register(Player player) 
	{
		boolean insert;
		try {
			
			// Creates a random String of the given length.
			String randomCode = RandomString.make(64);
			player.setVerifyCode(randomCode);
			player.setVerified(false);

			repo.save(player);
			repo.flush();

			sendVerificationEmail(player, Settings.URL);
			insert = true;	
		} catch (Exception e) {
			e.printStackTrace();
			insert = false;
		}
		return insert;
	}


	private void sendVerificationEmail(Player player, String siteURL) throws MessagingException, IOException 
	{
		String toAddress = player.getEmail();
		String fromAddress = "enigdam.class.info@gmail.com";
		String senderName = "EnigDam-Class";
		String subject = "Please verify your registration";
		String content = "<div><img alt='logo' width='500' height='300' src='https://i.ibb.co/3WDhPZv/tittle.png' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: none; display: block;' title='EnigdamClass'/></div><br><br>"
				+ "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "EnigDam-Class.";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", player.getUsername());
		String verifyURL = siteURL + "verify?code=" + player.getVerifyCode();
		content = content.replace("[[URL]]", verifyURL);

		helper.setText(content, true);

		mailSender.send(message);

		System.out.println("se ha enviado email de verificación");
	}

	public boolean verify(String verificationCode) 
	{
		Player player = repo.findByVerificationCode(verificationCode);

		if (player == null || player.isVerified()) {
			return false;
		} else {
			player.setVerifyCode(null);
			player.setVerified(true);
			repo.save(player);
			repo.flush();
			return true;
		}

	}

	public String verifySuccess()
	{
		String content = " <!DOCTYPE html>"
				+ "<head>"
				+ "<meta charset='ISO-8859-1'>"
				+ "<title>User Verification</title>"
				+ "<link rel='stylesheet' type='text/css' href='webjars/bootstrap/css/bootstrap.min.css' />"
				+ "</head>"
				+ "<body>"
				+"<div class='container text-center'><img alt='logo' width='500' height='300' src='https://i.ibb.co/3WDhPZv/tittle.png' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: none; display: block;' title='EnigdamClass'/></div><br>"
				+ "<div class='container text-center'>"
				+ "<h3>Congratulations, your account has been verified.</h3>"
				+ "</div>"
				+ "</body>"
				+ "</html>";
		return content;
	}

	public String verifyFail()
	{
		String content = "<!DOCTYPE html><head><meta charset='ISO-8859-1'>"
				+ "<title>User Verification</title>"
				+ "<link rel='stylesheet' type='text/css' href='/webjars/bootstrap/css/bootstrap.min.css' />"
				+ "</head>"
				+ "<body>"
				+"<div class='container text-center'><img alt='logo' width='500' height='300' src='https://i.ibb.co/3WDhPZv/tittle.png' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: none; display: block;' title='EnigdamClass'/></div><br>"
				+ "<div class='container text-center'>"
				+ "<h3>Sorry, we could not verify account. It maybe already verified, or verification code is incorrect.</h3>"
				+ "</div>"
				+ "</body>"
				+ "</html>";

		return content;
	}

	public void forgetPassword(Player player) {
		
		String password;
		try {
			password = decrypt(player.getPassword().trim());
			sendEmailPassword(password,player, Settings.URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
    private void sendEmailPassword(String password, Player player, String siteURL) throws Exception {
		
    	String toAddress = player.getEmail();
		String fromAddress = "enigdam.class.info@gmail.com";
		String senderName = "EnigDam-Class";
		String subject = "Password";
		String content = "<div><img alt='logo' width='500' height='300' src='https://i.ibb.co/3WDhPZv/tittle.png' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: none; display: block;' title='EnigdamClass'/></div><br><br>"
				+ "Dear [[name]],<br>" + "Your password is:<br>"
				+ "<h3> [[pass]] </h3>" + "Thank you,<br>" + "EnigDam-Class.";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", player.getName());
		content = content.replace("[[pass]]", password);

		helper.setText(content, true);

		mailSender.send(message);

		System.out.println("se ha enviado email con el password");
    	
	}


    private String decrypt(String password) throws Exception {

        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(Settings.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encrypt = Base64.getDecoder().decode(password);
        byte[] decrypted = cipher.doFinal(encrypt);
        return new String(decrypted, StandardCharsets.UTF_8);
    }


    private Key generateKey() {
        return new SecretKeySpec(Settings.ENCRYPT_KEY.getBytes(), Settings.ALGORITHM);
    }
}
