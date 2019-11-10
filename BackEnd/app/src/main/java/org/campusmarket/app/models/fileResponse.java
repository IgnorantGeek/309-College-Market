package org.campusmarket.app.models;

/**
 * A class to help generate a meaningful response (that has the filename,the download/view link, filetype, and the size) after uploading a file (image,doc,..etc)
 * @author fadelalshammasi
 *
 */
public class fileResponse {

	    private String fname;
	    private String downloadUrl;
	    private String fType;
	    private long size;

	    /**
	     *  A constructor to create a file response with all the required parameters 
	     * @param fname
	     * @param downloadUrl
	     * @param fType
	     * @param size
	     */
	    public fileResponse(String fname, String downloadUrl, String fType, long size) {
	        this.fname = fname;
	        this.downloadUrl = downloadUrl;
	        this.fType = fType;
	        this.size = size;
	    }

	    
	    /**
	     * A getter method for the filename
	     * @return fname
	     */
	    public String getFileName() {
	        return this.fname;
	    }

	    /**
	     * A getter method for the url to download/view the file
	     * @return
	     */
	    public String getFileDownloadUrl() {
	        return this.downloadUrl;
	    }
	    
	    /**
	     * A getter method to get the type of the file
	     * @return ftype
	     */
	    public String getFileType() {
	        return this.fType;
	    }
	    
	    /**
	     * A getter method to get the size of the file
	     * @return size
	     */
	    public long getSize() {
	        return size;
	    }
	    
	    /**
	     * A setter method to change the name of the file
	     * @param fname
	     */
	    public void setFileName(String fname) {
	        this.fname = fname;
	    }

	    /**
	     * A setter method to change the downloadUrl of the file
	     * @param downloadUrl
	     */
	    public void setFileDownloadUrl(String downloadUrl) {
	        this.downloadUrl = downloadUrl;
	    }

	    /**
	     * A setter method to change the type of the file
	     * @param fType
	     */
	    public void setFileType(String fType) {
	        this.fType = fType;
	    }

	    /**
	     * A setter method to change the size of the file 
	     * @param size
	     */
	    public void setSize(long size) {
	        this.size = size;
	    }
	}