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
import com.example.dell.webservice.model.Milktea;
import com.example.dell.webservice.model.User;

import java.util.List;

public class MilkTeaAdapter extends RecyclerView.Adapter<MilkTeaAdapter.ViewHolder> {
    private User user;
    private List<Milktea> milkTeas;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public MilkTeaAdapter(Context context, User user, List<Milktea> milkTeas) {
        this.context = context;
        this.user = user;
        this.milkTeas = milkTeas;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.milktea_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Milktea milkTea = milkTeas.get(position);
        holder.txtName.setText(milkTea.getTeaId().getName());
        holder.txtSize.setText(milkTea.getSize());
        if(milkTea.getTopping()!=null)
            holder.txtTopping.setText(milkTea.getTopping().getName());

        int priceTopping;
        if (milkTea.getTopping() == null)
            priceTopping = 0;
        else
            priceTopping = Integer.parseInt(milkTea.getTopping().getPrice());
        int priceTea = Integer.parseInt(milkTea.getTeaId().getPrice());
        if (milkTea.getSize().equals("L"))
            priceTea = priceTea + 7000;
        holder.txtPrice.setText(String.valueOf(priceTea + priceTopping));

        holder.imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return milkTeas.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLogo;
        private TextView txtName;
        private TextView txtPrice;
        private TextView txtSize;
        private TextView txtTopping;

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
