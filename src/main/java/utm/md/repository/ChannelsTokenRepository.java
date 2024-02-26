package utm.md.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utm.md.domain.ChannelsToken;

/**
 * Spring Data JPA repository for the ChannelsToken entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChannelsTokenRepository extends JpaRepository<ChannelsToken, UUID> {
    @Query(
        value = """
            select * from channels_token
            where token = :token
            and expiration_time <= now()
            and channel = :channel
        """,
        nativeQuery = true
    )
    Optional<ChannelsToken> findTokenAndActive(@Param("token") String token, @Param("channel") String channel);
}
