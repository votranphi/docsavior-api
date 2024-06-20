package com.example.restapi;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    // find all notifications
    public List<Notification> findNotificationByUsername(String username)
    {
        return notificationRepository.findNotificationByUsername(username);
    }

    public void saveNewNotificationType01(String username, Integer type, Integer idPost, String usernameInteract)
    {
        Notification notification = new Notification(username, type, idPost, usernameInteract);
        notificationRepository.save(notification);
    }

    public void saveNewNotificationType2(String username, Integer type, String usernameInteract)
    {
        Notification notification = new Notification(username, type, usernameInteract);
        notificationRepository.save(notification);
    }
}
