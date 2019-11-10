package org.campusmarket.db.repositories;

import org.campusmarket.app.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface FilesRepository extends JpaRepository<File, Integer>{
   // File findById(@Param("fid") int fid);

	
}
