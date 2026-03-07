package tech.csm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.csm.model.FunctionMaster;

@Repository
public interface FunctionMasterRepository extends JpaRepository<FunctionMaster, Integer> {

	 List<FunctionMaster> findByStatus(String status);
}
