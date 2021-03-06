package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.CommentForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<CommentForm> list;
    public CommentAdapter(List<CommentForm> list){
        this.list=list;
    }
    class MyHolder extends RecyclerView.ViewHolder{
        TextView sender, senderInfo, date;
        Context context;
        public MyHolder(View view){
            super(view);
            context=view.getContext();
            sender=(TextView) view.findViewById(R.id.senderTextView);
            sender.setTypeface(((MainActivity) context).getTypeFace("regular"));
            senderInfo=(TextView) view.findViewById(R.id.senderInfoTextView);
            senderInfo.setTypeface(((MainActivity) context).getTypeFace("bold"));
            date=(TextView) view.findViewById(R.id.dateTextView);
            date.setTypeface(((MainActivity) context).getTypeFace("bold"));
        }
        private void setInfo(CommentForm form){
            senderInfo.setText(form.getSender());
            date.setText(form.getDate());
        }
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        MyHolder holder=(MyHolder) holder1;
        holder.setInfo(list.get(position));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context=parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row,parent,false);
        return new MyHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
