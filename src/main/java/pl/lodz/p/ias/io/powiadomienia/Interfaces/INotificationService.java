package pl.lodz.p.ias.io.powiadomienia.Interfaces;

import pl.lodz.p.ias.io.powiadomienia.mock.MockUser;
import pl.lodz.p.ias.io.powiadomienia.notification.Notification;
import pl.lodz.p.ias.io.powiadomienia.notification.NotificationType;

public interface INotificationService {

    public Notification notify (String message, NotificationType type, MockUser user);

}
