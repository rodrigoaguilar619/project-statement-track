package project.statement.track.pojos.request;

import org.springframework.web.multipart.MultipartFile;

public class LoadFileStatementRequestPojo {

	MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
