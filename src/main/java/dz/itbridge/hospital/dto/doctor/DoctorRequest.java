package dz.itbridge.hospital.dto.doctor;

public class DoctorRequest {
    public DoctorRequest(String name, String userName, String password, String isPasswordUpdated, String role) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.isPasswordUpdated = isPasswordUpdated;
        this.role = role;
    }

    private String name;
    private String userName;
    private String password;
    private String isPasswordUpdated;
    private String role;

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public boolean isPasswordUpdated() {
        return isPasswordUpdated.equals("true");
    }

    @Override
    public String toString() {
        return "DoctorRequest{" +
                "name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isPasswordUpdated='" + isPasswordUpdated + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

}
