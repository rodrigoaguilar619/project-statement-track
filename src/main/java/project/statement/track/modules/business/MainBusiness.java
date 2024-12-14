package project.statement.track.modules.business;

import lib.base.backend.utils.JpaUtil;
import lib.base.backend.utils.date.DateUtil;
import project.statement.track.app.utils.BrokerSnowBallUtil;
import project.statement.track.app.utils.BuildEntityToPojoUtil;
import project.statement.track.app.utils.BuildPojoToEntityUtil;

public class MainBusiness {

	protected BrokerSnowBallUtil brokerSnowBallUtil = new BrokerSnowBallUtil();
	protected BuildPojoToEntityUtil buildPojoToEntityUtil = new BuildPojoToEntityUtil();
	protected BuildEntityToPojoUtil buildEntityToPojoUtil = new BuildEntityToPojoUtil();
	protected JpaUtil jpaUtil = new JpaUtil();
	protected DateUtil dateUtil = new DateUtil();
}
