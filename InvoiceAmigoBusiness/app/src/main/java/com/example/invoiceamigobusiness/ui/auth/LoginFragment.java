package com.example.invoiceamigobusiness.ui.auth;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.invoiceamigobusiness.HomeActivity;
import com.example.invoiceamigobusiness.R;
import com.example.invoiceamigobusiness.Repository;
import com.example.invoiceamigobusiness.databinding.LoginFragmentBinding;
import com.example.invoiceamigobusiness.network.RetrofitService;

public class LoginFragment extends Fragment implements LoginViewModel.LoginListener{
    private LoginViewModel mViewModel;
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }
    private LoginFragmentBinding loginFragmentBinding;
    private SharedPreferences sharedTokenPref;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Check for stored token
        sharedTokenPref = getActivity().getSharedPreferences("token",Context.MODE_PRIVATE);
        String token = sharedTokenPref.getString("token",null);
        if(token!=null){
            //Add Bearer token to header
            RetrofitService.addAuthToken("Bearer " +token);
            //Rebuild to update intercepters and callback factories
            Repository.getInstance().rebuild();
            onLogin(true,token);
        }

        //Inflate for AndroidX DataBinding
        loginFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false);
        //Create ViewModelProvider
        mViewModel =new ViewModelProvider(this).get(LoginViewModel.class);
        loginFragmentBinding.setLoading(false);

        //Login button listener - pass bindings  to view model to handle/read from UI
        loginFragmentBinding.setOnClickListener(view -> {
            mViewModel.login(loginFragmentBinding, this);
        });

        return loginFragmentBinding.getRoot();
    }


    @Override
    public void onLogin(Boolean success, String token) {
        if(success){
            //Save token to shared prefrences
            SharedPreferences.Editor editor = sharedTokenPref.edit();
            editor.putString("token", token );
            editor.apply();
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            getActivity().startActivity(intent);
        }else{
            loginFragmentBinding.setLoading(false);
        }
    }
}
