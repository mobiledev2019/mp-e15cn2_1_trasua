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

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private OrderItemAdapter.OnItemClickListener onItemClickListener;

    public OrderItemAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.order_item, parent, false);
        return new OrderItemAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLogo;
        private TextView txtName;
        private TextView txtSize;
        private TextView txtTopping;
        private TextView txtPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            txtName = itemView.findViewById(R.id.txtName);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtTopping = itemView.findViewById(R.id.txtTopping);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
    }
    public interface OnItemClickListener {
        void onItemClicked(int position);
    }
}
