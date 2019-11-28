package in.varadhismartek.patashalaerp.SessionModule;

public class SessionModel {


    private String added_employeeid, added_employee_name, to_date, session_serial_no, from_date,startDate,endDate;

        public SessionModel(String added_employeeid, String added_employee_name, String to_date, String session_serial_no, String from_date, String startDate, String endDate) {
        this.added_employeeid = added_employeeid;
        this.added_employee_name = added_employee_name;
        this.to_date = to_date;
        this.session_serial_no = session_serial_no;
        this.from_date = from_date;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getAdded_employeeid() {
        return added_employeeid;
    }

    public void setAdded_employeeid(String added_employeeid) {
        this.added_employeeid = added_employeeid;
    }

    public String getAdded_employee_name() {
        return added_employee_name;
    }

    public void setAdded_employee_name(String added_employee_name) {
        this.added_employee_name = added_employee_name;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getSession_serial_no() {
        return session_serial_no;
    }

    public void setSession_serial_no(String session_serial_no) {
        this.session_serial_no = session_serial_no;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}