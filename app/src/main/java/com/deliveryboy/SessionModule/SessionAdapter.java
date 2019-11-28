package com.deliveryboy.SessionModule;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class SessionAdapter extends RecyclerView.Adapter<SessionViewHolder> {

    private Context mContext;
    private ArrayList<String> sessionList;
    private ArrayList<Integer> integerArrayList;
    private String recyclerTag;
    List<SessionModel> modelList = new ArrayList<>();
    Button bt_submit;

    int rowNumber = 1;

    private int date, month, year;
    private String currentDate;
    APIService apiService;

    public SessionAdapter(ArrayList<String> sessionList, Context mContext, String recyclerTag, Button bt_submit) {

        this.mContext = mContext;
        this.sessionList = sessionList;
        this.recyclerTag = recyclerTag;
        this.bt_submit = bt_submit;
    }

    public SessionAdapter(Context mContext, ArrayList<Integer> integerArrayList, String recyclerTag) {

        this.mContext = mContext;
        this.integerArrayList = integerArrayList;
        this.recyclerTag = recyclerTag;

        Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = simpleDate.format(calendar.getTime());

    }

    public SessionAdapter(List<SessionModel> modelList, Context mContext, String recyclerTag) {
        this.modelList = modelList;
        this.mContext = mContext;
        this.recyclerTag = recyclerTag;
        apiService = ApiUtils.getAPIServicePatashala();

    }

    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        switch (recyclerTag) {

            case Constant.RV_SESSION_ROW:
                return new SessionViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.nested_session_date_row, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final SessionViewHolder holder, final int position) {

        switch (recyclerTag) {

            case Constant.RV_SESSION_ROW:
                holder.tv_toDate.setText(modelList.get(position).getTo_date());
                holder.tv_fromDate.setText(modelList.get(position).getFrom_date());
                holder.rl_fromDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePickerDialog dialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, i);
                                calendar.set(Calendar.MONTH, i1);
                                calendar.set(Calendar.DAY_OF_MONTH, i2);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                String str_from_date = simpleDateFormat.format(calendar.getTime());
                                holder.tv_fromDate.setText(str_from_date);

                            }
                        }, date, month, year);

                        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                        dialog.show();
                    }
                });

                holder.rl_toDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePickerDialog dialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, i);
                                calendar.set(Calendar.MONTH, i1);
                                calendar.set(Calendar.DAY_OF_MONTH, i2);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                String str_to_date = simpleDateFormat.format(calendar.getTime());
                                holder.tv_toDate.setText(str_to_date);

                            }
                        }, date, month, year);

                        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                        dialog.show();

                    }
                });
                holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(mContext,"Click"+position, Toast.LENGTH_SHORT).show();
                        String StartDate, EndDate, fromDate, toDate, serialNo, addempid;
                        StartDate = modelList.get(position).getStartDate();
                        EndDate = modelList.get(position).getEndDate();
                        fromDate = holder.tv_fromDate.getText().toString();
                        toDate = modelList.get(position).getTo_date();
                        serialNo = modelList.get(position).getSession_serial_no();
                        addempid = modelList.get(position).getAdded_employeeid();

                        deleteSession(StartDate, EndDate, fromDate, toDate, serialNo, addempid);
                        return false;
                    }
                });
                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
        }

    }

    private void deleteSession(String startDate, String endDate, String fromDate, String toDate, String serialNo, String addempid) {
        Log.d(TAG, "onResponse: DeleteSession" + startDate);
        Log.d(TAG, "onResponse: DeleteSession" + endDate);
        Log.d(TAG, "onResponse: DeleteSession" + fromDate);
        Log.d(TAG, "onResponse: DeleteSession" + toDate);
        Log.d(TAG, "onResponse: DeleteSession" + String.valueOf(serialNo));
        Log.d(TAG, "onResponse: DeleteSession" + addempid);
        Log.d(TAG, "onResponse: DeleteSession" + Constant.SCHOOL_ID);
        Log.d(TAG, "onResponse: DeleteSession" + Constant.EMPLOYEE_BY_ID);
/*school_id:02ee9c74-8d86-4526-9b05-ed40e0094e5f
academic_start_date:01-01-2019
academic_end_date:10-12-2019
session_serial_no:1
added_employeeid:23f13643-b9af-48ec-aab6-71a79525e015
session_from_date:02-01-2019 00:00
session_to_date:08-12-2019 00:00*/
        apiService.deleteSessions(Constant.SCHOOL_ID, startDate, endDate,"2", Constant.EMPLOYEE_BY_ID,
                fromDate, toDate)
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.d(TAG, "onResponse: DeleteSession" + response.body());
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        switch (recyclerTag) {

            case Constant.RV_SESSION_ROW:
                return modelList.size();
            // return integerArrayList.size();

        }
        return 0;
    }
}
