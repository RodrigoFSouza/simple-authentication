package br.com.cronos.simple_security.repository;

import br.com.cronos.simple_security.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team> {

    List<Team> findByOrganizationId(Long organizationId);

    List<Team> findByLeaderId(Long leaderId);
}
