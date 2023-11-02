package lab.cherry.nw.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import lab.cherry.nw.model.EmailAuthEntity;


@Service
public interface EmailAuthService {

    EmailAuthEntity updateExpired(String email);
    Optional<EmailAuthEntity> findValidAuthByEmail(String email, String token);
    void ConfirmEmailSend(String email, String token);
    void InviteOrgSend(String orgid, String orgname, String email);
    void ResetPasswordSend(String email, String password);

}