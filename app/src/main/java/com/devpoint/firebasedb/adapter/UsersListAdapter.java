package com.devpoint.firebasedb.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devpoint.firebasedb.R;
import com.devpoint.firebasedb.model.UsersListModel;

import java.util.ArrayList;


public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.MyViewHolder> {

    private ArrayList<UsersListModel> list;
    private int resource;

    private CallBackForSinglePost callBack;
    private String TAG = UsersListAdapter.class.getSimpleName();

    public UsersListAdapter(  int resource, ArrayList<UsersListModel> list, CallBackForSinglePost callback) {

        this.resource = resource;
        this.list = list;
        this.callBack = callback;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        UsersListModel item = list.get(position);
        holder.type.setText(item.getName());

        if(item.isOnline()){
            holder.onlineStatusOffline.setVisibility(View.GONE);
            holder.onlineStatusOnline.setVisibility(View.VISIBLE);
        }else{
            holder.onlineStatusOffline.setVisibility(View.VISIBLE);
            holder.onlineStatusOnline.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateNewList(ArrayList<UsersListModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public interface CallBackForSinglePost {
        void onClick(int position);
        void onClick(UsersListModel item);

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        // ImageView image;
        TextView type;
        View onlineStatusOnline,onlineStatusOffline;

        MyViewHolder(View view) {
            super(view);

            type = view.findViewById(R.id.postDescription);
            onlineStatusOnline = view.findViewById(R.id.onlineStatusOnline);
            onlineStatusOffline = view.findViewById(R.id.onlineStatusOffline);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    callBack.onClick(list.get(getAdapterPosition()));
                }
            });

        }
    }

}