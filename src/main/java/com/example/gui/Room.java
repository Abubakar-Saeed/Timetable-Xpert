package com.example.gui;

public class Room extends DataBaseLayer {

    String RoomNo;
    int RoomID;
    String Department;
    int capacity;

    public Room(String roomNo, int roomID, String department, int capacity) {
        RoomNo = roomNo;
        RoomID = roomID;
        Department = department;
        this.capacity = capacity;
    }

    public String getRoomNo() {
        return RoomNo;
    }

    public void setRoomNo(String roomNo) {
        RoomNo = roomNo;
    }

    public int getRoomID() {
        return RoomID;
    }

    public void setRoomID(int roomID) {
        RoomID = roomID;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}




