// package lab.cherry.nw.controller;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import lab.cherry.nw.service.EmailService;
// import lombok.RequiredArgsConstructor;

// @RestController
// @RequiredArgsConstructor
// @RequestMapping("/api/v1/user")
// @Tag(name = "Authenticate", description = "Authenticate API Document")
// public class EmailController{

//     private final EmailService emailService;

//     @PostMapping("/mail/send")
//     @Operation(summary = "인증코드 발송", description = "인증코드를 발송합니다.")
//     public String emailSend(@RequestParam String email) throws Exception {

//         String confirm = emailService.sendSimpleMessage(email);

//         return confirm;
//     }

//     @PostMapping("/mail/check")
//     @Operation(summary = "인증코드 확인", description = "인증코드를 확인합니다.")
//     public Boolean emailAuth(@RequestParam String email, String code) {

//         Boolean check = emailService.emailCheck(email,code);
//         return check;
//     }

// }

