package project.statement.track.app.beans.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lib.base.backend.entity.generic.GenericCatalogIntEntity;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Entity
@Table(name="catalog_broker")
public class CatalogBrokerEntity extends GenericCatalogIntEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="id_type_currency")
	private Integer idTypeCurrency;

	private String acronym;

	@OneToMany(mappedBy="catalogBroker", fetch=FetchType.LAZY)
	private List<BrokerAccountEntity> brokerAccounts = new ArrayList<>();

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_type_currency", insertable=false, updatable=false)
	private CatalogTypeCurrencyEntity catalogTypeCurrency;
}