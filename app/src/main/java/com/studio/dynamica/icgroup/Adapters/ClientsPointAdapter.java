package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.ClientsPointForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class ClientsPointAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        ImageView avatar;
        ConstraintLayout wholeLayout;
        RecyclerView recyclerView;
        RateStarsAdapter adapter;
        private myHolder(View view){
            super(view);
            nameTextView=(TextView)view.findViewById(R.id.nameTextView);
            avatar=(ImageView)view.findViewById(R.id.avatar);
            wholeLayout=(ConstraintLayout)view.findViewById(R.id.wholeLayout);
            recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
            ((MainActivity)context).setRecyclerViewOrientation(recyclerView,LinearLayoutManager.VERTICAL);
            adapter=new RateStarsAdapter(1);recyclerView.setAdapter(adapter);

            ((MainActivity)context).setType("demibold",nameTextView);
        }
        private void setInfo(ClientsPointForm form){
            nameTextView.setText(form.getName());
            adapter.setRate(form.getRate());
            adapter.notifyDataSetChanged();
        }
    }
    Context context;
    List<ClientsPointForm> list;
    public  ClientsPointAdapter(List<ClientsPointForm> forms){
        list=forms;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
         myHolder holder=( myHolder)holder1;
        holder.setInfo(list.get(position));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.clientspoint_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}