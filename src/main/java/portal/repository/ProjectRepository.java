package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import portal.entity.Project;




public interface ProjectRepository  extends JpaRepository<Project, Long>  {

}