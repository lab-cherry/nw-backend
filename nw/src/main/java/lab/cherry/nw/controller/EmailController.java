package lab.cherry.nw.controller;

import lab.cherry.nw.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class EmailController{

    private final EmailService emailService;



    @PostMapping("/mail/send")
    public String emailSend(@RequestParam String email) throws Exception {

        String confirm = emailService.sendSimpleMessage(email);

        return confirm;
    }

    @PostMapping("/mail/check")
    public Boolean emailAuth(@RequestParam String email, String code) {

        Boolean check = emailService.emailCheck(email,code);
        return check;
    }

}

