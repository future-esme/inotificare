package utm.md.service;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.md.domain.Department;
import utm.md.repository.DepartmentRepository;

/**
 * Service Implementation for managing {@link utm.md.domain.Department}.
 */
@Service
@Transactional
public class DepartmentService {

    private final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * Save a department.
     *
     * @param department the entity to save.
     * @return the persisted entity.
     */
    public Department save(Department department) {
        log.debug("Request to save Department : {}", department);
        return departmentRepository.save(department);
    }

    /**
     * Update a department.
     *
     * @param department the entity to save.
     * @return the persisted entity.
     */
    public Department update(Department department) {
        log.debug("Request to update Department : {}", department);
        return departmentRepository.save(department);
    }

    /**
     * Partially update a department.
     *
     * @param department the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Department> partialUpdate(Department department) {
        log.debug("Request to partially update Department : {}", department);

        return departmentRepository
            .findById(department.getId())
            .map(existingDepartment -> {
                if (department.getTitle() != null) {
                    existingDepartment.setTitle(department.getTitle());
                }

                return existingDepartment;
            })
            .map(departmentRepository::save);
    }

    /**
     * Get all the departments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Department> findAll(Pageable pageable) {
        log.debug("Request to get all Departments");
        return departmentRepository.findAll(pageable);
    }

    /**
     * Get all the departments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Department> findAllWithEagerRelationships(Pageable pageable) {
        return departmentRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one department by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Department> findOne(UUID id) {
        log.debug("Request to get Department : {}", id);
        return departmentRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the department by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Department : {}", id);
        departmentRepository.deleteById(id);
    }
}
