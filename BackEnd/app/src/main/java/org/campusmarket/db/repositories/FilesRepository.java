package org.campusmarket.db.repositories;
import org.campusmarket.app.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *  An interface for Item to represent the FilesRepository
 * @author fadelalshammasi
 *
 */
public interface FilesRepository extends JpaRepository<File, Integer>{
	/**
	 * A method to find a file by its unique id
	 * @param id
	 * @return the found file, otherwise null
	 */
    File findById(@Param("id") int id);

}
