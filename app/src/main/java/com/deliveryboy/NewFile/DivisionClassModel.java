package com.deliveryboy.NewFile;

import java.util.ArrayList;

import in.varadhismartek.patashalaerp.DivisionModule.ClassSectionAndDivisionModel;

public class DivisionClassModel {
    private String className;
    private ArrayList<ClassSectionAndDivisionModel> listSection;

    public DivisionClassModel(String className, ArrayList<ClassSectionAndDivisionModel> listSection) {
        this.className = className;
        this.listSection = listSection;
    }




    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<ClassSectionAndDivisionModel> getListSection() {
        return listSection;
    }

    public void setListSection(ArrayList<ClassSectionAndDivisionModel> listSection) {
        this.listSection = listSection;
    }
}
