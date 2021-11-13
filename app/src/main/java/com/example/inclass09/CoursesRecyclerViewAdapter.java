package com.example.inclass09;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class CoursesRecyclerViewAdapter extends RecyclerView.Adapter<CoursesRecyclerViewAdapter.CourseViewHolder> {

     List<Course> courses;

     public CoursesRecyclerViewAdapter(List<Course> courses, ICoursesRecyclerViewAdapterInterface mListener){
         this.courses = courses;
         this.mListener = mListener;
     }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_row_item, parent, false);
         CourseViewHolder courseViewHolder = new CourseViewHolder(view, mListener);
        return courseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Course course = courses.get(position);

        holder.courseNumber.setText(course.courseNumber);
        holder.courseName.setText(course.courseName);
        holder.creditHours.setText(Integer.toString(course.creditHours) + " " + holder.itemView.getContext().getString(R.string.credit_hours));
        holder.courseGrade.setText(String.valueOf(course.courseGrade));

        AppDatabase db = Room.databaseBuilder(holder.itemView.getContext(), AppDatabase.class, "course.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();


        holder.trashIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle(view.getContext().getString(R.string.delete_course))
                        .setMessage(view.getContext().getString(R.string.are_you_sure_course))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mListener.deleteCourse(course, position);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

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

        public CourseViewHolder(View itemView, ICoursesRecyclerViewAdapterInterface mListener){
            super(itemView);
            courseNumber = itemView.findViewById(R.id.courseNumberTextView);
            courseName = itemView.findViewById(R.id.courseNameTextView);
            creditHours = itemView.findViewById(R.id.courseCreditHoursTextView);
            courseGrade = itemView.findViewById(R.id.courseGradeTextView);
            trashIcon = itemView.findViewById(R.id.trashIconButton);
        }
    }

    ICoursesRecyclerViewAdapterInterface mListener;

    public interface ICoursesRecyclerViewAdapterInterface {
         void deleteCourse(Course course, int position);
    }
}
