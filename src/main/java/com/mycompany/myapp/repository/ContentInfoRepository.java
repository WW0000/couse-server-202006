package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ContentInfo;

import com.mycompany.myapp.domain.ContentType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ContentInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentInfoRepository extends JpaRepository<ContentInfo, Long> {
}
