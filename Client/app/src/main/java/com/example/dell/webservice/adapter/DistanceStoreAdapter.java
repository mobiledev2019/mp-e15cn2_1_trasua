package com.example.dell.webservice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.webservice.R;
import com.example.dell.webservice.model.DistanceStore;
import com.example.dell.webservice.model.User;

import java.util.List;

public class DistanceStoreAdapter extends RecyclerView.Adapter<DistanceStoreAdapter.ViewHolder> {
    private User user;
    private List<DistanceStore> storeList;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public DistanceStoreAdapter(Context context, User user, List<DistanceStore> storeList) {
        this.context=context;
        this.user = user;
        this.storeList = storeList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.store, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DistanceStore store = storeList.get(position);
        holder.txtName.setText(store.getStore().getName());
        holder.txtAddress.setText(store.getStore().getAddressList().get(0).getDescription());
        holder.txtDistance.setText(String.valueOf(store.getDistance()+" km"));
        holder.imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClicked(holder.getAdapterPosition());

            }
        });
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLogo;
        private TextView txtName;
        private TextView txtAddress;
        private TextView txtDistance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            txtName = itemView.findViewById(R.id.txtName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtDistance=itemView.findViewById(R.id.txtDistance);
        }
    }
    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

}
