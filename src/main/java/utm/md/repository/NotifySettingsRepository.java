package utm.md.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import utm.md.domain.NotifySettings;
import utm.md.domain.enumeration.Channel;

/**
 * Spring Data JPA repository for the NotifySettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotifySettingsRepository extends JpaRepository<NotifySettings, UUID>, JpaSpecificationExecutor<NotifySettings> {
    @Modifying
    @Transactional
    @Query(
        value = """
            update notify_settings
            set status = :status
            where credentials_id = (select id
                                    from channel_user_credentials
                                    where chat_id = :chatId
                                    and channel = :channel)
        """,
        nativeQuery = true
    )
    void updateByChatId(@Param("status") String status, @Param("chatId") String chatId, @Param("channel") String channel);

    Optional<NotifySettings> findByUserInternalIdAndChannel(UUID userId, Channel channel);
}
