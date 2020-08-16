package com.lms.backend.repositories;

import com.lms.backend.models.SystemRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SystemRoleRepository extends MongoRepository <SystemRole, String> {

    @Query("{'_id': {'$in':?0}}")
    Optional<List<SystemRole>> findByIdList(Set<String> systemRoleIdList);

    Page<SystemRole> findByNameContaining(String name, Pageable pageable);

}
