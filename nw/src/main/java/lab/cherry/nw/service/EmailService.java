package lab.cherry.nw.service;

import org.springframework.stereotype.Service;


@Service
public interface EmailService {
    String sendSimpleMessage(String to)throws Exception;
    Boolean emailCheck(String email,String code);
}