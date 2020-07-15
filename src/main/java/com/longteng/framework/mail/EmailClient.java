package com.longteng.framework.mail;

import java.io.*;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class EmailClient {

	private Session session;
	private Transport transport;

	private String mailHost = "";
	private String sender_username = "";
	private String sender_password = "";
	private String recv_list = "";
	private String nick_name = "";

	private Properties properties = new Properties();

	/*
	 * 初始化方法
	 */
	public EmailClient() {

		this.mailHost = "smtp.126.com";
		this.sender_username = "suzhe07@126.com";
		this.sender_password = "asdfqwer1234";
		this.nick_name = "测试中心";
		session = Session.getInstance(properties);
		session.setDebug(false);// 开启后有调试信息
	}
	// }mail.smtp.host=smtp.nuoyuan.com.cn
	// mail.sender.username=sso@nuoyuan.com.cn
	// mail.sender.password=Sys@)!^2016
	//
	// mail.sender.nickname=IT中心

	public EmailClient(Properties properties, boolean debug) {
		this.properties = properties;
		this.mailHost = properties.getProperty("mail.smtp.host");
		this.sender_username = properties.getProperty("mail.sender.username");
		this.sender_password = properties.getProperty("mail.sender.password");

		this.nick_name = properties.getProperty("mail.sender.nickname");

		session = Session.getInstance(properties);
		session.setDebug(false);// 开启后有调试信息

	}

	/**
	 * 发送邮件
	 * 
	 * @param subject
	 *            邮件主题
	 * @param sendHtml
	 *            邮件内容
	 * @param receiveUser
	 *            收件人地址
	 */
	public int doSendHtmlEmail(String subject, String sendHtml, String receiveUser) {
		try {
			MimeMessage message = new MimeMessage(session);
			// 发件人
			InternetAddress from = new InternetAddress(
					MimeUtility.encodeWord(this.nick_name) + " <" + this.sender_username + ">");
			message.setFrom(from);

			// 收件人
			InternetAddress to = new InternetAddress(receiveUser);
			message.setRecipient(Message.RecipientType.TO, to);// 还可以有CC、BCC

			// 邮件主题
			message.setSubject(subject);

			String content = sendHtml.toString();
			// 邮件内容,也可以使纯文本"text/plain"
			message.setContent(content, "text/html;charset=UTF-8");

			// 保存邮件
			message.saveChanges();

			transport = session.getTransport("smtp");
			session.getProperties().setProperty("mail.smtp.auth", "true");
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			transport.connect(mailHost, sender_username, sender_password);
			// 发送
			transport.sendMessage(message, message.getAllRecipients());

		} catch (Exception e) {
			e.printStackTrace();

			return -1;
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}

		return 0;
	}

	/**
	 * 发送带附件邮件
	 * 
	 * @param subject
	 *            邮件主题
	 * @param sendHtml
	 *            邮件内容
	 * @param receiveUser
	 *            收件人地址
	 */
	public int doSendAffixEmail(String subject, String sendHtml, String receiveUser, String affix) {
		try {
			MimeMessage message = new MimeMessage(session);
			// 加载发件人地址
			InternetAddress from = new InternetAddress(MimeUtility.encodeWord("系统邮件") + " <" + sender_username + ">");
			message.setFrom(from);

			// 加载收件人地址
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiveUser));
			// 加载标题
			message.setSubject(subject);

			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(sendHtml.toString());
			multipart.addBodyPart(contentPart);

			// 添加附件
			BodyPart messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(affix);

			// 添加附件的内容
			messageBodyPart.setDataHandler(new DataHandler(source));
			// 添加附件的标题
			messageBodyPart.setFileName(MimeUtility.encodeWord(source.getName()));
			multipart.addBodyPart(messageBodyPart);

			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			// 连接服务器的邮箱
			// transport.connect(host, user, pwd);

			transport.connect(mailHost, sender_username, sender_password);
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

			System.out.println("send mail sucess");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("send mail fail");
			return -1;
		}

		return 0;
	}

	public static void main(String[] args) {
		EmailClient test = new EmailClient();
		test.doSendHtmlEmail("testsubj", "XXXXXX", "56562370@qq.com");
		test.doSendAffixEmail("附件", "XXXXXX", "56562370@qq.com", "report\\\\report.html");
		System.out.println("ssssssssssssssssssssssssss");

	}
}
