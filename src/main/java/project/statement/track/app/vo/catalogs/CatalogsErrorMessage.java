package project.statement.track.app.vo.catalogs;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

public class CatalogsErrorMessage {
	
	private CatalogsErrorMessage(){}
	
	private static final String ERROR_MSG_PARAMETER_NOT_RECOGNIZED = "Parameter not recognized key: %s, value: %s";
	private static final String ERROR_MSG_LIST_NOT_ODD = "list is not odd. %s";
	private static final String ERROR_MSG_PARSING_DATE = "Error parsing date";
	private static final String ERROR_MSG_READING_FILE_HTML = "Error reading file html";
	private static final String ERROR_MSG_FUNCTION_CUT_DAY_NOT_IMPLEMENTED = "Function of cut day not implemented";
	private static final String ERROR_MSG_DEFINITION_BALANCE_NOT_FOUND = "For balance definition type operation not found. type operation: %s table: %s";
	private static final String ERROR_MSG_BAD_ID_TABLE_TRACK = "Error getting id of table track";
	private static final String ERROR_MSG_MAP_COMPANY_NOT_FOUND = "Error company %s not found on mapping";
	private static final String ERROR_MSG_CATALOG_NOT_FOUND_FOR_COMPANY = "Catalog no found for company '%s' on registering movement money transaction";
	private static final String ERROR_MSG_STATEMENT_ID_REGISTERED = "First id of statement is registed, statement process cancelled";
	private static final String ERROR_MSG_ISSUE_REDEEM_NOT_FOUND = "Issue for redeem not found, verify. company: %s, price: %s";
	private static final String ERROR_MSG_OPTION_MOVEMENT_TYPE_NOT_IMPLEMENTED = "Option of movement type not implemented. option: %s";
	private static final String ERROR_MSG_ISSUE_TRANSACTION_REGISTERED = "Issue transactions registered, can´t be delete";
	private static final String ERROR_MSG_STATEMENT_FILE_INCORRECT = "File snowball statement incorrect";
	
	public static String getErrorMsgParameterNotRecognized(String key, String value) {
		return String.format(ERROR_MSG_PARAMETER_NOT_RECOGNIZED, key, value);
	}
	
	public static String getErrorMsgListNotOdd(String[] list) {
		return String.format(ERROR_MSG_LIST_NOT_ODD, StringUtils.join(list, "|"));
	}
	
	public static String getErrorMsgParsingDate() {
		return ERROR_MSG_PARSING_DATE;
	}
	
	public static String getErrorMsgReadingFileHtml() {
		return ERROR_MSG_READING_FILE_HTML;
	}
	
	public static String getErrorMsgFunctionCutDayNotImplemented() {
		return ERROR_MSG_FUNCTION_CUT_DAY_NOT_IMPLEMENTED;
	}
	
	public static String getErrorMsgDefinitionBalanceNotFound(Integer idTypeOperation, String table) {
		return String.format(ERROR_MSG_DEFINITION_BALANCE_NOT_FOUND, idTypeOperation, table);
	}
	
	public static String getErrorMsgBadIdTableTrack() {
		return ERROR_MSG_BAD_ID_TABLE_TRACK;
	}
	
	public static String getErrorMsgMapCompanyNotFound(String company) {
		return String.format(ERROR_MSG_MAP_COMPANY_NOT_FOUND, company);
	}
	
	public static String getErrorMsgCatalogNotFoundForCompany(String company) {
		return String.format(ERROR_MSG_CATALOG_NOT_FOUND_FOR_COMPANY, company);
	}
	
	public static String getErrorMsgStatementIdRegistered() {
		return ERROR_MSG_STATEMENT_ID_REGISTERED;
	}
	
	public static String getErrorMsgIssueRedeemNotFound(String company, BigDecimal price) {
		return String.format(ERROR_MSG_ISSUE_REDEEM_NOT_FOUND, company, price);
	}
	
	public static String getErrorMsgOptionMovementTypeNotImplemented(String movementOption) {
		return String.format(ERROR_MSG_OPTION_MOVEMENT_TYPE_NOT_IMPLEMENTED, movementOption);
	}
	
	public static String getErrorMsgIssueTransactionRegistered() {
		return ERROR_MSG_ISSUE_TRANSACTION_REGISTERED;
	}
	
	public static String getErrorMsgStatementFileIncorrect() {
		return ERROR_MSG_STATEMENT_FILE_INCORRECT;
	}
}
