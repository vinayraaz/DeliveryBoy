package com.deliveryboy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.AddWing.AddWingsModel;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentViewHolder> {
    private String recyclerTag;
    private ArrayList<AssesmentModel> arrayList;
    private ArrayList<AddWingsModel> addExamArrayList;
    private ArrayList<AssesmentModel> addCurricularList;
    private Context mContext;
    private ImageView ivSendExam;
    private ArrayList<String> checkedWingsArrayList;
    private ArrayList<String> uncheckedWingsArrayList;
    APIService apiService;
    Dialog dialog;

    public AssessmentAdapter(ArrayList<AssesmentModel> arrayList, Context mContext, String recyclerTag) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.recyclerTag = recyclerTag;
    }

    public AssessmentAdapter(ArrayList<AddWingsModel> addExamArrayList, Context mContext,
                             ImageView ivSendExam, String recyclerTag) {

        this.addExamArrayList =addExamArrayList;
        this.mContext =mContext;
        this.ivSendExam =ivSendExam;
        this.recyclerTag =recyclerTag;
        checkedWingsArrayList = new ArrayList<>();
        uncheckedWingsArrayList = new ArrayList<>();
        apiService = ApiUtils.getAPIService();
        notifyDataSetChanged();

    }



    public AssessmentAdapter(Context mContext, ArrayList<AssesmentModel> addCurricularList, String recyclerTag) {
        this.mContext =mContext;
        this.addCurricularList =addCurricularList;
        this.recyclerTag =recyclerTag;
        apiService = ApiUtils.getAPIService();

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (recyclerTag) {

            case Constant.RV_ASSESSMENT_GRADE_ROW:
                return new AssessmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grade_row,
                        parent, false));
            case Constant.RV_EXAMS_ROW:
                return new AssessmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.select_module_row_card,
                        parent, false));
            case Constant.RV_CURRICULAR_ROW:
                return new AssessmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grade_row,
                        parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final AssessmentViewHolder holder, final int i) {
        switch (recyclerTag) {

            case Constant.RV_ASSESSMENT_GRADE_ROW:

                holder.tv_grade.setText(arrayList.get(i).getGrade());
                holder.tv_minMarks.setText(arrayList.get(i).getMinMarks().toString());
                holder.tv_maxMarks.setText(arrayList.get(i).getMaxMarks().toString());

                if (i == 0) {
                    holder.ll_gradeRow.setBackgroundResource(R.color.marksRowColor);

                } else if (i % 2 != 0) {
                    holder.ll_gradeRow.setBackgroundResource(R.color.white);

                }

                break;

            case Constant.RV_EXAMS_ROW:
                final AddWingsModel addWingsModel = addExamArrayList.get(i);

                holder.check_box.setText(addExamArrayList.get(i).getWingsName());
                boolean flag = addWingsModel.isWingsSelected();

                if (flag) {
                    holder.check_box.setBackgroundColor(Color.parseColor("#72d548"));
                    holder.check_box.setTextColor(Color.WHITE);

                } else {
                    holder.check_box.setBackgroundColor(Color.WHITE);
                    holder.check_box.setTextColor(Color.BLACK);

                }

                holder.check_box.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String examType = addExamArrayList.get(i).getWingsName();
                        String examTypeStatus = String.valueOf(addExamArrayList.get(i).isWingsSelected());
                        if (examTypeStatus.equalsIgnoreCase("true")){
                            examTypeStatus= "false";
                        }else{
                            examTypeStatus= "true";
                        }

                        JSONObject objectWings = new JSONObject();
                        try {

                            JSONObject exam = new JSONObject();
                            exam.put("name", examType);
                            exam.put("status", examTypeStatus);

                            objectWings.put(String.valueOf(1), exam);

                        } catch (JSONException je) {

                        }

                        Log.i("objectWings**",""+objectWings);

                        apiService.updateExamByStatus(Constant.SCHOOL_ID, objectWings, Constant.EMPLOYEE_BY_ID)
                                .enqueue(new Callback<Object>() {
                                    @Override
                                    public void onResponse(Call<Object> call, Response<Object> response) {
                                        Log.i("RESPONSE**",""+response.body());

                                      /*  Intent intent = new Intent(mContext, AssessmentActivity.class);
                                        intent.putExtra("Fragment_Type","AddExam");
                                        mContext.startActivity(intent);
                                        ((Activity)mContext).finish();*/
                                      notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onFailure(Call<Object> call, Throwable t) {

                                    }
                                });


                    }
                });


                holder.check_box.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        openDialogForUpdate(holder.check_box.getText().toString());
                        notifyDataSetChanged();

                        return false;
                    }
                });

                break;

            case Constant.RV_CURRICULAR_ROW:
                holder.tv_grade.setText(addCurricularList.get(i).getName());
                System.out.println("mmm**"+addCurricularList.get(i).getMarks());
                holder.tv_minMarks.setVisibility(View.GONE);
                holder.tv_maxMarks.setText(addCurricularList.get(i).getMarks());
                if (i == 0) {
                    holder.ll_gradeRow.setBackgroundResource(R.color.marksRowColor);

                } else if (i % 2 != 0) {
                    holder.ll_gradeRow.setBackgroundResource(R.color.white);

                }

                holder.ll_gradeRow.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        openDialogForUpdate(holder.tv_grade.getText().toString(), Long.parseLong(holder.tv_maxMarks.getText().toString()));
                        notifyDataSetChanged();
                        return false;
                    }
                });

                break;
        }
    }

    private void openDialogForUpdate(final String oldNmae, long oldmarks) {
        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.curricular_update_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
        final TextView tvOldActivityName = dialog.findViewById(R.id.tv_oldname);
        final TextView tv_oldmarks = dialog.findViewById(R.id.tv_oldmarks);
        final EditText et_newactivityname = dialog.findViewById(R.id.et_newactivityname);
        final EditText ed_newmarks = dialog.findViewById(R.id.ed_newmarks);

        tvOldActivityName.setText(oldNmae);
        tv_oldmarks.setText(String.valueOf(oldmarks));
        final TextView bt_cancel = dialog.findViewById(R.id.bt_cancel);
        final TextView bt_update = dialog.findViewById(R.id.bt_update);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newName = et_newactivityname.getText().toString();
                final String new_Marks = ed_newmarks.getText().toString();

                if (newName.isEmpty() ||new_Marks.isEmpty() ) {
                    Toast.makeText(mContext, "New Name and New Marks Can Not Be Empty", Toast.LENGTH_SHORT).show();

                } else {
                    apiService.updateCurricular(Constant.SCHOOL_ID,Constant.EMPLOYEE_BY_ID,oldNmae,newName,new_Marks,"true")
                            .enqueue(new Callback<Object>() {
                                @Override
                                public void onResponse(Call<Object> call, Response<Object> response) {
                                    Log.i("RES***",""+response.body());
                                    Log.i("RES***",""+response.code());
                                    JSONObject object = null;

                                    if (response.isSuccessful()) {
                                        Log.d("UPDATE_WINGS_SUCCESS", response.body().toString());
                                        dialog.dismiss();
                                        try {
                                            object = new JSONObject(new Gson().toJson(response.body()));
                                            String status = object.getString("status");

                                            if (status.equalsIgnoreCase("Success")) {
                                                Toast.makeText(mContext, "Activity Name Successfully Update",
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(mContext, AssessmentActivity.class);
                                                intent.putExtra("Fragment_Type","Curricular");
                                                mContext.startActivity(intent);
                                                ((Activity)mContext).finish();


                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }




                                    } else {
                                        Log.d("UPDATE_WINGS_FAIL", String.valueOf(response.code()));
                                        if (String.valueOf(response.code()).equals("409")) {
                                            Toast.makeText(mContext, "Activity name already exists", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                        if (String.valueOf(response.code()).equals("404")) {
                                            Toast.makeText(mContext, "Old Activity name not exists", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }

                                    }
                                }

                                @Override
                                public void onFailure(Call<Object> call, Throwable t) {

                                }
                            });

                }
            }
        });

    }

    private void openDialogForUpdate(final String oldName) {
        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.update_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        final TextView tv_heading = dialog.findViewById(R.id.tv_heading);
        final TextView tv_heading_name = dialog.findViewById(R.id.tv_heading_name);
        final EditText et_new_name = dialog.findViewById(R.id.et_new_name);
        final TextView bt_cancel = dialog.findViewById(R.id.bt_cancel);
        final TextView bt_update = dialog.findViewById(R.id.bt_update);

        tv_heading.setText("Old Exam Type Name");
        tv_heading_name.setText(oldName);
        et_new_name.setHint("New Exam Type Name");


        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String newName = et_new_name.getText().toString();

                if (newName.equals("")) {
                    Toast.makeText(mContext, "New Name Can Not Be Empty", Toast.LENGTH_SHORT).show();

                } else {

                    apiService.updateExameByname(Constant.SCHOOL_ID,
                            oldName, newName, Constant.EMPLOYEE_BY_ID).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {

                            JSONObject object = null;

                            if (response.isSuccessful()) {
                                Log.d("UPDATE_WINGS_SUCCESS", response.body().toString());
                                dialog.dismiss();
                                try {
                                    object = new JSONObject(new Gson().toJson(response.body()));
                                    String status = object.getString("status");

                                    if (status.equalsIgnoreCase("Sucess")) {
                                        Toast.makeText(mContext, "Exam Type Successfully Update",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext, AssessmentActivity.class);
                                        intent.putExtra("Fragment_Type","AddExam");
                                        mContext.startActivity(intent);
                                        ((Activity)mContext).finish();


                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }




                            } else {
                                Log.d("UPDATE_WINGS_FAIL", String.valueOf(response.code()));
                                if (String.valueOf(response.code()).equals("409")) {
                                    Toast.makeText(mContext, "Exam type name already exists", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                if (String.valueOf(response.code()).equals("404")) {
                                    Toast.makeText(mContext, "Old exam type name not exists", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Log.d("UPDATE_WINGS_EX", t.getMessage());

                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        switch (recyclerTag) {

            case Constant.RV_ASSESSMENT_GRADE_ROW:
                return arrayList.size();
            case Constant.RV_EXAMS_ROW:
                return addExamArrayList.size();
            case Constant.RV_CURRICULAR_ROW:
                return addCurricularList.size();
        }

        return 0;
    }

    public void newValues(String s) {
        addExamArrayList.add(new AddWingsModel(s, true));
        notifyDataSetChanged();

    }


}
