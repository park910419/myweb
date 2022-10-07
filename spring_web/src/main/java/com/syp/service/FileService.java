package com.syp.service;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;

import com.syp.dto.AttachFile;
import com.syp.dto.Thumbnail;

public interface FileService {
	
	//서블릿 기반
	public AttachFile fileUpload(FileItem item);
	
	//스프링 기반
	public AttachFile fileUpload(MultipartFile item);
	 
	public Thumbnail setThumbnail(File file, String saveFileName);
	
	public void fileDown(HttpServletRequest request, HttpServletResponse response);

	public int delete(String no, String savefilename, String filepath, String thumb_file);

	 
}
