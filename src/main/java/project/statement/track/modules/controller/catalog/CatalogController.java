package project.statement.track.modules.controller.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lib.base.backend.exception.BaseException;
import lib.base.backend.pojo.catalog.CatalogRequestPojo;
import lib.base.backend.pojo.catalog.CatalogDataPojo;
import lib.base.backend.utils.RestUtil;
import project.statement.track.modules.business.catalog.CatalogBusiness;

@RestController
public class CatalogController {

	@Autowired
	CatalogBusiness catalogBusiness;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = "/api/admin/catalog/getAll", consumes = "application/json", produces = "application/json")
	public ResponseEntity catalogGetAll(@RequestBody CatalogRequestPojo requestPojo) throws BaseException {
		
		CatalogDataPojo dataPojo = catalogBusiness.executeGetCatalog(requestPojo);
		return new RestUtil().buildResponseSuccess(dataPojo, "Catalog getted");
	}

}
