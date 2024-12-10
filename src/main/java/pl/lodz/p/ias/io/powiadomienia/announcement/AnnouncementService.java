package pl.lodz.p.ias.io.powiadomienia.announcement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Users;
import pl.lodz.p.ias.io.uwierzytelnianie.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class AnnouncementService {

    private AnnouncementRepository announcementRepository;
    private UserAnnouncementRepository userAnnouncementRepository;
    private UserRepository userRepository;

    public Announcement createAnnouncement(Announcement announcement) {
        Announcement savedAnnouncement = announcementRepository.save(announcement);

        List<Users> users = userRepository.findAll();

        for (Users user : users) {
            UserAnnouncement userAnnouncement = new UserAnnouncement();
            userAnnouncement.setUser(user);
            userAnnouncement.setAnnouncement(announcement);
            userAnnouncementRepository.save(userAnnouncement);
        }

        return savedAnnouncement;
    }

    public List<Announcement> getUsersAnnouncements(Long userId) {
        List<UserAnnouncement> userAnnouncementList = userAnnouncementRepository.findAllByUser(userRepository.findById(userId).get());
        List<Announcement> announcementList = new ArrayList<>();
        for (UserAnnouncement userAnnouncement : userAnnouncementList) {
            announcementList.add(userAnnouncement.getAnnouncement());
        }
        return announcementList;
    }

    public void hideAnnouncement(Long announcementId) {
        userAnnouncementRepository.deleteById(announcementId);
    }
}
