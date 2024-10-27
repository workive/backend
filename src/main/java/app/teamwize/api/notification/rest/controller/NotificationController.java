package app.teamwize.api.notification.rest.controller;

import app.teamwize.api.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
//    private final JobLauncher jobLauncher;
//    private final Job emailSenderJob;

    @PostMapping(value = "templates/{name}", produces = "text/html")
    public String getTemplate(@PathVariable String name, @RequestBody Map<String, Object> params) throws IOException {
        return notificationService.getTemplate(name, params);
    }


    @GetMapping("test")
    public String runJob() {
        try {
            return "Email Sender Job triggered successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to trigger the Email Sender Job!";
        }
    }
}
