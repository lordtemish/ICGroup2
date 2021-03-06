package com.studio.dynamica.icgroup.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.OrderForm;
import com.studio.dynamica.icgroup.ObjectFragments.InventoryEquipmentInfoFragment;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        LinearLayout wholeLayout;
        Context context;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);
        }
        private void setWholeLayoutListener(final Fragment fragment){
            wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) context).setFragment(R.id.content_frame,fragment);
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.order_row,parent,false);
        return new myHolder(view);
    }
    Context context;
    List<OrderForm> list;
    Fragment fragment;
    public OrderAdapter(List<OrderForm> forms, Fragment fragment){
        list=forms;
        this.fragment=fragment;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder) holder1;
        holder.setWholeLayoutListener(fragment);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
