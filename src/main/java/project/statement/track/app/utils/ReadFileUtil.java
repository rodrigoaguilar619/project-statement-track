package project.statement.track.app.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFileUtil {

	public String readFile(String path) throws IOException {

		String content = new String(Files.readAllBytes(Paths.get(path)));
		return content;
	}
}
