package com.enigdam.service;

import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.codec.binary.Base64;
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
	// definition of the type of algorithm to be used (AES,DES,RSA)
	private final static String ALG = "AES";
	// definition of the encryption mode to be used
	private final static String CI = "AES/CBC/PKCS5Padding";

	private static String key = "92AE31A79FEEB2A3";
	private static String iv = "0123456789ABCDEF";

	public void register(Player player) {
		try {

			String siteURL = "http://localhost:8080/game/";
			// Creates encrypt password
			String encryptPass = encryptPassword(player.getPassword());
			player.setPassword(encryptPass);
			// Creates a random String of the given length.
			String randomCode = RandomString.make(64);
			player.setVerifyCode(randomCode);
			player.setVerified(false);

			daoImpl.addPlayer(player);

			sendVerificationEmail(player, siteURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String encryptPassword(String password) throws Exception {

		Cipher cipher = Cipher.getInstance(CI);
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), ALG);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
		byte[] encrypted = cipher.doFinal(password.getBytes());
		return new String(Base64.encodeBase64(encrypted));
	}

	private void sendVerificationEmail(Player player, String siteURL) throws MessagingException, IOException {

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

	public String verifySuccess() {
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

	public String verifyFail() {
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
}
