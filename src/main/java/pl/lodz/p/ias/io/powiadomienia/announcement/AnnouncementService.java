package pl.lodz.p.ias.io.powiadomienia.announcement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.powiadomienia.mock.MockUser;
import pl.lodz.p.ias.io.powiadomienia.mock.MockUserRepo;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class AnnouncementService {

    private AnnouncementRepository announcementRepository;
    private UserAnnouncementRepository userAnnouncementRepository;
    private MockUserRepo mockUserRepo;

    public Announcement createAnnouncement(Announcement announcement) {
        Announcement savedAnnouncement = announcementRepository.save(announcement);

        List<MockUser> users = mockUserRepo.findAll();

        for (MockUser mockUser : users) {
            UserAnnouncement userAnnouncement = new UserAnnouncement();
            userAnnouncement.setUser(mockUser);
            userAnnouncement.setAnnouncement(announcement);
            userAnnouncementRepository.save(userAnnouncement);
        }

        return savedAnnouncement;
    }

    public List<Announcement> getUsersAnnouncements(Long userId) {
        List<UserAnnouncement> userAnnouncementList = userAnnouncementRepository.findAllByUser_Id(userId);
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
