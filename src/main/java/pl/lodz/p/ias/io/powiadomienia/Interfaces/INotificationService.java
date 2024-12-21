package pl.lodz.p.ias.io.powiadomienia.Interfaces;

import pl.lodz.p.ias.io.powiadomienia.notification.Notification;
import pl.lodz.p.ias.io.powiadomienia.notification.NotificationType;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

public interface INotificationService {

    public Notification notify (String message, NotificationType type, Account user);

}
