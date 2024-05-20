package com.proyecto.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity(name = "EM_EMPLEADOS")
public class Employee {

    @Id
    @Column(name = "ID_EMPLEADO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmployee;

    @Column(name = "TX_NIF", length = 9, unique = true)
    @Size(min = 9, max = 9, message = "El NIF debe tener exactamente 9 caracteres")
    private String nif;

    @Column(name = "TX_NOMBRE", nullable = false, length = 30)
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 30, message = "El nombre no puede superar los 30 caracteres")
    private String name;

    @Column(name = "TX_APELLIDO1", nullable = false, length = 40)
    @NotBlank(message = "El primer apellido no puede estar vacío")
    @Size(max = 40, message = "El primer apellido no puede superar los 40 caracteres")
    private String surname1;

    @Column(name = "TX_APELLIDO2", nullable = false, length = 40)
    @NotBlank(message = "El segundo apellido no puede estar vacío")
    @Size(max = 40, message = "El segundo apellido no puede superar los 40 caracteres")
    private String surname2;

    @Column(name = "F_NACIMIENTO", nullable = false)
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate birthdate;

    @Column(name = "N_TELEFONO1", nullable = false, length = 12)
    @NotBlank(message = "El teléfono 1 no puede estar vacío")
    @Size(min = 9, max = 12, message = "El teléfono 1 debe tener entre 9 y 12 caracteres")
    private String phone1;

    @Column(name = "N_TELEFONO2", nullable = false, length = 12)
    @NotBlank(message = "El teléfono 2 no puede estar vacío")
    @Size(min = 9, max = 12, message = "El teléfono 2 debe tener entre 9 y 12 caracteres")
    private String phone2;

    @Column(name = "TX_EMAIL", nullable = false, length = 40)
    @Email(message = "Debe ser una dirección de correo electrónico válida")
    @NotBlank(message = "El email no puede estar vacío")
    @Size(max = 40, message = "El email no puede superar los 40 caracteres")
    private String email;

    @Column(name = "F_ALTA", nullable = false)
    @NotNull(message = "La fecha de alta es obligatoria")
    private LocalDate hireDate;

    @Column(name = "F_BAJA")
    private LocalDate terminationDate;

    @Column(name = "CX_EDOCIVIL", nullable = false, length = 1)
    @NotNull(message = "El estado civil es obligatorio")
    @Pattern(regexp = "[S|C]", message = "El estado civil debe ser 'S' (soltero), 'C' (casado)")
    private String maritalStatus;

    @Column(name = "B_SERVMILITAR", nullable = false, length = 1)
    @NotNull(message = "La información del servicio militar es obligatoria")
    @Pattern(regexp = "[S|N]", message = "El servicio militar debe ser 'S' (SI), 'N' (NO)")
    private String militaryService;

    /**Establece una relación con la entidad EmployeeProject
     * mappedBy una las tablas Employee y EmployeeProject a través de la propiedad employee
     * cascade = CascadeType.ALL  indica que todas las operaciones de persistencia (persist, merge, remove, refresh, detach) realizadas en la entidad Employee se deben propagar a las entidades EmployeeProject
     * orphanRemoval = true indica que las instancias de EmployeeProject que están asociadas con un Employee deben ser eliminadas si se eliminan de la colección employeeProjects
     */
  @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonBackReference
  private Set<EmployeeProject> employeeProjects = new HashSet<>();
    /**
     * Este es el campo que mantiene la colección de instancias EmployeeProject asociadas con una instancia de Employee.
     * new HashSet<>() inicializa esta colección para asegurar que nunca sea null. Esto facilita la adición de nuevos EmployeeProject a la colección sin necesidad de comprobar si la colección ya está inicializada.
     */

    public Employee() {
    }

    public Employee(String nif, String name, String surname1, String surname2, LocalDate birthdate, String phone1, String phone2, String email, LocalDate hireDate, LocalDate terminationDate, String maritalStatus, String militaryService) {
        this.nif = nif;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.birthdate = birthdate;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
        this.hireDate = hireDate;
        this.terminationDate = terminationDate;
        this.maritalStatus = maritalStatus;
        this.militaryService = militaryService;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMilitaryService() {
        return militaryService;
    }

    public void setMilitaryService(String militaryService) {
        this.militaryService = militaryService;
    }

    public Set<EmployeeProject> getEmployeeProjects() {
        return employeeProjects;
    }

    public void setEmployeeProjects(Set<EmployeeProject> employeeProjects) {
        this.employeeProjects = employeeProjects;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "idEmployee=" + idEmployee +
                ", nif='" + nif + '\'' +
                ", name='" + name + '\'' +
                ", surname1='" + surname1 + '\'' +
                ", surname2='" + surname2 + '\'' +
                ", birthdate=" + birthdate +
                ", phone1='" + phone1 + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", email='" + email + '\'' +
                ", hireDate=" + hireDate +
                ", terminationDate=" + terminationDate +
                ", maritalStatus=" + maritalStatus +
                ", militaryService=" + militaryService +
                '}';
    }
}
