package com.example.invoiceamigobusiness.ui.dashboard;

import android.util.Log;

import androidx.lifecycle.ViewModel;


import com.example.invoiceamigobusiness.Repository;
import com.example.invoiceamigobusiness.databinding.DashboardFragmentBinding;
import com.example.invoiceamigobusiness.network.model.Dash;
import com.example.invoiceamigobusiness.network.model.User;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Response;

public class DashboardViewModel extends ViewModel {
    /**
     * Fill in XML with User, Business and stats asynchronously
     * @param dashboardFragmentBinding - The databinding for the frontend
     */
    public void fillDash(DashboardFragmentBinding dashboardFragmentBinding){
        //Call to the API to get the User - contains the business
        Single userResponse= Repository.getInstance().executeGetUser();
        //Listen to the response and update UI when User has been filled
        userResponse.subscribe(
                new DisposableSingleObserver<Response<User>>() {
                    @Override
                    public void onSuccess(Response<User> userResponse) {
                        //Pass User to the frontend
                        dashboardFragmentBinding.setUser(userResponse.body());
                        dashboardFragmentBinding.setLoading(false);

                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("RossLog","Get User Fail: Error!",e);
                    }
                }
        );

        Single dashResponse = Repository.getInstance().executeGetDash();
        dashResponse.subscribe(
                new DisposableSingleObserver<Response<Dash>>() {
                    @Override
                    public void onSuccess(Response<Dash> dashResponse) {
                        dashboardFragmentBinding.setDashStats(dashResponse.body());
                        float[] yData =dashResponse.body().getyData();
                        dashboardFragmentBinding.sparkview.setAdapter(new SparkViewAdapter(yData));
                        //Busness logic for charts
                        int paidVal = dashResponse.body().getPaidCount();
                        int unseenVal = dashResponse.body().getUnseenCount();
                        //Convert Val for graph
                        if(paidVal > unseenVal){
                            unseenVal= (int) Math.ceil((unseenVal/paidVal)*100);
                            paidVal=100;

                        }else{
                            paidVal= paidVal * 100 / unseenVal;
                            unseenVal=100;
                        }
                        dashboardFragmentBinding.progressBar.setProgress(paidVal);
                        dashboardFragmentBinding.progressBar2.setProgress(unseenVal);
                    }

                    @Override
                    public void onError(Throwable e)  {
                        Log.e("RossLog","Get User Fail: Error!",e);
                    }
                }
        );
    }
}
