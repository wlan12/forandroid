package com.example.lastproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adminMenuListAdapter extends RecyclerView.Adapter<adminMenuListAdapter.ViewHolder> {

    private ArrayList<adminData> mAdminData = null;
    String getID;

    adminMenuListAdapter(ArrayList<adminData> list){
        mAdminData = list;
    }

    @NonNull
    @Override
    public adminMenuListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adminMenuListAdapter.ViewHolder holder, int position) {
        holder.onBind(mAdminData.get(position));

        holder.menuList_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdminMenuActivity.class);
                intent.putExtra("menuID", getID);
                view.getContext().startActivity(intent);
            }
        });
        holder.menuList_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdminMenuActivity.class);
                intent.putExtra("menuID", getID);
                view.getContext().startActivity(intent);
            }
        });
        holder.menuList_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdminMenuActivity.class);
                intent.putExtra("menuID", getID);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAdminData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView menuList_name;
        TextView menuList_info;
        ImageView menuList_image;

        public ViewHolder(View itemView) {
            super(itemView);
            menuList_name = (TextView)itemView.findViewById(R.id.menuListName);
            menuList_info = (TextView)itemView.findViewById(R.id.menuListInfo);
            menuList_image = (ImageView)itemView.findViewById(R.id.menuListImage);
        }

        public void onBind(adminData item) {
            menuList_name.setText(item.getName());
            menuList_info.setText(item.getInfo());
            menuList_image.setImageBitmap(item.getbImage());

            getID = item.getId();
        }
    }
}
