package com.example.tutorconnect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewPostedRequirementAdapterTeacher extends RecyclerView.Adapter<ViewPostedRequirementAdapterTeacher.ViewHolder>{

    ArrayList<PostRequirement> data;
    Context c;

    public ViewPostedRequirementAdapterTeacher(Context c, ArrayList<PostRequirement> data)
    {
        this.data=data;
        this.c=c;
    }

    @NonNull
    @Override
    public ViewPostedRequirementAdapterTeacher.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_posted_requirement_design,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPostedRequirementAdapterTeacher.ViewHolder holder, int position) {

        PostRequirement i=data.get(position);
        holder.tvSubjectDetails.setText("Online "+i.getSubject()+" teacher required");
        holder.tvPostDetails.setText(i.getDetails());
        holder.tvLocation.setText(i.getLocation());
        holder.tvGenderPreference.setText("Gender Preference : "+i.getGender());
        holder.tvTime.setText("Time Preference : "+i.getTime());
        holder.tvBudget.setText(i.getBudget()+"$");

        holder.btnContact.setOnClickListener(v->
        {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{i.getEmail()});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Your subject here...");
            intent.putExtra(Intent.EXTRA_TEXT,"Your message here...");
            c.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvSubjectDetails, tvPostDetails, tvLocation, tvGenderPreference, tvTime, tvBudget;
        Button btnContact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSubjectDetails=itemView.findViewById(R.id.tvSubjectDetails);
            tvPostDetails=itemView.findViewById(R.id.tvPostDetails);
            tvLocation=itemView.findViewById(R.id.tvLocation);
            tvGenderPreference=itemView.findViewById(R.id.tvGenderPreference);
            tvTime=itemView.findViewById(R.id.tvTime);
            tvBudget=itemView.findViewById(R.id.tvBudget);
            btnContact=itemView.findViewById(R.id.btnContact);
        }
    }
}
