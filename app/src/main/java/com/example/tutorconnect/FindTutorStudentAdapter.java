package com.example.tutorconnect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FindTutorStudentAdapter extends RecyclerView.Adapter<FindTutorStudentAdapter.ViewHolder>{

    ArrayList<TeacherInfo> data;
    Context c;

    public FindTutorStudentAdapter(Context c, ArrayList<TeacherInfo> data)
    {
        this.data=data;
        this.c=c;
    }

    @NonNull
    @Override
    public FindTutorStudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_tutor_design,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FindTutorStudentAdapter.ViewHolder holder, int position) {

        TeacherInfo i=data.get(position);
        holder.tvName.setText(i.getName());
        holder.tvSpeciality.setText(i.getSpeciality());
        holder.tvSubject.setText(i.getSubject());
        holder.tvDesc.setText(i.getProfile());
        holder.tvGender.setText(i.getGender());
        holder.tvLocation.setText(i.getLocation());
        holder.tvFee.setText(i.getFee()+"$");
        holder.tvExperience.setText(i.getExperience()+" years");


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

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName,tvSpeciality,tvSubject,tvDesc,tvGender,tvLocation,tvFee,tvExperience;
        Button btnContact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tvName);
            tvSpeciality=itemView.findViewById(R.id.tvSpeciality);
            tvSubject=itemView.findViewById(R.id.tvSubject);
            tvDesc=itemView.findViewById(R.id.tvDesc);
            tvGender=itemView.findViewById(R.id.tvGender);
            tvLocation=itemView.findViewById(R.id.tvLocation);
            tvFee=itemView.findViewById(R.id.tvFee);
            tvExperience=itemView.findViewById(R.id.tvExperience);
            btnContact=itemView.findViewById(R.id.btnContact);

        }
    }
}
