package project.afore.track.app.beans.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter @Getter
@Entity
@Table(name = "afore_contribution")
public class AforeContributionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@Column(name = "date_inserted")
    private LocalDate dateInserted;

    @Column(name = "id_concept")
    private Integer idConcept;

    @Column(name = "id_concept_unified")
    private Integer idConceptUnified;

    @Column(name = "id_section")
    private Integer idSection;

    @Column(name = "period_reference")
    private String periodReference;

	@Column(name = "quoted_days")
    private Integer quotedDays;

	@Column(name = "base_salary")
    private BigDecimal baseSalary;

    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime period;

    @ManyToOne
    @JoinColumn(name = "id_section", referencedColumnName = "id", insertable = false, updatable = false)
    private CatalogAforeSectionEntity catalogAforeSectionEntity;

    @ManyToOne
    @JoinColumn(name = "id_concept", referencedColumnName = "id", insertable = false, updatable = false)
    private CatalogAforeConceptEntity catalogAforeConceptEntity;

    @ManyToOne
    @JoinColumn(name = "id_concept_unified", referencedColumnName = "id", insertable = false, updatable = false)
    private CatalogAforeConceptUnifiedEntity catalogAforeConceptUnifiedEntity;
    
}
