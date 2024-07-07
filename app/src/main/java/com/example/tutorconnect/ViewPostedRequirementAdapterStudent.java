package com.example.tutorconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewPostedRequirementAdapterStudent extends RecyclerView.Adapter<ViewPostedRequirementAdapterStudent.ViewHolder>{

    ArrayList<PostRequirement> data;

    public ViewPostedRequirementAdapterStudent(Context c, ArrayList<PostRequirement> data)
    {
        this.data=data;
    }


    @NonNull
    @Override
    public ViewPostedRequirementAdapterStudent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post_requirement_design,parent,false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewPostedRequirementAdapterStudent.ViewHolder holder, int position)
    {
        PostRequirement i=data.get(position);
        holder.tvSubjectDetails.setText("Online "+i.getSubject()+" teacher required");
        holder.tvPostDetails.setText(i.getDetails());
        holder.tvLocation.setText(i.getLocation());
        holder.tvGenderPreference.setText("Gender Preference : "+i.getGender());
        holder.tvTime.setText("Time Preference : "+i.getTime());
        holder.tvBudget.setText(i.getBudget()+"$");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvSubjectDetails, tvPostDetails, tvLocation, tvGenderPreference, tvTime, tvBudget;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSubjectDetails=itemView.findViewById(R.id.tvSubjectDetails);
            tvPostDetails=itemView.findViewById(R.id.tvPostDetails);
            tvLocation=itemView.findViewById(R.id.tvLocation);
            tvGenderPreference=itemView.findViewById(R.id.tvGenderPreference);
            tvTime=itemView.findViewById(R.id.tvTime);
            tvBudget=itemView.findViewById(R.id.tvBudget);

        }
    }
}
