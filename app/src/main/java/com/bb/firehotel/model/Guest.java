package com.bb.firehotel.model;

public class Guest {

    private String GuestName;
    private String GuestRoomNum;

    public Guest(){}

    public Guest(String guestName, String guestRoomNum) {
        this.GuestName = guestName;
        this.GuestRoomNum = guestRoomNum;
    }

    public String getGuestName() {
        return GuestName;
    }

    public void setGuestName(String guestName) {
        this.GuestName = guestName;
    }

    public String getGuestRoomNum() {
        return GuestRoomNum;
    }

    public void setGuestRoomNum(String guestRoomNum) {
        this.GuestRoomNum = guestRoomNum;
    }

    @Override
    public String toString(){
        return "Name: " + getGuestName() + " Room Number: " + getGuestRoomNum();
    }
}
