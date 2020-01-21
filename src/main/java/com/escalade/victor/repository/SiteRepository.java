package com.escalade.victor.repository;
import com.escalade.victor.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SiteRepository extends JpaRepository<Site, Long> {
    //
}
