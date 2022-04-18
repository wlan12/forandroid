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

public class adminShopListAdapter extends RecyclerView.Adapter<adminShopListAdapter.ViewHolder> {

    private ArrayList<adminData> mAdminData = null;
    String getID;

    adminShopListAdapter(ArrayList<adminData> list){
        mAdminData = list;
    }

    @NonNull
    @Override
    public adminShopListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adminShopListAdapter.ViewHolder holder, int position) {
        holder.onBind(mAdminData.get(position));

        holder.shopList_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdminShopActivity.class);
                intent.putExtra("shopID", getID);
                view.getContext().startActivity(intent);
            }
        });

        holder.shopList_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdminShopActivity.class);
                intent.putExtra("shopID", getID);
                view.getContext().startActivity(intent);
            }
        });

        holder.shopList_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AdminShopActivity.class);
                intent.putExtra("shopID", getID);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAdminData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView shopList_image;
        TextView shopList_name;
        TextView shopList_info;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopList_image = (ImageView) itemView.findViewById(R.id.shopListImage);
            shopList_name = (TextView) itemView.findViewById(R.id.shopListName);
            shopList_info = (TextView) itemView.findViewById(R.id.shopListInfo);
        }

        public void onBind(adminData item) {
            shopList_image.setImageBitmap(item.getbImage());
            shopList_name.setText(item.getName());
            shopList_info.setText(item.getInfo());

            getID = item.getId();
        }
    }
}
