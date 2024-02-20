package utm.md.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import utm.md.domain.ChannelUserCredentials;

/**
 * Spring Data JPA repository for the ChannelUserCredentials entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChannelUserCredentialsRepository
    extends JpaRepository<ChannelUserCredentials, UUID>, JpaSpecificationExecutor<ChannelUserCredentials> {}
