package utm.md.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import utm.md.domain.ViberSubscriptions;

/**
 * Spring Data JPA repository for the ViberSubscriptions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViberSubscriptionsRepository
    extends JpaRepository<ViberSubscriptions, UUID>, JpaSpecificationExecutor<ViberSubscriptions> {}
