package project.emsbackend.Event;

import org.springframework.context.ApplicationEvent;
import project.emsbackend.Model.User;

public class RegistrationCompleteEvent extends ApplicationEvent {
    private final User user;
    public RegistrationCompleteEvent(User user) {
        super(user);
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
