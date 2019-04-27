package io.lishman.webflux.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public final class Employee {

    @Id
    private String employeeId;
    private String name;
    private String employeeNumber;
    private String email;
    private String phone;

    public Employee() {
    }

    public Employee(final String name,
                    final String employeeNumber,
                    final String email,
                    final String phone) {
        this.name = name;
        this.employeeNumber = employeeNumber;
        this.email = email;
        this.phone = phone;
    }

    public Employee(final String employeeId,
                    final String name,
                    final String employeeNumber,
                    final String email,
                    final String phone) {
        this(name, employeeNumber, email, phone);
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(employeeNumber, employee.employeeNumber) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(phone, employee.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, name, employeeNumber, email, phone);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", employeeNumber='" + employeeNumber + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
