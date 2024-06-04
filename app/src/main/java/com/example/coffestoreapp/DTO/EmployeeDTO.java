package com.example.coffestoreapp.DTO;

public class EmployeeDTO {
    private final String fullName, userName, password, email, phoneNumber, gender, birthday;
    private final int employId, roleId;

    private EmployeeDTO(EmployeeBuilder builder) {
        this.fullName = builder.fullName;
        this.userName = builder.userName;
        this.password = builder.password;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.gender = builder.gender;
        this.birthday = builder.birthday;
        this.employId = builder.employId;
        this.roleId = builder.roleId;
    }

    public static class EmployeeBuilder {
        private String fullName, userName, password, email, phoneNumber, gender, birthday;
        private int employId, roleId;

        public EmployeeBuilder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public EmployeeBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public EmployeeBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public EmployeeBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public EmployeeBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public EmployeeBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public EmployeeBuilder setBirthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        public EmployeeBuilder setEmployId(int employId) {
            this.employId = employId;
            return this;
        }

        public EmployeeBuilder setRoleId(int roleId) {
            this.roleId = roleId;
            return this;
        }

        public EmployeeDTO build() {
            return new EmployeeDTO(this);
        }
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getEmployId() {
        return employId;
    }

    public int getRoleId() {
        return roleId;
    }
}
