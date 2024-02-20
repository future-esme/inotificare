package utm.md.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import utm.md.domain.Department;

public interface DepartmentRepositoryWithBagRelationships {
    Optional<Department> fetchBagRelationships(Optional<Department> department);

    List<Department> fetchBagRelationships(List<Department> departments);

    Page<Department> fetchBagRelationships(Page<Department> departments);
}
