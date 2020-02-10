package com.escalade.victor.repository;
import com.escalade.victor.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopologieRepository extends JpaRepository<Topologie, Long>{
}
