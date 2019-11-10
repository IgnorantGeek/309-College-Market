package org.campusmarket.app.models;
import org.campusmarket.app.exception.*;
import org.campusmarket.app.models.File;
import org.campusmarket.db.repositories.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
 * A service class for File to implement some methods to help in the controller class
 * @author fadelalshammasi
 *
 */
@Service
public class FileService {

    @Autowired
    private FilesRepository repo;

    /**
     * A method to store the file in the db
     * @param file
     * @return file
     */
    public File storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Filename contains invalid path characters " + fileName);
            }

            File f = new File(fileName, file.getContentType(), file.getBytes());

            return repo.save(f);
        } catch (IOException ex) {
            throw new FileStorageException("Couldn't save file into the db " + fileName + ". Please try again", ex);
        }
    }

    /**
     * A method to search for a file in the db
     * @param id
     * @return the file found otherwise throwing an exception
     */
    public File getFile(int id) {
    	try {
        return repo.findById(id);
    	}
    	
    	catch (MyFileNotFoundException e) {
    		throw new MyFileNotFoundException("Couldn't find file with id " + id);
    	}
    }
}