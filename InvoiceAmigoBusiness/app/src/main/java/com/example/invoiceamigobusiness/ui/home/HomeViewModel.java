package com.example.invoiceamigobusiness.ui.home;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.db.williamchart.data.DataPoint;
import com.db.williamchart.data.Frame;
import com.example.invoiceamigobusiness.Repository;
import com.example.invoiceamigobusiness.databinding.HomeFragmentBinding;
import com.example.invoiceamigobusiness.network.model.Dash;
import com.example.invoiceamigobusiness.network.model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    /**
     * Fill in XML with User, Business and stats asynchronously
     * @param homeFragmentBinding - The databinding for the frontend
     */
    public void fillDash(HomeFragmentBinding homeFragmentBinding){
        //Call to the API to get the User - contains the business
        Single userResponse= Repository.getInstance().executeGetUser();
        //Listen to the response and update UI when User has been filled
        userResponse.subscribe(
                new DisposableSingleObserver<Response<User>>() {
                    @Override
                    public void onSuccess(Response<User> userResponse) {
                        //Pass User to the frontend
                        homeFragmentBinding.setUser(userResponse.body());
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("RossLog","Get User Fail: Error!",e);
                    }
                }
        );

        Single dashResponse = Repository.getInstance().executeGetDash();
        dashResponse.subscribe(
                new DisposableSingleObserver<Response<Dash>>() {
                    @Override
                    public void onSuccess(Response<Dash> dashResponse) {
                        Log.d("Ross","recieved the dash");
                        homeFragmentBinding.setDashStats(dashResponse.body());
                        ArrayList<DataPoint> dataPoints = new ArrayList<>();
                        homeFragmentBinding.barChart.drawBars(dataPoints, new Frame(100,100,100,100));
                    }

                    @Override
                    public void onError(Throwable e)  {
                        Log.d("RossLog","Get User Fail: Error!",e);
                    }
                }
        );
    }
}
