package com.notjoji.OrganisationApp.services;

import com.notjoji.OrganisationApp.model.dto.OrganisationDTO;
import com.notjoji.OrganisationApp.model.dto.OrganisationNodeDTO;
import com.notjoji.OrganisationApp.model.entity.Employee;
import com.notjoji.OrganisationApp.model.entity.Organisation;
import com.notjoji.OrganisationApp.model.mappers.OrganisationMapper;
import com.notjoji.OrganisationApp.repo.EmployeeRepo;
import com.notjoji.OrganisationApp.repo.OrganisationRepo;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganisationService {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private OrganisationRepo organisationRepo;

    private OrganisationMapper organisationMapper = Mappers.getMapper(OrganisationMapper.class);

    public List<OrganisationDTO> getAll() {
        return organisationRepo.findAll().stream()
                .map(x -> organisationMapper.organisationToDTO(x))
                .collect(Collectors.toList());
    }

    public OrganisationDTO getById(Long id) {
        Optional<Organisation> organisation = organisationRepo.findById(id);
        return organisation.isPresent() ? organisationMapper.organisationToDTO(organisation.get()) : null;
    }

    public boolean add(OrganisationDTO dto) {
        Optional<Organisation> organisation = organisationRepo.findByName(dto.getName());
        if (organisation.isPresent())
            return false;
        Optional<Organisation> baseOrganisation = organisationRepo.findByName(dto.getBaseName());
        Optional<Employee> supervisor = employeeRepo.findByName(dto.getSupervisorName());
        Organisation bo = baseOrganisation.orElse(null);
        Employee s = supervisor.orElse(null);
        if (dto.getSupervisorName() != null && bo == null && s == null ||
                bo != null && s == null ||
                bo != null && bo.getName().equals(s.getOrganisation().getName())) {
            organisationRepo.save(organisationMapper.organisationFromDTO(dto, bo, s));
            return true;
        } else return false;
    }

    public boolean update(Long id, OrganisationDTO dto) {
        Optional<Organisation> organisation = organisationRepo.findById(id);
        Optional<Organisation> baseOrganisation = organisationRepo.findByName(dto.getBaseName());
        Optional<Employee> supervisor = employeeRepo.findByName(dto.getSupervisorName());
        Organisation bo = baseOrganisation.orElse(null);
        Employee s = supervisor.orElse(null);
        if (!organisation.isPresent() || bo == null || s == null || bo.getId().equals(id) || !bo.getName().equals(s.getOrganisation().getName()))
            return false;
        Organisation newOrganisation = organisationMapper.organisationFromDTO(dto, bo, s);
        newOrganisation.setId(id);
        organisationRepo.save(newOrganisation);
        return true;
    }

    public boolean delete(Long id) {
        try {
            Optional<Organisation> organisation = organisationRepo.findById(id);
            if (!organisation.isPresent())
                return false;
            organisationRepo.delete(organisation.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public OrganisationNodeDTO getTree() {
        List<Organisation> organisationsList = organisationRepo.findAll();

        if (organisationsList.isEmpty())
            return null;

        OrganisationNodeDTO root = new OrganisationNodeDTO();
        root.setId((long)0);
        root.setName("Все организации");

        List<OrganisationNodeDTO> organisationNodes = new ArrayList<>();

        for (Organisation organisation:organisationsList) {
            OrganisationNodeDTO organisationNodeDTO = new OrganisationNodeDTO();
            organisationNodeDTO.setId(organisation.getId());
            organisationNodeDTO.setName(organisation.getName());
            if (organisation.getBaseOrganisation() != null)
                organisationNodeDTO.setBaseId(organisation.getBaseOrganisation().getId());
            organisationNodes.add(organisationNodeDTO);
        }

        for (OrganisationNodeDTO organisationNode:organisationNodes) {
            List<OrganisationNodeDTO> children = organisationNodes
                    .stream()
                    .filter(x -> x.getBaseId() != null)
                    .filter(x -> x.getBaseId().equals(organisationNode.getId()))
                    .collect(Collectors.toList());
            organisationNode.setChildren(children);
        }

        root.setChildren(organisationNodes
                .stream()
                .filter(x -> x.getBaseId() == null)
                .collect(Collectors.toList()));

        return root;
    }
}
