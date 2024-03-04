package com.dfm.chatapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SpecializationDTO {

    @NotNull(message = "id is required")
    private Long id;

    @NotNull
    private boolean allergyImmunology;

    @NotNull
    private boolean anesthesiology;

    @NotNull
    private boolean dermatology;

    @NotNull
    private boolean diagnosticRadiology;

    @NotNull
    private boolean emergencyMedicine;

    @NotNull
    private boolean familyMedicine;

    @NotNull
    private boolean internalMedicine;

    @NotNull
    private boolean medicalGenetics;

    @NotNull
    private boolean neurology;

    @NotNull
    private boolean nuclearMedicine;

    @NotNull
    private boolean obstetricsGynaecology;

    @NotNull
    private boolean opthalmology;

    @NotNull
    private boolean pathology;

    @NotNull
    private boolean pediatrics;

    @NotNull
    private boolean physicalMedicineRehabilitation;

    @NotNull
    private boolean preventiveMedicine;

    @NotNull
    private boolean psychiatry;

    @NotNull
    private boolean radiationOncology;

    @NotNull
    private boolean surgery;

    @NotNull
    private boolean urology;

    @NotNull
    private boolean als;

    @NotNull
    private boolean alzheimersDementias;

    @NotNull
    private boolean arthritis;

    @NotNull
    private boolean asthma;

    @NotNull
    private boolean cancer;

    @NotNull
    private boolean copd;

    @NotNull
    private boolean inflammatoryBowelDisease;

    @NotNull
    private boolean cysticFibrosis;

    @NotNull
    private boolean alcoholAddiction;

    @NotNull
    private boolean prescriptionDrugAddiction;

    @NotNull
    private boolean drugAddiction;

    @NotNull
    private boolean heroinAddiction;

    @NotNull
    private boolean opioidAddiction;

    @NotNull
    private boolean moodDisorders;

    @NotNull
    private boolean schizophrenia;

    @NotNull
    private boolean anxietyDisorders;

    @NotNull
    private boolean personalityDisorders;

    @NotNull
    private boolean eatingDisorders;
    @NotNull
    private boolean problemGambling;
    @NotNull
    private boolean substanceDependency;

    public SpecializationDTO() {
        // this.id = UUID.randomUUID().toString();
    }

    public SpecializationDTO(Long id, boolean allergyImmunology, boolean anesthesiology, boolean dermatology,
            boolean diagnosticRadiology, boolean emergencyMedicine, boolean familyMedicine, boolean internalMedicine,
            boolean medicalGenetics, boolean neurology, boolean nuclearMedicine, boolean obstetricsGynaecology,
            boolean opthalmology, boolean pathology, boolean pediatrics, boolean physicalMedicineRehabilitation,
            boolean preventiveMedicine, boolean psychiatry, boolean radiationOncology, boolean surgery, boolean urology,
            boolean als, boolean alzheimersDementias, boolean arthritis, boolean asthma, boolean cancer,
            boolean copd, boolean inflammatoryBowelDisease, boolean cysticFibrosis, boolean alcoholAddiction,
            boolean prescriptionDrugAddiction, boolean drugAddiction, boolean heroinAddiction, boolean opioidAddiction,
            boolean moodDisorders, boolean schizophrenia, boolean anxietyDisorders, boolean personalityDisorders,
            boolean eatingDisorders, boolean problemGambling, boolean substanceDependency) {
        this.id = id;
        this.allergyImmunology = allergyImmunology;
        this.anesthesiology = anesthesiology;
        this.dermatology = dermatology;
        this.diagnosticRadiology = diagnosticRadiology;
        this.emergencyMedicine = emergencyMedicine;
        this.familyMedicine = familyMedicine;
        this.internalMedicine = internalMedicine;
        this.medicalGenetics = medicalGenetics;
        this.neurology = neurology;
        this.nuclearMedicine = nuclearMedicine;
        this.obstetricsGynaecology = obstetricsGynaecology;
        this.opthalmology = opthalmology;
        this.pathology = pathology;
        this.pediatrics = pediatrics;
        this.physicalMedicineRehabilitation = physicalMedicineRehabilitation;
        this.preventiveMedicine = preventiveMedicine;
        this.psychiatry = psychiatry;
        this.radiationOncology = radiationOncology;
        this.surgery = surgery;
        this.urology = urology;
        this.als = als;
        this.alzheimersDementias = alzheimersDementias;
        this.arthritis = arthritis;
        this.asthma = asthma;
        this.cancer = cancer;
        this.copd = copd;
        this.inflammatoryBowelDisease = inflammatoryBowelDisease;
        this.cysticFibrosis = cysticFibrosis;
        this.alcoholAddiction = alcoholAddiction;
        this.prescriptionDrugAddiction = prescriptionDrugAddiction;
        this.drugAddiction = drugAddiction;
        this.heroinAddiction = heroinAddiction;
        this.opioidAddiction = opioidAddiction;
        this.moodDisorders = moodDisorders;
        this.schizophrenia = schizophrenia;
        this.anxietyDisorders = anxietyDisorders;
        this.personalityDisorders = personalityDisorders;
        this.eatingDisorders = eatingDisorders;
        this.problemGambling = problemGambling;
        this.substanceDependency = substanceDependency;
    }

}