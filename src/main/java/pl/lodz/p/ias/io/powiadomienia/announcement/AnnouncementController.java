package pl.lodz.p.ias.io.powiadomienia.announcement;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.powiadomienia.notification.NotificationService;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;

import java.util.List;

/**
 * Kontroler ogłoszeń.
 */
@CrossOrigin("http://localhost:5173")
@RestController
@AllArgsConstructor
public class AnnouncementController {

    private AnnouncementService announcementService;

    /**
     * Pobiera listę ogłoszeń użytkownika.
     *
     * @return lista {@link Announcement} danego użytkownika
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/announcements/user")
    public List<Announcement> getAnnouncements() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return announcementService.getUsersAnnouncements(username);
    }

    /**
     * Ukrywa ogłoszenie o podanym ID.
     *
     * @param id ID ogłoszenia do ukrycia
     */
    @PreAuthorize("isAuthenticated()")
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
    @PreAuthorize("hasAnyRole('ORGANIZACJA_POMOCOWA', 'PRZEDSTAWICIEL_WŁADZ')")
    @PostMapping("/announcements")
    public Announcement createAnnouncement(@RequestBody @Valid Announcement announcement) {
        return announcementService.createAnnouncement(announcement);
    }
}
