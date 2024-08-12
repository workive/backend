package app.teamwize.api.user.listener;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;



@Slf4j
@Component
@Profile("!it")
@RequiredArgsConstructor
public class UserEventSQSListener {


//    @SqsListener(value = "${cloud.aws.sqs.new-user-invited-queue}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
//    public void handleUserInvitedEvent(UserInvitedEvent event)
//            throws UserNotFoundException, MessagingException, UserAlreadyActivatedException {
//        try {
//            activationService.sendActivationEmail(event.getSiteId(), event.getUserId(), event.getLanguage());
//        } catch (Exception ex) {
//            log.error("Error in sending activation email for : " + event.toString(), ex);
//            throw ex;
//        }
//    }
//
//    @SqsListener(value = "${cloud.aws.sqs.user-password-reset-queue}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
//    public void handleUserPasswordResetEvent(UserInvitedEvent event) throws MessagingException {
//        try {
//            resetPasswordService.sendResetPasswordEmail(event.getSiteId(), event.getUserId(), event.getEmail(),
//                    event.getLanguage());
//        } catch (MessagingException ex) {
//            log.error("Error in sending reset password email for : " + event.toString(), ex);
//            throw ex;
//        }
//    }
}
