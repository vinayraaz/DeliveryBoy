package com.deliveryboy.ClassAndSection;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.DivisionModule.ClassSectionAndDivisionModel;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassSectionAdapter extends RecyclerView.Adapter<ClassViewHolder> {
    Context context;
    private ArrayList<ClassSectionAndDivisionModel> arrayList,newarrayList;
    private ArrayList<ClassSectionModel> modelArrayList;
    private ArrayList<String> list;
    private ImageView ivSendClass, iv_sendAddSubmit;
    private ArrayList<String> checkedArrayList;
    private ArrayList<String> uncheckedArrayList;
    private String recyclerTag;
    private Button buttonAdd;
    private ArrayList<DivisionClassModel> divisionClassModels;
    private APIService mApiService;


    public ClassSectionAdapter(ArrayList<ClassSectionAndDivisionModel> arrayListForClasses, Context context,
                               /*Button buttonAdd,*/ ImageView iv_sendAddClasses, String recyclerTag) {
        this.context=context;
        this.arrayList =arrayListForClasses;
      //  this.buttonAdd =buttonAdd;
        this.ivSendClass =iv_sendAddClasses;
        this.recyclerTag =recyclerTag;
        checkedArrayList = new ArrayList<>();
        uncheckedArrayList = new ArrayList<>();

        newarrayList = new ArrayList<>() ;

        mApiService = ApiUtils.getAPIService();

    }

    public ClassSectionAdapter(Context context, ArrayList<ClassSectionModel> modelArrayList, String recyclerTag) {

        this.context = context;
        this.recyclerTag = recyclerTag;
        this.modelArrayList = modelArrayList;

        mApiService = ApiUtils.getAPIService();
    }




    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (recyclerTag){
            case Constant.RV_CLASSES_CARD:
                return new ClassViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.select_module_row_card, parent, false));
            case Constant.RV_SECTION_ROW:
                return new ClassViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.section_item_card, parent, false));
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull final ClassViewHolder holder, final int i) {
        switch (recyclerTag){
            case Constant.RV_CLASSES_CARD:

                holder.check_box.setText(arrayList.get(i).getName());
                boolean flag1 = arrayList.get(i).isChecked();
                if (flag1)
                {
                    holder.check_box.setBackgroundColor(Color.parseColor("#72d548"));
                    holder.check_box.setTextColor(Color.WHITE);
                    checkedArrayList.add(arrayList.get(i).getName());
                    Log.d("AaCheckedArrayList", ""+checkedArrayList.size());

                } else {
                    holder.check_box.setBackgroundColor(Color.WHITE);
                    holder.check_box.setTextColor(Color.BLACK);
                    checkedArrayList.remove(arrayList.get(i).getName());
                    Log.d("AaCheckedArrayList1", ""+checkedArrayList.size());

                }



             /*  buttonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                       *//* String strDiv = gson.toJson(divisionClassModels);
                        SharedPreferences preferences = context.getSharedPreferences("DIV_CLASS", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("DIV_CLASS",strDiv);
                        editor.commit();*//*


                    }
                });*/


              //  holder.check_box.setText(arrayList.get(i).getName());
               // boolean flag1 = arrayList.get(i).isChecked();
                /*holder.check_box.setText(arrayList.get(i).getName());
                boolean flag1 = arrayList.get(i).isChecked();

                if (flag1)
                {
                    holder.check_box.setBackgroundColor(Color.parseColor("#72d548"));
                    holder.check_box.setTextColor(Color.WHITE);
                    checkedArrayList.add(arrayList.get(i).getName());
                    Log.d("AaCheckedArrayList", ""+checkedArrayList.size());

                } else {
                    holder.check_box.setBackgroundColor(Color.WHITE);
                    holder.check_box.setTextColor(Color.BLACK);
                    checkedArrayList.remove(arrayList.get(i).getName());
                    Log.d("AaCheckedArrayList1", ""+checkedArrayList.size());

                }

                holder.check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(!arrayList.get(i).isChecked())
                        {
                            checkedArrayList.add(holder.check_box.getText().toString());
                            arrayList.get(i).setChecked(true);

                            Log.d("Checked", ""+checkedArrayList.size());
                            holder.check_box.setBackgroundColor(Color.parseColor("#72d548"));
                            holder.check_box.setTextColor(Color.WHITE);
                        }
                        else {
                            checkedArrayList.remove(holder.check_box.getText().toString());
                            arrayList.get(i).setChecked(false);
                            Log.d("Unchecked", ""+checkedArrayList.size());
                            holder.check_box.setBackgroundColor(Color.WHITE);
                            holder.check_box.setTextColor(Color.BLACK);
                        }

                    }
                });

                buttonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(uncheckedArrayList.size()>0){
                            uncheckedArrayList.clear();
                        }

                        if(checkedArrayList.size()>0){
                            checkedArrayList.clear();
                        }

                        for (ClassSectionAndDivisionModel module : arrayList)
                        {

                            if (!module.isChecked()) {
                                uncheckedArrayList.add(module.getName());
                            }
                            else  if (module.isChecked()) {

                                checkedArrayList.add(module.getName());
                            }

                        }

                        Log.i("checkedArrayList",""+checkedArrayList.size());

                        JSONObject jsonObject= new JSONObject();

                        JSONArray array = new JSONArray();
                        for (int i = 0; i < checkedArrayList.size(); i++) {
                            array.put(checkedArrayList.get(i));
                        }

                        JSONObject objectClassMain = new JSONObject();
                        JSONObject classObject = new JSONObject();
                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("name", array.get(i));
                            obj.put("status", "true");
                            classObject.put("" + i, obj);
                            objectClassMain.put("classes",classObject);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.i("CALSS",""+objectClassMain);


                        Toast.makeText(context, "Add Successfully", Toast.LENGTH_SHORT).show();

                    }

                });
                ivSendClass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkedArrayList.size()>0) {
                            final AddDivisionActivity addDivisionActivity = (AddDivisionActivity) context;
                            addDivisionActivity.loadFragment(Constant.ADD_SECTION_FRAGMENT, null);
                        }
                    }
                });*/

                break;
            case Constant.RV_SECTION_ROW:

                int size = modelArrayList.get(i).getListSection().size();

                holder.tv_class.setText(modelArrayList.get(i).getClassName());

                StringBuffer sb = new StringBuffer();

                for (int j=0; j<size;j++){

                    sb.append(modelArrayList.get(i).getListSection().get(j)+" ");

                    Log.d("MY_ASCII_adapter", modelArrayList.get(i).getListSection().get(j));

                }

                holder.tv_section.setText(sb.toString());



                holder.linearLayoutSection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(context,"Delete", Toast.LENGTH_SHORT).show();
                        String classValue="";
                        JSONArray array = new JSONArray();
                        for (int p=0;p<modelArrayList.size();p++){
                            classValue = modelArrayList.get(p).getClassName();
                            for (int j =0;j<modelArrayList.get(p).getListSection().size();j++){
                                String strSec= modelArrayList.get(p).getListSection().get(j);
                                System.out.println("strSec***"+strSec);
                                array.put(strSec);

                            }

                        }
                        JSONObject obj = new JSONObject();
                        JSONObject objClass = new JSONObject();
                        try {
                            obj.put("sections", array);
                            obj.put("order", 1);
                            objClass.put(classValue,obj);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        Log.d("OBJECT_ARR", obj.toString());
                        Log.d("OBJECT_ARR", objClass.toString());
                        Log.d("OBJECT_ARR", ""+objClass);
                        Log.d("OBJECT_ARR", Constant.SCHOOL_ID);
                        Log.d("OBJECT_ARR",     Constant.DIVISION_NAME);
                        Log.d("OBJECT_ARR", Constant.EMPLOYEE_BY_ID);

                        mApiService.addClassSections(Constant.SCHOOL_ID,Constant.DIVISION_NAME,objClass,Constant.EMPLOYEE_BY_ID)
                                .enqueue(new Callback<Object>() {
                                    @Override
                                    public void onResponse(Call<Object> call, Response<Object> response) {
                                        Log.i("Ress**",""+response.body());
                                    }

                                    @Override
                                    public void onFailure(Call<Object> call, Throwable t) {

                                    }
                                });

                    }
                });
                break;

        }


    }

    @Override
    public int getItemCount() {
        switch (recyclerTag){
            case Constant.RV_CLASSES_CARD:
                return arrayList.size();
               // return divisionClassModels.size();

            case Constant.RV_SECTION_ROW:
                return modelArrayList.size() ;

        }
        return arrayList.size();

    }
}
