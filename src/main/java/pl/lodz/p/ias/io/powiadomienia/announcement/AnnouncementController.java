package pl.lodz.p.ias.io.powiadomienia.announcement;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class AnnouncementController {

    private AnnouncementService announcementService;

    @GetMapping("/announcements/user/{user_id}")
    public List<Announcement> getAnnouncements(@PathVariable Long user_id) {
        return announcementService.getUsersAnnouncements(user_id);
    }

    @PostMapping("/announcements/{id}/hide")
    public void hideAnnouncement(@PathVariable Long id) {
        announcementService.hideAnnouncement(id);
    }

    @PostMapping("/announcements")
    public Announcement createAnnouncement(@RequestBody @Valid Announcement announcement) {
        return announcementService.createAnnouncement(announcement);
    }
}
