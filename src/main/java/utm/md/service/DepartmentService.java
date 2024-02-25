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
import utm.md.domain.User;
import utm.md.domain.enumeration.DepartmentRoleEnum;
import utm.md.repository.DepartmentRepository;
import utm.md.repository.UserRepository;
import utm.md.security.SecurityUtils;
import utm.md.web.rest.errors.NotFoundAlertException;

/**
 * Service Implementation for managing {@link utm.md.domain.Department}.
 */
@Service
@Transactional
public class DepartmentService {

    private final Logger log = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    public DepartmentService(DepartmentRepository departmentRepository, UserRepository userRepository) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
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

    public Department addMember(UUID departmentId, UUID userId) {
        log.debug("Add member to department");
        var departmentOptional = departmentRepository.findById(departmentId);
        if (departmentOptional.isPresent()) {
            var department = departmentOptional.get();
            var userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                var user = userOptional.get();
                department.addMembers(user);
                departmentRepository.save(department);
            }
        }
        throw new NotFoundAlertException("Entity not found", "user/department", "notFound");
    }

    public Department removeMember(UUID departmentId, UUID userId) {
        log.debug("Remove member to department");
        var departmentOptional = departmentRepository.findById(departmentId);
        if (departmentOptional.isPresent()) {
            var department = departmentOptional.get();
            var userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                var user = userOptional.get();
                department.removeMembers(user);
                departmentRepository.save(department);
            }
        }
        throw new NotFoundAlertException("Entity not found", "user/department", "notFound");
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

    @Transactional(readOnly = true)
    public Page<Department> findMyDepartments(Pageable page) {
        var userLogin = SecurityUtils.getCurrentUserLogin();
        if (userLogin.isPresent()) {
            var userId = userRepository.findOneByLogin(userLogin.get());
            var resultList = departmentRepository.findDepartmentByUserId(userId.get().getId(), page);
            return resultList.map(department -> {
                department.setDepartmentRole(getRoleForCurrentUserInDepartment(userId.get(), department));
                return department;
            });
        }
        return Page.empty();
    }

    private DepartmentRoleEnum getRoleForCurrentUserInDepartment(User user, Department department) {
        if (department.getDepartmentAdmin().equals(user)) {
            return DepartmentRoleEnum.ADMIN;
        } else {
            return DepartmentRoleEnum.MEMBER;
        }
    }
}
