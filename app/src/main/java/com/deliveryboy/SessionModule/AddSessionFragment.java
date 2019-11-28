package com.deliveryboy.SessionModule;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.GeneralClass.CustomSpinnerAdapter;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class AddSessionFragment extends Fragment implements View.OnClickListener {
    private ImageView iv_backBtn;
    private TextView tv_fromDate, tv_toDate;
    private RelativeLayout rl_fromDate, rl_toDate;
    private Button bt_add, bt_submit;
    private Spinner spinner;
    private TextView tvSessionName, tv_minus, tv_noSession, tv_plus;
    private RecyclerView recycler_view;
    ImageView iv_down, iv_up;
    LinearLayout ll_showHide, ll_NoOfSession;

    SimpleDateFormat simpleDate;

    private int year, month, date, rowNumber = 1;
    private String currentDate, str_sessionName = "", str_from_date, str_toDate, startYear = "", endYear = "";
    ArrayList<String> sessionList, spinnerList;
    ArrayList<Integer> list;
    CardView cardView;
    APIService apiService;
    List<SessionModel> modelList = new ArrayList<>();
    String StartDate, EndDate, added_employeeid, added_employee_name, to_date, from_date, session_serial_no;

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_session, container, false);

        initViews(view);
        initListeners();
        getAcademicYear();
        return view;
    }


    private void initViews(View view) {
        apiService = ApiUtils.getAPIServicePatashala();
        iv_backBtn = view.findViewById(R.id.iv_backBtn);
        tv_fromDate = view.findViewById(R.id.tv_fromDate);
        tv_toDate = view.findViewById(R.id.tv_toDate);
        spinner = view.findViewById(R.id.spinnerForSession);
        bt_add = view.findViewById(R.id.bt_add);
        bt_submit = view.findViewById(R.id.bt_submit);
        //recycler_view = view.findViewById(R.id.recycler_view);

        rl_fromDate = view.findViewById(R.id.rl_fromDate);
        rl_toDate = view.findViewById(R.id.rl_toDate);

        tvSessionName = view.findViewById(R.id.tvSessionName);
        tv_minus = view.findViewById(R.id.tv_minus);
        tv_noSession = view.findViewById(R.id.tv_noSession);
        tv_plus = view.findViewById(R.id.tv_plus);
        recycler_view = view.findViewById(R.id.recycler_view);

        iv_down = view.findViewById(R.id.iv_down);
        iv_up = view.findViewById(R.id.iv_up);
        ll_showHide = view.findViewById(R.id.ll_showHide);
        ll_NoOfSession = view.findViewById(R.id.ll_NoOfSession);
        //cardView.setVisibility(View.GONE);

        sessionList = new ArrayList<>();
        spinnerList = new ArrayList<>();

        spinnerList.add(0, "Select Academic Year");
        list = new ArrayList<>();
        list.add(0, 1);


    }

    private void initListeners() {
        iv_backBtn.setOnClickListener(this);
        rl_fromDate.setOnClickListener(this);
        rl_toDate.setOnClickListener(this);
        bt_add.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        tv_minus.setOnClickListener(this);
        tv_plus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_backBtn:
                getActivity().onBackPressed();
                break;
            case R.id.rl_fromDate:
                fromDatePickerDialog();
                break;
            case R.id.rl_toDate:
                toDatePickerDialog();
                break;
            case R.id.bt_add:
               /* if (startYear.equals("") || endYear.equals("")) {
                    Toast.makeText(getActivity(), "Please Select Academics Years", Toast.LENGTH_SHORT).show();

                }*/
                if (tv_fromDate.getText().toString().equals("From Date") || tv_toDate.getText().toString().equals("To Date")) {
                    Toast.makeText(getActivity(), "Please Select From and To Dates", Toast.LENGTH_SHORT).show();

                }
               else {

                    //String selectSession = startYear + " - " + endYear;
                    String selectSession = tv_fromDate.getText().toString() + " - " + tv_toDate.getText().toString();

                    sessionList.add(selectSession);
                    spinnerList.add(selectSession);
                    setSpinner();
                    tv_fromDate.setText("From Date");
                    tv_toDate.setText("To Date");
                    Toast.makeText(getActivity(), "Date Added Successfully", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_plus:
                Log.d("BUTTON*****3+", String.valueOf(rowNumber));

                if (rowNumber < 4) {
                    rowNumber = Integer.parseInt(tv_noSession.getText().toString());
                    rowNumber++;
                    tv_noSession.setText(String.valueOf(rowNumber));
                    Log.d("BUTTON****4+-", String.valueOf(rowNumber));


                    modelList.add(new SessionModel(added_employeeid, added_employee_name, to_date,
                            session_serial_no, from_date, StartDate, EndDate));
                    SessionAdapter adapter = new SessionAdapter(modelList, getActivity(), Constant.RV_SESSION_ROW);
                    recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recycler_view.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                   /* list.add(1);
                    SessionAdapter adapter = new SessionAdapter(getActivity(), list, Constant.RV_SESSION_ROW);
                    recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recycler_view.setAdapter(adapter);
                    adapter.notifyDataSetChanged();*/
                }
                break;
            case R.id.tv_minus:
                Log.d("BUTTON*****1-", String.valueOf(rowNumber));

                if (rowNumber > 1) {
                    rowNumber = Integer.parseInt(tv_noSession.getText().toString());
                    rowNumber--;
                    tv_noSession.setText(String.valueOf(rowNumber));
                    Log.d("BUTTON****2-", String.valueOf(rowNumber));

                    SessionAdapter adapter = new SessionAdapter(modelList, getActivity(), Constant.RV_SESSION_ROW);
                    recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recycler_view.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    modelList.remove(rowNumber - 1);
                  /*  list.remove(rowNumber - 1);
                    SessionAdapter adapter = new SessionAdapter(getActivity(), list, Constant.RV_SESSION_ROW);
                    recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recycler_view.setAdapter(adapter);
                    adapter.notifyDataSetChanged();*/
                }
                break;
            case R.id.bt_submit:

                JSONObject sessionObject = new JSONObject();
                JSONObject mainObject = new JSONObject();
                System.out.println("M****+" + modelList.size());
                for (int i = 0; i < modelList.size(); i++) {
                    String strFromDate = modelList.get(i).getFrom_date();
                    String strToDate = modelList.get(i).getTo_date();

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("from_date", strFromDate);
                        jsonObject.put("to_date", strToDate);
                        mainObject.put(String.valueOf(i + 1), jsonObject);
                        sessionObject.put("session", mainObject);

                    } catch (JSONException je) {

                    }

                }

                if (tv_fromDate.getText().toString().equals("From DateF") || tv_toDate.getText().toString().equals("To Date")) {
                    Toast.makeText(getActivity(), "Please Select date", Toast.LENGTH_SHORT).show();

                }
                apiService.addSession(Constant.SCHOOL_ID, tv_fromDate.getText().toString(), tv_toDate.getText().toString(),
                        "5", sessionObject, Constant.EMPLOYEE_BY_ID)
                        .enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                Log.d(TAG, "onResponse: ADDSession" + response.body());
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {

                            }
                        });

                /*Intent intent = new Intent(getActivity(), AdmissionBarriersActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                getActivity().startActivity(intent);
                getActivity().finish();*/


                break;

        }

    }

    private void getAcademicYear() {
        spinnerList.clear();
        apiService.getAcademicYear(Constant.SCHOOL_ID).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("SESSION**AYEAR", "onResponse: " + response.body());

                if (response.isSuccessful()) {
                    Log.i("DIVISION**GET", "" + response.body());
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = (String) object.get("status");

                        if (status.equalsIgnoreCase("success")) {
                            JSONObject jsonObject1 = object.getJSONObject("data");
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("Academic_years");

                            Iterator<String> keys = jsonObject2.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject jsonObjectValue = jsonObject2.getJSONObject(key);
                                String startDate = jsonObjectValue.getString("start_date");
                                String endDate = jsonObjectValue.getString("end_date");


                                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                                SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
                                SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");
                                try {
                                    Date fromYear = formater.parse(startDate);
                                    Date toYear = formater.parse(endDate);

                                    startYear = formatterDate.format(fromYear);
                                    endYear = formatterDate.format(toYear);
                                    String selectedYear = startYear + " - " + endYear;

                                    spinnerList.add(selectedYear);
                                    setSpinner();

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                            }

                        }
                    } catch (JSONException je) {

                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private void setSpinner() {
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getActivity(), spinnerList);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItemPosition() != 0) {
                    rowNumber = 1;
                    tv_noSession.setText(String.valueOf(rowNumber));
                    list.clear();

                    list.add(0, 1);

                    str_sessionName = spinner.getSelectedItem().toString();
                    tvSessionName.setText(str_sessionName);

                    //setRecyclerView();
                    getSchoolSession(str_sessionName);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getSchoolSession(String str_sessionName) {
        modelList.clear();
        EndDate = str_sessionName.substring(13);
        StartDate = str_sessionName.substring(0, Math.min(str_sessionName.length(), 10));
        System.out.println("str_sessionName***" + str_sessionName + "**" + StartDate + "**" + EndDate);
        apiService.getSessions(Constant.SCHOOL_ID, StartDate, EndDate).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d(TAG, "onResponse:getsession " + response.code());
                Log.d(TAG, "onResponse:getsession " + response.body());


                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = (String) object.get("status");

                        if (status.equalsIgnoreCase("success")) {
                            JSONObject jsonObject1 = object.getJSONObject("data");
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("sessions");

                            Iterator<String> keys = jsonObject2.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JSONObject jsonObjectValue = jsonObject2.getJSONObject(key);


                                added_employeeid = jsonObjectValue.getString("added_employeeid");
                                added_employee_name = jsonObjectValue.getString("added_employee_name");
                                to_date = jsonObjectValue.getString("to_date");
                                from_date = jsonObjectValue.getString("from_date");
                                session_serial_no = jsonObjectValue.getString("session_serial_no");


                                modelList.add(new SessionModel(added_employeeid, added_employee_name, to_date,
                                        session_serial_no, from_date, StartDate, EndDate));


                            }
                            System.out.println("modelList*****" + modelList.size());
                        }
                        tv_noSession.setText(String.valueOf(modelList.size()));
                        SessionAdapter adapter = new SessionAdapter(modelList, getActivity(), Constant.RV_SESSION_ROW);
                        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recycler_view.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    } catch (JSONException je) {

                    }
                }
                else {
                    modelList.clear();

                    Log.d(TAG, "onResponse:getsession " + modelList.size());
                    if (String.valueOf(response.code()).equals("404") ||(String.valueOf(response.code()).equals("409"))){

                        SessionAdapter adapter = new SessionAdapter(modelList,getActivity(), Constant.RV_SESSION_ROW_NODATA);
                        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recycler_view.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else {
                        SessionAdapter adapter = new SessionAdapter(modelList,getActivity(), Constant.RV_SESSION_ROW_NODATA);
                        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recycler_view.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d(TAG, "onResponse:getsessionE " + t.toString());
                modelList.clear();
            }
        });

    }

    private void toDatePickerDialog() {
        if (getActivity() != null) {

            DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, i);
                    calendar.set(Calendar.MONTH, i1);
                    calendar.set(Calendar.DAY_OF_MONTH, i2);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

                    endYear = yearFormat.format(calendar.getTime());
                    str_toDate = simpleDateFormat.format(calendar.getTime());
                    Log.d("CHECK_DATE", str_toDate);


                    tv_toDate.setText(str_toDate);
                }
            }, year, month, date);

            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dialog.show();
        }

    }

    private void fromDatePickerDialog() {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

                startYear = yearFormat.format(calendar.getTime());
                str_from_date = simpleDateFormat.format(calendar.getTime());

                Log.d("CHECK_DATE", str_from_date);

                tv_fromDate.setText(str_from_date);
            }
        }, year, month, date);

        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }


}
