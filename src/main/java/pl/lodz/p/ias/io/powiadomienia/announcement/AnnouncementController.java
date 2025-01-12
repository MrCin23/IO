package pl.lodz.p.ias.io.powiadomienia.announcement;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kontroler ogłoszeń.
 */
@CrossOrigin("*")
@RestController
@AllArgsConstructor
public class AnnouncementController {

    private AnnouncementService announcementService;

    /**
     * Pobiera listę ogłoszeń użytkownika.
     *
     * @param user_id ID użytkownika którego ogłoszenia zostaną pobrane
     * @return lista {@link Announcement} danego użytkownika
     */
    @GetMapping("/announcements/user/{user_id}")
    public List<Announcement> getAnnouncements(@PathVariable Long user_id) {
        return announcementService.getUsersAnnouncements(user_id);
    }

    /**
     * Ukrywa ogłoszenie o podanym ID.
     *
     * @param id ID ogłoszenia do ukrycia
     */
    @PostMapping("/announcements/{id}/hide")
    public void hideAnnouncement(@PathVariable Long id) {
        announcementService.hideAnnouncement(id);
    }

    /**
     * Tworzy nowe ogłoszenie i przypisuje je do każdego użytkownika.
     *
     * @param announcement {@link Announcement} obiekt do utworzenia
     * @return stworzony {@link Announcement} obiekt
     */
    @PostMapping("/announcements")
    public Announcement createAnnouncement(@RequestBody @Valid Announcement announcement) {
        return announcementService.createAnnouncement(announcement);
    }
}
