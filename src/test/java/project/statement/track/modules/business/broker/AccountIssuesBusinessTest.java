package project.statement.track.modules.business.broker;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lib.base.backend.test.assessment.Assessment;
import project.statement.track.ProjectUnitTest;
import project.statement.track.app.beans.entity.BrokerAccountEntity;
import project.statement.track.app.beans.entity.CatalogIssueEntity;
import project.statement.track.app.beans.entity.CatalogTypeTransactionEntity;
import project.statement.track.app.beans.entity.MovementsMoneyEntity;
import project.statement.track.app.beans.pojos.petition.data.GetAccountDividendsDataPojo;
import project.statement.track.app.beans.pojos.petition.request.GetAccountDividendsRequestPojo;
import project.statement.track.app.repository.MovementsMoneyRepository;
import project.statement.track.app.vo.entities.CatalogBrokerAccountEnum;
import project.statement.track.app.vo.entities.CatalogTypeTransactionEnum;

class AccountIssuesBusinessTest extends ProjectUnitTest {

	@InjectMocks
	AccountIssuesBusiness accountIssuesBusiness;
	
	@Mock
	MovementsMoneyRepository movementsMoneyRepository;
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	void testExecuteGetAccountDividends() {
		
		GetAccountDividendsRequestPojo requestDto = new GetAccountDividendsRequestPojo();
		requestDto.setIdIssue(1);
		requestDto.setIdBrokerAccount(CatalogBrokerAccountEnum.SNOWBALL_MAIN.getValue());
		
		CatalogIssueEntity catalogIssue = new CatalogIssueEntity();
		catalogIssue.setId(1);
		catalogIssue.setInitials("AAA");
		catalogIssue.setDescriptionSnowball("Snowball Test");
		catalogIssue.setDescription("Snow test");
		
		CatalogTypeTransactionEntity catalogTypeTransaction = new CatalogTypeTransactionEntity();
		catalogTypeTransaction.setId(1);
		catalogTypeTransaction.setDescription("Transaction test");
		
		BrokerAccountEntity brokerAccount = new BrokerAccountEntity();
		brokerAccount.setId(1);
		brokerAccount.setDescription("Broker account test");
		
		MovementsMoneyEntity movementsMoney1 = new MovementsMoneyEntity();
		movementsMoney1.setId(1);
		movementsMoney1.setIdBrokerAccount(requestDto.getIdBrokerAccount());
		movementsMoney1.setIdIssue(requestDto.getIdIssue());
		movementsMoney1.setIdTypeTransaction(CatalogTypeTransactionEnum.DEPOSIT.getValue());
		movementsMoney1.setCatalogIssue(catalogIssue);
		movementsMoney1.setCatalogTypeTransaction(catalogTypeTransaction);
		movementsMoney1.setBrokerAccount(brokerAccount);
		
		List<MovementsMoneyEntity> movementsMoneyDividends = new ArrayList<>();
		movementsMoneyDividends.add(movementsMoney1);
		
		when(movementsMoneyRepository.getMovementsMoneyDividend(requestDto.getIdBrokerAccount(), requestDto.getIdIssue())).thenReturn(movementsMoneyDividends);
		
		GetAccountDividendsDataPojo result =  accountIssuesBusiness.executeGetAccountDividends(requestDto);
		
		assertNotNull(result);
		Assessment.assertDataList(result.getMovementMoneyDividends());
	}

}
