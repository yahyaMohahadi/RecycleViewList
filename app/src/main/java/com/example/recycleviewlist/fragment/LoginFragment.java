package com.example.recycleviewlist.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.recycleviewlist.OnlineUser;
import com.example.recycleviewlist.R;
import com.example.recycleviewlist.activity.MainActivity;
import com.example.recycleviewlist.model.User;
import com.example.recycleviewlist.database.user.UserRepository;

enum StateStart {
    LOGIN, SIGNUP
}

public class LoginFragment extends Fragment {

    private Button mButtonState;
    private Button mButtonLogIn;
    private EditText mEditTextUserNameLogin;
    private EditText mEditTextUserPasswordLogin;
    private LinearLayout mLayoutSignUp;
    private LinearLayout mLayoutLogin;
    private StateStart mCurentState = StateStart.LOGIN;

    private Button mButtonSignUp;
    private EditText mEditTextUserNameSignUp;
    private EditText mEditTextUserPasswordSignUp;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findView(view);
        onCklick();
        return view;
    }

    private void onCklick() {
        mButtonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        mButtonState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurentState = mCurentState == StateStart.SIGNUP ? StateStart.LOGIN : StateStart.SIGNUP;
                changeVisibility();

            }
        });
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    //TODO declerate newIntent in mainActivity
    private void login() {
        User user = UserRepository.getInstance(getActivity().getApplicationContext()).isUserExist(
                mEditTextUserNameLogin.getText().toString(),
                mEditTextUserPasswordLogin.getText().toString());
        if (mEditTextUserNameLogin.getText().toString().equals("root") &&
                mEditTextUserPasswordLogin.getText().toString().equals("root")) {
            OnlineUser.newInstance().setOnlineUser(OnlineUser.mUserRoot);
            startActivity(new Intent(LoginFragment.this.getActivity(), MainActivity.class));
            return;
        }
        if (user != null) {
            OnlineUser.newInstance().setOnlineUser(user);
            startActivity(new Intent(LoginFragment.this.getActivity(), MainActivity.class));
            Toast.makeText(getActivity(), "Login successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "user not foud try again or sign up",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void signUp() {
        if (mEditTextUserNameSignUp.getText().toString().equals("") || mEditTextUserPasswordSignUp.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Fill in all the blanks", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User(mEditTextUserNameSignUp.getText().toString(),
                    mEditTextUserPasswordSignUp.getText().toString());
            UserRepository.getInstance(getActivity().getApplicationContext()).addUser(user);
            OnlineUser.newInstance().setOnlineUser(user);
            startActivity(new Intent(LoginFragment.this.getActivity(), MainActivity.class));
            Toast.makeText(getActivity(), "created successfully!", Toast.LENGTH_SHORT).show();

        }
    }

    private void findView(View view) {
        mButtonLogIn = view.findViewById(R.id.button_login);
        mButtonState = view.findViewById(R.id.button_state);
        mEditTextUserNameLogin = view.findViewById(R.id.editText_use_name_login);
        mEditTextUserPasswordLogin = view.findViewById(R.id.editText_password);
        mLayoutSignUp = view.findViewById(R.id.LinearLayout_sign_up);
        mLayoutLogin = view.findViewById(R.id.LinearLayout_login);
        mButtonSignUp = view.findViewById(R.id.button_sign_up_inner);
        mEditTextUserNameSignUp = view.findViewById(R.id.editText_use_name_sign_up);
        mEditTextUserPasswordSignUp = view.findViewById(R.id.editText_password_sign_up);
    }

    private void changeVisibility() {
        switch (mCurentState) {
            case LOGIN: {
                mButtonState.setText("Login");
                mLayoutLogin.setVisibility(View.VISIBLE);
                mLayoutSignUp.setVisibility(View.INVISIBLE);
                break;
            }
            case SIGNUP: {
                mButtonState.setText("Sign up");
                mLayoutLogin.setVisibility(View.INVISIBLE);
                mLayoutSignUp.setVisibility(View.VISIBLE);
                break;
            }
        }
    }
}