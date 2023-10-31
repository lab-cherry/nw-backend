package lab.cherry.nw.service.Impl;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.EmailAuthEntity;
import lab.cherry.nw.repository.EmailAuthRepository;
import lab.cherry.nw.service.EmailAuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
// @AllArgsConstructor
@RequiredArgsConstructor
@Service
public class EmailAuthServiceImpl implements EmailAuthService{
    
    @Value("${frontend.host}")
    private String FRONTEND_ADDR;

    private final JavaMailSender  javaMmailSender;
    private final EmailAuthRepository emailAuthRepository;

    private static final String FROM_ADDRESS = "no-reply@nangmanwedding.com";

    public Optional<EmailAuthEntity> findValidAuthByEmail(String email, String token) {

        LocalDateTime currentTime = LocalDateTime.now();
        EmailAuthEntity emailAuthEntity = emailAuthRepository.findByEmail(email).get();

        if(email == emailAuthEntity.getEmail() || token == emailAuthEntity.getToken() || emailAuthEntity.getExpired().isAfter(currentTime) ) { return Optional.ofNullable(emailAuthEntity);
        } else {
            return null;
        }
    }

    /**
     * [UserServiceImpl] 이메일 인증 부분 수정 함수
     *
     * @param email 조회할 사용자의 고유번호입니다.
     * @throws EntityNotFoundException 사용자 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 특정 사용자에 대해 이메일 인증 정보를 수정합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public EmailAuthEntity updateExpired(String id) {

        EmailAuthEntity emailAuthEntity = emailAuthRepository.findById(id).get();
        emailAuthEntity.updateExpireDate();
        emailAuthEntity.updateToken();

        return emailAuthRepository.save(emailAuthEntity);
    }

    public void ConfirmEmailSend(String email, String token) {

        MimeMessage message = javaMmailSender.createMimeMessage();
        String _link = FRONTEND_ADDR + "/auth/confirm?email=" + email + "&token=" + token;
        // "/api/auth/confirm?email=" + email + "&token=" + token;

        try {
            message.addRecipients(MimeMessage.RecipientType.TO, email);
            message.setFrom(EmailAuthServiceImpl.FROM_ADDRESS);
            message.setSubject("[낭만웨딩] 이메일 인증 요청 메일입니다."); //제목
            String text="";
            text+= "<div style='margin:100px;'>";
            text+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
            text+= "<h3 style='color:blue;'>다음 링크를 눌러 인증을 완료하세요.</h3>";
            text+= "<div style='font-size:130%'>";
            text+= "LINK : <strong>";
            text+= "<a href=" + "'" + _link + "'>인증하기</a>" + "</strong><di`v><br/> ";
            text+= "</di>";
            message.setText(text, "utf-8", "html");

            javaMmailSender.send(message);

        } catch (MessagingException e) {
            log.error("email Error {}", e);
        }

    }

    // public void InviteOrgSend(String orgname, String email, String token) {

    //     MimeMessage message = javaMmailSender.createMimeMessage();
    //     String _link = FRONTEND_ADDR + "/auth/register/confirm?email" + email + "&token=" + token;
    //     // "/api/auth/register/confirm?email" + email + "&token=" + token;

    //     try {
    //         message.addRecipients(MimeMessage.RecipientType.TO, email);
    //         message.setFrom(EmailAuthServiceImpl.FROM_ADDRESS);
    //         message.setSubject("[낭만웨딩] " + orgname + " 회원가입 요청 메일입니다."); //제목
    //         String text="";
    //         text+= "<div style='margin:100px;'>";
    //         text+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
    //         text+= "<h3 style='color:blue;'>다음 링크를 눌러 회원가입을 진행하세요.</h3>";
    //         text+= "<p>회원이시라면 링크를 누르면 [" + orgname + "]에 소속됩니다.</p>";
    //         text+= "<div style='font-size:130%'>";
    //         text+= "LINK : <strong>";
    //         text+= "<a href=" + "'" + _link + "'>진행하기</a>" + "</strong><div><br/> ";
    //         text+= "</div>";
    //         message.setText(text, "utf-8", "html");

    //         javaMmailSender.send(message);


    //     } catch (MessagingException e) {
    //         log.error("email Error {}", e);
    //     }
    // }
    

    public void ResetPasswordSend(String email, String password) {

        MimeMessage message = javaMmailSender.createMimeMessage();

        try {
            message.addRecipients(MimeMessage.RecipientType.TO, email);
            message.setFrom(EmailAuthServiceImpl.FROM_ADDRESS);
            message.setSubject("[낭만웨딩] 비밀번호 초기화 메일입니다."); //제목
            String text="";
            text+= "<div style='margin:100px;'>";
            text+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
            text+= "<h3 style='color:blue;'>다음 비밀번호로 초기화되었습니다.</h3>";
            text+= "<p>로그인 하신 후, 비밀번호를 변경해주세요.</p>";
            text+= "<div style='font-size:130%'>";
            text+= "NEW PASSWORD : <strong>";
            text+= password + "</strong><div><br/> ";
            text+= "</div>";
            message.setText(text, "utf-8", "html");

            javaMmailSender.send(message);

        } catch (MessagingException e) {
            log.error("email Error {}", e);
        }
    }
    

    public void InviteUserSend(String orgid, String orgname, String email) {

        MimeMessage message = javaMmailSender.createMimeMessage();
        String _link = FRONTEND_ADDR + "/auth/register/org/" + orgid;

        try {
            message.addRecipients(MimeMessage.RecipientType.TO, email);
            message.setFrom(EmailAuthServiceImpl.FROM_ADDRESS);
            message.setSubject("[낭만웨딩] " + orgname + " 초대 메일입니다."); //제목
            String text="";
            text+= "<div style='margin:100px;'>";
            text+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
            text+= "<h3 style='color:blue;'>다음 링크를 눌러 회원가입을 진행하세요.</h3>";
            text+= "<p>회원이시라면 링크를 누르면 [" + orgname + "]에 소속됩니다.</p>";
            text+= "<div style='font-size:130%'>";
            text+= "LINK : <strong>";
            text+= "<a href=" + "'" + _link + "'>진행하기</a>" + "</strong><div><br/> ";
            text+= "</div>";
            message.setText(text, "utf-8", "html");

            javaMmailSender.send(message);


        } catch (MessagingException e) {
            log.error("email Error {}", e);
        }
        
    }
}