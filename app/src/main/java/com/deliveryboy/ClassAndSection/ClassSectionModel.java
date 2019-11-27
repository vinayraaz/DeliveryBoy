package in.varadhismartek.patashalaerp.ClassAndSection;

import java.util.ArrayList;

public class ClassSectionModel {
    private String className;
    private ArrayList<String> listSection;

    public ClassSectionModel(String className, ArrayList<String> listSection) {
        this.className = className;
        this.listSection = listSection;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<String> getListSection() {
        return listSection;
    }

    public void setListSection(ArrayList<String> listSection) {
        this.listSection = listSection;
    }
}
