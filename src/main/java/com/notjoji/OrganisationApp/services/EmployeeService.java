package com.notjoji.OrganisationApp.services;

import com.notjoji.OrganisationApp.model.dto.EmployeeDTO;
import com.notjoji.OrganisationApp.model.dto.EmployeeNodeDTO;
import com.notjoji.OrganisationApp.model.entity.Employee;
import com.notjoji.OrganisationApp.model.entity.Organisation;
import com.notjoji.OrganisationApp.model.mappers.EmployeeMapper;
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
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private OrganisationRepo organisationRepo;

    private EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    public List<EmployeeDTO> getAll() {
        return employeeRepo.findAll().stream()
                .map(x -> employeeMapper.employeeToDTO(x))
                .collect(Collectors.toList());
    }

    public EmployeeDTO getById(Long id) {
        Optional<Employee> employee = employeeRepo.findById(id);
        return employee.isPresent() ? employeeMapper.employeeToDTO(employee.get()) : null;
    }

    public boolean add(EmployeeDTO dto) {
        Optional<Employee> employee = employeeRepo.findByName(dto.getName());
        if (employee.isPresent() || dto.getOrganisationName() == null && dto.getSupervisorName() == null)
            return false;
        Optional<Organisation> organisation = organisationRepo.findByName(dto.getOrganisationName());
        Optional<Employee> supervisor = employeeRepo.findByName(dto.getSupervisorName());
        Organisation o = organisation.orElse(null);
        Employee s = supervisor.orElse(null);
        if (dto.getOrganisationName() != null && o == null && s == null ||
                o != null && s == null ||
                o != null && o.getName().equals(s.getOrganisation().getName())) {
            employeeRepo.save(employeeMapper.employeeFromDTO(dto, o, s));
            return true;
        } else return false;
    }

    public boolean update(Long id, EmployeeDTO dto) {
        Optional<Employee> employee = employeeRepo.findById(id);
        Optional<Organisation> organisation = organisationRepo.findByName(dto.getOrganisationName());
        Optional<Employee> supervisor = employeeRepo.findByName(dto.getSupervisorName());
        Organisation o = organisation.orElse(null);
        Employee s = supervisor.orElse(null);
        if (!employee.isPresent() || o == null || s == null || s.getId().equals(id) || !o.getName().equals(s.getOrganisation().getName()))
            return false;
        Employee newEmployee = employeeMapper.employeeFromDTO(dto, o, s);
        newEmployee.setId(id);
        employeeRepo.save(newEmployee);
        return true;
    }

    public boolean delete(Long id) {
        try {
            Optional<Employee> employee = employeeRepo.findById(id);
            if (!employee.isPresent())
                return false;
            employeeRepo.delete(employee.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public EmployeeNodeDTO getTree(Long organisationId) {
        List<Employee> employeeList = employeeRepo.findAll()
                .stream()
                .filter(x -> x.getOrganisation().getId().equals(organisationId))
                .collect(Collectors.toList());

        if (employeeList.isEmpty())
            return null;

        List<EmployeeNodeDTO> employeeNodes = new ArrayList<>();

        for (Employee employee:employeeList) {
            EmployeeNodeDTO employeeNodeDTO = new EmployeeNodeDTO();
            employeeNodeDTO.setId(employee.getId());
            employeeNodeDTO.setName(employee.getName());
            if (employee.getSupervisor() != null)
                employeeNodeDTO.setSupervisorId(employee.getSupervisor().getId());
            employeeNodes.add(employeeNodeDTO);
        }

        for (EmployeeNodeDTO emloyeeNode:employeeNodes) {
            List<EmployeeNodeDTO> children = employeeNodes
                    .stream()
                    .filter(x -> x.getSupervisorId() != null)
                    .filter(x -> x.getSupervisorId().equals(emloyeeNode.getId()))
                    .collect(Collectors.toList());
            emloyeeNode.setChildren(children);
        }

        return employeeNodes.get(0);
    }
}
