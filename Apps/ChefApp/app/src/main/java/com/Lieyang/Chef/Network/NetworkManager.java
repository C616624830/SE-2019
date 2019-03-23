package com.Lieyang.Chef.Network;

import com.Lieyang.Chef.Models.completedOrders;
import com.Lieyang.Chef.Interfaces.NetworkResponseListener;
import com.Lieyang.Chef.Models.MenuItem;
import com.Lieyang.Chef.Models.Order;
import com.Lieyang.Chef.Models.currentOrders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class NetworkManager{
    private OkHttpClient httpClient;
    private List<NetworkResponseListener> listeners = new ArrayList();
    private static NetworkManager networkManager = new NetworkManager();
    // things above only called once

    private NetworkManager(){
        httpClient = new OkHttpClient();
    }
    public static NetworkManager getInstance(){
        return networkManager;
    }

    public void addListener(NetworkResponseListener listener){
        if(!listeners.contains(listener))
            listeners.add(listener);
    }

    public void getCurrentOrders(){
        httpClient.newCall(RequestProvider.getCurrentOrdersRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    currentOrders currentorders = new currentOrders(new ArrayList<>());
                    JSONArray jOrders = new JSONArray(response.body().string());
                    for (int i = 0; i < jOrders.length(); i++){
                        try {
                            Order order = new Order();
                            order.id = jOrders.getJSONObject(i).getString("_id");
                            order.datetime = jOrders.getJSONObject(i).getString("datetime");
                            order.userid = jOrders.getJSONObject(i).getString("userid");
                            JSONArray jmenuItems = jOrders.getJSONObject(i).getJSONArray("menuItems");

                            for (int j=0; j<jmenuItems.length(); j++){
                            String dishname = jmenuItems.getJSONObject(j).getString("dishname");
                            String dishprice = jmenuItems.getJSONObject(j).getString("price");
                            order.menuItems.add(new MenuItem(dishname, dishprice));}

                            order.extra = jOrders.getJSONObject(i).getString("extra");
                            order.paid = jOrders.getJSONObject(i).getBoolean("paid");
                            order.payment_method = jOrders.getJSONObject(i).getString("payment_method");
                            order.completed = jOrders.getJSONObject(i).getBoolean("completed");
                            order.served = jOrders.getJSONObject(i).getBoolean("served");


                            if (order.completed == false) {
                                currentorders.orders.add(order);
                            }
                        }
                        catch (Exception e){

                        }
                    }

                    for (NetworkResponseListener listener: listeners) {
                        listener.OnNetworkResponseReceived(RequestType.GET_CURRENTORDERS, currentorders);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getCompletedOrders(){
        httpClient.newCall(RequestProvider.getCompletedOrdersRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    completedOrders completedorders = new completedOrders(new ArrayList<>());
                    JSONArray jOrders = new JSONArray(response.body().string());
                    for (int i = 0; i < jOrders.length(); i++){
                        try {
                            Order order = new Order();
                            order.id = jOrders.getJSONObject(i).getString("_id");
                            order.datetime = jOrders.getJSONObject(i).getString("datetime");
                            order.userid = jOrders.getJSONObject(i).getString("userid");

                            JSONArray jmenuItems = jOrders.getJSONObject(i).getJSONArray("menuItems");

                            for (int j = 0; j < jmenuItems.length(); j++) {
                                String dishname = jmenuItems.getJSONObject(j).getString("dishname");
                                String dishprice = jmenuItems.getJSONObject(j).getString("price");
                                order.menuItems.add(new MenuItem(dishname, dishprice));
                            }

                            order.extra = jOrders.getJSONObject(i).getString("extra");
                            order.paid = jOrders.getJSONObject(i).getBoolean("paid");
                            order.payment_method = jOrders.getJSONObject(i).getString("payment_method");
                            order.completed = jOrders.getJSONObject(i).getBoolean("completed");
                            order.served = jOrders.getJSONObject(i).getBoolean("served");

                            if (order.completed == true) {
                                completedorders.orders.add(order);
                            }
                        } catch (Exception e){

                        }
                    }

                    for (NetworkResponseListener listener: listeners) {
                        listener.OnNetworkResponseReceived(RequestType.GET_COMPLETEDORDERS, completedorders);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void completeOrder(Order order){
        httpClient.newCall(RequestProvider.getCompleteOrderRequest(order.id)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject statusObj = new JSONObject(response.body().string());
                    boolean status = statusObj.getBoolean("success");
                    order.completed = status;

                    for (NetworkResponseListener listener: listeners) {
                        listener.OnNetworkResponseReceived(RequestType.COMLETE_ORDER, order);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
