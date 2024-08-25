package project.statement.track.app.vo.catalogs;

public class CatalogsEntity {

	private CatalogsEntity() {
	}
	
	public static final class CatalogBrokerAccount {
		
		private CatalogBrokerAccount() {
		}
		
		public static final Integer SNOWBALL_MAIN = 1;
	}
	
	public static final class CatalogTypeMovement {
		
		private CatalogTypeMovement() {
		}
		
		public static final Integer BUY = 1;
		public static final Integer SELL = 2;
		public static final Integer BUY_MARKET_SECUNDARY = 3;
		public static final Integer BUY_MARKET_SECUNDARY_CANCELLED = 4;
	}
	
	public static final class CatalogTypeTransaction {
		
		private CatalogTypeTransaction() {
		}
		
		public static final Integer DEPOSIT = 1;
		public static final Integer WITHDRAW = 2;
		public static final Integer DIVIDEND = 3;
	}
}
