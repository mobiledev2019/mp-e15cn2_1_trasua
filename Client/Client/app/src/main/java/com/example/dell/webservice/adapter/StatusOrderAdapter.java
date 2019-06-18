package com.example.dell.webservice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.webservice.R;
import com.example.dell.webservice.model.Addressship;
import com.example.dell.webservice.model.Statusorder;
import com.example.dell.webservice.model.User;

import java.util.List;

public class StatusOrderAdapter extends RecyclerView.Adapter<StatusOrderAdapter.ViewHolder> {
    private User user;
    private List<Statusorder> statusorderList;
    private Context context;
    private LayoutInflater inflater;
    private StoreAdapter.OnItemClickListener onItemClickListener;

    public StatusOrderAdapter(Context context, User user, List<Statusorder> statusorderList) {
        this.context = context;
        this.user = user;
        this.statusorderList = statusorderList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = inflater.inflate(R.layout.statusorder_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Statusorder statusorder = statusorderList.get(position);
        Addressship addressship = statusorder.getOrderId().getAddressShipId();
        String address = addressship.getDescription() + " - " + addressship.getTown() + " - " + addressship.getCity();
        viewHolder.txtNameStore.setText(statusorder.getOrderId().getStore().getName());
        viewHolder.txtAddressShip.setText(address);
        viewHolder.txtStatus.setText(statusorder.getStatus());
        viewHolder.txtDate.setText(statusorder.getOrderId().getDate().substring(4, 16));
        viewHolder.txtNumber.setText(String.valueOf(statusorder.getOrderId().getMilkteaList().size()) + " phần");
        if (statusorder.getStatus().equalsIgnoreCase("Đã hoàn thành")) {
            viewHolder.txtStatus.setTextColor(Color.RED);
            viewHolder.imgStatus.setImageResource(R.drawable.doneorder);
        } else {
            viewHolder.txtStatus.setTextColor(0XFF348118);
            if (statusorder.getStatus().equalsIgnoreCase("Đã đặt đơn"))
                viewHolder.imgStatus.setImageResource(R.drawable.bookingorder);
            if (statusorder.getStatus().equalsIgnoreCase("Đã xác nhận"))
                viewHolder.imgStatus.setImageResource(R.drawable.choiceorder);
            if (statusorder.getStatus().equalsIgnoreCase("Đang giao hàng"))
                viewHolder.imgStatus.setImageResource(R.drawable.trackingorder);
        }

        viewHolder.imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClicked(viewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return statusorderList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLogo;
        private TextView txtNameStore;
        private TextView txtAddressShip;
        private TextView txtStatus;
        private TextView txtDate;
        private TextView txtNumber;
        private ImageView imgStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo1);
            txtNameStore = itemView.findViewById(R.id.txtNameStore);
            txtAddressShip = itemView.findViewById(R.id.txtAddressShip);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            imgStatus = itemView.findViewById(R.id.imgStatus);

        }
    }

    public void setOnItemClickListener(StoreAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }
}
