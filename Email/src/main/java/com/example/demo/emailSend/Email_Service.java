package com.example.demo.emailSend;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

@Service
public class Email_Service {

	//이메일 보내기 
	public static void sendMail() {
		// 메일 인코딩
		final String bodyEncoding = "UTF-8"; //콘텐츠 인코딩
		
		String subject = "메일 발송 테스트";
		String fromEmail = "cks@eromnet.com"; // 보낼 이메일 주소
		String fromUsername = "SYSTEM MANAGER";
		String toEmail = "cks@eromnet.com"; // 받을 이메일 주소1,받을 이메일 주소2(콤마(,)로 여러개 나열)
		
		final String username = "cks@eromnet.com"; // 보내는 이메일 계정         
		final String password = "vmffjtm13!"; // 보내는 이메일 비밀번호
		
		// 메일에 출력할 텍스트
		StringBuffer sb = new StringBuffer();
		sb.append("<h3>안녕하세요!!!</h3>\n");
		sb.append("<h4>Eromnet TEST 중입니다.</h4>\n");    
		String html = sb.toString();
		
		// 메일 옵션 설정
		Properties props = new Properties();    
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", "outbound.daouoffice.com");
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");
		
		try {
			// 메일 서버  인증 계정 설정
			Authenticator auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			};
		
			// 메일 세션 생성
			Session session = Session.getInstance(props, auth);
			  
			// 메일 송/수신 옵션 설정
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail, fromUsername));
			message.setRecipients(RecipientType.TO, InternetAddress.parse(toEmail, false));
			message.setSubject(subject);
			message.setSentDate(new Date());
			
			// 메일 콘텐츠 설정
			Multipart mParts = new MimeMultipart();
			MimeBodyPart mTextPart = new MimeBodyPart();
			MimeBodyPart mFilePart = new MimeBodyPart();
			
			FileDataSource fds = new FileDataSource("C:\\eula.1031.txt"); // 파일 경로 설정 
			
			mFilePart.setDataHandler(new DataHandler(fds));
			mFilePart.setFileName(fds.getName());
			
			// 메일 콘텐츠 - 내용 및 파일
			mTextPart.setText(html, bodyEncoding, "html");
			mParts.addBodyPart(mTextPart);
			if(mFilePart != null) {
				mParts.addBodyPart(mFilePart);
			}
			      
			// 메일 콘텐츠 설정
			message.setContent(mParts);
			 
			// 메일 발송
			Transport.send( message );
		  
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
//	// 이메일 받아보기 
//	public static void receiveMail() throws Exception {
//		System.out.println("-- IMAP Emal 가져오기: Start\n\n");
//        String host = "outbound.daouoffice.com"; //imap 호스트 주소. ex) imap.gmail.com
//        String userEmail = "cks@eromnet.com"; //유저 이메일 주소
//        String password = "vmffjtm13!"; //유저 암호
//        mailService.login(host, userEmail, password);
//        int messageCount = mailService.getMessageCount();
//        //테스트 목적이라서 5개 초과이면 5개만 처리: TODO 삭제
//        if (messageCount > 5) {
//             messageCount = 5;
//        }
//        Message[] msgArray = mailService.getMessages(false);
//        for (int i = 0; i < messageCount; i++) {
//             Message msg = msgArray[i];
//             if (msg.getSubject() != null) {
//                  System.out.println(String.format("컨텐츠타임: %s", msg.getContentType()));
//                  System.out.println(String.format("발신자[0]: %s", msg.getFrom()[0]));
//                  System.out.println(String.format("메일제목: %s", msg.getSubject()));
//                  String mailText = mailService.getEmalText(msg.getContent());
//                  System.out.println(String.format("메일내용: %s", mailText));
//             }
//        }
//        mailService.logout(); //로그아웃
//        System.out.println("\n\n-- IMAP Emal 가져오기: 종료");
//	}
//	
//	public static class IMAPMailService {
//	     private Session session;
//	     private Store store;
//	     private Folder folder;
//	     // hardcoding protocol and the folder
//	     // it can be parameterized and enhanced as required
//	     private String protocol = "imaps";
//	     private String file = "INBOX";
//	     public IMAPMailService() {
//	     }
//	     public boolean isLoggedIn() {
//	          return store.isConnected();
//	     }
//	     /**
//	      * 메일 본문 텍스트 내용을 가져옴
//	      *
//	      * @param content
//	      * @return
//	      * @throws Exception
//	      */
//	     public String getEmalText(Object content) throws Exception {
//	          //TODO: 개발 필요
//	          System.out.println("####  컨텐츠 타입에 따라서 text body 또는 멀티파트 처리 기능 구현이 필요");
//	          if (content instanceof Multipart) {
//	               System.out.println("Multipart 이메일임");
//	          } else {
//	               System.out.println(content);
//	          }
//	          return null;
//	     }
//	     /**
//	      * to login to the mail host server
//	      */
//	     public void login(String host, String username, String password) throws Exception {
//	          URLName url = new URLName(protocol, host, 25, file, username, password);
//	          if (session == null) {
//	               Properties props = null;
//	               try {
//	                    props = System.getProperties();
//	               } catch (SecurityException sex) {
//	                    props = new Properties();
//	               }
//	               session = Session.getInstance(props, null);
//	          }
//	          store = session.getStore(url);
//	          store.connect();
//	          folder = store.getFolder("inbox"); //inbox는 받은 메일함을 의미
//	          //folder.open(Folder.READ_WRITE);
//	          folder.open(Folder.READ_ONLY); //읽기 전용
//	     }
//	     /**
//	      * to logout from the mail host server
//	      */
//	     public void logout() throws MessagingException {
//	          folder.close(false);
//	          store.close();
//	          store = null;
//	          session = null;
//	     }
//	     public int getMessageCount() {
//	          //TODO: 안 읽은 메일의 건수만 조회하는 기능 추가
//	          int messageCount = 0;
//	          try {
//	               messageCount = folder.getMessageCount();
//	          } catch (MessagingException me) {
//	               me.printStackTrace();
//	          }
//	          return messageCount;
//	     }
//	     /**
//	      * 이메일 리스트를 가져옴
//	      *
//	      * @param onlyNotRead 안읽은 메일 리스트만 가져올지 여부
//	      * @return
//	      * @throws MessagingException
//	      */
//	     public Message[] getMessages(boolean onlyNotRead) throws MessagingException {
//	          if (onlyNotRead) {
//	               return folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
//	          } else {
//	               return folder.getMessages();
//	          }
//	     }
//	}
}
