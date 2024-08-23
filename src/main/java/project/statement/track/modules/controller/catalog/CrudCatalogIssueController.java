package project.statement.track.modules.controller.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.enumerators.CrudOptionsEnum;
import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.pojo.catalog.CatalogDataPojo;
import lib.base.backend.pojo.catalog.CatalogRequestPojo;
import lib.base.backend.utils.RestUtil;
import project.statement.track.app.beans.pojos.petition.data.GenericCatalogDataPojo;
import project.statement.track.app.beans.pojos.petition.data.GetCatalogIssueDataPojo;
import project.statement.track.app.beans.pojos.petition.request.catalog.CrudCatalogIssueRequestPojo;
import project.statement.track.app.beans.pojos.petition.request.catalog.GenericCatalogIdRequestPojo;
import project.statement.track.modules.business.catalog.CrudCatalogIssueBusiness;

@RestController
public class CrudCatalogIssueController {

	@Autowired
	CrudCatalogIssueBusiness crudCatalogIssueBusiness;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = "/api/admin/customCatalog/catalogIssue/getCatalogIssues", consumes = "application/json", produces = "application/json")
	public ResponseEntity getCatalogIssues(@RequestBody CatalogRequestPojo requestPojo) {
		
		CatalogDataPojo dataPojo = crudCatalogIssueBusiness.executeGetCatalogIssues(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Catalog issues getted");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = "/api/admin/customCatalog/catalogIssue/getCatalogIssue", consumes = "application/json", produces = "application/json")
	public ResponseEntity getCatalogIssue(@RequestBody GenericCatalogIdRequestPojo requestPojo) {
		
		GetCatalogIssueDataPojo dataPojo = crudCatalogIssueBusiness.executeGetCatalogIssue(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Catalog issue data getted");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = "/api/admin/customCatalog/catalogIssue/saveCatalogIssue", consumes = "application/json", produces = "application/json")
	public ResponseEntity saveCatalogIssue(@RequestBody CrudCatalogIssueRequestPojo requestPojo) {
		
		GenericCatalogDataPojo dataPojo = crudCatalogIssueBusiness.executeSaveUpdateCatalogIssue(requestPojo, CrudOptionsEnum.SAVE);
		return new RestUtil().buildResponseSuccess(dataPojo, "Catalog issues saved");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = "/api/admin/customCatalog/catalogIssue/updateCatalogIssue", consumes = "application/json", produces = "application/json")
	public ResponseEntity updateCatalogIssue(@RequestBody CrudCatalogIssueRequestPojo requestPojo) {
		
		GenericCatalogDataPojo dataPojo = crudCatalogIssueBusiness.executeSaveUpdateCatalogIssue(requestPojo, CrudOptionsEnum.UPDATE);
		return new RestUtil().buildResponseSuccess(dataPojo, "Catalog issues updated");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = "/api/admin/customCatalog/catalogIssue/deleteCatalogIssue", consumes = "application/json", produces = "application/json")
	public ResponseEntity deleteCatalogIssue(@RequestBody GenericCatalogIdRequestPojo requestPojo) throws BusinessException {
		
		GenericCatalogDataPojo dataPojo = crudCatalogIssueBusiness.executeDeleteCatalogIssue(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Catalog issues deleted");
	}
}
