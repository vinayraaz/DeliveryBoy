package com.deliveryboy.NewFile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import in.varadhismartek.patashalaerp.R;

public class ClassViewHolder extends RecyclerView.ViewHolder {
    public CheckBox check_box;
    public TextView text_view, tv_section, tv_class;
    public ClassViewHolder(View itemView) {
        super(itemView);

        check_box  = itemView.findViewById(R.id.check_box);
      //  text_view  = itemView.findViewById(R.id.text_view);

        tv_class   = itemView.findViewById(R.id.tv_class);
        tv_section = itemView.findViewById(R.id.tv_section);

    }
}
