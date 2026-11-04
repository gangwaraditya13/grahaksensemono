package com.adish.grahaksensemono.user.service.Impl;

import com.adish.grahaksensemono.user.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendOtpEmail(String toEmail, String otp, String userName) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Reset your Grahak Sense password");

            String htmlMsg = "<div style='font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif; max-width: 600px; margin: 0 auto; border: 1px solid #eaeaea; padding: 30px; border-radius: 12px; background-color: #ffffff;'>"
                    + "<div style='text-align: center; margin-bottom: 25px;'>"
                    + "  <h1 style='color: #ff6f00; margin: 0; font-size: 30px; font-weight: 700; letter-spacing: 1px;'>Grahak Sense</h1>"
                    + "  <p style='margin: 8px 0 0; color: #666; font-size: 15px;'>Shop smart. Shop easy.</p>"
                    + "  <div style='height: 2px; background: linear-gradient(to right, transparent, #ff6f00, transparent); margin-top: 15px;'></div>"
                    + "</div>"

                    + "<p style='font-size: 16px; color: #444; line-height: 1.6;'>Hello " + userName + ",</p>"

                    + "<p style='font-size: 16px; color: #444; line-height: 1.6;'>We received a request to reset the password for your <strong>Grahak Sense</strong> account.</p>"

                    + "<p style='font-size: 16px; color: #444; line-height: 1.6;'>Use the following One-Time Password (OTP) to set a new password:</p>"

                    + "<div style='text-align: center; margin: 35px 0;'>"
                    + "  <span style='font-size: 36px; font-weight: 700; letter-spacing: 8px; color: #333; background-color: #fff8f0; padding: 20px 40px; border-radius: 8px; border: 2px solid #ff6f00; display: inline-block; box-shadow: 0 4px 8px rgba(255,111,0,0.15);'>"
                    + otp
                    + "</span>"
                    + "</div>"

                    + "<p style='font-size: 15px; color: #666; line-height: 1.6;'>"
                    + "This OTP is valid for <strong>5 minutes</strong>. "
                    + "For your security, never share this code with anyone. Grahak Sense will never ask for your OTP over a call, message or email."
                    + "</p>"

                    + "<p style='font-size: 15px; color: #666; line-height: 1.6;'>"
                    + "If you didn't request a password reset, you can safely ignore this email. "
                    + "Your account and saved orders will remain secure."
                    + "</p>"

                    + "<hr style='border: none; border-top: 1px solid #eee; margin: 30px 0;' />"

                    + "<p style='font-size: 13px; color: #999; text-align: center; line-height: 1.5;'>"
                    + "This is an automated email from Grahak Sense. Please do not reply to this message."
                    + "</p>"

                    + "<p style='font-size: 13px; color: #aaa; text-align: center; margin-top: 20px;'>"
                    + "&copy; " + Year.now().getValue() + " Grahak Sense. All rights reserved."
                    + "</p>"
                    + "</div>";

            String plainTextMsg =
                    "Hello " + userName + ",\n\n"
                            + "We received a request to reset the password for your Grahak Sense account.\n\n"
                            + "Your One-Time Password (OTP) is: " + otp + "\n\n"
                            + "This OTP is valid for 5 minutes.\n"
                            + "Do not share this code with anyone. Grahak Sense will never ask for your OTP.\n\n"
                            + "If you didn't request a password reset, you can safely ignore this email.\n\n"
                            + "Regards,\n"
                            + "Team Grahak Sense";

            helper.setText(plainTextMsg, htmlMsg);
            javaMailSender.send(message);

// helper.setReplyTo("");


        }catch (MessagingException e) {
            throw new RuntimeException("Failed to send professional OTP email", e);
        }
    }
}
