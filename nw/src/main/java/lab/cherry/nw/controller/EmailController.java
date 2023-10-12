package lab.cherry.nw.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lab.cherry.nw.service.EmailService;
import lombok.RequiredArgsConstructor;

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

