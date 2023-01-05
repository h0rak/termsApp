package com.example.termsapp.UI;

import android.content.Context;
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
            holder.courseItemView.setText(name);
        }
        else {
            holder.courseItemView.setText("No Term Name");
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
