package com.syp.dto;

public class AttachFile {
	private String no;
	private String fileName;
	private String saveFileName;
	private String filePath;
	private String fileSize;
	private String fileType;
	private Thumbnail thumbnail;

	public AttachFile() {
		super();
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public AttachFile(Thumbnail thumbnail) {
		super();
		this.thumbnail = thumbnail;
	}
	public Thumbnail getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
}
