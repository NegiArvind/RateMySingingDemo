package com.arvind.ratemysingingdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private UserRecyclerViewCustomAdapter userRecyclerViewCustomAdapter;
    private ArrayList<User> userArrayList;
    private ArrayList<String> userKeyArrayList;
    private Button addUserButton;
    private EditText userNameEditText;
    private EditText userAgeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        addUserButton=findViewById(R.id.addNewUserButton);

        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        userArrayList=new ArrayList<>();
        userKeyArrayList=new ArrayList<>();

        fetchAllUsers();
        userRecyclerViewCustomAdapter=new UserRecyclerViewCustomAdapter(this,userArrayList);
        recyclerView.setAdapter(userRecyclerViewCustomAdapter);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view=layoutInflater.inflate(R.layout.add_new_user_layout,null,false);
        userNameEditText=view.findViewById(R.id.newUserNameEditText);
        userAgeEditText=view.findViewById(R.id.newUserAgeEditText);
        alertDialog.setView(view);
        alertDialog.setTitle("ADD New User")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User user=new User();
                        user.setUserName(userNameEditText.getText().toString());
                        user.setUserAge(userAgeEditText.getText().toString());
                        FirebaseDatabase.getInstance().getReference("Users").push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"Added Successfully",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                })
                .setNegativeButton("NO",null);
        alertDialog.show();


    }

    private void fetchAllUsers() {
        FirebaseDatabase.getInstance().getReference("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user=dataSnapshot.getValue(User.class);
                userArrayList.add(user);
                userKeyArrayList.add(dataSnapshot.getKey());
                if(userRecyclerViewCustomAdapter!=null){
                    userRecyclerViewCustomAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                int i=userKeyArrayList.indexOf(dataSnapshot.getKey());
                if(i>=0){
                    userArrayList.remove(i);
                    userKeyArrayList.remove(i);
                    userRecyclerViewCustomAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
