package pl.lodz.p.ias.io.powiadomienia.announcement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.UserRepository;

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
    private UserRepository userRepository;

    /**
     * Tworzy nowe ogłoszenie i przypisuje je do każdego użytkownika.
     *
     * @param announcement {@link Announcement} obiekt do utworzenia
     * @return zapisany {@link Announcement} obiekt
     */
    public Announcement createAnnouncement(Announcement announcement) {
        Announcement savedAnnouncement = announcementRepository.save(announcement);

        List<Account> users = userRepository.findAll();

        for (Account user : users) {
            UserAnnouncement userAnnouncement = new UserAnnouncement();
            userAnnouncement.setUser(user);
            userAnnouncement.setAnnouncement(announcement);
            userAnnouncementRepository.save(userAnnouncement);
        }

        return savedAnnouncement;
    }

    /**
     * Pobiera listę ogłoszeń użytkownika.
     *
     * @param userId ID użytkownika którego ogłoszenia zostaną pobrane
     * @return lista {@link Announcement} danego użytkownika
     */
    public List<Announcement> getUsersAnnouncements(Long userId) {
        List<UserAnnouncement> userAnnouncementList = userAnnouncementRepository.findAllByUser(userRepository.findById(userId).get());
        List<Announcement> announcementList = new ArrayList<>();
        for (UserAnnouncement userAnnouncement : userAnnouncementList) {
            announcementList.add(userAnnouncement.getAnnouncement());
        }
        return announcementList;
    }

    /**
     * Ukrywa ogłoszenie o podanym ID.
     *
     * @param announcementId ID ogłoszenia do ukrycia
     */
    public void hideAnnouncement(Long announcementId) {
        userAnnouncementRepository.deleteById(announcementId);
    }
}
