package pl.lodz.p.ias.io.powiadomienia.announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repozytorium ogłoszeń.
 */
@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
