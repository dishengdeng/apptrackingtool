package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portal.entity.Parameter;

public interface ParameterRepository  extends JpaRepository<Parameter,Long>{

}
