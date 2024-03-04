package com.dfm.chatapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Table(name = "specializations")
public class Specialization {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Specialization() {
        // this.id = UUID.randomUUID().toString();
    }

    @Column(name = "allergy_immunology")
    private boolean allergyImmunology;

    @Column(name = "anesthesiology")
    private boolean anesthesiology;

    @Column(name = "dermatology")
    private boolean dermatology;

    @Column(name = "diagnostic_radiology")
    private boolean diagnosticRadiology;

    @Column(name = "emergency_medicine")
    private boolean emergencyMedicine;

    @Column(name = "family_medicine")
    private boolean familyMedicine;

    @Column(name = "internal_medicine")
    private boolean internalMedicine;

    @Column(name = "medical_genetics")
    private boolean medicalGenetics;

    @Column(name = "neurology")
    private boolean neurology;

    @Column(name = "nuclear_medicine")
    private boolean nuclearMedicine;

    @Column(name = "obstetrics_gynaecology")
    private boolean obstetricsGynaecology;

    @Column(name = "opthalmology")
    private boolean opthalmology;

    @Column(name = "pathology")
    private boolean pathology;

    @Column(name = "pediatrics")
    private boolean pediatrics;

    @Column(name = "physical_medicine_rehabilitation")
    private boolean physicalMedicineRehabilitation;

    @Column(name = "preventive_medicine")
    private boolean preventiveMedicine;

    @Column(name = "psychiatry")
    private boolean psychiatry;

    @Column(name = "radiation_oncology")
    private boolean radiationOncology;

    @Column(name = "surgery")
    private boolean surgery;

    @Column(name = "urology")
    private boolean urology;

    @Column(name = "als")
    private boolean als;

    @Column(name = "alzheimers_dementias")
    private boolean alzheimersDementias;

    @Column(name = "arthritis")
    private boolean arthritis;

    @Column(name = "asthma")
    private boolean asthma;

    @Column(name = "cancer")
    private boolean cancer;

    /*
     * Crohn’s Disease, Ulcerative Colitis,
     * Other Inflammatory Bowel Diseases,
     * Irritable Bowel Syndrome
     */
    @Column(name = "copd")
    private boolean copd;

    @Column(name = "inflammatory_bowel_disease")
    private boolean inflammatoryBowelDisease;

    @Column(name = "cystic_fibrosis")
    private boolean cysticFibrosis;

    @Column(name = "alcohol_addiction")
    private boolean alcoholAddiction;

    @Column(name = "prescription_drug_addiction")
    private boolean prescriptionDrugAddiction;

    @Column(name = "drug_addiction")
    private boolean drugAddiction;

    @Column(name = "heroin_addiction")
    private boolean heroinAddiction;

    @Column(name = "opioid_addiction")
    private boolean opioidAddiction;

    @Column(name = "mood_disorders")
    private boolean moodDisorders;

    @Column(name = "schizophrenia")
    private boolean schizophrenia;

    @Column(name = "anxiety_disorders")
    private boolean anxietyDisorders;

    @Column(name = "personality_disorders")
    private boolean personalityDisorders;

    @Column(name = "eating_disorders")
    private boolean eatingDisorders;

    @Column(name = "problem_gambling")
    private boolean problemGambling;

    @Column(name = "substance_dependency")
    private boolean substanceDependency;

    // public Specialization(String id) {
    // this.id = id;
    // }

    public Map<String, Object> getTrueFields() {
        Map<String, Object> trueFields = new HashMap<>();
        trueFields.put("id", this.id); // Add this line to include the id
        Map<String, Boolean> fields = new HashMap<>();
        for (Field field : Specialization.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.getType() == boolean.class && field.getBoolean(this)) {
                    fields.put(field.getName(), field.getBoolean(this));
                }

            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        trueFields.put("fields", fields);
        return trueFields;
    }

}
