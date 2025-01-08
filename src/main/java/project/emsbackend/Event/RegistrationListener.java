package project.emsbackend.Event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import project.emsbackend.Model.User;
import project.emsbackend.Service.EmailService;
import project.emsbackend.Service.VerificationTokenService;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    public RegistrationListener(VerificationTokenService verificationTokenService, EmailService emailService) {
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
    }

    public void confirmRegistration(RegistrationCompleteEvent event){
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        verificationTokenService.createVerificationToken(user, token);

        String address = user.getEmail();
        String subject = "Email Verification";
        String confirmatioURL = "https://emsbackend-h3xs.onrender.com/verify-email?token=" + token;
        String message = "Click the link to verify your email. Best Regards!" + confirmatioURL;

        emailService.sendEmail(address, subject, message);
    }
}
