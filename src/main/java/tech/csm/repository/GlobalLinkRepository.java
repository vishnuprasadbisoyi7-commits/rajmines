package tech.csm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.csm.model.GlobalLink;

@Repository
public interface GlobalLinkRepository extends JpaRepository<GlobalLink, Integer>{
	
	List<GlobalLink> findByStatus(String status);

	List<GlobalLink> findByStatusOrderByOrderNo(String status);

}
