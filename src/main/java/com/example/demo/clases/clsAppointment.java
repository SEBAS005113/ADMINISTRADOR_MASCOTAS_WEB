package com.example.demo.clases;

public class clsAppointment {
    private int id;
    private int idPet;
    private String petName;
    private String appointmentDate;
    private String reason;
    private String status;

    public clsAppointment(int id, int idPet, String petName, String appointmentDate, String reason, String status) {
        this.id = id;
        this.idPet = idPet;
        this.petName = petName;
        this.appointmentDate = appointmentDate;
        this.reason = reason;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdPet() { return idPet; }
    public void setIdPet(int idPet) { this.idPet = idPet; }
    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }
    public String getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}