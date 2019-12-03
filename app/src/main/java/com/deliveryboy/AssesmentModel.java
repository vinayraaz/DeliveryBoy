package com.deliveryboy;

public class AssesmentModel {

    private String grade;
    private Long minMarks;
    private Long maxMarks;

    private String name;
    private String count;
    private String marks;
    private boolean isSelect;

    public AssesmentModel(String name, String marks, boolean isSelect) {
        this.name = name;
        this.marks = marks;
        this.isSelect = isSelect;
    }

    public AssesmentModel(String grade, Long minMarks, Long maxMarks) {
        this.grade = grade;
        this.minMarks = minMarks;
        this.maxMarks = maxMarks;
    }

    public AssesmentModel(String name, String count){
        this.name = name;
        this.count = count;

    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }


    public String getGrade() {
        return grade;
    }

    public Long getMinMarks() {
        return minMarks;
    }

    public void setMinMarks(Long minMarks) {
        this.minMarks = minMarks;
    }

    public Long getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Long maxMarks) {
        this.maxMarks = maxMarks;
    }
}
