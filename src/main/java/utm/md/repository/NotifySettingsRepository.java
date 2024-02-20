package utm.md.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import utm.md.domain.NotifySettings;

/**
 * Spring Data JPA repository for the NotifySettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotifySettingsRepository extends JpaRepository<NotifySettings, UUID>, JpaSpecificationExecutor<NotifySettings> {}
