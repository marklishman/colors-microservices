package io.lishman.webflux.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public final class Employee {

    @Id
    private String employeeId;
    private String name;
    private String employeeName;
    private String email;
    private String phone;
    private String website;

    public Employee() {
    }

    public Employee(final String name,
                    final String employeeName,
                    final String email,
                    final String phone,
                    final String website) {
        this.name = name;
        this.employeeName = employeeName;
        this.email = email;
        this.phone = phone;
        this.website = website;
    }

    public Employee(final String employeeId,
                    final String name,
                    final String employeeName,
                    final String email,
                    final String phone,
                    final String website) {
        this(name, employeeName, email, phone, website);
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(employeeName, employee.employeeName) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(phone, employee.phone) &&
                Objects.equals(website, employee.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, name, employeeName, email, phone, website);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
