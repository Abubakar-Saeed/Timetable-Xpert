package com.timetablexpert;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public class Program extends DataBaseLayer {

    private String name;
    private int id;


    // Constructor initializes programs list
    public Program() {

    }
    public Program(int id, String name){

        this.id = id;
        this.name = name;
    }

    public String getProgramName(){

        return name;

    }
    public int getProgramID(){

        return id;
    }
    public void setProgramName(String a){

        this.name = a;
    }

    public void setProgramID (int id){

        this.id = id;
    }

}
