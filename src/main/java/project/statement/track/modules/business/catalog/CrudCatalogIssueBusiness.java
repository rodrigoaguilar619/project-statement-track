package project.statement.track.modules.business.catalog;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.enumerators.CrudOptionsEnum;
import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.persistance.GenericPersistence;
import lib.base.backend.pojo.catalog.CatalogDataPojo;
import lib.base.backend.pojo.catalog.CatalogRequestPojo;
import lib.base.backend.utils.CatalogUtil;
import lib.base.backend.web.repository.CatalogBaseRepository;
import project.statement.track.app.beans.entity.CatalogIssue;
import project.statement.track.app.beans.pojos.entity.CatalogIssuePojo;
import project.statement.track.app.beans.pojos.petition.data.GenericCatalogDataPojo;
import project.statement.track.app.beans.pojos.petition.data.GetCatalogIssueDataPojo;
import project.statement.track.app.beans.pojos.petition.request.catalog.CrudCatalogIssueRequestPojo;
import project.statement.track.app.beans.pojos.petition.request.catalog.GenericCatalogIdRequestPojo;
import project.statement.track.app.repository.CatalogsRepository;
import project.statement.track.app.repository.MovementsIssueRepository;
import project.statement.track.app.utils.BuildEntityToPojoUtil;
import project.statement.track.app.utils.BuildPojoToEntityUtil;

@Component
public class CrudCatalogIssueBusiness {

	@SuppressWarnings("rawtypes")
	@Autowired
	protected GenericPersistence genericCustomPersistance;
	
	@Autowired
	CatalogBaseRepository catalogBaseRepository;
	
	@Autowired
	MovementsIssueRepository movementsIssueRepository;
	
	@Autowired
	CatalogsRepository catalogsRepository;
	
	@Autowired
	CatalogUtil catalogUtil;
	
	@Autowired
	BuildPojoToEntityUtil buildPojoToEntityUtil;
	
	@Autowired
	BuildEntityToPojoUtil buildEntityToPojoUtil;
	
	@SuppressWarnings("unchecked")
	@Transactional
	public GetCatalogIssueDataPojo executeGetCatalogIssue(GenericCatalogIdRequestPojo requestPojo) {
		
		CatalogIssue catalogIssue = (CatalogIssue) genericCustomPersistance.findById(CatalogIssue.class, requestPojo.getId());
		CatalogIssuePojo catalogIssuePojo = buildEntityToPojoUtil.mapCatalogIssuePojo(null, catalogIssue);
		
		GetCatalogIssueDataPojo responsePojo = new GetCatalogIssueDataPojo();
		responsePojo.setCatalogIssueData(catalogIssuePojo);
		
		return responsePojo;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public CatalogDataPojo executeGetCatalogIssues(CatalogRequestPojo requestPojo) {
		
		List<CatalogIssue> catalogList = genericCustomPersistance.findAll(CatalogIssue.class);
		List<CatalogIssuePojo> catalogPojoList = new ArrayList<>();
		
		for (CatalogIssue catalogIssue: catalogList) {
			
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
	@Transactional
	public GenericCatalogDataPojo executeSaveUpdateCatalogIssue(CrudCatalogIssueRequestPojo requestPojo, CrudOptionsEnum crudOptionsEnum) {
		
		CatalogIssuePojo catalogData = requestPojo.getCatalogData();
		CatalogIssue catalogIssue = crudOptionsEnum.equals(CrudOptionsEnum.SAVE) ? null : (CatalogIssue) genericCustomPersistance.findById(CatalogIssue.class, catalogData.getId());
		
		catalogIssue = buildPojoToEntityUtil.generateCatalogIssueEntity(catalogIssue, catalogData);
		
		if (crudOptionsEnum.equals(CrudOptionsEnum.SAVE))
			genericCustomPersistance.save(catalogIssue);
		else
			genericCustomPersistance.update(catalogIssue);
		
		GenericCatalogDataPojo dataPojo = new GenericCatalogDataPojo();
		dataPojo.setId(catalogIssue.getId());
		
		return dataPojo;
	}
	
	@Transactional
	public GenericCatalogDataPojo executeDeleteCatalogIssue(GenericCatalogIdRequestPojo requestPojo) throws BusinessException {
	
		if (movementsIssueRepository.verifyIssueRegistered(requestPojo.getId()))
			throw new BusinessException("Issue transactions registered, can´t be delete");
		
		catalogBaseRepository.deleteCatalog(CatalogIssue.class, requestPojo.getId());
		
		GenericCatalogDataPojo dataDto = new GenericCatalogDataPojo();
		dataDto.setId(requestPojo.getId());
		
		return dataDto;
	}
}
