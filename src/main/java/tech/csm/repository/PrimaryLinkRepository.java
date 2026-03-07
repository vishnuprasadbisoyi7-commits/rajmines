package tech.csm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tech.csm.model.PrimaryLink;

@Repository
public interface PrimaryLinkRepository extends JpaRepository<PrimaryLink, Integer> {

}
