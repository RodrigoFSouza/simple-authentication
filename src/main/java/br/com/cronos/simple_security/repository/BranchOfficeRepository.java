package br.com.cronos.simple_security.repository;

import br.com.cronos.simple_security.domain.entity.BranchOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchOfficeRepository extends JpaRepository<BranchOffice, Long>, JpaSpecificationExecutor<BranchOffice> {

    List<BranchOffice> findByOrganizationId(Long organizationId);
}
