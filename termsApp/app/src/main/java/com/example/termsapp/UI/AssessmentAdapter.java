package com.example.termsapp.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termsapp.Entity.Assessment;
import com.example.termsapp.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflator;

    public AssessmentAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }

    class AssessmentViewHolder extends RecyclerView.ViewHolder {

        private final TextView assessmentItemView;

        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.putExtra("aID", current.getAssessmentID());
                    intent.putExtra("aName", current.getAssessmentName());
                    intent.putExtra("aType", current.getAssessmentType());
                    intent.putExtra("aStart", current.getAssessmentStart());
                    intent.putExtra("aEnd", current.getAssessmentEnd());
                    intent.putExtra("cID", current.getCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            Assessment current = mAssessments.get(position);
            String name = current.getAssessmentName();
            holder.assessmentItemView.setText(name);
        } else {
            holder.assessmentItemView.setText("No Assessment Name");
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

    public void setAssessments(List<Assessment> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }


}
