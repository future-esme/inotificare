package utm.md.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import utm.md.domain.RequestData;

/**
 * Spring Data JPA repository for the RequestData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestDataRepository extends JpaRepository<RequestData, UUID>, JpaSpecificationExecutor<RequestData> {}
