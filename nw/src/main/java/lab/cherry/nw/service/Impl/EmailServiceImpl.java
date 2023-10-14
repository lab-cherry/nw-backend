package lab.cherry.nw.service.Impl;

import jakarta.mail.internet.MimeMessage;
import lab.cherry.nw.service.EmailService;
import lab.cherry.nw.util.RedisUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;


@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    JavaMailSender emailSender;
    private final RedisUtil redisUtil;

    private static final String FROM_ADDRESS = "haerin2222@naver.com";
    public static final String authkey = createKey();


    private MimeMessage createMessage(String to)throws Exception{

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setFrom(EmailServiceImpl.FROM_ADDRESS);
        message.setSubject("낭만웨딩 회원가입을 위해 이메일 인증을 해주세요.");//제목

        message.setText("인증번호를 확인해주세요 : " + authkey);
//      message.setFrom(new InternetAddress("properties에 입력한 이메일","haeri"));//보내는 사람


        return message;
    }


    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }

    @Override
    public String sendSimpleMessage(String to)throws Exception {
        // TODO Auto-generated method stub
        MimeMessage message = createMessage(to);

        redisUtil.setDataExpire(authkey,to,60 * 5L);
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }

        return authkey;
    }

    @Override
    public Boolean emailCheck(String email,String code) {

        boolean result = false;

        System.out.println("redis에 저장 된 authkey : " + redisUtil.getData(email));

        if (redisUtil.getData(email).equals(code)) {
            return true;
        }

        return  result;
    }
}