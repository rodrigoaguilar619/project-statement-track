package project.statement.track;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.utils.ServerH2Util;

@Transactional
public abstract class ProjectConfigFlywayTest {
	
	private static boolean isInitLoaded = false;
	
	 @BeforeEach
    public void initTestClass() throws SQLException {
        
		 if (isInitLoaded) {
				return;
			}
			
			new ServerH2Util().startServerDefault();
		    isInitLoaded = true;
    }
}