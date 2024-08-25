package project.statement.track.app.beans.pojos.petition.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoadFileStatementRequestPojo {

	MultipartFile file;
}
