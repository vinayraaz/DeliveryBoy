package com.deliveryboy.sport;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.AddWing.AddWingsModel;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentViewHolder> {
    private String recyclerTag;
    private ArrayList<AssesmentModel> arrayList;
    private ArrayList<AddWingsModel> addExamArrayList;
    private ArrayList<AddWingsModel> addSportsModel;
    private Context mContext;
    private ImageView ivSendExam;
    private ArrayList<String> checkedWingsArrayList;
    private ArrayList<String> uncheckedWingsArrayList;
    private ArrayList<String> listSubject;
    ArrayList<AssesmentModel> assesmentModels;
    APIService apiService;
    Dialog dialog;
    ArrayList<SectionSubjectModel> sectionSubjectModelsNew;

    String strCode, strSec, strDiv, strClass, strSub, strSubType, subStatus;
    String strStatus,strSports;

    public AssessmentAdapter(ArrayList<AssesmentModel> arrayList, Context mContext, String recyclerTag) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.recyclerTag = recyclerTag;
        apiService = ApiUtils.getAPIService();
    }

    public AssessmentAdapter(ArrayList<AddWingsModel> addExamArrayList, Context mContext,
                             ImageView ivSendExam, String recyclerTag) {

        this.addExamArrayList = addExamArrayList;
        this.mContext = mContext;
        this.ivSendExam = ivSendExam;
        this.recyclerTag = recyclerTag;
        checkedWingsArrayList = new ArrayList<>();
        uncheckedWingsArrayList = new ArrayList<>();
        apiService = ApiUtils.getAPIService();
        notifyDataSetChanged();

    }

    public AssessmentAdapter(Context mContext, ArrayList<AssesmentModel> assesmentModels, String recyclerTag) {
        this.mContext = mContext;
        this.assesmentModels = assesmentModels;
        this.recyclerTag = recyclerTag;
        apiService = ApiUtils.getAPIService();

    }


    public AssessmentAdapter(ArrayList<SectionSubjectModel> sectionSubjectModelsNew, String recyclerTag, Context mContext) {
        this.sectionSubjectModelsNew = sectionSubjectModelsNew;
        this.recyclerTag = recyclerTag;
        this.mContext = mContext;
        apiService = ApiUtils.getAPIService();
        notifyDataSetChanged();

    }

    public AssessmentAdapter(Context mContext, String recyclerTag, ArrayList<AddWingsModel> addSportsModel) {
        this.mContext = mContext;
        this.recyclerTag = recyclerTag;
        this.addSportsModel = addSportsModel;
        apiService = ApiUtils.getAPIService();

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

            case Constant.RV_EXAMS_BARRIER_ROW:
                return new AssessmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_exam_barriers_list,
                        parent, false));

            case Constant.RV_SUBJECT_ROW:
                return new AssessmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.select_module_row_card,
                        parent, false));
            case Constant.RV_ASSESSMENT_SPORTS_ROW:
                return new AssessmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.select_module_row_card,
                        parent, false));


        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final AssessmentViewHolder holder, final int i) {
        switch (recyclerTag) {
            case Constant.RV_ASSESSMENT_SPORTS_ROW:
                holder.check_box.setText(addSportsModel.get(i).getWingsName());
                boolean flagNew = addSportsModel.get(i).isWingsSelected();

                if (flagNew) {
                    holder.check_box.setBackgroundColor(Color.parseColor("#72d548"));
                    holder.check_box.setTextColor(Color.WHITE);

                } else {
                    holder.check_box.setBackgroundColor(Color.WHITE);
                    holder.check_box.setTextColor(Color.BLACK);

                }

                holder.check_box.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                         strSports = holder.check_box.getText().toString();
                        String status= String.valueOf(addSportsModel.get(i).isWingsSelected());
                        if (status.equalsIgnoreCase("true")){
                             strStatus ="false";
                        }else {
                             strStatus ="true";
                        }
                        JSONObject objectSecdtion = new JSONObject();
                        JSONObject object = new JSONObject();
                        try{
                            //data:{"1":{"sports_name":"kabbadi","status":"false"},"2":{"sports_name":"VolleyBall","status":"false"}}


                            object.put("sports_name",strSports);
                            object.put("status",strStatus);
                            objectSecdtion.put("1",object);
                        }catch (JSONException je){

                        }

                        apiService.updateSportsByStatus(Constant.SCHOOL_ID,objectSecdtion,Constant.EMPLOYEE_BY_ID)
                                .enqueue(new Callback<Object>() {
                                    @Override
                                    public void onResponse(Call<Object> call, Response<Object> response) {
                                        System.out.println("objectSecdtion***"+response.body());
                                        if (response.isSuccessful()){
                                            Intent intent = new Intent(mContext, AssessmentActivity.class);
                                            intent.putExtra("Fragment_Type","Sports");
                                            mContext.startActivity(intent);
                                            ((Activity)mContext).finish();
                                        }
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
                        openDialogForUpdateSport(holder.check_box.getText().toString());
                        notifyDataSetChanged();

                        return false;
                    }
                });


                break;


            case Constant.RV_SUBJECT_ROW:

                holder.check_box.setText(sectionSubjectModelsNew.get(i).getSubjectName());
                holder.check_box.setBackgroundColor(Color.parseColor("#72d548"));
                holder.check_box.setTextColor(Color.WHITE);

                holder.check_box.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        System.out.println("code**" + sectionSubjectModelsNew.get(i).getSubCode());

                        strCode = sectionSubjectModelsNew.get(i).getSubCode();
                        strSec = sectionSubjectModelsNew.get(i).getSectionName();
                        strDiv = sectionSubjectModelsNew.get(i).getDivisionName();
                        strClass = sectionSubjectModelsNew.get(i).getClassName();
                        strSub = sectionSubjectModelsNew.get(i).getSubjectName();
                        strSubType = sectionSubjectModelsNew.get(i).getSubType();

                        String strStatus = String.valueOf(sectionSubjectModelsNew.get(i).isIsselect());
                        if (strStatus.equalsIgnoreCase("true")) {
                            subStatus = "false";
                        } else {
                            subStatus = "true";
                        }
                        openDialogForSubjectUpdate(strCode, strSec, strDiv, strClass, strSub, strSubType, subStatus);
                        return false;
                    }
                });


                break;

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
                        if (examTypeStatus.equalsIgnoreCase("true")) {
                            examTypeStatus = "false";
                        } else {
                            examTypeStatus = "true";
                        }

                        JSONObject objectWings = new JSONObject();
                        try {

                            JSONObject exam = new JSONObject();
                            exam.put("name", examType);
                            exam.put("status", examTypeStatus);

                            objectWings.put(String.valueOf(1), exam);

                        } catch (JSONException je) {

                        }

                        Log.i("objectWings**", "" + objectWings);

                        apiService.updateExamByStatus(Constant.SCHOOL_ID, objectWings, Constant.EMPLOYEE_BY_ID)
                                .enqueue(new Callback<Object>() {
                                    @Override
                                    public void onResponse(Call<Object> call, Response<Object> response) {
                                        Log.i("RESPONSE**", "" + response.body());

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


            case Constant.RV_EXAMS_BARRIER_ROW:
                holder.tvExamType.setText("Exam Type : " + assesmentModels.get(i).getExamType());
                holder.tvDivision.setText("Division : " + assesmentModels.get(i).getDivisionNmae());
                holder.tvClass.setText("Class : " + assesmentModels.get(i).getClassName());
                holder.tvSubject.setText("Subject : " + assesmentModels.get(i).getSubjectName());
                holder.tvMin.setText("Min Marks : " + String.valueOf(assesmentModels.get(i).getMinMarks()));
                holder.tvMax.setText("Max Marks : " + String.valueOf(assesmentModels.get(i).getMaxMarks()));
                holder.tvDuration.setText("Exam Duration : " + assesmentModels.get(i).getExamDuration() + " min ");

                break;
        }
    }

    private void openDialogForUpdateSport(final String oldname) {
        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.sport_update_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        final TextView oldName = dialog.findViewById(R.id.tv_heading_name);
        final EditText et_new_name = dialog.findViewById(R.id.et_new_name);
        final TextView bt_cancel = dialog.findViewById(R.id.bt_cancel);
        final TextView bt_update = dialog.findViewById(R.id.bt_update);

        oldName.setText(oldname);
        et_new_name.setHint("New Sports Name");

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_new_name.getText().toString().isEmpty()) {
                    Toast.makeText(mContext, "New Name Can Not Be Empty", Toast.LENGTH_SHORT).show();

                } else {

                    apiService.updateSportsByName(Constant.SCHOOL_ID,Constant.EMPLOYEE_BY_ID,
                            oldName.getText().toString(),et_new_name.getText().toString())
                            .enqueue(new Callback<Object>() {
                                @Override
                                public void onResponse(Call<Object> call, Response<Object> response) {
                                    if (response.isSuccessful()){
                                        Log.i("res**",""+response.body());
                                        dialog.dismiss();
                                        Toast.makeText(mContext, "Sports have update successfully", Toast.LENGTH_SHORT);
                                        Intent intent = new Intent(mContext, AssessmentActivity.class);
                                        intent.putExtra("Fragment_Type", "Sports");
                                        mContext.startActivity(intent);
                                        ((Activity) mContext).finish();

                                    }
                                }

                                @Override
                                public void onFailure(Call<Object> call, Throwable t) {
                                    Log.i("res**tt",""+t.toString());

                                }
                            });

                }
            }
        });

    }


    private void openDialogForSubjectUpdate(String strCode, String strSec, final String strDiv,
                                            final String strClass, String strSub, final String strSubType, final String subStatus) {

        dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.subject_update_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
        final TextView subName = dialog.findViewById(R.id.tvOldSub);
        final EditText newSub = dialog.findViewById(R.id.ed_newsub);
        final EditText edCode = dialog.findViewById(R.id.subcode);
        final EditText subType = dialog.findViewById(R.id.ed_subtype);

        final TextView bt_cancel = dialog.findViewById(R.id.bt_cancel);
        final TextView bt_update = dialog.findViewById(R.id.bt_update);
        subName.setText(strSub);
        newSub.setHint("New Subject Name");
        edCode.setText(strCode);
        subType.setText(strSubType);
        final JSONArray jsonArray = new JSONArray();
        jsonArray.put(strSec);

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newSub.equals("")) {
                    Toast.makeText(mContext, "New Name Can Not Be Empty", Toast.LENGTH_SHORT).show();

                } else {


                    System.out.println("jsonArray****" + jsonArray);
                    apiService.updateSubject(Constant.SCHOOL_ID, strDiv, Constant.EMPLOYEE_BY_ID, strClass,
                            jsonArray, subName.getText().toString(), newSub.getText().toString(),
                            edCode.getText().toString(), subType.getText().toString(), subStatus)
                            .enqueue(new Callback<Object>() {
                                @Override
                                public void onResponse(Call<Object> call, Response<Object> response) {

                                    if (response.isSuccessful()) {
                                        dialog.dismiss();
                                        Toast.makeText(mContext, "Subject havse update successfully", Toast.LENGTH_SHORT);
                                        Intent intent = new Intent(mContext, AssessmentActivity.class);
                                        intent.putExtra("Fragment_Type", "Subject");
                                        mContext.startActivity(intent);
                                        ((Activity) mContext).finish();
                                    }


                                }

                                @Override
                                public void onFailure(Call<Object> call, Throwable t) {
                                    dialog.dismiss();
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
        final TextView et_new_name = dialog.findViewById(R.id.et_new_name);
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

                                try {
                                    object = new JSONObject(new Gson().toJson(response.body()));
                                    String status = object.getString("status");

                                    if (status.equalsIgnoreCase("Sucess")) {
                                        Toast.makeText(mContext, "Exam Type Successfully Update",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext, AssessmentActivity.class);
                                        intent.putExtra("Fragment_Type", "AddExam");
                                        mContext.startActivity(intent);
                                        ((Activity) mContext).finish();


                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                dialog.dismiss();

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

            case Constant.RV_EXAMS_BARRIER_ROW:
                return assesmentModels.size();
            case Constant.RV_SUBJECT_ROW:
                return sectionSubjectModelsNew.size();
            case Constant.RV_ASSESSMENT_SPORTS_ROW:
                return addSportsModel.size();
        }

        return 0;
    }

    public void newValues(String s) {
        addExamArrayList.add(new AddWingsModel(s, true));
        notifyDataSetChanged();

    }
}
