package utm.md.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utm.md.domain.RequestData;
import utm.md.service.dto.IUserRequestDataDTO;

/**
 * Spring Data JPA repository for the RequestData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestDataRepository extends JpaRepository<RequestData, UUID>, JpaSpecificationExecutor<RequestData> {
    @Query(
        value = """
        select jhi_user.id as userId, channel_user_credentials.id as channelId, notify_settings.channel as channel, channel_user_credentials.chat_id as chatId
        from jhi_user
        join notify_settings on jhi_user.id = notify_settings.user_internal_id
        join channel_user_credentials on notify_settings.credentials_id = channel_user_credentials.id
        where cast(jhi_user.id as varchar) in (:userIds)
        and notify_settings.status = 'ON'
        and jhi_user.activated = true
        """,
        nativeQuery = true
    )
    List<IUserRequestDataDTO> findRecipientsByUserIds(@Param("userIds") List<String> userIds);

    @Query(
        value = """
        select jhi_user.id as userId, channel_user_credentials.id as channelId, notify_settings.channel as channel, channel_user_credentials.chat_id as chatId
        from jhi_user
        join notify_settings on jhi_user.id = notify_settings.user_internal_id
        join channel_user_credentials on notify_settings.credentials_id = channel_user_credentials.id
        join rel_department__members on jhi_user.id = rel_department__members.members_id
        where cast(rel_department__members.department_id as varchar) in (:departmentsIds)
        and notify_settings.status = 'ON'
        and jhi_user.activated = true
        """,
        nativeQuery = true
    )
    List<IUserRequestDataDTO> findRecipientsByDepartmentIds(@Param("departmentsIds") List<String> departmentsIds);
}
