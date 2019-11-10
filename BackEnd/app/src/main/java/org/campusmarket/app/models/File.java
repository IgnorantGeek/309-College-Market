package org.campusmarket.app.models;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "files")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class File {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fid")
    private int fid;
	
    @Column(name = "fname")
    private String  fname;
    
    @Column(name = "ftype")
    private String ftype;
    
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column (name= "fdata",columnDefinition="BLOP")
    private byte[] fdata;
	
    public File() {
    	
    }
    
    public File(String fname, String ftype, byte[] fdata) {
        this.fname=fname;
        this.ftype=ftype;
        this.fdata=fdata;
    
    }
    
    public int getId() {
    	return this.fid;
    }
    
    public String getFileName() {
    	return this.fname;
    }
    
    public String getFileType() {
    	return this.ftype;
    }
    
    public byte[] getFileData() {
    	return this.fdata;
    }
    
    //setters to be added later 
    
	
}
