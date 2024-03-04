package com.dfm.chatapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dfm.chatapp.entity.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

    Specialization save(Specialization specialization);

    Optional<Specialization> findById(Long id);

    void deleteById(Long id);

    Iterable<Specialization> findByAllergyImmunologyOrAnesthesiologyOrDermatologyOrDiagnosticRadiologyOrEmergencyMedicineOrFamilyMedicineOrInternalMedicineOrMedicalGeneticsOrNeurologyOrNuclearMedicineOrObstetricsGynaecologyOrOpthalmologyOrPathologyOrPediatricsOrPhysicalMedicineRehabilitationOrPreventiveMedicineOrPsychiatryOrRadiationOncologyOrSurgeryOrUrologyOrAlsOrAlzheimersDementiasOrArthritisOrAsthmaOrCancerOrCopdOrInflammatoryBowelDiseaseOrCysticFibrosisOrAlcoholAddictionOrPrescriptionDrugAddictionOrDrugAddictionOrHeroinAddictionOrOpioidAddictionOrMoodDisordersOrSchizophreniaOrAnxietyDisordersOrPersonalityDisordersOrEatingDisorders(
            boolean allergyImmunology, boolean anesthesiology, boolean dermatology, boolean diagnosticradiology,
            boolean emergencymedicine, boolean familymedicine, boolean internalmedicine, boolean medicalgenetics,
            boolean neurology, boolean nuclearmedicine, boolean obstetricsgynaecology, boolean opthalmology,
            boolean pathology, boolean pediatrics, boolean physicalmedicinerehabilitation,
            boolean preventivemedicine, boolean psychiatry, boolean radiationoncology, boolean surgery,
            boolean urology, boolean als, boolean alzheimersDementias, boolean arthritis, boolean asthma,
            boolean cancer, boolean copd, boolean inflammatoryboweldisease, boolean cysticfibrosis,
            boolean alcoholaddiction, boolean prescriptiondrugaddiction, boolean drugaddiction,
            boolean heroinaddiction, boolean opioidaddiction, boolean mooddisorders, boolean schizophrenia,
            boolean anxietydisorders, boolean personalitydisorders, boolean eatingdisorders);

}
