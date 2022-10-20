package project.statement.track.app.pojos.petition.request;

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
