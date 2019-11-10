package org.campusmarket.app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * A class to represent the model for a file(image,doc,...etc)
 * @author fadelalshammasi
 *
 */
@Entity
@Table(name = "files")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class File {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
    @Column(name = "fname")
    private String  fname;
    
    @Column(name = "ftype")
    private String ftype;
    
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column (name= "fdata",columnDefinition="BLOP")
    private byte[] fdata;
	
    
    /**
     * Default constructor 
     */
    public File() {
    	
    }
    
   /**
    * A constructor to create a file with all the required parameters of the files table 
    * @param fname
    * @param ftype
    * @param fdata
    */
    public File(String fname, String ftype, byte[] fdata) {
        this.fname=fname;
        this.ftype=ftype;
        this.fdata=fdata;
    
    }
    
    /**
     * a getter method to get the id of the file 
     * @return id
     */
    public int getId() {
    	return this.id;
    }
    
    /**
     * a getter method to get the name of the file 
     * @return fname
     */
    public String getFileName() {
    	return this.fname;
    }
    
    /**
     * a getter method to get the type of the file 
     * @return ftype
     */
    public String getFileType() {
    	return this.ftype;
    }
    
    /**
     * a getter method to get the filedata(in BLOB) of the file 
     * @return fdata
     */
    public byte[] getFileData() {
    	return this.fdata;
    }
    
    /**
     * A setter method to change the id of a file 
     * @param id
     */
   public void setId(int id) {
	   this.id=id;
   }
   
   /**
    * A setter method to change the name of a file 
    * @param fname
    */
   public void setFileName(String fname) {
	   this.fname=fname;
   }
   
   /**
    * A setter method to change the type of a file 
    * @param ftype
    */
   public void setFtype(String ftype) {
	   this.ftype=ftype;
   }
   
   /**
    * A setter method to change the filedata(BLOB) of a file 
    * @param fdata
    */
   public void setFdata(byte[] fdata) {
	   this.fdata=fdata;
   }
    
	
}
