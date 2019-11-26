package com.deliveryboy.NewFile;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.DivisionModule.AddDivisionActivity;
import in.varadhismartek.patashalaerp.DivisionModule.ClassSectionAndDivisionModel;
import in.varadhismartek.patashalaerp.GeneralClass.CustomSpinnerAdapter;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddClassesFragment extends Fragment implements View.OnClickListener {

    ImageView iv_backBtn, iv_sendAddClasses;
    EditText edit_enter;
    ImageButton add_image;
    RecyclerView recycler_view;
    Spinner spinnerForDivision;
    ArrayList<ClassSectionModel> list;
    ArrayList<ClassSectionAndDivisionModel> classList;
    ArrayList<DivisionClassModel> divisionClassModels = new ArrayList<>();

    ArrayList<String> listDivision;
    ProgressDialog progressDialog;
    APIService mApiService;
    Button buttonAdd;
    String strSpinnerValue;
    public AddClassesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_classes, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        SharedPreferences preferences = getActivity().getSharedPreferences("DIV_CLASS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        initViews(view);
        initListeners();
       // getDivisionApi();

        return view;
    }


    private void initViews(View view) {

        progressDialog = new ProgressDialog(getActivity());
        mApiService = ApiUtils.getAPIService();

        iv_backBtn = view.findViewById(R.id.iv_backBtn);
        spinnerForDivision = view.findViewById(R.id.spinnerForDivision);
        edit_enter = view.findViewById(R.id.edit_enter);
        add_image = view.findViewById(R.id.add_image);
        buttonAdd = view.findViewById(R.id.button_added);
        recycler_view = view.findViewById(R.id.recycler_view);
        iv_sendAddClasses = view.findViewById(R.id.iv_sendAddClasses);

        list = new ArrayList<>();
        listDivision = new ArrayList<>();
        classList = new ArrayList<>();
       // divisionClassModels.clear();
        listDivision.add("Select Division");
        listDivision.add("primary");
        listDivision.add("secondary");
        setSpinnerForDivision();

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setHasFixedSize(true);
    }

    private void initListeners() {
        iv_backBtn.setOnClickListener(this);
        add_image.setOnClickListener(this);
        iv_sendAddClasses.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);

    }

    private void setSpinnerForDivision() {
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), listDivision);
        spinnerForDivision.setAdapter(customSpinnerAdapter);

        spinnerForDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                JSONArray array = new JSONArray();
                JSONObject jsonObject = new JSONObject();

                //  if (position != 0) {
                 strSpinnerValue = parent.getSelectedItem().toString();
                Constant.DIVISION_NAME = strSpinnerValue;
                try {
                    array.put(Constant.wingName);
                    jsonObject.put("wings", array);
                    Constant.DEPART_NAME = Constant.wingName;

                    //checkDepartmentValues(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.add_image:

                String editvalue = edit_enter.getText().toString();
                if (editvalue.equals("")) {
                    Toast.makeText(getContext(), "Please enter the Value", Toast.LENGTH_SHORT).show();
                } else if (spinnerForDivision.getSelectedItemPosition() ==0){
                    Toast.makeText(getContext(), "Please Select Division", Toast.LENGTH_SHORT).show();
                } else {
                    if (classList.size() > 0) {

                        boolean b = true;
                        for (int i = 0; i < classList.size(); i++) {
                            if ((classList.get(i).getName()).contains(editvalue)) {
                                b = false;
                                Toast.makeText(getActivity(), "Already added", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (b) {
                            classList.add(new ClassSectionAndDivisionModel(editvalue, true));

                        }

                    } else {
                        classList.add(new ClassSectionAndDivisionModel(editvalue, true));

                    }
                   // divisionClassModels.add(new DivisionClassModel(strSpinnerValue,classList));

                    ClassSectionAdapter classSectionAdapter = new ClassSectionAdapter(classList
                            , getActivity(), buttonAdd,iv_sendAddClasses,Constant.RV_CLASSES_CARD);
                    recycler_view.setAdapter(classSectionAdapter);
                    edit_enter.setText("");
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                }
                break;
            case R.id.iv_sendAddClasses:
                if (classList.size()>0){
                    AddDivisionActivity addDivisionActivity = (AddDivisionActivity) getActivity();
                    addDivisionActivity.loadFragment(Constant.ADD_SECTION_FRAGMENT, null);
                }else {
                    Toast.makeText(getActivity(), "Please Add Class", Toast.LENGTH_SHORT).show();
                }

                break;


            case R.id.iv_backBtn:
                getActivity().onBackPressed();
                break;
        }

    }

    private void getDivisionApi() {
        mApiService.getDivisions(Constant.SCHOOL_ID).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                listDivision.clear();
                listDivision.add(0, "Select Division");
                boolean statusDivision = false;
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = (String) object.get("status");

                        if (status.equalsIgnoreCase("success")) {
                            JSONArray jsonArray = object.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object1 = jsonArray.getJSONObject(i);

                                String added_datetime = object1.getString("added_datetime");
                                String Id = object1.getString("id");
                                statusDivision = object1.getBoolean("status");
                                if (statusDivision) {
                                    String division = object1.getString("division");
                                    String school_id = object1.getString("school_id");
                                    String added_by_id = object1.getString("added_by_id");

                                    listDivision.add(division);
                                }

                                setSpinnerForDivision();
                            }


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("GET_DIVISION_EXCEPTION", t.getMessage());

            }
        });

    }

}
