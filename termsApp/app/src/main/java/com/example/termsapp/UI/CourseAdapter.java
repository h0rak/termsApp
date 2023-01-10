package com.example.termsapp.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.termsapp.Entity.Course;
import com.example.termsapp.R;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflator;
    public CourseAdapter(Context context){
        mInflator = LayoutInflater.from(context);
        this.context = context;

    }

    class CourseViewHolder extends RecyclerView.ViewHolder{

        private final TextView courseItemView;

        private CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetailAssessmentList.class);
                    intent.putExtra("id", current.getCourseID()); // id name start end status iname iphone iemail oNote termid
                    intent.putExtra("name", current.getCourseName());
                    intent.putExtra("start", current.getCourseStart());
                    intent.putExtra("end", current.getCourseEnd());
                    intent.putExtra("status", current.getCourseStatus());
                    intent.putExtra("iName", current.getInstructorName());
                    intent.putExtra("iPhone", current.getInstructorPhone());
                    intent.putExtra("iEmail", current.getInstructorEmail());
                    intent.putExtra("oNote", current.getOptionalNote());
                    intent.putExtra("tID", current.getTermID());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if (mCourses!=null){
            Course current = mCourses.get(position);
            String name = current.getCourseName();
            int termID = current.getTermID();
            holder.courseItemView.setText(name);
        }
        else {
            holder.courseItemView.setText("No Term Name");
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void setCourses(List<Course> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }

}
