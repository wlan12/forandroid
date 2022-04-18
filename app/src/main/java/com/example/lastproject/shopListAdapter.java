package com.example.lastproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class shopListAdapter extends RecyclerView.Adapter<shopListAdapter.ViewHolder> {

    private ArrayList<listData> mlistData = null;
    String getName;

    shopListAdapter(ArrayList<listData> list){
        mlistData = list;
    }

    @NonNull
    @Override
    public shopListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mlistData.get(position));

        holder.shopList_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShopActivity.class);
                intent.putExtra("shopName", getName);
                view.getContext().startActivity(intent);
            }
        });

        holder.shopList_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShopActivity.class);
                intent.putExtra("shopName", getName);
                view.getContext().startActivity(intent);
            }
        });

        holder.shopList_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShopActivity.class);
                intent.putExtra("shopName", getName);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlistData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView shopList_image;
        TextView shopList_name;
        TextView shopList_info;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopList_image = (ImageView) itemView.findViewById(R.id.shopListImage);
            shopList_name = (TextView) itemView.findViewById(R.id.shopListName);
            shopList_info = (TextView) itemView.findViewById(R.id.shopListInfo);
        }

        void onBind(listData item){
            shopList_image.setImageBitmap(item.getbImage());
            shopList_name.setText(item.getName());
            shopList_info.setText(item.getInfo());

            getName = item.getName();
        }
    }
}
