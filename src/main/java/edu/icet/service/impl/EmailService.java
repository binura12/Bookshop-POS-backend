package edu.icet.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    private final Map<String, OTPData> otpStore = new ConcurrentHashMap<>();
    private static final int OTP_EXPIRY_MINUTES = 5;

    private static class OTPData {
        String otp;
        long expiryTime;

        OTPData(String otp) {
            this.otp = otp;
            this.expiryTime = System.currentTimeMillis() + (OTP_EXPIRY_MINUTES * 60 * 1000);
        }

        boolean isValid() {
            return System.currentTimeMillis() < expiryTime;
        }
    }

    public void sendOTPEmail(String to, String otp) throws MessagingException {
        // Store OTP
        otpStore.put(to, new OTPData(otp));

        // Prepare template context
        Context context = new Context();
        context.setVariable("otp", otp);
        context.setVariable("expiryMinutes", OTP_EXPIRY_MINUTES);

        // Process template
        String emailContent = templateEngine.process("otp-email", context);

        // Send email
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("noreply@mskbookshop.com");
        helper.setTo(to);
        helper.setSubject("MSK BOOKSHOP - Password Reset OTP");
        helper.setText(emailContent, true);

        emailSender.send(message);
    }

    public boolean verifyOTP(String email, String otp) {
        OTPData otpData = otpStore.get(email);
        if (otpData != null && otpData.isValid()) {
            boolean isValid = otpData.otp.equals(otp);
            if (isValid) {
                otpStore.remove(email);
            }
            return isValid;
        }
        return false;
    }

    public String generateOTP() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }
}
