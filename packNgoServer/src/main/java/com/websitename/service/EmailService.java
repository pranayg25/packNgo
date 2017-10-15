package com.websitename.service;

import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;



@Service
public class EmailService {

	@Autowired
	private JavaMailSender	mailSender;

	@Autowired
	private VelocityEngine	velocityEngine;

	@PostConstruct
	public void init() throws Exception{
		// connect to database
		// obtain properties
		try {
			JavaMailSenderImpl ms = (JavaMailSenderImpl) mailSender;
			ms.setHost("smtp.gmail.com");
			ms.setUsername("pranayg25@gmail.com");
			ms.setPassword("");		
			String port = "465";
			int intPort = Integer.parseInt(port);
			ms.setPort(intPort);

			Properties javaMailProperties = new Properties();
			javaMailProperties.setProperty("mail.smtp.timeout", "60000");
			javaMailProperties.setProperty("mail.smtp.connectiontimeout", "60000");
			javaMailProperties.setProperty("mail.smtp.auth", "true");
			javaMailProperties.setProperty("mail.smtp.socketFactory.port", port);
			javaMailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			ms.setJavaMailProperties(javaMailProperties);
		} catch (NumberFormatException e) {
			throw e;
		}
	}


	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@Async
	public void sendEmail(final Map<String, Object> velocityModel, final String bodyTemplate,
			final String subject, final String sendToEmailAddress, final String formEmailAddress,
			final String ccEmailAddress) throws Exception {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

				InternetAddress addressFrom = new InternetAddress(formEmailAddress);

				message.setTo(InternetAddress.parse(sendToEmailAddress));
				if (!StringUtils.isEmpty(ccEmailAddress))
					message.setCc(InternetAddress.parse(ccEmailAddress));
				message.setFrom(addressFrom);
				message.setSubject(subject);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
						bodyTemplate, velocityModel);
				message.setText(text, true);
			}
		};

		this.mailSender.send(preparator);
	}

	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@Async
	public void sendEmail(final Map<String, Object> velocityModel, final String bodyTemplate,
			final String subject, final String sendToEmailAddress, final String formEmailAddress)
					throws Exception {
		sendEmail(velocityModel, bodyTemplate, subject, sendToEmailAddress, formEmailAddress, null);
	}
}
