package pl.lodz.p.ias.io.powiadomienia.announcement;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.powiadomienia.notification.NotificationService;
import pl.lodz.p.ias.io.powiadomienia.notification.NotificationType;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.AccountRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Serwis ogłoszeń.
 */
@Service
@AllArgsConstructor
public class AnnouncementService {

    private AnnouncementRepository announcementRepository;
    private UserAnnouncementRepository userAnnouncementRepository;
    private AccountRepository accountRepository;
    private NotificationService notificationService;

    /**
     * Tworzy nowe ogłoszenie i przypisuje je do każdego użytkownika.
     *
     * @param announcement {@link Announcement} obiekt do utworzenia
     * @return zapisany {@link Announcement} obiekt
     */
    public Announcement createAnnouncement(Announcement announcement) {
        Announcement savedAnnouncement = announcementRepository.save(announcement);

        List<Account> users = accountRepository.findAll();

        for (Account user : users) {
            UserAnnouncement userAnnouncement = new UserAnnouncement();
            userAnnouncement.setUser(user);
            userAnnouncement.setAnnouncement(announcement);
            userAnnouncementRepository.save(userAnnouncement);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        notificationService.notify("Successfully created Announcement.", NotificationType.INFORMATION, accountRepository.findByUsername(username));

        return savedAnnouncement;
    }

    /**
     * Pobiera listę ogłoszeń użytkownika.
     *
     * @param username login użytkownika którego ogłoszenia zostaną pobrane
     * @return lista {@link Announcement} danego użytkownika
     */
    public List<Announcement> getUsersAnnouncements(String username) {
        List<UserAnnouncement> userAnnouncementList = userAnnouncementRepository.findAllByUser(accountRepository.findByUsername(username));
        List<Announcement> announcementList = new ArrayList<>();
        for (UserAnnouncement userAnnouncement : userAnnouncementList) {
            announcementList.add(userAnnouncement.getAnnouncement());
            announcementList.getLast().setId(userAnnouncement.getId());
        }
        return announcementList;
    }

    /**
     * Ukrywa ogłoszenie o podanym ID.
     *
     * @param announcementId ID ogłoszenia do ukrycia
     */
    public void hideAnnouncement(Long announcementId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if(userAnnouncementRepository.findById(announcementId).get().getUser().getUsername().equals(username)) {
            userAnnouncementRepository.deleteById(announcementId);
        }
    }
}
