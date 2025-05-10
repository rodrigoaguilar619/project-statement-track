package project.statement.track.app.vo.catalogs;

public class CatalogsUri {
	
	private CatalogsUri() {}
	
	public static final String API_ACCOUNT_ISSUES_MOVEMENTS_DIVIDENT_LIST_GET = "/api/account/issues/getMovementsDividend";
	public static final String API_ACCOUNT_RESUME_GET = "/api/account/getAccountResume";
	public static final String API_ACCOUNT_STATEMENT_GET = "/api/accountStatement/getAccountStatement";
	public static final String API_BROKER_ACCOUNT_LIST_GET = "/api/broker/getBrokerAccounts";
	public static final String API_BROKER_STATEMENT_LIST_GET = "/api/broker/getDateStatements";
	public static final String API_ISSUES_BUY_LIST_GET = "/api/issues/getIssuesBuy";
	public static final String API_ADMIN_CUSTOM_CATALOG_ISSUE_LIST_GET = "/api/admin/customCatalog/catalogIssue/getCatalogIssues";
	public static final String API_ADMIN_CUSTOM_CATALOG_ISSUE_INDIVIDUAL_GET = "/api/admin/customCatalog/catalogIssue/getCatalogIssue";
	public static final String API_ADMIN_CUSTOM_CATALOG_ISSUE_ADD = "/api/admin/customCatalog/catalogIssue/saveCatalogIssue";
	public static final String API_ADMIN_CUSTOM_CATALOG_ISSUE_UPDATE = "/api/admin/customCatalog/catalogIssue/updateCatalogIssue";
	public static final String API_ADMIN_CUSTOM_CATALOG_ISSUE_DELETE = "/api/admin/customCatalog/catalogIssue/deleteCatalogIssue";
	public static final String API_STATEMENT_READ_FILE_BASE64 = "/api/readStatement/readStatementSnowBallFileBase64";
	public static final String API_STATEMENT_READ_FILE_BYTES = "/api/readStatement/readStatementSnowBall";
	
	public static final String API_AFORE_PERIODS_RESUME_LIST_GET = "/api/afore/getAforePeriodsResume";
	public static final String API_AFORE_PERIOD_DATA_GET = "/api/afore/getAforePeriodData";
}
