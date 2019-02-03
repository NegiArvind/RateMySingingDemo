package com.arvind.ratemysingingdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserRecyclerViewCustomAdapter extends RecyclerView.Adapter<UserRecyclerViewCustomAdapter.UserViewHolder> {

    private ArrayList<User> userArrayList;
    private Context context;

    public UserRecyclerViewCustomAdapter(Context context,ArrayList<User> userArrayList) {
        this.userArrayList=userArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.user_raw_layout,viewGroup,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        userViewHolder.nameTextView.setText(userArrayList.get(i).getUserName());
        userViewHolder.ageTextView.setText(userArrayList.get(i).getUserAge());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        private TextView nameTextView;
        private TextView ageTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView=itemView.findViewById(R.id.userNameTextView);
            ageTextView=itemView.findViewById(R.id.ageTextView);
        }
    }
}
