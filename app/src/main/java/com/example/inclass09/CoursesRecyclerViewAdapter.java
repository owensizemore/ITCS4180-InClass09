package com.example.inclass09;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CoursesRecyclerViewAdapter extends RecyclerView.Adapter<CoursesRecyclerViewAdapter.CourseViewHolder> {

     List<Course> courses;

     public CoursesRecyclerViewAdapter(List<Course> courses){
         this.courses = courses;
     }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_row_item, parent, false);
         CourseViewHolder courseViewHolder = new CourseViewHolder(view);
        return courseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courses.get(position);

        holder.courseNumber.setText(course.courseNumber);
        holder.courseName.setText(course.courseName);
        // TODO replace with non-hardcoded string
        holder.creditHours.setText(Integer.toString(course.creditHours) + " " + "Credit Hours");
        holder.courseGrade.setText(String.valueOf(course.courseGrade));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseNumber;
        TextView courseName;
        TextView creditHours;
        TextView courseGrade;
        ImageButton trashIcon;

        public CourseViewHolder(View itemView){
            super(itemView);
            courseNumber = itemView.findViewById(R.id.courseNumberTextView);
            courseName = itemView.findViewById(R.id.courseNameTextView);
            creditHours = itemView.findViewById(R.id.courseCreditHoursTextView);
            courseGrade = itemView.findViewById(R.id.courseGradeTextView);
            trashIcon = itemView.findViewById(R.id.trashIconButton);
        }
    }
}
