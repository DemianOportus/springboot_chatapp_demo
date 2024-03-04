package com.dfm.chatapp.mapper;

import com.dfm.chatapp.dto.SpecializationDTO;
import com.dfm.chatapp.entity.Specialization;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-07T09:07:04-0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.1 (Homebrew)"
)
@Component
public class ISpecializationMapperImpl implements ISpecializationMapper {

    @Override
    public SpecializationDTO entityToDto(Specialization specialization) {
        if ( specialization == null ) {
            return null;
        }

        SpecializationDTO specializationDTO = new SpecializationDTO();

        specializationDTO.setId( specialization.getId() );
        specializationDTO.setAllergyImmunology( specialization.isAllergyImmunology() );
        specializationDTO.setAnesthesiology( specialization.isAnesthesiology() );
        specializationDTO.setDermatology( specialization.isDermatology() );
        specializationDTO.setDiagnosticRadiology( specialization.isDiagnosticRadiology() );
        specializationDTO.setEmergencyMedicine( specialization.isEmergencyMedicine() );
        specializationDTO.setFamilyMedicine( specialization.isFamilyMedicine() );
        specializationDTO.setInternalMedicine( specialization.isInternalMedicine() );
        specializationDTO.setMedicalGenetics( specialization.isMedicalGenetics() );
        specializationDTO.setNeurology( specialization.isNeurology() );
        specializationDTO.setNuclearMedicine( specialization.isNuclearMedicine() );
        specializationDTO.setObstetricsGynaecology( specialization.isObstetricsGynaecology() );
        specializationDTO.setOpthalmology( specialization.isOpthalmology() );
        specializationDTO.setPathology( specialization.isPathology() );
        specializationDTO.setPediatrics( specialization.isPediatrics() );
        specializationDTO.setPhysicalMedicineRehabilitation( specialization.isPhysicalMedicineRehabilitation() );
        specializationDTO.setPreventiveMedicine( specialization.isPreventiveMedicine() );
        specializationDTO.setPsychiatry( specialization.isPsychiatry() );
        specializationDTO.setRadiationOncology( specialization.isRadiationOncology() );
        specializationDTO.setSurgery( specialization.isSurgery() );
        specializationDTO.setUrology( specialization.isUrology() );
        specializationDTO.setAls( specialization.isAls() );
        specializationDTO.setAlzheimersDementias( specialization.isAlzheimersDementias() );
        specializationDTO.setArthritis( specialization.isArthritis() );
        specializationDTO.setAsthma( specialization.isAsthma() );
        specializationDTO.setCancer( specialization.isCancer() );
        specializationDTO.setCopd( specialization.isCopd() );
        specializationDTO.setInflammatoryBowelDisease( specialization.isInflammatoryBowelDisease() );
        specializationDTO.setCysticFibrosis( specialization.isCysticFibrosis() );
        specializationDTO.setAlcoholAddiction( specialization.isAlcoholAddiction() );
        specializationDTO.setPrescriptionDrugAddiction( specialization.isPrescriptionDrugAddiction() );
        specializationDTO.setDrugAddiction( specialization.isDrugAddiction() );
        specializationDTO.setHeroinAddiction( specialization.isHeroinAddiction() );
        specializationDTO.setOpioidAddiction( specialization.isOpioidAddiction() );
        specializationDTO.setMoodDisorders( specialization.isMoodDisorders() );
        specializationDTO.setSchizophrenia( specialization.isSchizophrenia() );
        specializationDTO.setAnxietyDisorders( specialization.isAnxietyDisorders() );
        specializationDTO.setPersonalityDisorders( specialization.isPersonalityDisorders() );
        specializationDTO.setEatingDisorders( specialization.isEatingDisorders() );
        specializationDTO.setProblemGambling( specialization.isProblemGambling() );
        specializationDTO.setSubstanceDependency( specialization.isSubstanceDependency() );

        return specializationDTO;
    }

    @Override
    public Specialization dtoToEntity(SpecializationDTO specializationDTO) {
        if ( specializationDTO == null ) {
            return null;
        }

        Specialization specialization = new Specialization();

        specialization.setId( specializationDTO.getId() );
        specialization.setAllergyImmunology( specializationDTO.isAllergyImmunology() );
        specialization.setAnesthesiology( specializationDTO.isAnesthesiology() );
        specialization.setDermatology( specializationDTO.isDermatology() );
        specialization.setDiagnosticRadiology( specializationDTO.isDiagnosticRadiology() );
        specialization.setEmergencyMedicine( specializationDTO.isEmergencyMedicine() );
        specialization.setFamilyMedicine( specializationDTO.isFamilyMedicine() );
        specialization.setInternalMedicine( specializationDTO.isInternalMedicine() );
        specialization.setMedicalGenetics( specializationDTO.isMedicalGenetics() );
        specialization.setNeurology( specializationDTO.isNeurology() );
        specialization.setNuclearMedicine( specializationDTO.isNuclearMedicine() );
        specialization.setObstetricsGynaecology( specializationDTO.isObstetricsGynaecology() );
        specialization.setOpthalmology( specializationDTO.isOpthalmology() );
        specialization.setPathology( specializationDTO.isPathology() );
        specialization.setPediatrics( specializationDTO.isPediatrics() );
        specialization.setPhysicalMedicineRehabilitation( specializationDTO.isPhysicalMedicineRehabilitation() );
        specialization.setPreventiveMedicine( specializationDTO.isPreventiveMedicine() );
        specialization.setPsychiatry( specializationDTO.isPsychiatry() );
        specialization.setRadiationOncology( specializationDTO.isRadiationOncology() );
        specialization.setSurgery( specializationDTO.isSurgery() );
        specialization.setUrology( specializationDTO.isUrology() );
        specialization.setAls( specializationDTO.isAls() );
        specialization.setAlzheimersDementias( specializationDTO.isAlzheimersDementias() );
        specialization.setArthritis( specializationDTO.isArthritis() );
        specialization.setAsthma( specializationDTO.isAsthma() );
        specialization.setCancer( specializationDTO.isCancer() );
        specialization.setCopd( specializationDTO.isCopd() );
        specialization.setInflammatoryBowelDisease( specializationDTO.isInflammatoryBowelDisease() );
        specialization.setCysticFibrosis( specializationDTO.isCysticFibrosis() );
        specialization.setAlcoholAddiction( specializationDTO.isAlcoholAddiction() );
        specialization.setPrescriptionDrugAddiction( specializationDTO.isPrescriptionDrugAddiction() );
        specialization.setDrugAddiction( specializationDTO.isDrugAddiction() );
        specialization.setHeroinAddiction( specializationDTO.isHeroinAddiction() );
        specialization.setOpioidAddiction( specializationDTO.isOpioidAddiction() );
        specialization.setMoodDisorders( specializationDTO.isMoodDisorders() );
        specialization.setSchizophrenia( specializationDTO.isSchizophrenia() );
        specialization.setAnxietyDisorders( specializationDTO.isAnxietyDisorders() );
        specialization.setPersonalityDisorders( specializationDTO.isPersonalityDisorders() );
        specialization.setEatingDisorders( specializationDTO.isEatingDisorders() );
        specialization.setProblemGambling( specializationDTO.isProblemGambling() );
        specialization.setSubstanceDependency( specializationDTO.isSubstanceDependency() );

        return specialization;
    }

    @Override
    public List<SpecializationDTO> entityToDtos(Iterable<Specialization> specializations) {
        if ( specializations == null ) {
            return null;
        }

        List<SpecializationDTO> list = new ArrayList<SpecializationDTO>();
        for ( Specialization specialization : specializations ) {
            list.add( entityToDto( specialization ) );
        }

        return list;
    }

    @Override
    public List<Specialization> dtoToEntity(Iterable<SpecializationDTO> specializationDTOs) {
        if ( specializationDTOs == null ) {
            return null;
        }

        List<Specialization> list = new ArrayList<Specialization>();
        for ( SpecializationDTO specializationDTO : specializationDTOs ) {
            list.add( dtoToEntity( specializationDTO ) );
        }

        return list;
    }
}
