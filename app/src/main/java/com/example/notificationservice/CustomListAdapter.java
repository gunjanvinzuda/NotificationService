package com.example.notificationservice;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ViewHolder> {

    private Context context;
    private List<Model> modelList;
    public CustomListAdapter(Context context, List<Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public CustomListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomListAdapter.ViewHolder holder, int position) {
        Model model = modelList.get(position);
        holder.text.setText(model.getText());

        if(!model.getTitle().isEmpty()) holder.title.setText(model.getTitle());
        else holder.title.setVisibility(View.GONE);

        if(!model.getText().isEmpty()) holder.text.setText(model.getText());
        else holder.text.setVisibility(View.GONE);

        int leftId = 0;
        for(String a: model.getActions().split("\\|")){
            if(!a.isEmpty()){
                leftId = addButton(holder.itemView, holder.text.getId(), leftId, a);
            }
        }
    }

    private int addButton(View itemView, int topId, int leftId, String text) {
        Button button = new Button(context);
        button.setId(View.generateViewId());
        button.setText(text.trim());
        button.setAllCaps(false);
        button.setTextSize(11);
        button.setPadding(0,1,0,1);
        button.setBackgroundResource(R.drawable.action_button);

        int width = text.length() < 6 ? ViewGroup.LayoutParams.WRAP_CONTENT : text.length()*20;
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(width, 53);
        layoutParams.setMarginStart(10);
        layoutParams.topToBottom = topId;
        if(leftId!=0) layoutParams.leftToRight = leftId;

        button.setLayoutParams(layoutParams);
        ConstraintLayout constraintLayout = itemView.findViewById(R.id.actionButtonLayout);
        constraintLayout.addView(button);

        return button.getId();
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text, title;
        ImageView arrow;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            title = itemView.findViewById(R.id.title);
            arrow = itemView.findViewById(R.id.arrow);
            constraintLayout = itemView.findViewById(R.id.actionButtonLayout);
            int height = text.getLayoutParams().height;

            arrow.setOnClickListener(view -> {
                Model model = modelList.get(getAdapterPosition());
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) text.getLayoutParams();
                if(model.getLongView()){
                    model.setLongView(false);
                    params.height = height;
                    arrow.setImageResource(R.drawable.arrow_drop_down_24);
                    constraintLayout.setVisibility(View.GONE);
                }else{
                    model.setLongView(true);
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    arrow.setImageResource(R.drawable.arrow_drop_up_24);
                    constraintLayout.setVisibility(View.VISIBLE);
                }
                text.setLayoutParams(params);
            });
        }
    }
}
