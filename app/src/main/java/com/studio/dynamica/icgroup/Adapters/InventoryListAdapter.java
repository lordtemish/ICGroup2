package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.InventoryFragments.EquipmentMainFragment;
import com.studio.dynamica.icgroup.InventoryFragments.MaterialMainFragment;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class InventoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        ConstraintLayout wholeLayout;
        TextView text;
        private myHolder(View view){
            super(view);
            text=(TextView) view.findViewById(R.id.text);
            wholeLayout=(ConstraintLayout) view.findViewById(R.id.wholeLayout);

            ((MainActivity)context).setType("demibold",text);
        }
    }
    List<String> list;
    Context context;
    public InventoryListAdapter(List<String> strings){
        list=strings;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.inventorylist_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder)holder1;
        holder.text.setText(list.get(position));
        switch (position){
            case 0:
                holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment=new EquipmentMainFragment();
                        Bundle bundle=new Bundle();

                        fragment.setArguments(bundle);
                        ((MainActivity)context).setFragment(R.id.content_frame, fragment);
                    }
                });
                break;
            case 1:
                holder.wholeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment=new MaterialMainFragment();
                        Bundle bundle=new Bundle();

                        fragment.setArguments(bundle);
                        ((MainActivity)context).setFragment(R.id.content_frame, fragment);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
