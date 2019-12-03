package com.deliveryboy;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AssessmentViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout ll_gradeRow;
    public TextView tv_minMarks, tv_maxMarks, tv_grade;
    CheckBox check_box;
    LinearLayout ll_exam_row;
    TextView tv_unit, tv_division, tv_marks;

    LinearLayout ll_row;
    TextView tv_count, tv_name;

    public AssessmentViewHolder(@NonNull View itemView) {
        super(itemView);

        ll_gradeRow = itemView.findViewById(R.id.ll_gradeRow);
        tv_grade = itemView.findViewById(R.id.tv_grade);
        tv_minMarks = itemView.findViewById(R.id.tv_minMarks);
        tv_maxMarks = itemView.findViewById(R.id.tv_maxMarks);
        check_box=itemView.findViewById(R.id.check_box);



     /*   ll_exam_row = itemView.findViewById(R.id.ll_exam_row);
        tv_unit = itemView.findViewById(R.id.tv_unit);
        tv_marks = itemView.findViewById(R.id.tv_marks);
        tv_division = itemView.findViewById(R.id.tv_division);

        tv_name = itemView.findViewById(R.id.tv_name);
        tv_count = itemView.findViewById(R.id.tv_count);
        ll_row = itemView.findViewById(R.id.ll_row);*/
    }
}
