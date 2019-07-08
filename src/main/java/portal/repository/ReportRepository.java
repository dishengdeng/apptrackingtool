package portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import portal.entity.Report;

public interface ReportRepository extends JpaRepository<Report,Long>{

}
