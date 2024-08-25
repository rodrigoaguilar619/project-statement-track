package project.statement.track.app.beans.pojos.petition.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import project.statement.track.app.beans.pojos.tuple.IssueTotalsPojo;

@Getter @Setter
public class GetIssuesBuyDataPojo {

	List<IssueTotalsPojo> issuesBuy = new ArrayList<>();
}
