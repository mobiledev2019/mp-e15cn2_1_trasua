package com.example.dell.webservice.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.webservice.MainActivity;
import com.example.dell.webservice.adapter.MilkTeaAdapter;
import com.example.dell.webservice.R;
import com.example.dell.webservice.model.Addressship;
import com.example.dell.webservice.model.Milktea;
import com.example.dell.webservice.model.Order1;
import com.example.dell.webservice.model.Payment;
import com.example.dell.webservice.model.Store;
import com.example.dell.webservice.model.User;
import com.example.dell.webservice.webservice.Client;
import com.lib.vtcpay.sdk.ICallBackPayment;
import com.lib.vtcpay.sdk.InitModel;
import com.lib.vtcpay.sdk.PaymentModel;
import com.lib.vtcpay.sdk.VTCPaySDK;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmAcitivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "ConfirmActivity";
    private Store store;
    private User user;
    private List<Milktea> milkteaList;
    private TextView txtUser;
    private RecyclerView rvMilkTea;
    private MilkTeaAdapter milkTeaAdapter;
    private Button btnConfirm;
    private EditText edtCity;
    private EditText edtTown;
    private EditText edtDescription;
    private TextView txtPrice;
    private RadioButton rbTienMat;
    private RadioButton rbOnline;
    private int price;

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_confirm);
        reciveData();
        initView();
    }

    private void reciveData() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("USER");
        Log.d(TAG, user.toString());
        store = (Store) intent.getSerializableExtra("STORE");
        milkteaList = (List<Milktea>) intent.getSerializableExtra("MILKTEA");

    }

    private void initView() {
        milkTeaAdapter = new MilkTeaAdapter(this, user, milkteaList);
        milkTeaAdapter.setOnItemClickListener(new MilkTeaAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {

            }
        });
        rvMilkTea = findViewById(R.id.rvMilkTea);
        rvMilkTea.setAdapter(milkTeaAdapter);
        txtUser = findViewById(R.id.txtUser);
        txtUser.setText(store.getName());
        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);
        edtCity = findViewById(R.id.edtCity);
        edtTown = findViewById(R.id.edtTown);
        edtDescription = findViewById(R.id.edtDescription);
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        txtPrice = findViewById(R.id.txtPrice);
        rbTienMat = findViewById(R.id.rbTienMat);
        rbOnline = findViewById(R.id.rbOnline);
        price = 0;
        for (Milktea m : milkteaList) {
            String priceTopping = m.getTopping().getPrice();
            String price1 = m.getTeaId().getPrice();
            if (m.getSize().equalsIgnoreCase("M")) {
                price = price + Integer.parseInt(price1) + Integer.parseInt(priceTopping);
            } else
                price = price + Integer.parseInt(price1) + 7000 + Integer.parseInt(priceTopping);
            ;

        }
        txtPrice.setText(milkteaList.size() + " phần - " + String.valueOf(price) + " VNĐ");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm:

                String city = edtCity.getText().toString();
                if (city.equalsIgnoreCase("")) if (city.equalsIgnoreCase("")) {
                    Toasty.warning(this,"Nhập thành phố",Toasty.LENGTH_SHORT).show();
                    edtCity.requestFocus();
                    return;
                }
                String town = edtTown.getText().toString();
                if (town.equalsIgnoreCase("")) {
                    Toasty.warning(this,"Nhập quận/huyện",Toasty.LENGTH_SHORT).show();
                    edtTown.requestFocus();
                    return;
                }
                String description = edtDescription.getText().toString();
                if (description.equalsIgnoreCase("")) {
                    Toasty.warning(this,"Nhập chi tiết địa chỉ",Toasty.LENGTH_SHORT).show();
                    edtDescription.requestFocus();
                    return;
                }
                Addressship addressship = new Addressship();
                addressship.setId(UUID.randomUUID().toString());
                addressship.setUserId(user);
                addressship.setCity(city);
                addressship.setTown(town);
                addressship.setDescription(description);

                Order1 order = new Order1();
                order.setId(UUID.randomUUID().toString());

                order.setAddressShipId(addressship);
                order.setMilkteaList(milkteaList);

                Payment payment = new Payment();
                payment.setId(UUID.randomUUID().toString());
                if (rbTienMat.isChecked())
                    payment.setType("Tiền mặt");
                else
                    payment.setType("Online");
                payment.setStatus("Đang chờ");

                order.setUser(user);

                order.setPaymentId(payment);
                order.setStore(store);

                Date date = Calendar.getInstance().getTime();
                order.setDate(date.toString());
                if (rbTienMat.isChecked()) {
                    Client.getService().addOrder(order).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.body() == true) {
                                Toasty.success(ConfirmAcitivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                            } else
                                Toasty.error(ConfirmAcitivity.this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("USER", user);
                            intent.setClass(ConfirmAcitivity.this, HomeActivity.class);
                            startActivity(intent);


                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });
                } else {
                    paymentOnline();
                }
                break;
            case R.id.btnBack:
                finish();
                onBackPressed();
                break;
        }
    }

    private void paymentOnline() {

        InitModel initModel = new InitModel();
        initModel.setSandbox(true);//[Required] set enviroment test, default is true
        initModel.setAmount(Long.parseLong(String.valueOf(price))); //[Required] your amount
        initModel.setOrderCode(UUID.randomUUID().toString());//[Required] your order code
        initModel.setAppID(500004538); //[Required] your AppID that registered with VTC
        initModel.setSecretKey("15041997"); //[Required] your secret key that registered with VTC
        initModel.setReceiverAccount("0966869524");//[Required] your account
        initModel.setCurrency(VTCPaySDK.VND);//[Option] set currency, default is VND
        initModel.setDrawableLogoMerchant(R.mipmap.ic_launcher); //[Option] Your logo
        VTCPaySDK.getInstance().setInitModel(initModel); //init model

        VTCPaySDK.getInstance().payment(this,
                new ICallBackPayment() {
                    @Override
                    public void onPaymentSuccess(PaymentModel paymentModel) {
                        Toast.makeText(
                                getBaseContext(),
                                "payment success transctionID "
                                        + paymentModel.getTransactionID(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPaymentError(int errorCode, String errorMessage, String bankName) {
                        Toast.makeText( getBaseContext(),
                                "Payment error " + errorMessage, Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onPaymentCancel() {
                        // TODO Auto-generated method stub
                        Toast.makeText( getBaseContext(), "Payment cancel ",
                                Toast.LENGTH_SHORT).show();
                    }

//					@Override
//					public void onPaymentError(String error) {
//						// TODO Auto-generated method stub
//						Toast.makeText(MainActivity.this,
//								"Payment error " + error, Toast.LENGTH_SHORT)
//								.show();
//					}
                });
    }



}



