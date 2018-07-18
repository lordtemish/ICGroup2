package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.dynamica.icgroup.R;

import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<String> list;
    Context context;
    List<View.OnClickListener> listener;
    int imageResource;
    public class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        ConstraintLayout layout;
        public MyHolder(View view){
            super(view);
            textView=(TextView) view.findViewById(R.id.buttonRowTextView);
            textView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNext-Medium.ttf"));
            imageView=(ImageView) view.findViewById(R.id.buttonRowImageView);
            layout=(ConstraintLayout) view.findViewById(R.id.buttonRowLayout);
        }
    }
    public ButtonAdapter(List<String> list, Context context){
        this.list=list;
        this.context=context;
    }
    public ButtonAdapter(List<String> list, Context context, List<View.OnClickListener> listener, int imageResource){
        this.list=list;
        this.context=context;
        this.listener=listener;
        this.imageResource=imageResource;
    }
    @Override
    public  void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
        MyHolder holder=(MyHolder) holder1;
        holder.textView.setText(list.get(position));
        holder.layout.setOnClickListener(listener.get(position));
        holder.imageView.setImageResource(imageResource);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup groupm, int viewType){
        View itemView = LayoutInflater.from(groupm.getContext()).inflate(R.layout.button_row, groupm, false);
        return new ButtonAdapter.MyHolder(itemView);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
