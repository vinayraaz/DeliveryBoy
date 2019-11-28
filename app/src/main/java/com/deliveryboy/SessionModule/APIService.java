package in.varadhismartek.patashalaerp.Retrofit;


import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by varadhi on 10/3/18.
 */

public interface APIService {



    // select modules

    @FormUrlEncoded
    @POST("get_school_modules_list")
    Call<Object> getModuleList(
            @Field("school_id") String school_id);


    @FormUrlEncoded
    @POST("add_school_modules")
    Call<Object> addSchoolModules(
            @Field("school_id") String school_id,
            @Field("modules_name") JSONObject modules_name,
            @Field("added_employeeid") String added_employeeid);



    @FormUrlEncoded
    @POST("update_school_modules_status")
    Call<Object> updateModuleStatus(
            @Field("school_id") String school_id,
            @Field("modules_name") JSONObject modules_name,
            @Field("updated_employeeid") String updated_employeeid);


    // wings modules

    @FormUrlEncoded
    @POST("get_school_wings")
    Call<Object> gettingWings(
            @Field("school_id") String school_id

    );

    @FormUrlEncoded
    @POST("add_school_wings")
    Call<Object> addWings(@Field("school_id") String school_id,
                          @Field("wings_names") JSONObject jsonObject,
                          @Field("added_employeeid") String added_employeeid);

    @FormUrlEncoded
    @POST("update_school_wing_name")
    Call<Object> updateSchoolWingName(
            @Field("school_id") String school_id,
            @Field("old_wing_name") String old_wing_name,
            @Field("new_wing_name") String new_wing_name,
            @Field("updated_employeeid") String updated_employeeid

    );


    @FormUrlEncoded
    @POST("update_school_wings_status")
    Call<Object>updateWingsStatus(@Field("school_id") String school_id,
                                  @Field("wings_names") JSONObject jsonObject,
                                  @Field("updated_employeeid") String added_employeeid);

// department module

    @FormUrlEncoded
    @POST("get_school_department")
    Call<Object> gettingDept(
            @Field("school_id") String mobileNumber,
            @Field("wings_name") JSONObject wing_name

    );


    @FormUrlEncoded
    @POST("add_school_department")
    Call<Object> addingDept(
            @Field("school_id") String school_id,
            @Field("wing_name") String wing_name,
            @Field("departments_name") JSONObject departments_name,
            @Field("added_employeeid") String added_employeeid
    );

    @FormUrlEncoded
    @POST("update_school_department_name")
    Call<Object> updateDeptName(
            @Field("school_id") String mobileNumber,
            @Field("wing_name") String wingName,
            @Field("old_department_name") String oldDeptName,
            @Field("new_department_name") String newDeptName,
            @Field("updated_employeeid") String updated_employeeid
    );

    @FormUrlEncoded
    @POST("update_school_departments_status")
    Call<Object> updateDeptStatus(
            @Field("school_id") String school_id,
            @Field("wing_name") String wing_name,
            @Field("departments_name") JSONObject departments_name,
            @Field("updated_employeeid") String added_employeeid
    );


// roles module

    @FormUrlEncoded
    @POST("get_school_roles")
    Call<Object> gettingRoles(
            @Field("school_id") String school_id,
            @Field("wings_name") JSONObject wingName,
            @Field("departments_name") JSONObject deptName);


    @FormUrlEncoded
    @POST("add_school_roles")
    Call<Object> addWingRoles(
            @Field("school_id") String school_id,
            @Field("wing_name") String wingName,
            @Field("department_name") String deptName,
            @Field("roles_name") JSONObject roles,
            @Field("added_employeeid") String employeeid
    );


    @FormUrlEncoded
    @POST("update_school_role_name")
    Call<Object> updateRolesName(
            @Field("school_id") String school_id,
            @Field("wing_name") String wingName,
            @Field("department_name") String deptName,
            @Field("old_role_name") String oldRole,
            @Field("new_role_name") String newRole,
            @Field("updated_employeeid") String updated_employeeid
    );


    @FormUrlEncoded
    @POST("update_school_roles_status")
    Call<Object> updateRolesStatus(
            @Field("school_id") String school_id,
            @Field("wing_name") String wingName,
            @Field("department_name") String deptName,
            @Field("roles_name") JSONObject roles_name,
            @Field("updated_employeeid") String updated_employeeid
    );


//school barrier

    @FormUrlEncoded
    @POST("update_school_student_barrier")
    Call<Object> addStudentBarrier(@Field("school_id") String school_id,
                                   @Field("default_student_admission_no") String default_admission_no,
                                   @Field("minimum_percentage_required") String minimum_percentage_required,
                                   @Field("father_qualification") String father_qualification,
                                   @Field("mother_qualification") String mother_qualification,
                                   @Field("food_facility") String food_facility,
                                   @Field("transport_facility") String transport_facility,
                                   @Field("no_of_guardians") String no_of_guardians,
                                   @Field("updated_employee_id") String updated_employee_id
                                   );

    @FormUrlEncoded
    @POST("get_school_student_barrier")
    Call<Object> getstatusStudentBarriers(
            @Field("school_id") String get_school_id);


//staff barriers

    @FormUrlEncoded
    @POST("get_school_staff_barrier")
    Call<Object> getStaffBarriers(
            @Field("school_id") String get_school_id);

    @FormUrlEncoded
    @POST("add_school_staff_barrier")
    Call<Object> addStaffBarrier(
            @Field("school_id") String schoolId,
            @Field("default_teacher_admission_no") String default_teacher_admission_no,
            @Field("added_by_employeeid") String added_by_employeeid);


    @FormUrlEncoded
    @POST("update_school_staff_barrier")
    Call<Object> updateStaffBarriers(
            @Field("school_id") String get_school_id,
            @Field("updated_teacher_admission_no") String updated_teacher_admission_no,
            @Field("updated_by_employeeid") String updated_by_employeeid);

    //Division

    @FormUrlEncoded
    @POST("get_divisions")
    Call<Object> getDivisions(
            @Field("school_id") String school_id);

    @FormUrlEncoded
    @POST("add_division")
    Call<Object> addDivision(
            @Field("school_id") String school_id,
            @Field("data") String data,
            @Field("employee_uuid") String employee_uuid);

    @FormUrlEncoded
    @POST("update_division_name")
    Call<Object> updateDivisionName(
            @Field("school_id") String school_id,
            @Field("division_old_name") String old_name,
            @Field("division_new_name") String new_name,
            @Field("employee_uuid") String employee_uuid
    );

    @FormUrlEncoded
    @POST("update_division_status")
    Call<Object> updateDivisionStatus(
            @Field("school_id") String school_id,
            @Field("divisions") JSONArray divisions,
            @Field("employee_uuid") String employee_uuid);


//maker checker


    @FormUrlEncoded
    @POST("add_school_maker_and_checker")
    Call<Object> addMakerChecker(
            @Field("school_id") String school_id,
            @Field("module_name") String module_name,
            @Field("added_employeeid") String added_employeeid,
            @Field("makers") JSONObject maker,
            @Field("checkers") JSONObject  checkers
    );

    @FormUrlEncoded
    @POST("get_school_maker_and_checker")
    Call<Object> getMakerChecker(
            @Field("school_id") String school_id
    );


    @FormUrlEncoded
    @POST("delete_school_maker_and_checker")
    Call<Object> deleteMakerChecker(
            @Field("school_id") String school_id,
            @Field("module_name") String module_name,
            @Field("deleting_employeeid") String deleting_employeeid
    );

    //Class Module


    @FormUrlEncoded
    @POST("get_school_classes_sections")
    Call<Object> getClassSections(
            @Field("school_id") String school_id,
            @Field("divisions") JSONObject division
    );


    @FormUrlEncoded
    @POST("add_classes_sections")
    Call<Object> addClassSections(
            @Field("school_id") String school_id,
            @Field("division_name") String  division,
            @Field("classes_sections") JSONObject  classSection,
            @Field("added_employeeid") String  employeeID
    );


    @FormUrlEncoded
    @POST("del_classes_sections")
    Call<Object> deleteClassSections(
            @Field("school_id") String school_id,
            @Field("classes_sections") JSONObject  classSection,
            @Field("deleting_employeeid") String  employeeID
    );

    //sessions
    @FormUrlEncoded
    @POST("add_school_sessions")
    Call<Object> addSession(
            @Field("school_id") String school_id,
            @Field("academic_start_date") String  fdate,
            @Field("academic_end_date") String  todate,
            @Field("weekly_working_days") String  workingday,
            @Field("sessions") JSONObject  sessions,
            @Field("added_employeeid") String  empID
    );

    @FormUrlEncoded
    @POST("get_school_academic_years")
    Call<Object> getAcademicYear(
            @Field("school_id") String school_id
    );

    @FormUrlEncoded
    @POST("get_school_sessions")
    Call<Object> getSessions(
            @Field("school_id") String school_id,
             @Field("academic_start_date") String  startDate,
            @Field("academic_end_date") String  toDate
    );

    @FormUrlEncoded
    @POST("delete_school_sessions")
    Call<Object> deleteSessions(
            @Field("school_id") String school_id,
            @Field("academic_start_date") String  startDate,
            @Field("academic_end_date") String  endDate,
            @Field("session_serial_no") String  serialNo,
            @Field("added_employeeid") String  empId,
            @Field("session_from_date") String  fromdate,
            @Field("session_to_date") String  todate
    );

    @FormUrlEncoded
    @POST("update_school_sessions")
    Call<Object> upDateSession(
            @Field("school_id") String school_id,
            @Field("academic_start_date") String  startDate,
            @Field("academic_end_date") String  endDate,
            @Field("weekly_working_days") String  workday,
            @Field("added_employeeid") String  empId,
            @Field("session_from_date") String  fromdate,
            @Field("session_to_date") String  todate
    );


    //Assessment -> Grade


    @FormUrlEncoded
    @POST("add_grade_barrier")
    Call<Object>addGradeBarrier(
            @Field("school_id") String school_id,
            @Field("grade_data") JSONObject grade_data,
            @Field("employee_uuid") String employee_uuid
    );


    @FormUrlEncoded
    @POST("get-status-grade-barrier")
    Call<Object> getGradeBarrier(
            @Field("school_id") String school_id);

    /*Homework*/

    @FormUrlEncoded
    @POST("get_homework_barrier")
    Call<Object> getHomeworkBarrier(
            @Field("school_id") String school_id
    );

    @FormUrlEncoded
    @POST("add_homework_barrier")
    Call<Object> addHomeworkBarrier(
            @Field("school_id") String school_id,
            @Field("division") String division,
            @Field("homework_status") String homework_status,
            @Field("no_of_homeworks") String no_of_homeworks,
            @Field("custom_employee_id") String custom_employee_id
    );

    @FormUrlEncoded
    @POST("update_homework_barrier")
    Call<Object> updateHomeworkBarrier(
            @Field("school_id") String school_id,
            @Field("division") String division,
            @Field("no_of_homeworks") String no_of_homeworks,
            @Field("custom_employee_id") String custom_employee_id
    );

    @FormUrlEncoded
    @POST("update_school_homework_barrier_status")
    Call<Object> updateSchoolHomeworkBarrierStatus(
            @Field("school_id") String school_id,
            @Field("barrier_status") String barrier_status,
            @Field("employee_uuid") String employee_uuid
    );

    @FormUrlEncoded
    @POST("update_divisions_homework_barrier_status")
    Call<Object> updateDivisionsHomeworkBarrierStatus(
            @Field("school_id") String school_id,
            @Field("custom_employee_id") String custom_employee_id,
            @Field("data") String data
    );
}


