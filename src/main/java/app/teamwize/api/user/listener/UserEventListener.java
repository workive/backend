package app.teamwize.api.user.listener;

import app.teamwize.api.user.domain.event.UserInvitedEvent;
import app.teamwize.api.user.domain.event.UserPasswordResetEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventListener {



//    @Value("${cloud.aws.sqs.new-user-invited-queue}")
//    private String userInvitedQueue;
//    @Value("${cloud.aws.sqs.user-password-reset-queue}")
//    private String userPasswordResetQueue;

    @EventListener
    public void handleUserInvitedEvent(UserInvitedEvent event) {
//        sqsUtil.sendMessage(userInvitedQueue, event);
    }

    @EventListener
    public void handleUserPasswordResetEvent(UserPasswordResetEvent event) {
//        sqsUtil.sendMessage(userPasswordResetQueue, event);
    }
}
