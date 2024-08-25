package project.statement.track.modules.business.catalog;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.enumerators.CrudOptionsEnum;
import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.modules.catalog.repository.CatalogRepository;
import lib.base.backend.persistance.GenericPersistence;
import lib.base.backend.pojo.catalog.CatalogDataPojo;
import lib.base.backend.utils.CatalogUtil;
import project.statement.track.app.beans.entity.CatalogIssueEntity;
import project.statement.track.app.beans.pojos.entity.CatalogIssuePojo;
import project.statement.track.app.beans.pojos.petition.data.GenericCatalogDataPojo;
import project.statement.track.app.beans.pojos.petition.data.GetCatalogIssueDataPojo;
import project.statement.track.app.beans.pojos.petition.request.catalog.CrudCatalogIssueRequestPojo;
import project.statement.track.app.beans.pojos.petition.request.catalog.GenericCatalogIdRequestPojo;
import project.statement.track.app.repository.CatalogsRepository;
import project.statement.track.app.repository.MovementsIssueRepository;
import project.statement.track.app.vo.catalogs.CatalogsErrorMessage;
import project.statement.track.modules.business.MainBusiness;

@Component
public class CrudCatalogIssueBusiness extends MainBusiness {

	@SuppressWarnings("rawtypes")
	@Autowired
	protected GenericPersistence genericCustomPersistance;
	
	@Autowired
	CatalogRepository catalogBaseRepository;
	
	@Autowired
	MovementsIssueRepository movementsIssueRepository;
	
	@Autowired
	CatalogsRepository catalogsRepository;
	
	CatalogUtil catalogUtil = new CatalogUtil();
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public GetCatalogIssueDataPojo executeGetCatalogIssue(GenericCatalogIdRequestPojo requestPojo) {
		
		CatalogIssueEntity catalogIssue = (CatalogIssueEntity) genericCustomPersistance.findById(CatalogIssueEntity.class, requestPojo.getId());
		CatalogIssuePojo catalogIssuePojo = buildEntityToPojoUtil.mapCatalogIssuePojo(null, catalogIssue);
		
		GetCatalogIssueDataPojo responsePojo = new GetCatalogIssueDataPojo();
		responsePojo.setCatalogIssueData(catalogIssuePojo);
		
		return responsePojo;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public CatalogDataPojo executeGetCatalogIssues() {
		
		List<CatalogIssueEntity> catalogList = genericCustomPersistance.findAll(CatalogIssueEntity.class);
		List<CatalogIssuePojo> catalogPojoList = new ArrayList<>();
		
		for (CatalogIssueEntity catalogIssue: catalogList) {
			
			CatalogIssuePojo catalogIssuePojo = new CatalogIssuePojo();
			catalogIssuePojo.setDescription(catalogIssue.getDescription());
			catalogIssuePojo.setDescriptionCustom(catalogIssue.getDescriptionSnowball());
			catalogIssuePojo.setInitials(catalogIssue.getInitials());
			catalogIssuePojo.setId(catalogIssue.getId());
			
			catalogPojoList.add(catalogIssuePojo);
		}
		
		CatalogDataPojo responsePojo = new CatalogDataPojo();
		responsePojo.setCatalogs(catalogPojoList);
		
		return responsePojo;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public GenericCatalogDataPojo executeSaveUpdateCatalogIssue(CrudCatalogIssueRequestPojo requestPojo, CrudOptionsEnum crudOptionsEnum) {
		
		CatalogIssuePojo catalogData = requestPojo.getCatalogData();
		CatalogIssueEntity catalogIssue = crudOptionsEnum.equals(CrudOptionsEnum.SAVE) ? null : (CatalogIssueEntity) genericCustomPersistance.findById(CatalogIssueEntity.class, catalogData.getId());
		
		catalogIssue = buildPojoToEntityUtil.generateCatalogIssueEntity(catalogIssue, catalogData);
		
		if (crudOptionsEnum.equals(CrudOptionsEnum.SAVE))
			genericCustomPersistance.save(catalogIssue);
		else
			genericCustomPersistance.update(catalogIssue);
		
		GenericCatalogDataPojo dataPojo = new GenericCatalogDataPojo();
		dataPojo.setId(catalogIssue.getId());
		
		return dataPojo;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public GenericCatalogDataPojo executeDeleteCatalogIssue(GenericCatalogIdRequestPojo requestPojo) throws BusinessException {
	
		if (movementsIssueRepository.verifyIssueRegistered(requestPojo.getId()))
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgIssueTransactionRegistered());
		
		catalogBaseRepository.deleteCatalog(CatalogIssueEntity.class, requestPojo.getId());
		
		GenericCatalogDataPojo dataDto = new GenericCatalogDataPojo();
		dataDto.setId(requestPojo.getId());
		
		return dataDto;
	}
}
