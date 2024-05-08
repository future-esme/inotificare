package utm.md.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utm.md.domain.Department;

/**
 * Spring Data JPA repository for the Department entity.
 *
 * When extending this class, extend DepartmentRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface DepartmentRepository
    extends DepartmentRepositoryWithBagRelationships, JpaRepository<Department, UUID>, JpaSpecificationExecutor<Department> {
    default Optional<Department> findOneWithEagerRelationships(UUID id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default Page<Department> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    @Query(
        value = """
            select * from (
                select department.* from department
                join rel_department__members rdm on department.id = rdm.department_id
                where department.department_admin_id = :userId
                or rdm.members_id = :userId
            ) dep
        """,
        nativeQuery = true
    )
    Page<Department> findDepartmentByUserId(@Param("userId") UUID userId, Pageable pageable);
}
