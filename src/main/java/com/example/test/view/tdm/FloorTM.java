package com.example.test.view.tdm;

public class FloorTM {

    private int floorNo;
    private int noOfHouses;

    public FloorTM(int floorNo, int noOfHouses) {
        this.floorNo = floorNo;
        this.noOfHouses = noOfHouses;
    }

    public FloorTM() {
    }

    public int getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    public int getNoOfHouses() {
        return noOfHouses;
    }

    public void setNoOfHouses(int noOfHouses) {
        this.noOfHouses = noOfHouses;
    }
}
